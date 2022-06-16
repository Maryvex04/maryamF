package MyData;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import neveen.samih.maryamBooks.R;

public class MyTaskAdapter extends ArrayAdapter<MyBook> {//هو وسيط بين مصدر المعطيات وعوضها على الشاشه وهي عباره عن فئه وارثه لفئه من نوع adapter
    public MyTaskAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  View view=View.inflate(getContext(), R.layout.task_item_layout,parent);// تتم بحسبها عملية الوساطه اي ملائمة المعطى وطريقة عرضه
        // تاخذ المعطى من قاعدة البيانات وبناء واجهه وعرضها عالواجهه وبعدها تقوم بارجاع الواجهه لكل معطى
        View view= LayoutInflater.from(getContext()).inflate(R.layout.task_item_layout,parent,false);
        MyBook item = getItem(position);
        TextView title=view.findViewById(R.id.BookName);
        TextView subject=view.findViewById(R.id.Authortv);
        ImageView image=view.findViewById(R.id.iv1);
        ImageButton delete=view.findViewById(R.id.ib3);
        RatingBar ratingBar=view.findViewById(R.id.ratingBar);
        downloadImageToLocalFile(item.getImage(),image);
        ImageButton edit=view.findViewById(R.id.ib2);
     //وضع قيم المعطى المستخرج على كائنات الواجهه
        title.setText(item.getTitle());
        subject.setText(item.getSubject());
        ratingBar.setRating(5*(item.getImportant()/(float)10));
//        if(item.getImage()=null||){
        return view;
    }
    private void downloadImageToLocalFile(String fileURL, final ImageView toView) {
        if (fileURL== null)return;
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileURL);
        final File localFile;
        try {
            localFile = File.createTempFile("images", "jpg");


            httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Toast.makeText(getContext(), "downloaded Image To Local File", Toast.LENGTH_SHORT).show();
                    toView.setImageURI(Uri.fromFile(localFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(getContext(), "onFailure downloaded Image To Local File "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
