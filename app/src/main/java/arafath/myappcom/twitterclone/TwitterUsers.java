package arafath.myappcom.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, list);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);

        FancyToast.makeText(this, ParseUser.getCurrentUser().getUsername() + " welcome", FancyToast.SUCCESS, Toast.LENGTH_SHORT, true).show();

        try {
            ParseQuery<ParseUser> query = ParseUser.getQuery();

            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseUser user : objects) {
                                list.add(user.getUsername());
                            }

                            listView.setAdapter(arrayAdapter);
                            if (ParseUser.getCurrentUser().getList("fanOf") != null) {
                                for (String twitUsers : list) {
                                    if (ParseUser.getCurrentUser().getList("fanOf").contains(twitUsers)) {
                                        listView.setItemChecked(list.indexOf(twitUsers), true);
                                    }
                                }

                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logOutUser:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Intent intent = new Intent(TwitterUsers.this,LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;

        if(checkedTextView.isChecked()){
            FancyToast.makeText(this,list.get(position)+" is Followed", Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
            ParseUser.getCurrentUser().add("fanOf",list.get(position));
        }else{
            FancyToast.makeText(this,list.get(position)+" is UnFollowed", Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(list.get(position));
            List currentList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentList);
        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    FancyToast.makeText(TwitterUsers.this,"Changes Made", Toast.LENGTH_SHORT,FancyToast.INFO,true).show();

                }
            }
        });

    }
}
