package codepath.com.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import codepath.com.instagram.models.Post;

public class NewPostActivity extends AppCompatActivity {
    @BindView(R.id.btNewPost) Button btNewPost;
    @BindView(R.id.ivPreview) ImageView ivPreview;
    @BindView(R.id.etCaption) EditText etCaption;

    static final int REQUEST_PIC_CAPTURE = 20;
    public static final String APP_TAG = "FakeInsta";

    Context context;
    File photoFile;
    String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        context = getApplicationContext();
        ButterKnife.bind(this);

        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (takePicIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicIntent, REQUEST_PIC_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PIC_CAPTURE) {
            if (resultCode  == RESULT_OK) {
                // we now have the file on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                // TODO: resize the image here

                // load the image into a preview
                ivPreview.setImageBitmap(takenImage);

                // turn photo into a ParseFile
                final ParseFile parseFile = new ParseFile(photoFile);

                btNewPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createPost(etCaption.getText().toString(), parseFile, ParseUser.getCurrentUser());
                        Intent i = new Intent(context, HomeActivity.class);
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
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + filename);

        return file;
    }
}
