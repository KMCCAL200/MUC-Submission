package gcu.ac.uk.gamenews;

import java.io.Serializable;

public class RSSDataItem implements Serializable {

    // Declare variables
    private String theData;

    //get rss data
    public String getTheData()
    {
        return this.theData;
    }

    //set rss data
    public void setTheData(String sTheData )
    {
        this.theData = sTheData;
    }

    //initialise variables
    public RSSDataItem(){this.theData = ""; }
}