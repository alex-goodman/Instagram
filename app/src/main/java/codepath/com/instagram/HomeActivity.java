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
        PostAdapter.onItemSelectedListener,
        ProfAdapter.onItemSelectedListener,
        CameraFragment.onPicTakenListener,
        CreatePostFragment.onNewPostListener,
        ProfileFragment.onActionClickListener{

    public static final int FROM_PROF_CODE = 10;
    public static final int NEW_POST_CODE = 20;

    BottomNavigationView bnv;
    CreatePostFragment createPostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment timelineFragment = new TimelineFragment();
        final Fragment cameraFragment = new CameraFragment();
        final Fragment profileFragment = new ProfileFragment();


        bnv = findViewById(R.id.bnv);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        FragmentTransaction ft2 = fragmentManager.beginTransaction();
                        ft2.replace(R.id.placeholder, profileFragment).commit();
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

    @Override
    public void onProfPicTaken(Bitmap bmp, ParseFile file) {
        ProfileFragment profFrag = new ProfileFragment();
        ParseUser user = ParseUser.getCurrentUser();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.placeholder, profFrag).commit();
        profFrag.setProfilePic(bmp, file);
    }

    @Override
    public void onNewPost() {
        bnv.setSelectedItemId(R.id.home);
    }

    @Override
    public void onActionClick() {
        CameraFragment camFrag = new CameraFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.placeholder, camFrag).commit();
        camFrag.setup(FROM_PROF_CODE);
    }

    @Override
    public void onLogoutClick() {
        ParseUser.logOut();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
}
