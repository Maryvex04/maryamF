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
        //This segment checks if the email you entered is long enough to have access,if the email`s length is shorter than what's required an alert will show up to inform the user that the inputs are not valid.
        //The same goes with the password, the segment checks if the password you  entered is valid according to the requirements.
        //All the info you enter will be stored in the firebase.
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
                //According to the inputs you have entered, this segment checks if these inputs are valid, the user will receive a message that his login is successful.
                //Otherwise the user will receive a message that his log in failed.
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