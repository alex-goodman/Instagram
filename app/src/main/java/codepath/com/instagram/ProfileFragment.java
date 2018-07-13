package codepath.com.instagram;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import codepath.com.instagram.models.Post;


public class ProfileFragment extends Fragment {

    ImageView ivProfPic;
    TextView tvUsername;
    ParseUser user;
    onActionClickListener listener;
    ParseFile profPic;
    Bitmap preview;
    int newProfPic;
    Button btLogout;
    RecyclerView rvProf;

    ProfAdapter adapter;
    ArrayList<Post> posts;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public interface onActionClickListener {
        public void onActionClick();
        public void onLogoutClick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        posts = new ArrayList<>();
        adapter = new ProfAdapter(posts);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvProf = (RecyclerView) view.findViewById(R.id.rvProf);
        rvProf.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        rvProf.setAdapter(adapter);

        // add spacing
        SpacesItemDecoration decoration = new SpacesItemDecoration(28);
        rvProf.addItemDecoration(decoration);
        loadProfPosts();

        user = ParseUser.getCurrentUser();
        listener = (onActionClickListener) getActivity();
        ivProfPic = (ImageView) view.findViewById(R.id.ivProfPic);
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        tvUsername.setText(user.getUsername());
        btLogout = (Button) view.findViewById(R.id.btLogout);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLogoutClick();
            }
        });

        ParseFile file = user.getParseFile("profPic");

        if (newProfPic == 1) {
            ivProfPic.setImageBitmap(preview);
            user.put("profPic", profPic);
            user.saveInBackground();
        } else {
            // look for old prof pic
            if (file != null) GlideApp.with(view).load(file.getUrl()).into(ivProfPic);
        }

        ivProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onActionClick();
            }
        });

        Toast.makeText(getActivity(), "Click on the image to add a profile picture", Toast.LENGTH_SHORT).show();
    }

    // for first-time setting of profile pic
    public void setProfilePic(Bitmap bmp, ParseFile file) {
        preview = bmp;
        profPic = file;
        newProfPic = 1;
    }

    public void loadProfPosts() {
        final Post.Query postsQuery = new Post.Query();

        postsQuery.withUser();
        postsQuery.whereEqualTo("user", ParseUser.getCurrentUser());

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (Post p: objects) {
                        posts.add(0, p);
                        adapter.notifyItemInserted(0);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
