package viewlift.com.jge.calmmeditation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private static final String URL = "http://sample-firetv-web-app.s3-website-us-west-2.amazonaws.com/feed_firetv.xml";
    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private ProgressBar mProgressBar;
    private ArrayList<VideoItem> videoItems;

    @Override
    public void onListItemClick(VideoItem videoItemIndexClicked) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("videoUrl",videoItemIndexClicked.getVideoUrl());
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.pb);
        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecyclerView.setHasFixedSize(true);
        mVideoAdapter = new VideoAdapter(this);
        mRecyclerView.setAdapter(mVideoAdapter);
        new DownloadXmlTask().execute(URL);
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, ArrayList<VideoItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<VideoItem> doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return null;
            } catch (XmlPullParserException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<VideoItem> result) {
            videoItems = result;
            mVideoAdapter.setVideoData(result);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<VideoItem> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        ViewliftVideoXmlParser viewliftVideoXmlParser = new ViewliftVideoXmlParser();
        List<VideoItem> videoItems;
        try {
            stream = downloadUrl(urlString);
            videoItems = viewliftVideoXmlParser.parse(stream);

        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return (ArrayList<VideoItem>) videoItems;
    }


    private InputStream downloadUrl(String urlString) throws IOException {
        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("SAVED INSTANCE", videoItems);
    }
}
