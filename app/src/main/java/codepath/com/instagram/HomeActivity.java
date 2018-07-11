package codepath.com.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment timelineFragment = new TimelineFragment();
        final Fragment newPostFragment = new NewPostFragment();

        bnv = findViewById(R.id.bnv);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        ParseUser.logOut();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        return true;
                    case R.id.home:
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.placeholder, timelineFragment).commit();
                        //ft.addToBackStack("home");
                        return true;
                    case R.id.compose:
                        FragmentTransaction ft1 = fragmentManager.beginTransaction();
                        ft1.replace(R.id.placeholder, newPostFragment).commit();
                        //ft1.addToBackStack("new post");
                        return true;
                    default:
                        return false;
                }
            }
        });
        bnv.setSelectedItemId(R.id.home);
    }
}
