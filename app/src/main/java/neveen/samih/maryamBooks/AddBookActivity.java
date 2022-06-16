package neveen.samih.maryamBooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import MyData.MyBook;

public class AddBookActivity<etDueDate> extends AppCompatActivity {

    private TextInputEditText txtAuthor,txtBook;
    private Button btnSave;
    private RatingBar rb4;
    private Spinner spnrCat,spnrLang;
    private TextView txtLang,txtCat;
    private ImageButton imgBtn;
    private Button btnUpload;
    private TextView tvImgUrl;
    private Uri filePath;
    private Uri toUploadimageUri;
    private Uri downladuri;
    StorageTask uploadTask;

    private MyBook book= new MyBook();
    private static final int PERMISSION_CODE = 101;
    private static final int IMAGE_PICK_CODE = 100;


    String[] cats = { "Romance", "Horror",
            "Fantasy","Adventure", "Drama","Poetry", "Short Story"
    };
    String[] Langs = { "English", "Arabic",
            "Hebrew"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ToggleButton button;
        txtLang=findViewById(R.id.txtLang);
        txtBook=findViewById(R.id.txtBook);
        btnSave=findViewById(R.id.btnSave);
        rb4=findViewById(R.id.rb4);
        spnrCat=findViewById(R.id.spnrCat);
        spnrLang=findViewById(R.id.spnrLang);
        txtAuthor=findViewById(R.id.txtAuthor);
        txtCat=findViewById(R.id.txtCat);
        imgBtn =findViewById(R.id.imgBtn);
        btnUpload=findViewById(R.id.btnUpload);

        ArrayAdapter adCats
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cats);
        spnrCat.setAdapter(adCats);
        adCats.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spnrCat.setAdapter(adCats);

        ArrayAdapter adLangs
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                Langs);
        spnrLang.setAdapter(adCats);
        adLangs.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        spnrLang.setAdapter(adLangs);
     //    etDueDate=findViewById(R.id.etDueDate);
   //     btnDatePicker=findViewById(R.id.btnDatePicker);
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                validate();
            }
        });
        //upload: 4
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                Toast.makeText(getApplicationContext(), "image", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }

                }
            }
        });
       btnUpload.setOnClickListener(new View.OnClickListener() {
           @Override
         public void onClick(View v) {
               uploadImage(toUploadimageUri);
               btnSave.setEnabled(false);
           }
        });




   //     btnDatePicker.setOnClickListener(new View.OnClickListener() {
      //  @Override
  //         public void onClick(View view) {
 //     }
   //    });
    }
    private void uploadImage(Uri filePath) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            FirebaseStorage storage= FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            uploadTask=ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    downladuri = task.getResult();
                                    book.setImage(downladuri.toString());
                                    btnSave.setEnabled(true);

                                  //  createBook(book);

                                }
                            });
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
                         } else
                      {
                        book.setImage("");

                       }
                        }

//    @Override
 //  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  //      super.onActivityResult(requestCode, resultCode, data);
   //     if (requestCode == 99 && resultCode == RESULT_OK
 //               && data != null && data.getData() != null) {
       //     filePath = data.getData();
 //           try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
 //               imgBtn.setImageBitmap(bitmap);
  //          } catch (IOException e) {
//                e.printStackTrace();
 //           }
 //       }
//    }







    private void validate()
        {
        String Title=txtBook.getText().toString();
        String stCat=spnrCat.getSelectedItem().toString();
        String stLang=spnrLang.getSelectedItem().toString();
        boolean isOk=true;
        if (Title.length()==8)
        {
            txtBook.setError("must enter the book`s content");
            isOk=false;
        }
        if (isOk) {
            book.setTitle(txtBook.getText().toString());

            //myBook.setImportant(importance);

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            book.setOwner(uid);
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference ref= db.getReference();
            String key=ref.child("MyBooks").push().getKey();
            book.setKey(key);
            ref.child("MyBooks").child(key).setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()){


                    Toast.makeText(getApplicationContext(), "add successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "add isnt successful", Toast.LENGTH_SHORT).show();
                }
                }

            });
        }



    }

    private void pickImageFromGallery(){
        //intent to pick image
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked images
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK && requestCode== IMAGE_PICK_CODE){
            //set image to image view
            toUploadimageUri = data.getData();
            imgBtn.setImageURI(toUploadimageUri);
        }
    }

}