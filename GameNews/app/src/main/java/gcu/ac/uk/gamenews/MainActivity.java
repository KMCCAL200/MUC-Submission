package gcu.ac.uk.gamenews;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    // Declare variables
    SharedPreferences mySharedPrefs;
    FragmentManager fmAboutDialogue;
    String sOutputMsg;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialise activity
        super.onCreate(savedInstanceState);
        //set orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //set layout
        setContentView(R.layout.activity_main);
        //get saved data
        mySharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        fmAboutDialogue = this.getFragmentManager();
    }

    @Override
    //when user clicks a button
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            //press news button
            case R.id.btnNews:
                //open news page
                Intent mcOutput_Screen = new Intent(getApplicationContext(), OutputScreen.class);
                mcOutput_Screen.putExtra("mcOutputMsg", sOutputMsg);
                //start news activity
                startActivity(mcOutput_Screen);
                break;
            //press map button
            case R.id.btnMap:
                //open map page
                Intent mcMap = new Intent(this, MapActivity.class);
                //start map activity
                this.startActivity(mcMap);
                break;
            //press preferences button
            case R.id.btnPrefs:
                //open preferences page
                Intent sPref = new Intent(this, SavingDataOutput.class);
                //start preferences activity
                this.startActivity(sPref);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get the options menu items
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sgn_menu, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    //when user clicks menu button
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //choose quit option
            case R.id.mQuit:
                //close application
                finish();
                return true;
            //choose about option
            case R.id.mAbout:
                //display about message box
                DialogFragment mcAboutDlg = new AboutDialogue();
                mcAboutDlg.show(fmAboutDialogue, "mc_About_dlg");
                return true;
            //choose help option
            case R.id.mHelp:
                //display help message box
                DialogFragment mcHelpDlg = new HelpDialogue();
                mcHelpDlg.show(fmAboutDialogue, "mc_About_dlg");
                return true;
            //choose screensaver option
            case R.id.mScreensaver:
                //switch to screensaver page
                Intent SS = new Intent(this, DrawDisk.class);
                //star screensaver activity
                this.startActivity(SS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
