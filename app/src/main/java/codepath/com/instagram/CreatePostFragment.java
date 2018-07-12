package codepath.com.instagram;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import codepath.com.instagram.models.Post;


public class CreatePostFragment extends Fragment {

    ImageView ivPreview;
    EditText etCaption;
    Button btNewPost;
    ParseFile photoFile;
    Bitmap preview;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ivPreview = (ImageView) view.findViewById(R.id.ivPreview);
        etCaption = (EditText) view.findViewById(R.id.etCaption);
        btNewPost = (Button) view.findViewById(R.id.btNewPost);

        ivPreview.setImageBitmap(preview);

        btNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost(etCaption.getText().toString(), photoFile, ParseUser.getCurrentUser());
            }
        });
    }

    public void setup(Bitmap bmp, ParseFile photo) {
        preview = bmp;
        photoFile = photo;
    }

    private void createPost(String caption, ParseFile image, ParseUser user) {
        final Post newPost = new Post();
        newPost.setImage(image);
        newPost.setUser(user);
        newPost.setCaption(caption);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "created new post!", Toast.LENGTH_SHORT).show();
                    Log.d("PostAction", "Successful post");
                } else {
                    e.printStackTrace();
                    Log.d("PostAction", "Failed");
                }
            }
        });
    }
}
