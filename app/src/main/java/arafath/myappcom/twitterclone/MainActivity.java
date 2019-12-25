package arafath.myappcom.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mail,username,password;
    private Button signUp;
    private TextView clickText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Twitter Dup");
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.emailText);
        username = findViewById(R.id.nameEdit);
        password = findViewById(R.id.passwordText);
        signUp = findViewById(R.id.signupButton);
        clickText = findViewById(R.id.clickText);

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(signUp);
                }
                return false;
            }
        });

        signUp.setOnClickListener(this);
        clickText.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){
            transitionToMainActivity();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signupButton:

                if (mail.getText().toString() == "" || username.getText().toString() == "" || password.getText().toString() == "") {
                    FancyToast.makeText(this, "Email, username, password required", FancyToast.INFO, Toast.LENGTH_SHORT, true).show();
                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(mail.getText().toString());
                    appUser.setUsername(username.getText().toString());
                    appUser.setPassword(username.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + username.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, username.getText().toString() + " is Signed Up", FancyToast.SUCCESS, Toast.LENGTH_SHORT, true).show();
                                transitionToMainActivity();
                            } else {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.ERROR, Toast.LENGTH_SHORT, true).show();
                            }
                        }

                    });
                    progressDialog.dismiss();
                }
            break;

            case R.id.clickText:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void rootLayoutTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToMainActivity(){
        Intent intent = new Intent(MainActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}
