package neveen.samih.samihtasksmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etEmail,etPassword, etRePassword,etName;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etRePassword=findViewById(R.id.etRePassword);
        etName=findViewById(R.id.etName);
        btnSave=findViewById(R.id.btnRegisterActivity);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });    }

    private void validate() {
        boolean isOk=true;
        String email = etEmail.getText().toString();
        String pass1 = etPassword.getText().toString();
        String pass2 = etRePassword.getText().toString();
        String name = etName.getText().toString();
        if (email.length()<5||email.indexOf('@')<=0)
        {
            etEmail.setError("wrong email syntax");
            isOk=false;
        }
        if (pass1.length()<8)
        {
            etPassword.setError("at least 8 chars");
            isOk=false;
        }
        if (pass1.equals(pass2)==false)
        {
            etRePassword.setError("not equal passwords");
            isOk=false;
        }
        if (pass1.length()<8)
        {
            etName.setError("must enter full name");
            isOk=false;
        }

        if(isOk)//isOk==true;
        {
            createAccount(email,pass1);
        }
    }

    private void createAccount(String email, String pass1) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        //request                                         //wait for response
        auth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()==true)
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainTasksActivity.class));
                }
                else {
                    //dialog
                    Toast.makeText(getApplicationContext(), "error create account"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}