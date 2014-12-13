package gcu.ac.uk.gamenews;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class OutputScreen extends Activity implements View.OnClickListener {

    // Declare variables
    TextView tvNews;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
   protected void onCreate(Bundle savedInstanceState){
        //create the page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        //new rss data item
        RSSDataItem news = new RSSDataItem();
        //rss url
        String RSSFeedURL = "http://www.rockstargames.com/newswire.rss";
        //get rss data from url
        GetRSS rssAsyncParse = new GetRSS(this, RSSFeedURL);
        try {
            news = rssAsyncParse.execute("").get();
    } catch (InterruptedException e) {//catch any errors
            e.printStackTrace();
        } catch (ExecutionException e) {//catch any errors
            e.printStackTrace();
        }
        //display the rss data
        tvNews = (TextView) findViewById(R.id.tvNews);
        tvNews.setText(news.getTheData());
    }
    //when user clicks a button
    public void onClick(View view) {
        setResult(Activity.RESULT_OK);
        final int id = view.getId();
        switch (id) {
            //press back button
            case R.id.btnBack:
                //close activity
                finish();
                break;
                //press refresh button
            case R.id.btnRefresh:
                //reload rss data
                Intent mcSDataOutput = new Intent(this, SavingDataOutput.class);
                this.startActivity(mcSDataOutput);
                break;
        }
    }
}
