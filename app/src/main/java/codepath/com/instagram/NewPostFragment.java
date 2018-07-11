package codepath.com.instagram;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
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

import java.io.File;

import codepath.com.instagram.models.Post;

import static android.app.Activity.RESULT_OK;


public class NewPostFragment extends Fragment {

    Button btNewPost;
    ImageView ivPreview;
    EditText etCaption;

    static final int REQUEST_PIC_CAPTURE = 20;
    public static final String APP_TAG = "FakeInsta";

    Context context;
    File photoFile;
    String photoFileName = "photo.jpg";

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // bind views
        btNewPost = view.findViewById(R.id.btNewPost);
        ivPreview = view.findViewById(R.id.ivPreview);
        etCaption = view.findViewById(R.id.etCaption);

        // send to camera right away
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(context, "com.codepath.fileprovider", photoFile);
        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (takePicIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePicIntent, REQUEST_PIC_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PIC_CAPTURE) {
            if (resultCode  == RESULT_OK) {
                // we now have the file on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                // load the image into a preview
                ivPreview.setImageBitmap(takenImage);

                // turn photo into a ParseFile
                final ParseFile parseFile = new ParseFile(photoFile);

                btNewPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createPost(etCaption.getText().toString(), parseFile, ParseUser.getCurrentUser());
                        Intent i = new Intent(context, HomeActivity.class);
                        i.putExtra("origin", "newPost");
                        startActivity(i);
                    }
                });
            } else {
                Log.d("Image capture", "Capture failed");
            }
        }
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
                    Toast.makeText(context, "created new post!", Toast.LENGTH_SHORT).show();
                    Log.d("PostAction", "Successful post");
                } else {
                    e.printStackTrace();
                    Log.d("PostAction", "Failed");
                }
            }
        });
    }

    public File getPhotoFileUri(String filename) {
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + filename);

        return file;
    }
}
