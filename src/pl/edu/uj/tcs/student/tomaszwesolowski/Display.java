package pl.edu.uj.tcs.student.tomaszwesolowski;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

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
		
		public static void setColor(Layer colourView, int r, int g, int b){
		    //Handle all conditions
			colourView.setColor(100, r, g, b);
		    Log.d("display", "setting..." + r + " " + g + " " + b);
		}

	}
	
	class Layer extends View
	{
		  private int a = 0;
		  private int b = 0;
		  private int g = 0;
		  private int r = 0;
		
		  public Layer(Context context){
		    super(context);
		  }
		  
		  @Override
		  protected void onDraw(Canvas canvas){
		    super.onDraw(canvas);
		    canvas.drawARGB(this.a, this.r, this.g, this.b);
		    Log.d("display", "rendering..");
		  }
		  
		  @Override
		    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		        this.setMeasuredDimension(parentWidth / 2, parentHeight);
		        //Since you are attatching it to the window use window layout params.
		        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(parentWidth / 2,
		                parentHeight);
		        this.setLayoutParams(layoutParams);

		        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		        Log.d("display", "filling...");
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
	public static final String SET_COLORS = "setColors";
	
	public Layer view;
	public Layer colourView;
	
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
			else if (SET_COLORS.equals(action)) {
				final JSONObject arg_object = args.getJSONObject(0);
				cordova.getActivity().runOnUiThread(new Runnable() {
					public void run() {
						setColorsViews();

						WindowManager.LayoutParams layout = cordova.getActivity().getWindow().getAttributes();
						try {
							if(arg_object.getInt("red") != -1) red = arg_object.getInt("red");
							if(arg_object.getInt("green") != -1) green = arg_object.getInt("green");
							if(arg_object.getInt("blue") != -1) blue = arg_object.getInt("blue");
							ScreenAdjuster.setColor(colourView, red, green, blue) ;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//
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
	
	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
		WindowManager localWindowManager = (WindowManager) cordova.getActivity().getWindowManager();
		localWindowManager.removeView(colourView);
		colorsFirstTime = false;
	}
	
	@Override
	public void onResume(boolean multitasking) {
		setColorsViews();
		ScreenAdjuster.setColor(colourView, red, green, blue) ;
	}
	
	public void setColorsViews() {
	    if (!colorsFirstTime) {
	        view = new Layer(cordova.getActivity());
	        colourView = new Layer(cordova.getActivity());
	        
	        WindowManager localWindowManager = (WindowManager) cordova.getActivity().getWindowManager();
	        LayoutParams layoutParams = new LayoutParams();
	        layoutParams.format = PixelFormat.TRANSLUCENT;
	        
	        layoutParams.type=LayoutParams.TYPE_SYSTEM_ALERT;
	        layoutParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
	        layoutParams.gravity=Gravity.LEFT|Gravity.TOP; 

	        //localWindowManager.addView(view, layoutParams);
	        try {
	        	localWindowManager.addView(colourView, layoutParams);
	        }
	        catch(Exception e) {
	        	localWindowManager.removeViewImmediate(colourView);
	        }
	        
	        //cordova.getActivity().getWindow().setAttributes(layoutParams);

	        colorsFirstTime = true;
	        Log.d("display", "views added");
	    }
	}
}