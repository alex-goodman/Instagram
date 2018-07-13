package codepath.com.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import codepath.com.instagram.models.Post;

public class HomeActivity extends AppCompatActivity implements
        PostAdapter.onItemSelectedListener, CameraFragment.onPicTakenListener {

    BottomNavigationView bnv;
    CreatePostFragment createPostFragment;
    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment timelineFragment = new TimelineFragment();
        final Fragment cameraFragment = new CameraFragment();

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
                        return true;
                    case R.id.compose:
                        FragmentTransaction ft1 = fragmentManager.beginTransaction();
                        ft1.replace(R.id.placeholder, cameraFragment).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
        bnv.setSelectedItemId(R.id.home);
    }

    @Override
    public void onItemSelected(Post post) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("post", Parcels.wrap(post));
        startActivity(i);
    }

    @Override
    public void onPicTaken(Bitmap bmp, ParseFile file) {
        createPostFragment = new CreatePostFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.placeholder, createPostFragment).commit();
        createPostFragment.setup(bmp, file);
    }
}
