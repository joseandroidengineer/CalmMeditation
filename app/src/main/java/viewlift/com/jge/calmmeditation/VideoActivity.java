package viewlift.com.jge.calmmeditation;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView video;
    private int current = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String url = getIntent().getStringExtra("videoUrl");

        video = findViewById(R.id.videoView);
        video.setVideoURI(Uri.parse(url));
        if(savedInstanceState != null){
            video.seekTo(savedInstanceState.getInt("current"));
            video.start();

        }else{
            video.start();
        }
        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(video.isPlaying()){
                    video.pause();
                    current = video.getCurrentPosition();
                }else if(!video.isPlaying()){
                    video.seekTo(current);
                    video.start();
                }
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int currentPosition = video.getCurrentPosition();
        outState.putInt("current", currentPosition);

    }
}
