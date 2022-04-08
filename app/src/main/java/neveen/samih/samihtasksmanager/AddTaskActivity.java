package neveen.samih.samihtasksmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import MyData.MyTask;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText txtAge,txtBook;
    private Button btnSave;
    private RatingBar rb4;
    private Spinner spnrCat,spnrLang;
    private TextView txtLang,txtCat;
    String[] cats = { "Romance", "Horror",
            "Fiction" };




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
        txtAge=findViewById(R.id.txtAge);
        txtCat=findViewById(R.id.txtCat);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        ArrayAdapter adCats
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cats);
        spnrCat.setAdapter(adCats);

    }

    private void validate() {
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
            MyTask myTask = new MyTask();
            myTask.setTitle(txtBook.getText().toString());

            //myTask.setImportant(importance);

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            myTask.setOwner(uid);
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference ref= db.getReference();
            String key=ref.child("my tasks").push().getKey();
            myTask.setKey(key);
            ref.child("my task").child(uid).child(key).setValue(myTask).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()){


                    Toast.makeText(getApplicationContext(), "add successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "add not successful", Toast.LENGTH_SHORT).show();
                }
                }

            });
        }



    }
}