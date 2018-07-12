package codepath.com.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import codepath.com.instagram.models.Post;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.ivPicture) ImageView ivPicture;
    @BindView(R.id.tvCaption) TextView tvCaption;
    @BindView(R.id.tvTime) TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername.setText("@" + post.getUser().getUsername());
        tvCaption.setText(post.getCaption());
        tvTime.setText(post.getDate());
        GlideApp.with(this).load(post.getImage().getUrl()).into(ivPicture);
    }
}
