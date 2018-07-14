package codepath.com.instagram;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import codepath.com.instagram.models.Post;


public class DetailFragment extends Fragment {

    Post currentPost;
    TextView tvUsername;
    ImageView ivPicture;
    ImageView ivProfPic;
    TextView tvCaption;
    TextView tvTime;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        ivPicture = (ImageView) view.findViewById(R.id.ivPicture);
        tvCaption = (TextView) view.findViewById(R.id.tvCaption);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        ivProfPic = (ImageView) view.findViewById(R.id.ivProfPic);

        tvUsername.setText(currentPost.getUser().getUsername());
        tvCaption.setText(currentPost.getCaption());
        tvTime.setText(currentPost.getDate());
        GlideApp.with(this)
                .load(currentPost.getImage().getUrl())
                .into(ivPicture);
        GlideApp.with(this)
                .load(currentPost.getUser().getParseFile("profPic").getUrl())
                .transform(new CircleCrop())
                .into(ivProfPic);

    }

    public void setup(Post post) {
        currentPost = post;
    }
}
