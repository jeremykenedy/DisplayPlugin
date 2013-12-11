package pl.edu.uj.tcs.student.tomaszwesolowski;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.ActivityInfo;
import android.view.WindowManager;

public class Display extends CordovaPlugin {
	public static final String SET_BRIGHTNESS = "setBrightness";
	public static final String SET_ORIENTATION = "setOrientation";

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		try {
			if (SET_BRIGHTNESS.equals(action)) {
				final JSONObject arg_object = args.getJSONObject(0);

				cordova.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						// Main Code goes here
						WindowManager.LayoutParams layout = cordova.getActivity().getWindow().getAttributes();
						try {
							layout.screenBrightness = (float) arg_object.getDouble("brightness");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cordova.getActivity().getWindow().setAttributes(layout);
						callbackContext.success();
					}
				});

				callbackContext.success();
				return true;
			}
			else if (SET_ORIENTATION.equals(action)) {
				final JSONObject arg_object = args.getJSONObject(0);

				cordova.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						// Main Code goes here
						try {
							cordova.getActivity().setRequestedOrientation(arg_object.getInt("orientation"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						callbackContext.success();
					}
				});

				callbackContext.success();
				return true;
			}
			callbackContext.error("Invalid action");
			return false;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}
	}
}