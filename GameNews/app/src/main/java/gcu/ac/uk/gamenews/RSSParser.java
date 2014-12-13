package gcu.ac.uk.gamenews;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class RSSParser {

    // Declare variables
    private RSSDataItem RSSDataItem;

    //set the rss data
    public void setRSSDataItem(String sItemData)
    {RSSDataItem.setTheData(sItemData); }

    //get the rss data
    public RSSDataItem getRSSDataItem()
    {
        return this.RSSDataItem;
    }

    public RSSParser()
    {
        //initialise parser
        this.RSSDataItem =  new RSSDataItem();
        setRSSDataItem(null);
    }

    //parse the rss
    public void parseRSSDataItem(XmlPullParser theParser, int theEventType)
    {
        //hold rss data
        String data = "";
        try
        {   //while not at the end of the rss
            while (theEventType != XmlPullParser.END_DOCUMENT)
            {
                //found a start tag
                if(theEventType == XmlPullParser.START_TAG)
                {
                    //check the tag
                    if (theParser.getName().equalsIgnoreCase("title"))
                    {
                        //get the text
                        String temp = theParser.nextText();
                        //store temporary data
                        data = data + "\n" + temp + "\n";
                    }
                    else
                        //check the tag
                        if (theParser.getName().equalsIgnoreCase("description"))
                        {
                            //get the text
                            String temp = theParser.nextText();
                            //store temporary data
                            data = data + "\n" + temp + "\n\n";
                        }
                }

                data = data.replaceAll("\\<.*?\\>", "");
                //store data in class
                RSSDataItem.setTheData(data);
                // Get the next event
                theEventType = theParser.next();
            } // End of while
        }
        //catch any parsing errors
        catch (XmlPullParserException parserExp1)
        {
            Log.e("MyTag","Parsing error" + parserExp1.toString());
        }
        catch (IOException parserExp1)
        {
            Log.e("MyTag","IO error during parsing");
        }
    }

    public void parseRSSData(String RSSItemsToParse) throws MalformedURLException {
        //get the url of the rss
        URL rssURL = new URL(RSSItemsToParse);
        InputStream rssInputStream;

        //try to get the rss and the text it contains
        try
        {
            XmlPullParserFactory parseRSSfactory = XmlPullParserFactory.newInstance();
            parseRSSfactory.setNamespaceAware(true);
            XmlPullParser RSSxmlPP = parseRSSfactory.newPullParser();
            //get rss text
            String xmlRSS = getStringFromInputStream(getInputStream(rssURL), "UTF-8");
            RSSxmlPP.setInput(new StringReader(xmlRSS));
            int eventType = RSSxmlPP.getEventType();
            //parse the rss
            parseRSSDataItem(RSSxmlPP,eventType);

        }
        //catch any errors while trying to get rss data
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }
    }

    //get the rss page
    public InputStream getInputStream(URL url) throws IOException
    {
        return url.openConnection().getInputStream();
    }

    public static String getStringFromInputStream(InputStream stream, String charsetName) throws IOException
    {
        //get raw text from rss
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, charsetName);
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        //return the text
        return writer.toString();
    }
}
