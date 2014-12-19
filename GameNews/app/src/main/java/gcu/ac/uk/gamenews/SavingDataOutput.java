package gcu.ac.uk.gamenews;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class SavingDataOutput extends Activity implements View.OnClickListener {

    // Declare variables
    SharedPreferences prefSetting;
    SharedPreferences.Editor prefEdit;
    static final int PMP = 0;
    TextView tvName;
    TextView tvCity;
    TextView tvRss;
    EditText edName;
    EditText edCity;
    EditText edRss;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override

    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_data);
        //input boxes
        edName = (EditText) findViewById(R.id.teName);
        edCity = (EditText) findViewById(R.id.teCity);
        edRss = (EditText) findViewById(R.id.teRss);
        //text boxes
        tvName = (TextView) findViewById(R.id.tvName);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvRss = (TextView) findViewById(R.id.tvRSS);
        //load the saved data
       loadSavedPreferences();
    }

    private void loadSavedPreferences() {
        //Get all saved preferences
        prefSetting = getPreferences(PMP);
        //user name
        tvName.setText("Name: " + prefSetting.getString("Name", "Name..."));
        //user city
        tvCity.setText("City: " + prefSetting.getString("City", "City..."));
        //user rss url
        tvRss.setText("RSS: " + prefSetting.getString("RSS", "RSS..."));
    }

    //when user clicks a button
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            //press back button
            case R.id.btnBack:
                setResult(Activity.RESULT_OK);
                //close activity
                finish();
                break;
            //press edit button
            case R.id.btnEdit:
                //allow editing of preferences
                prefSetting = getPreferences(PMP);
                prefEdit = prefSetting.edit();
                //get new data frm input boxes
                prefEdit.putString("Name", edName.getText().toString());
                prefEdit.putString("City", edCity.getText().toString());
                prefEdit.putString("RSS", edRss.getText().toString());
                //save the new data
                prefEdit.commit();
                //show the updated data
                loadSavedPreferences();
                break;
        }
    }
}
