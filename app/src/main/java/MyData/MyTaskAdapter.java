package MyData;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import neveen.samih.samihtasksmanager.R;

public class MyTaskAdapter extends ArrayAdapter<MyTask> {//هو وسيط بين مصدر المعطيات وعوضها على الشاشه وهي عباره عن فئه وارثه لفئه من نوع adapter
    public MyTaskAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  View view=View.inflate(getContext(), R.layout.task_item_layout,parent);// تتم بحسبها عملية الوساطه اي ملائمة المعطى وطريقة عرضه
        // تاخذ المعطى من قاعدة البيانات وبناء واجهه وعرضها عالواجهه وبعدها تقوم بارجاع الواجهه لكل معطى
        View view= LayoutInflater.from(getContext()).inflate(R.layout.task_item_layout,parent,false);
        MyTask item = getItem(position);
        TextView title=view.findViewById(R.id.itemtasktitle);
        TextView subject=view.findViewById(R.id.itemtasksubject);
        ImageView image=view.findViewById(R.id.iv1);
        ImageButton delete=view.findViewById(R.id.ib3);
        RatingBar ratingBar=view.findViewById(R.id.ratingBar);
        ImageButton call=view.findViewById(R.id.ib1);
        ImageButton edit=view.findViewById(R.id.ib2);
     //وضع قيم المعطى المستخرج على كائنات الواجهه
        title.setText(item.getTitle());
        subject.setText(item.getSubject());
        ratingBar.setRating(5*(item.getImportant()/(float)10));
//        if(item.getImage()=null||){
        return view;
    }
}
