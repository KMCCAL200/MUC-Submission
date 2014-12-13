package gcu.ac.uk.gamenews;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AboutDialogue extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //initialise message box
        AlertDialog.Builder mcAboutDialog = new AlertDialog.Builder(getActivity());
        //Message displayed to user
        mcAboutDialog.setMessage("This app shows the locations of, and news from game studios in Scotland")
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