package codepath.com.instagram;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import org.parceler.Parcels;

import codepath.com.instagram.models.Post;

public class DetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DetailFragment detFrag = new DetailFragment();

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder, detFrag).commit();
        detFrag.setup(post);
    }
}
