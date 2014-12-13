package gcu.ac.uk.gamenews;


import java.io.Serializable;

public class MapData implements Serializable{

    // Declare variables
    private int entryID;
    private String Name;
    private String Website;
    private float Latitude;
    private float Longitude;

    private static final long serialVersionUID = 0L;

//getters and setters

    //get id of item
    public int getEntryID() {
        return entryID;
    }

    //set id of item
    public void setEntryID(int entryID) {
        this.entryID = entryID;
    }

    //get name for item
    public String getName() {
        return Name;
    }

    //set name for item
    public void setName(String name) {
        this.Name = name;
    }

    //get website for item
    public String getWebsite() {
        return Website;
    }

    //set website for item
    public void setWebsite(String website) {
        this.Website = website;
    }

    //get latitude for item
    public float getLatitude()
    {
        return Latitude;
    }

    //get latitude for item
    public void setLatitude(float Lat)
    {
        this.Latitude = Lat;
    }

    //get longitude for item
    public float getLongitude()
    {
        return Longitude;
    }

    //get longitude for item
    public void setLongitude(float fLongitude)
    {
        this.Longitude = fLongitude;
    }
}
