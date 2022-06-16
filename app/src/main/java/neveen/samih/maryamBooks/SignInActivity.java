package neveen.samih.maryamBooks;

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

public class SignInActivity extends AppCompatActivity {
  ///1.
    private TextInputEditText etEmail,etPassword;
    private Button btnSignIn,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //2.
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });



    }
    private void validate(){
        boolean isOk=true;
        String email=etEmail.getText().toString();
        String passw=etPassword.getText().toString();
        if (email.length()==0)
        {
            etEmail.setError("enter email");
            isOk=false;
        }
        if (passw.length()<8)
        {
            etPassword.setError("pass word at least 8 letters");
            isOk=false;
        }
        if(isOk){
            signingIn(email,passw);
        }

    }
    private void signingIn(String email,String passw){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "signing in successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MyBooksActivity.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "failed to sign in"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}