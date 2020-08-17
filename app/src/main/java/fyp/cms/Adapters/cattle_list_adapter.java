package fyp.cms.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;

import fyp.cms.Firebase_Operations.firebase_operations;
import fyp.cms.Models.Animal;
import fyp.cms.Models.dbhelper;
import fyp.cms.Models.user;
import fyp.cms.R;
import fyp.cms.Utils.utils;

public class cattle_list_adapter extends RecyclerView.Adapter<cattle_list_adapter.cattle_list_viewholder> {

    public cattle_list_adapter(Context context, ArrayList<Animal> animals, ArrayList<String> animalIds,Fragment fragment) {
        this.context = context;
        this.animals = animals;
        this.animalIds = animalIds;
        this.fragment=fragment;
        prefs= PreferenceManager.getDefaultSharedPreferences(context);
        u=new Gson().fromJson(prefs.getString("user_info",null),user.class);
    }
   SharedPreferences prefs;
    Context context;
ArrayList<Animal> animals;
ArrayList<String> animalIds;
Fragment fragment;
user u;
    @NonNull
    @Override
    public cattle_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new cattle_list_viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull cattle_list_viewholder holder, int position) {
          if(u!=null&&u.getRole().equals("Seller")||prefs.getString("user_role",null)!=null){
           holder.btnOrder.setText("Remove");
          }
          holder.productName.setText(animals.get(position).getName()+" "+"("+animals.get(position).getGender()+")");
          holder.productPrice.setText("Rs "+animals.get(position).getPrice());
          holder.productWeight.setText(animals.get(position).getWeight());
          holder.image.setImageBitmap(utils.getBitmapFromBytes(animals.get(position).getImage().toBytes()));
          holder.btnOrder.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(u!=null&&u.getRole().equals("Seller")){
                      AlertDialog.Builder deleteAnimalDialog =new AlertDialog.Builder(context);
                      deleteAnimalDialog.setTitle("Delete Animal");
                      deleteAnimalDialog.setMessage("Are You Sure to Delete this animal it will not be visible to Buyers");
                      deleteAnimalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              firebase_operations.deleteAnimalsSeller(context,animalIds.get(position));
                          }
                      });
                      deleteAnimalDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                          }
                      });
                      deleteAnimalDialog.show();
                  }else if(u!=null&&u.getRole().equals("Buyer")){
                      View quantityPickerView=LayoutInflater.from(context).inflate(R.layout.quantity_picker,null);
                      final NumberPicker quantityPicker=quantityPickerView.findViewById(R.id.selectquantity);
                      quantityPicker.setMaxValue(animals.get(position).getQuantity());
                      quantityPicker.setMinValue(1);
                      android.app.AlertDialog quantitypicker=new android.app.AlertDialog.Builder(context)
                              .setTitle("Select Quantity")
                              // .setMessage("In Stock "+String.valueOf(bottles.get(position).getQuantity()))
                              .setView(quantityPickerView)
                              .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      if(quantityPicker.getValue()>animals.get(position).getQuantity()){
                                          Toast.makeText(context,"Not Enough Available",Toast.LENGTH_LONG).show();
                                      }else{
                                          int alreadyExist= new dbhelper(context).check_if_already_exist(FirebaseAuth.getInstance().getCurrentUser().getUid(),animalIds.get(position));
                                          if(alreadyExist>0){
                                              Toast.makeText(context,"Animal Already Exists",Toast.LENGTH_LONG).show();
                                              int isUpdated=new dbhelper(context).updateProduct(animalIds.get(position),animals.get(position).getPrice()*quantityPicker.getValue(),quantityPicker.getValue(),FirebaseAuth.getInstance().getCurrentUser().getUid());
                                              if(isUpdated>0){
                                                  Toast.makeText(context,"Animal is Updated",Toast.LENGTH_LONG).show();
                                              }
                                          }else{
                                              if(new dbhelper(context).insert_product_toshoppingcart(animals.get(position).getName(),animals.get(position).getPrice()*quantityPicker.getValue(),animals.get(position).getImage().toBytes(),quantityPicker.getValue(), FirebaseAuth.getInstance().getCurrentUser().getUid(),animalIds.get(position),animals.get(position).getWeight(),animals.get(position).getGender(),animals.get(position).getSellerId(),animals.get(position).getSellerAccountNo())){
                                                  Toast.makeText(context,"Animal Added to the Cart",Toast.LENGTH_LONG).show();
                                              }else{
                                                  Toast.makeText(context,"Animal not Added to the Cart due to some error",Toast.LENGTH_LONG).show();
                                              }
                                          }
                                      }
                                  }
                              }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      dialog.dismiss();
                                  }
                              }).create();
                      quantitypicker.show();
                  }else if(prefs.getString("user_role",null)!=null){
                      AlertDialog.Builder deleteAnimalDialog =new AlertDialog.Builder(context);
                      deleteAnimalDialog.setTitle("Delete Animal");
                      deleteAnimalDialog.setMessage("Are You Sure to Delete this animal it will not be visible to Buyers");
                      deleteAnimalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              firebase_operations.deleteAnimalsAdmin(context,animalIds.get(position),fragment);
                          }
                      });
                      deleteAnimalDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                          }
                      });
                      deleteAnimalDialog.show();
                  }
              }
          });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    class cattle_list_viewholder extends RecyclerView.ViewHolder{
        TextView productName,productPrice,productWeight;
        Button btnOrder;
        ImageView image;
        CardView productCard;
        public cattle_list_viewholder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.textViewName);
            productPrice=itemView.findViewById(R.id.textViewPrice);
            productWeight=itemView.findViewById(R.id.textViewQuantity);
            productCard=itemView.findViewById(R.id.productCard);
            btnOrder=itemView.findViewById(R.id.btnOrder);
            image=itemView.findViewById(R.id.imageView);
        }
    }
}
