package android.kz.BaseBean;
import android.app.Activity;
import android.os.Build;
import android.view.View;

public abstract class Base extends Activity
{
	
	
	
	public void fullscreen(){
		getWindow().addFlags(0x08000000);
		getWindow().addFlags(1024);
		if(getActionBar()!=null)
			getActionBar().hide();
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(uiOptions);
		}
		if((getRequestedOrientation()==2?true:null)!=null){
			SinkFullScreen s= SinkFullScreen.INSTANCE;
			s.blockStatusCutout(getWindow());
		}else{
			SinkFullScreen s= SinkFullScreen.INSTANCE;
			s.extendStatusCutout(getWindow(),this);
		}
	}
	
	
	
}


