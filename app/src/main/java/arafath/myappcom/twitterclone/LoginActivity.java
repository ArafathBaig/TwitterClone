package arafath.myappcom.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText user,pass;
    private Button login;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");
        user = findViewById(R.id.logUser);
        pass = findViewById(R.id.logPass);
        login = findViewById(R.id.loginBut);
        signUpText = findViewById(R.id.signupText);

        if(ParseUser.getCurrentUser() != null){
            transitionToMainActivity();
        }

        login.setOnClickListener(this);
        signUpText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.loginBut:



                if(user.getText().toString().equals("") || pass.getText().toString().equals("")){
                    FancyToast.makeText(this,"Username,Password required",FancyToast.SUCCESS, Toast.LENGTH_SHORT,true).show();
                }else{
                    ParseUser.logInInBackground(user.getText().toString(), pass.getText().toString(), new LogInCallback() {


                        @Override
                        public void done(ParseUser userr, ParseException e) {
                            if(userr != null && e == null){
                                FancyToast.makeText(LoginActivity.this, userr.getUsername() + " LoggedIn", FancyToast.SUCCESS, Toast.LENGTH_SHORT, true).show();
                                transitionToMainActivity();
                            }else{
                                FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.ERROR, Toast.LENGTH_SHORT, true).show();

                            }
                        }
                    });
                }
                break;

            case R.id.signupText:
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
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
        Intent intent = new Intent(LoginActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}
