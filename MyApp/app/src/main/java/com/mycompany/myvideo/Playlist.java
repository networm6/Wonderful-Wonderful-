package com.mycompany.myvideo;
import android.kz.BaseBean.Base;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.mycompany.myvideo.adap.OnRecyclerViewScrollListener;
import com.mycompany.myvideo.adap.StudyRvAdapter;
import com.szcx.lib.encrypt.utils.MD5Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Playlist extends Base
{
  RecyclerView lv;
  EditText ed;
	public static String appKey = "0d27dfacef1338483561a46b246bf36d";
    String nn;
	int pages=1;
	ArrayList datas=new ArrayList<Bean>();
	StudyRvAdapter adp;
	boolean issearch=false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		fullscreen();
		setContentView(R.layout.list);
		lv=findViewById(R.id.listListView1);
		ed=findViewById(R.id.listEditText1);
		lv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));//纵向
		
		
		adp = new StudyRvAdapter(datas, this);
		
		
		lv.setAdapter(adp);
		lv.addOnScrollListener(new OnRecyclerViewScrollListener() {
				@Override
				public void onBottom() {
					super.onBottom();
					Toast.makeText(getApplicationContext(), "正在加载", Toast.LENGTH_SHORT).show();
					if(!issearch){
				pages++;
				search();
				}
					}
			});
	}
	
	public void gogo(View v){
		nn=ed.getText().toString();
		pages=1;
		datas.clear();
		adp.notifyDataSetChanged();
		search();
			
	}
	public void search(){
		issearch=true;
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					try
					{
						dosearch();
						runOnUiThread(new Runnable(){

								@Override
								public void run()
								{
									//Loger.d("setadapter","go");
									adp.notifyDataSetChanged();

								}
							});
					
					}
					catch (JSONException e)
					{
						//Loger.d("tag",e.getMessage());
					}
					issearch=false;
				}
			}).start();
	}
	public void dosearch() throws JSONException {
		
        EncryptManager.getInstance().init("74029765cfeaf8dd791322dfd24691b4", "0d27dfacef1338483561a46b246bf36d");
        if(EncryptManager.getInstance().isCanEncrypt()) {
            String form = "{\"mod\":\"search\",\"code\":\"index\",\"type\":\"av\",\"tag\":\""+ nn + "\",\"page\":"+pages+",\"oauth_id\":\"5492611e14f9ce45\",\"oauth_new_id\":\"861305031143184\",\"oauth_type\":\"android_rn\",\"version\":\"3.1.0\",\"os_version\":\"7.1.2\",\"app_type\":\"rn\",\"bundleId\":\"com.sunrise\",\"via\":\"agent\",\"channel\":\"self\"}";
            String timestamp = getSecondTime();
            String data = EncryptManager.getInstance().encrypt(form);
            String sign = getMd5(getSHA256StrJava("data=" + data + "&timestamp=" + getSecondTime() + appKey));
            String result = doPost("http://lu_new.tiansexyl.tv:80/api.php?t=" + System.currentTimeMillis(), "timestamp="+timestamp+"&data="+data+"&sign="+sign, "");
            JSONObject json = new JSONObject(result);
            String dataArr = EncryptManager.getInstance().decrypt(json.getString("data"));
            JSONObject dec = new JSONObject(dataArr);
            JSONArray arr = dec.getJSONArray("data");
            for(int i = 0; i < arr.length(); i++) {
				Bean one=new Bean();
                JSONObject js = (JSONObject) arr.get(i);
                String id = js.getString("id");
                String title = js.getString("title");
				one.setId(id);
				one.setTitle(title);
                System.out.println("id: "+id+"    title: "+title);
            datas.add(one);
				}
		}
		
	}
	public static String getSecondTime() {
        return String.format("%010d", new Object[]{Long.valueOf(System.currentTimeMillis() / 1000)});
    }

    private static String getCookie() throws IOException  {
        URL url = new URL("http://lu_new.tiansexyl.tv:80/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getHeaderField("Set-Cookie");
    }

    public static String doPost(String url, String params, String cookie)  {
        
		try
		{
			URL urls = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
            //conn.setRequestProperty("Cookie", cookie);
            conn.setRequestProperty("Host", "sir_new.hitik.org:8080");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.connect();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(params);
            writer.close();

            InputStream is =  conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String inputLine;
            StringBuilder result = new StringBuilder("");
            if ((inputLine = br.readLine()) != null)
			{
                result.append(inputLine);
            }
            conn.disconnect();
            return result.toString();
		}
		catch (ProtocolException e)
		{
			return "";
		}
		catch(IOException e){
			return "";
		}

    }

    public static String getMd5(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return MD5Util.getMD5(str);
    }

    public static String getSHA256StrJava(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes("UTF-8"));
            return byte2Hex(instance.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String byte2Hex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }
}
