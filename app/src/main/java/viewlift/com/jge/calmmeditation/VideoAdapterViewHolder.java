package viewlift.com.jge.calmmeditation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mTitle;
    public TextView mDuration;
    public ImageView mImage;
    private ListItemClickListener mOnClickListener;
    private ArrayList<VideoItem> videoItems;


    public VideoAdapterViewHolder(@NonNull View itemView, ListItemClickListener mOnClickListener, ArrayList<VideoItem> videos) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.title_tv);
        mDuration = itemView.findViewById(R.id.duration_tv);
        mImage = itemView.findViewById(R.id.imageView);
        this.mOnClickListener = mOnClickListener;
        videoItems = videos;
        Log.e("VIEWHOLDER", videos.get(0).title);
        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        mOnClickListener.onListItemClick(videoItems.get(position));

    }
}
