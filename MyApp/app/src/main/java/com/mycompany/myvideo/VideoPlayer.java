package com.mycompany.myvideo;
import android.content.pm.ActivityInfo;
import android.kz.BaseBean.Base;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class VideoPlayer extends Base
{

	VideoView vv;
	Uri dd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		fullscreen();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.hhh);
		vv=findViewById(R.id.hhhVideoView1);
		de();
		Toast.makeText(this,"正在解析",10).show();
		
		vv.start();
		Toast.makeText(this,util.id.toString(),10).show();
	
		vv.setMediaController(new MediaController(this));
		}
  public void de(){
	  
	  new Thread(new Runnable(){

			  @Override
			  public void run()
			  {
				  try
				  {

					  String tt = "{\"id\":\"" + util.id + "\",\"mod\":\"av\",\"code\":\"detail\",\"oauth_id\":\"fcdc0c6cd391a965\",\"oauth_new_id\":\"868982036927978\",\"oauth_type\":\"android_rn\",\"version\":\"3.1.0\",\"os_version\":\"9\",\"app_type\":\"rn\",\"bundleId\":\"com.sunrise\",\"via\":\"agent\",\"channel\":\"self\"}";
					  String e = Playlist.getSecondTime();
					  String fuck = EncryptManager.getInstance().encrypt(tt);
					  String sb =Playlist .getMd5(Playlist.getSHA256StrJava("data=" + fuck + "&timestamp=" +Playlist .getSecondTime() +Playlist. appKey));
					  String res =Playlist. doPost("http://lu_new.hitik.net:80/api.php?t=" + System.currentTimeMillis(), "timestamp=" + e + "&data=" + fuck + "&sign=" + sb, "");
					  JSONObject oii = new JSONObject(res);
					  String opo = EncryptManager.getInstance().decrypt(oii.getString("data"));
					  JSONObject yuy = new JSONObject(opo);
					  JSONArray line = yuy.getJSONArray("line");
					  JSONObject seco = (JSONObject) line.get(2);
					  String lines = seco.getString("line");
					  JSONObject klk = new JSONObject(lines);
					dd=   Uri.parse(klk.getString("s720"));
					  runOnUiThread(new Runnable(){

							  @Override
							  public void run()
							  {
								  vv.setVideoURI(dd);
							  }
						  });
				  }
				  catch (JSONException e)
				  {
					  runOnUiThread(new Runnable(){

							  @Override
							  public void run()
							  {
								  Toast.makeText(VideoPlayer.this,"无法播放",10).show();
								//  finish();
							  }
						  });
					  
				  }
			  }
		  }).start();
	  
  
  }
}
