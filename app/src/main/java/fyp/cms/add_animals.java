package fyp.cms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.Blob;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import fyp.cms.Firebase_Operations.firebase_operations;
import fyp.cms.Utils.utils;

public class add_animals extends AppCompatActivity {
 EditText animalName,animalPrice,animalAge,animalQuantity,animalWeight;
 Button addBtn;
 Spinner animalGender,animalTeeth;
 ImageView animalImage;
 Bitmap bitmap;
    Uri image_project_uri;
    String resultUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animals);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        animalName=findViewById(R.id.productnametxt);
        animalPrice=findViewById(R.id.pricetxt);
        animalAge=findViewById(R.id.animal_age);
        animalQuantity=findViewById(R.id.productquantitytxt);
        animalTeeth=findViewById(R.id.numOfTeeth);
        animalGender=findViewById(R.id.gender);
        animalWeight=findViewById(R.id.productweighttxt);
        addBtn=findViewById(R.id.upload_btn);
        animalImage=findViewById(R.id.productimg);
        animalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(add_animals.this);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalName.getText().toString().isEmpty()){
                    animalName.setError("Provide Animal Name");
                }else if(animalGender.getSelectedItemPosition()==0){
                    Toast.makeText(add_animals.this,"Select Animal gender",Toast.LENGTH_LONG).show();
                }else if(animalTeeth.getSelectedItemPosition()==0){
                    Toast.makeText(add_animals.this,"Select Animal Number of Teeth",Toast.LENGTH_LONG).show();
                }else if(animalPrice.getText().toString().isEmpty()||Integer.parseInt(animalPrice.getText().toString())==0||Integer.parseInt(animalPrice.getText().toString())<50000){
                    animalPrice.setError("Price too Low");
                }else if(animalAge.getText().toString().isEmpty()){
                    animalAge.setError("Enter Animal Age");
                }else if(bitmap==null){
                    Toast.makeText(add_animals.this,"Select Animal Picture",Toast.LENGTH_LONG).show();
                }else if(animalQuantity.getText().toString().isEmpty()||Integer.parseInt(animalQuantity.getText().toString())==0){
                    animalQuantity.setError("Animal Quantity too Low");
                }else{
                    if(utils.isNetworkAvailable(add_animals.this)){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        bitmap.recycle();
                        firebase_operations.addAnimal(add_animals.this,animalName.getText().toString(),animalWeight.getText().toString(),animalPrice.getText().toString(),animalQuantity.getText().toString(),Blob.fromBytes(byteArray),animalGender.getSelectedItem().toString(),animalTeeth.getSelectedItem().toString(),animalAge.getText().toString());
                    }else {
                        Toast.makeText(add_animals.this,"Network Not Available",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            resultUri = result.getUri().getPath();
            image_project_uri = result.getUri();
            bitmap = BitmapFactory.decodeFile(resultUri);
            animalImage.setImageBitmap(bitmap);
        }
    }
}
