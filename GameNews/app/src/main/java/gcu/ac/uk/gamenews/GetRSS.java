package gcu.ac.uk.gamenews;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;

public class GetRSS extends AsyncTask<String, Integer, RSSDataItem> {

    // Declare variables
    private Context appContext;
    private String urlRSSToParse;

    public GetRSS(Context currentAppContext, String urlRSS) {
        //initialise variables
        appContext = currentAppContext;
        urlRSSToParse = urlRSS;
    }

    // A callback method executed on UI thread on starting the task
    @Override
    protected void onPreExecute() {
        // Message to indicate start of parsing
        Toast.makeText(appContext, "Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected RSSDataItem doInBackground(String... params) {
        RSSDataItem parsedData;
        //run the parser
        RSSParser rssParser = new RSSParser();
        //try to get rss data
        try {
            rssParser.parseRSSData(urlRSSToParse);
        } catch (MalformedURLException e) { //if unsuccessful give error message
            e.printStackTrace();
        }
        //get final text
        parsedData = rssParser.getRSSDataItem();
        //return the final text
        return parsedData;
    }

// A callback method executed on UI thread, invoked after the completion of the task
// When doInbackground has completed, the return value from that method is passed into this event handler.

    @Override
    protected void onPostExecute(RSSDataItem result) {
        // Message to indicate end of parsing
        Toast.makeText(appContext, "Loading Complete", Toast.LENGTH_SHORT).show();
    }
}