package gcu.ac.uk.gamenews;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HelpDialogue extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //initialise message box
        AlertDialog.Builder mcAboutDialog = new AlertDialog.Builder(getActivity());
        //Message displayed to user
        mcAboutDialog.setMessage("Click News to see news items from Rockstar \n" +
                "Click Map to view the locations of game studios in Scotland\n" +
                "Click Preferences to set your information and enter a custom rss feed")
                //button to close message box
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        //Title of message box
        mcAboutDialog.setTitle("About");
        //icon on message box
        mcAboutDialog.setIcon(R.drawable.ic_menu_action_about);
        //create the message box
        return mcAboutDialog.create();
    }
}