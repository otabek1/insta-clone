package otabek.io.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        setTitle(username+"'s photos");



        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Images");
        query.whereEqualTo("username",username);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("hah", "done:fail ");
                if (e == null && objects.size() >0 ){
                    for (ParseObject parseObject: objects){
                        ParseFile file = (ParseFile) parseObject.get("image");
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e==null && data !=null)
                                {
                                    Log.i("hah", "ok: ");
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                                    linearLayout = findViewById(R.id.linearLayout);

                                    imageView = new ImageView(getApplicationContext());

                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));

//                                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.instagram));


                                    imageView.setImageBitmap(bitmap);
                                    linearLayout.addView(imageView);




                                } else{
                                    Log.i("hah", "done: "+e.getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });




    }
}
