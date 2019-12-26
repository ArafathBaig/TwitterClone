package arafath.myappcom.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SendTweet extends AppCompatActivity implements View.OnClickListener {

    EditText tweet;
    Button viewOther;
    ListView viewtweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        setTitle("Feed");
        tweet = findViewById(R.id.tweetText);
        viewOther = findViewById(R.id.viewTweets);
        viewtweet = findViewById(R.id.listView2);

        viewOther.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    public void sendTweet(View view){

        ParseObject object = new ParseObject("myTweet");
        object.put("tweet",tweet.getText().toString());
        object.put("user", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading tweet");
        progressDialog.show();

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    FancyToast.makeText(SendTweet.this,"Tweet Uploaded", Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                }else{
                    FancyToast.makeText(SendTweet.this,e.getMessage(), Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
