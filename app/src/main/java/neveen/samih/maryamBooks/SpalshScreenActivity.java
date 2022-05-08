package neveen.samih.maryamBooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class SpalshScreenActivity extends AppCompatActivity {
    private ImageView imv1;
    private ImageView imv2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        //Thread: 1
        Thread th=new Thread(){
            //Thread: 2
            @Override
            public void run() {
                //هنا المقطع الذي سيعمل بالتزامن مع مقاطع اخرى
                //Thread:3
                int ms=3*1000;//milliseconds
                try {
                    sleep(ms);
                    finish();
                    //فحص هل تم الدخول مسبقا
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    if (auth.getCurrentUser()!=null)
                        startActivity(new Intent(getApplicationContext(),MainTasksActivity.class));
                    else
                    startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //Thread: 4
        th.start();


    }
}