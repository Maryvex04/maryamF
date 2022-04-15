package neveen.samih.samihtasksmanager;
//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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import MyData.MyTask;
import MyData.MyTaskAdapter;

// listener 1.
public class MainTasksActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private FloatingActionButton floatingActionButton;
    //read1
    private SearchView svSearchTask;
    private ListView lstvAllTasks;
    private MyTaskAdapter taskAdapter;
    private Button Bookl;

///lkjhhkhjk
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bookl = findViewById(R.id.Bookl);
        //read2
        lstvAllTasks = findViewById(R.id.lstvAllTasks);
        taskAdapter = new MyTaskAdapter(this, R.layout.task_item_layout);
        //read3 ,set adapter to listview(connect the data to list view)
        lstvAllTasks.setAdapter(taskAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
            }
        });
    //read4





        svSearchTask=findViewById(R.id.svSearchTask); }
        protected void onResume(){
        super.onResume();

        readDataFromFireBase("");
        }
        //read5

    /**
     * read tasks from fire base and fill the adapterdata structure
     * S- is text to speech
     * @param
     */

    private void readDataFromFireBase(String s) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        String uid=FirebaseAuth.getInstance().getUid();//current user id

        // اضافة امكانية "التحتلن"بكل تغيير سيحصل على القيم في قاعدة البيانات
        ref.child("mytasks").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskAdapter.clear();
                for (DataSnapshot d:dataSnapshot.getChildren()
                     ) {
                    MyTask t=d.getValue(MyTask.class);
                    taskAdapter.add(t);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //1. build menu xml
    //2. to add menu (3 point right-top corner) to the current activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                                 //the xml menu file
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    //3. select item event handler
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnitmSettings)
        {
//            Intent i=new Intent(getApplicationContext(),SettingsActivity.class);
//            startActivity(i);
        }
        if(item.getItemId()==R.id.mnitemHistory)
        {
            Intent i=new Intent(getApplicationContext(),HistoryActivity.class);
            startActivity(i);
        }
        if(item.getItemId()==R.id.mnitmSignOut)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?");
            builder.setCancelable(true);//
                                             //listener 3.  add listener to the buttons
            builder.setPositiveButton("Yes",this);
            builder.setNegativeButton("No",this);
            AlertDialog dialog = builder.create();//
            dialog.show();

        }
        return true;
    }

    //listener 2. implemnet method

     /**
      * event handler
      * @param dialogInterface the active dialog
      * @param which the button id which cause the event
      */
    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        ////listener 4. react for each action
        if(which==dialogInterface.BUTTON_POSITIVE)
        {

            Toast.makeText(getApplicationContext(), "loging out", Toast.LENGTH_SHORT).show();
            dialogInterface.cancel();
            //لتسجيل الخروج
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.signOut();
            finish();//to close current activity
        }
        if(which==dialogInterface.BUTTON_NEGATIVE)
        {
            Toast.makeText(getApplicationContext(), "loging out canceled", Toast.LENGTH_SHORT).show();
            dialogInterface.cancel();
        }
    }
}