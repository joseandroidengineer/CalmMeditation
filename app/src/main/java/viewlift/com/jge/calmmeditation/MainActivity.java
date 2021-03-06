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
import android.widget.TextView;

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
    private RecyclerView grassRecyclerView;
    private RecyclerView waterRecyclerView;
    private RecyclerView buildingRecyclerView;
    private VideoAdapter mVideoAdapter;
    private GrassVideoAdapter grassVideoAdapter;
    private BuildingVideoAdapter buildingVideoAdapter;
    private WaterVideoAdapter waterVideoAdapter;

    private ProgressBar mProgressBar;
    private TextView allTV;
    private TextView grassTV;
    private TextView waterTV;
    private TextView buildingTV;
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
        grassRecyclerView = findViewById(R.id.grass_rv);
        waterRecyclerView = findViewById(R.id.water_rv);
        buildingRecyclerView = findViewById(R.id.building_rv);
        allTV = findViewById(R.id.all_tv);
        grassTV =findViewById(R.id.grass_tv);
        waterTV = findViewById(R.id.water_tv);
        buildingTV = findViewById(R.id.building_tv);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager gllm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager wllm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager bllm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(llm);
        grassRecyclerView.setLayoutManager(gllm);
        waterRecyclerView.setLayoutManager(wllm);
        buildingRecyclerView.setLayoutManager(bllm);
        mRecyclerView.setHasFixedSize(true);
        grassRecyclerView.setHasFixedSize(true);
        waterRecyclerView.setHasFixedSize(true);
        buildingRecyclerView.setHasFixedSize(true);
        mVideoAdapter = new VideoAdapter(this);
        grassVideoAdapter = new GrassVideoAdapter(this);
        buildingVideoAdapter = new BuildingVideoAdapter(this);
        waterVideoAdapter = new WaterVideoAdapter(this);

        allTV.setText("Trail And Car");
        grassTV.setText("Grass");
        waterTV.setText("Water");
        buildingTV.setText("Building");
        mRecyclerView.setAdapter(mVideoAdapter);
        grassRecyclerView.setAdapter(grassVideoAdapter);
        waterRecyclerView.setAdapter(waterVideoAdapter);
        buildingRecyclerView.setAdapter(buildingVideoAdapter);
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
            mVideoAdapter.setVideoData(Util.getCarsVideos(result));
            grassVideoAdapter.setVideoData(Util.getGrassVideos(result));
            waterVideoAdapter.setVideoData(Util.getWaterVideos(result));
            buildingVideoAdapter.setVideoData(Util.getBuildingVideos(result));
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
            for(VideoItem videoItem: videoItems){
                if(videoItem.mediaCategory.equals("Grass")){
                    //Put video in Grass Category*/
                }else if(videoItem.mediaCategory.equals("Building")){
                    //Put video in Building Category*/
                }else if(videoItem.mediaCategory.equals("Water")){
                    //Put video in Water Category*/
                }else{
                    //Put video in ALL Category
                }
            }

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
