package pl.edu.uj.tcs.student.tomaszwesolowski;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class DisplayPlugin extends CordovaPlugin {
    public static final String SET_BRIGHTNESS = "setBrightness";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (SET_BRIGHTNESS.equals(action)) { 
                JSONObject arg_object = args.getJSONObject(0);
                
                new AlertDialog.Builder(cordova.getActivity())
                .setTitle("Delete entry")
                .setMessage(arg_object.getString("brightness"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        // continue with delete
                    }
                 })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { 
                        // do nothing
                    }
                 })
                 .show();
             
               callbackContext.success();
               return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}