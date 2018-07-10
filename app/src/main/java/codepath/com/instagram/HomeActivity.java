package codepath.com.instagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codepath.com.instagram.models.Post;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.etCaption) TextView etCaption;
    @BindView(R.id.btPost) Button btPost;
    @BindView(R.id.btRefresh) Button btRefresh;

    Context context;

    private static final String IMAGE_PATH = "/Users/alexgood/desktop/stars.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        context = getApplicationContext();
        loadTopPosts();

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String caption = etCaption.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(IMAGE_PATH);
                final ParseFile parseFile = new ParseFile(file);

                createPost(caption, parseFile, user);
            }
        });

        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });
    }

    private void loadTopPosts() {
        // create a new post query
        final Post.Query postsQuery = new Post.Query();
        // set the query params
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (Post p: objects) {
                        Log.d("HomeActivity", p.getCaption() + " by " + p.getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPost(String caption, ParseFile image, ParseUser user) {
        final Post newPost = new Post();
        newPost.setCaption(caption);
        newPost.setImage(image);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(context, "created new post!", Toast.LENGTH_SHORT).show();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
