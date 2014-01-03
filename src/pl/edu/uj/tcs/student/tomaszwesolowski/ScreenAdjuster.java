package pl.edu.uj.tcs.student.tomaszwesolowski;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ScreenAdjuster {

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
	}

}
