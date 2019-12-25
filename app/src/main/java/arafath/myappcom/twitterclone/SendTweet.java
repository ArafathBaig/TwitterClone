package arafath.myappcom.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SendTweet extends AppCompatActivity {

    EditText tweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        tweet = findViewById(R.id.tweetText);

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
