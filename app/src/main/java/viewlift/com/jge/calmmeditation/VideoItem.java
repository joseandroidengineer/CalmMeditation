package viewlift.com.jge.calmmeditation;

import android.support.annotation.NonNull;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class VideoItem {

    public String title;
    public String videoUrl;
    public String duration;

    private VideoItem(String title, String videoUrl, String duration){
        this.title = title;
        this.videoUrl = videoUrl;
        this.duration = duration;
    }

    @NonNull
    public static VideoItem readItem(XmlPullParser parser, String ns) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String videoUrl = null;
        String duration = null;
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("title")){
                title = readTitle(parser, ns);
            }else if(name.equals("media:content")){
                videoUrl = readContent(parser, ns);
                duration = readDuration(parser, ns);
            }else{
                skip(parser);
            }
        }
        return new VideoItem(title,videoUrl,duration);
    }

    private static String readTitle(XmlPullParser parser, String ns) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns,"title");
        return title;
    }

    private static String readContent(XmlPullParser parser, String ns) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG,ns,"media:content");

        String tag = parser.getName();
        String url = parser.getAttributeValue(null,"url");
        Log.e("TAG NAME",tag);
        Log.e("TAG URL", url);
        return url;
    }

    private static String readDuration(XmlPullParser parser, String ns) throws XmlPullParserException, IOException{
        String tag = parser.getName();
        String duration = parser.getAttributeValue(null, "duration");
        Log.e("TAG NAME",tag);
        Log.e("TAG DURATION", duration);
        while(parser.next()!=XmlPullParser.END_TAG){
            if (parser.getEventType()!= XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            skip(parser);
        }
        parser.require(XmlPullParser.END_TAG, ns,"media:content");
        return duration;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if(parser.next() == XmlPullParser.TEXT){
            result = parser.getText();
            parser.nextTag();
        }
        return result;

    }

    public static void skip(XmlPullParser parser)throws XmlPullParserException, IOException{
        if(parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;
        while(depth != 0){

            switch(parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
