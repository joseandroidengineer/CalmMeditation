package viewlift.com.jge.calmmeditation;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String url = getIntent().getStringExtra("videoUrl");
        final MediaController mediaController = new MediaController(this);
        video = findViewById(R.id.videoView);
        video.setMediaController(mediaController);
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
                mediaController.show();
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
