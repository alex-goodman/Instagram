package codepath.com.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codepath.com.instagram.models.Post;

public class ProfAdapter extends RecyclerView.Adapter<ProfAdapter.ViewHolder> {

    Context context;
    private static List<Post> mPosts;
    static onItemSelectedListener listener;

    public ProfAdapter(List<Post> posts) {
        mPosts = posts;
    }

    public interface onItemSelectedListener {
        public void onItemSelected(Post post);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        listener = (onItemSelectedListener) context;

        View profView = inflater.inflate(R.layout.item_prof, parent, false);
        ProfAdapter.ViewHolder viewHolder = new ProfAdapter.ViewHolder(profView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        String imageUrl = post.getImage().getUrl();

        GlideApp.with(context).load(imageUrl).into(holder.ivPost);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPost) ImageView ivPost;

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
