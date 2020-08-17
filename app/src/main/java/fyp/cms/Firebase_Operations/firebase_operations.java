package fyp.cms.Firebase_Operations;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fyp.cms.Adapters.cattle_list_adapter;
import fyp.cms.Adapters.orders_list_adapter;
import fyp.cms.Adapters.user_list_adapter;
import fyp.cms.Admin_Home;
import fyp.cms.Models.Animal;
import fyp.cms.Models.Orders;
import fyp.cms.Models.dbhelper;
import fyp.cms.Models.user;
import fyp.cms.buyer_home_page;
import fyp.cms.sellar_home_page;

public class firebase_operations {
    public static void SignIn(String email, String password, final Context context, AlertDialog loginDialog){
        final ProgressDialog pd=new ProgressDialog(context);
        final SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        pd.setMessage("Authenticating User...");
        pd.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals("qcCyrpcYEeQj8qi4Xqr0wTuNued2")){
                        prefs.edit().putString("user_role","Admin").apply();
                        Toast.makeText(context,"Login Sucess",Toast.LENGTH_LONG).show();
                        loginDialog.dismiss();
                        context.startActivity(new Intent(context, Admin_Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        ((AppCompatActivity)context).finish();
                    }else{
                        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                pd.dismiss();
                                if(documentSnapshot.exists()){
                                    user u=documentSnapshot.toObject(user.class);
                                    prefs.edit().putString("user_info",new Gson().toJson(u)).apply();
                                    Toast.makeText(context,"Login Sucess",Toast.LENGTH_LONG).show();
                                    loginDialog.dismiss();
                                    if(u.getRole().equals("Seller")){
                                        context.startActivity(new Intent(context, sellar_home_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        ((AppCompatActivity)context).finish();
                                    }else if(u.getRole().equals("Buyer")){
                                        context.startActivity(new Intent(context, buyer_home_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        ((AppCompatActivity)context).finish();
                                    }
                                }else{
                                    Toast.makeText(context,"No User Data Exist",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                loginDialog.dismiss();
                                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                loginDialog.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void ForgotPassword(String email, final Context context,AlertDialog forgotPasswordDialog){
        final ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Reseting Password...");
        pd.show();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                forgotPasswordDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Password Reset Mail is Sent",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                forgotPasswordDialog.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void Register(final String name, final String email, final String password, final String phone, final String role, final Context context,AlertDialog registerDialog,String accountNo){
        final ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Registering User...");
        pd.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user u=new user(name,email,phone,password,role,accountNo);
                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            registerDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(context,"User Registered Sucessfully",Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            registerDialog.dismiss();
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                registerDialog.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void addAnimal(Context context, String animalName, String animalWeight, int animalPrice, int animalQuantity, Blob image, String gender, int age){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Adding Animal");
        pd.show();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
        user u=new Gson().fromJson(prefs.getString("user_info",null),user.class);
        FirebaseFirestore.getInstance().collection("Animals").document().set(new Animal(animalName,animalPrice,age,animalQuantity,gender,animalWeight,image,FirebaseAuth.getInstance().getCurrentUser().getUid(),u.getAccountNo())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Animal Added Sucessfully",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,sellar_home_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((AppCompatActivity)context).finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getAnimalsForSeller(Context context, RecyclerView animalList){
        ArrayList<Animal> animals=new ArrayList<>();
        ArrayList<String> animalId=new ArrayList<>();
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Animals of Seller...");
        pd.show();
        FirebaseFirestore.getInstance().collection("Animals").whereEqualTo("sellerId",FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        animals.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Animal.class));
                        animalId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    animalList.setAdapter(new cattle_list_adapter(context,animals,animalId,null));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getAnimalsForBuyers(Context context, RecyclerView animalList){
        ArrayList<Animal> animals=new ArrayList<>();
        ArrayList<String> animalId=new ArrayList<>();
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Animals....");
        pd.show();
        FirebaseFirestore.getInstance().collection("Animals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        animals.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Animal.class));
                        animalId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    animalList.setAdapter(new cattle_list_adapter(context,animals,animalId,null));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void deleteAnimalsSeller(Context context,String AnimalId){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Deleting Animal...");
        pd.show();
        FirebaseFirestore.getInstance().collection("Animals").document(AnimalId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Animal Deleted Sucessfully",Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(context,sellar_home_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((AppCompatActivity)context).finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void deleteAnimalsAdmin(Context context,String AnimalId,Fragment fragment){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Deleting Animal...");
        pd.show();
        FirebaseFirestore.getInstance().collection("Animals").document(AnimalId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Animal Deleted Sucessfully",Toast.LENGTH_LONG).show();
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getAllUsers(Context context,RecyclerView usersList,Fragment fragment){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching User List");
        pd.show();
        ArrayList<user> users=new ArrayList<>();
        ArrayList<String> userId=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        users.add(queryDocumentSnapshots.getDocuments().get(i).toObject(user.class));
                        userId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    usersList.setAdapter(new user_list_adapter(users,userId,context,fragment));
                }else{
                    Toast.makeText(context,"No Users Found",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void deleteUsers(Context context, String userId, Fragment fragment){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Deleting User");
        pd.show();
        FirebaseFirestore.getInstance().collection("Users").document(userId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"User Deleted Sucessfully",Toast.LENGTH_LONG).show();
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getAnimalsForAdmin(Context context, RecyclerView animalList,Fragment fragment){
        ArrayList<Animal> animals=new ArrayList<>();
        ArrayList<String> animalId=new ArrayList<>();
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Animals....");
        pd.show();
        FirebaseFirestore.getInstance().collection("Animals").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        animals.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Animal.class));
                        animalId.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    animalList.setAdapter(new cattle_list_adapter(context,animals,animalId,fragment));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void placeOrders(Context context, Orders order){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Placing Order....");
        pd.show();
        FirebaseFirestore.getInstance().collection("Orders").document().set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Order is Placed",Toast.LENGTH_LONG).show();
                    for(int i=0;i<order.getCartItems().size();i++){
                        decrementQuantity(context,order.getCartItems().get(i).getQuantity(),order.getCartItems().get(i).getProductid());
                    }
                    new dbhelper(context).delete_all(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    context.startActivity(new Intent(context,buyer_home_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    ((FragmentActivity)context).finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             pd.dismiss();
             Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void decrementQuantity(Context context,int quantity,String productId){
        FirebaseFirestore.getInstance().collection("Animals").document(productId).update("quantity", FieldValue.increment(-quantity)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getOrdersAdmin(Context context,RecyclerView OrdersList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Orders....");
        pd.show();
        ArrayList<Orders> orders=new ArrayList<>();
        ArrayList<String> orderIds=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        orders.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Orders.class));
                        orderIds.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    OrdersList.setAdapter(new orders_list_adapter(orders,orderIds,context));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              pd.dismiss();
              Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getOrdersSeller(Context context,RecyclerView OrdersList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Orders....");
        pd.show();
        ArrayList<Orders> orders=new ArrayList<>();
        ArrayList<String> orderIds=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Orders").whereArrayContains("sellerId",FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        orders.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Orders.class));
                        orderIds.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    OrdersList.setAdapter(new orders_list_adapter(orders,orderIds,context));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void getOrdersBuyer(Context context,RecyclerView OrdersList){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Fetching Orders....");
        pd.show();
        ArrayList<Orders> orders=new ArrayList<>();
        ArrayList<String> orderIds=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("customerId",FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pd.dismiss();
                if(queryDocumentSnapshots.getDocuments().size()>0){
                    for(int i=0;i<queryDocumentSnapshots.getDocuments().size();i++){
                        orders.add(queryDocumentSnapshots.getDocuments().get(i).toObject(Orders.class));
                        orderIds.add(queryDocumentSnapshots.getDocuments().get(i).getId());
                    }
                    OrdersList.setAdapter(new orders_list_adapter(orders,orderIds,context));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void processPayment(Context context,String orderId){
        ProgressDialog pd=new ProgressDialog(context);
        pd.setMessage("Procesing Payment....");
        pd.show();
        Map<String,Object> statusUpdate=new HashMap<>();
        statusUpdate.put("status","Payment Processed");
        FirebaseFirestore.getInstance().collection("Orders").document(orderId).update(statusUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(context,"Payment Processed",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
