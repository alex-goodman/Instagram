package codepath.com.instagram;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    ImageView ivProfPic;
    TextView tvUsername;
    ParseUser user;
    onActionClickListener listener;
    ParseFile profPic;
    Bitmap preview;
    int newProfPic;
    Button btLogout;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
}
