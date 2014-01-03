package pl.edu.uj.tcs.student.tomaszwesolowski;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class Display extends CordovaPlugin {
	
	static class ScreenAdjuster {

		public static void setAlpha(Layer view, int alpha){
		    //Handle all conditions
		       view.setColor(alpha, 0, 0, 0);
		}
		public static void setContrast(Layer view, int contrast){
		    //Handle all conditions
		    view.setColor(contrast, 100, 100, 100);
		}
		
		public static void setColor(Layer redView, Layer greenView, Layer blueView, int r, int g, int b){
		    //Handle all conditions
		    redView.setColor(r, 255, 0, 0);
		    greenView.setColor(g, 0, 255, 0);
		    blueView.setColor(b, 0, 0, 255);
		    Log.d("display", "setting..." + r + " " + g + " " + b);
		}

	}
	
	class Layer extends View
	{
		  private int a;
		  private int b;
		  private int g;
		  private int r;
		
		  public Layer(Context context){
		    super(context);
		  }
		  
		  @Override
		  protected void onDraw(Canvas canvas){
		    super.onDraw(canvas);
		    canvas.drawARGB(this.a, this.r, this.g, this.b);
		    Log.d("display", "rendering..");
		    this.bringToFront();
		  }
		  
		  @Override 
		  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		     int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		     int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		     this.setMeasuredDimension(parentWidth/2, parentHeight);
		     this.setLayoutParams(new ViewGroup.LayoutParams(parentWidth/2,parentHeight));
		     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		  }
		
		  public void setColor(int a, int r, int g, int b){
		    this.a = a;
		    this.r = r;
		    this.g = g;
		    this.b = b;
		    invalidate();
		   }
	}
	
	
	public static final String SET_BRIGHTNESS = "setBrightness";
	public static final String SET_ORIENTATION = "setOrientation";
	public static final String SET_RED = "setRed";
	
	public Layer view;
	public Layer redView;
	public Layer blueView;
	public Layer greenView;
	
	private int red = 0;
	private int green = 0;
	private int blue = 0;
	
	boolean colorsFirstTime = false;

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
					}
				});

				callbackContext.success();
				return true;
			}
			else if (SET_RED.equals(action)) {
				final JSONObject arg_object = args.getJSONObject(0);
				
				cordova.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						setColorsViews();

						WindowManager.LayoutParams layout = cordova.getActivity().getWindow().getAttributes();
						try {
							red = arg_object.getInt("red");
							ScreenAdjuster.setColor(redView, greenView, blueView, red, green, blue) ;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cordova.getActivity().getWindow().setAttributes(layout);
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
	
	public void setColorsViews() {
		if(!colorsFirstTime) {
			view = new Layer(cordova.getActivity());
			redView = new Layer(cordova.getActivity());
			greenView = new Layer(cordova.getActivity());
			blueView = new Layer(cordova.getActivity());
			
			
			WindowManager localWindowManager = (WindowManager)cordova.getActivity().getSystemService("window");
			WindowManager.LayoutParams layoutParams = cordova.getActivity().getWindow().getAttributes();
			localWindowManager.addView(view, layoutParams);
			localWindowManager.addView(greenView, layoutParams);
			localWindowManager.addView(redView, layoutParams);
			localWindowManager.addView(blueView, layoutParams);
			colorsFirstTime = true;
			 Log.d("display", "views added");
		}
	}
}