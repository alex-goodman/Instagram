package codepath.com.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codepath.com.instagram.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private static List<Post> mPosts;
    static Context context;
    private static onItemSelectedListener listener;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        listener = (onItemSelectedListener) context;

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);

        return viewHolder;
    }

    public interface onItemSelectedListener {
        public void onItemSelected(Post post);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        ParseFile photoFile = post.getUser().getParseFile("profPic");

        // populate the views according to the post
        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getCaption());

        if (photoFile != null) GlideApp.with(context).load(photoFile.getUrl()).into(holder.ivProfPic);
        GlideApp.with(context).load(post.getImage().getUrl()).into(holder.ivPic);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPic) ImageView ivPic;
        @BindView(R.id.tvUsername) TextView tvUsername;
        @BindView(R.id.tvCaption) TextView tvCaption;
        @BindView(R.id.ivProfPic) ImageView ivProfPic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Post post = mPosts.get(position);
                    listener.onItemSelected(post);
                }
            });
        }
    }
}
