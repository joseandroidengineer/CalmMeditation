package viewlift.com.jge.calmmeditation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapterViewHolder> {

    private ArrayList<VideoItem> videos;
    private final ListItemClickListener mOnClickListener;

    public VideoAdapter(ListItemClickListener mOnClickListener){
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.video_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new VideoAdapterViewHolder(view, mOnClickListener, videos);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterViewHolder videoAdapterViewHolder, int i) {
        VideoItem videoItem = videos.get(i);
        Log.e("VIDEONAME",videoItem.title);
        Picasso.get().load(videoItem.getThumbnailUrl()).into(videoAdapterViewHolder.mImage);
        videoAdapterViewHolder.mTitle.setText(videoItem.title);
        videoAdapterViewHolder.mDuration.setText("Duration: "+videoItem.duration);
    }

    @Override
    public int getItemCount() {
        if(videos == null) return 0;
        return videos.size();
    }

    public void setVideoData(ArrayList<VideoItem> videoData){
        videos = videoData;
        Log.e("setVideo", videoData.toString());
        notifyDataSetChanged();

    }
}
