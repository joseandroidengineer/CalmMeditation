package viewlift.com.jge.calmmeditation;

import android.util.Xml;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewliftVideoXmlParser {
    private static final String ns = null;


    public List parse(InputStream in) throws XmlPullParserException, IOException{
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        }finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        List videoItems = new ArrayList();

        VideoItem videoItem = new VideoItem(null,null,null,null, null);
        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while(parser.next()!= XmlPullParser.END_TAG){
            if(parser.getEventType()!= XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("item")){
                videoItems.add(videoItem.readItem(parser, ns));
            }else{
                VideoItem.skip(parser);
            }
        }
        return videoItems;
    }
}
