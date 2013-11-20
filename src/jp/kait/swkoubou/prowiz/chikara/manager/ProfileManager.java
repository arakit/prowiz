package jp.kait.swkoubou.prowiz.chikara.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import jp.crudefox.chikara.util.CFUtil;
import jp.crudefox.chikara.util.CFUtil.HttpResponseData;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



/**
 * 		@auth Chikara Funabashi
 * 		@first_date 2013/08/09
 *
 */


public class ProfileManager {




	private static String GET_PROFILE_URL = CFConst.SERVER + CFConst.PROFILE;
	private static String SET_PROFILE_URL = CFConst.SERVER + CFConst.SET_PROFILE;

	private Context mContext;

	private final Profile mProfile = new Profile();


	public static class Profile implements Serializable{
		public String name;
		public Bitmap icon;
		public Bitmap bg;
		public Date birthday;
		public String introduction;
		public String gender;

		public void clear(){
			icon = null;
			bg =null;
			birthday = null;
			introduction = null;
			gender = null;
		}
	}




	public ProfileManager(Context context){
		mContext = context;
	}


	public synchronized boolean update(LoginInfo l){
		
		if(CFConst.IS_MIRAI){
			return update_mirai(l);
		}

		if(!l.isLoggedIn()) return false;

		//ArrayList<FormBodyPart> params;
		ArrayList<NameValuePair> params;


		//params = new ArrayList<FormBodyPart>();

		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid", l.sid.sid) );
		params.add(new BasicNameValuePair("item","user_introduction"));
		params.add(new BasicNameValuePair("item","user_name"));
		params.add(new BasicNameValuePair("item","user_birthday"));
		params.add(new BasicNameValuePair("item","user_gender"));
		params.add(new BasicNameValuePair("item","user_icon"));

//		try{
//			params.add(new FormBodyPart("sid",  new StringBody(l.sid.sid)) );
//			params.add(new FormBodyPart("item", new StringBody("user_introduction")) );
//			params.add(new FormBodyPart("item", new StringBody("user_name")) );
//			params.add(new FormBodyPart("item", new StringBody("user_birthday")) );
//			params.add(new FormBodyPart("item", new StringBody("user_gender")) );
//			params.add(new FormBodyPart("item", new StringBody("user_icon")) );
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}


		String data = CFUtil.postData(GET_PROFILE_URL, params);

		//JSONObject json = CFUtil.postDataReturnJson( GET_PROFILE_URL, params);

		try{
			JSONObject json = new JSONObject(data);

			String result = json.getString("result");
			if(!result.equals("OK")) return false;

			String data_introduction = json.getString("user_introduction");
			mProfile.introduction = data_introduction;

			String data_name = json.getString("user_name");
			mProfile.name = data_name;

			String data_birthday = json.getString("user_birthday");
			Date birthday_date = "null".equals(data_birthday) ?
					null :
					CFUtil.parseDate(data_birthday);
			mProfile.birthday = birthday_date;

			String data_gender = json.getString("user_gender");
			mProfile.gender = data_gender;

			String data_icon = json.getString("user_icon");
			byte[] bytes_icon = data_icon!=null ? CFUtil.toBytesFromBase64(data_icon) : null;
			Bitmap icon_icon = null;
			if(bytes_icon!=null){
				icon_icon = BitmapFactory.decodeByteArray(bytes_icon, 0, bytes_icon.length);
			}
			mProfile.icon = icon_icon;


		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}



		return true;

	}
	
	public synchronized boolean update_mirai(LoginInfo l){

		if(!l.isLoggedIn()) return false;

		//ArrayList<FormBodyPart> params;
		ArrayList<NameValuePair> params;

		params = new ArrayList<NameValuePair>();
		
		
		ArrayList<Cookie> cookie = new ArrayList<Cookie>();
		cookie.add(new BasicClientCookie("PHPSESSID", l.sid.sid));

		for(int i=0;i<cookie.size();i++){
			BasicClientCookie c = (BasicClientCookie) cookie.get(i);
			c.setDomain("hellin.dip.jp");
			c.setPath("/");
		}

		HttpResponseData rd = CFUtil.postData("http://hellin.dip.jp/m/DisplayChangeProfile.php", params, cookie);
		
		if(rd==null || rd.data==null) return false;
		

		try{
			JSONObject json = new JSONObject(rd.data);

			String data_introduction = json.getString("changeprofile");
			mProfile.introduction = data_introduction;

			String data_name = json.getString("changename");
			mProfile.name = data_name;

			String data_birthday = json.getString("changebirth");
			Date birthday_date = "null".equals(data_birthday) ?
					null :
					CFUtil.parseDate(data_birthday);
			mProfile.birthday = birthday_date;

			String data_gender = json.getString("changesex");
			mProfile.gender = data_gender;

//			String data_icon = json.getString("user_icon");
//			byte[] bytes_icon = data_icon!=null ? CFUtil.toBytesFromBase64(data_icon) : null;
//			Bitmap icon_icon = null;
//			if(bytes_icon!=null){
//				icon_icon = BitmapFactory.decodeByteArray(bytes_icon, 0, bytes_icon.length);
//			}
//			mProfile.icon = icon_icon;


		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}



		return true;

	}
	
	


	public synchronized boolean save(LoginInfo l){

		if(!l.isLoggedIn()) return false;

		String str = mProfile.introduction;
		if(str==null) return false;

		ArrayList<FormBodyPart> params;

		params = new ArrayList<FormBodyPart>();

		try{
			//ArrayList<NameValuePair> params;

			//params = new ArrayList<NameValuePair>();
			params.add(new FormBodyPart("sid", new StringBody(l.sid.sid)));

			//紹介文
			params.add(new FormBodyPart("user_introduction", new StringBody(mProfile.introduction) ) );

			//名前
			params.add(new FormBodyPart("user_name", new StringBody(mProfile.name)));

			//性別
			params.add(new FormBodyPart("user_gender", new StringBody(mProfile.gender)));

			//誕生日
			params.add(new FormBodyPart("user_birthday",
					new StringBody(
							mProfile.birthday!=null ? CFUtil.toDateString(mProfile.birthday) : "null"  ))
					);

			//アイコン
			//String data_icon = null;
			InputStream in = null;
			if(mProfile.icon!=null){
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				mProfile.icon.compress(Bitmap.CompressFormat.PNG, 100, os);
				byte[] buf = os.toByteArray();
				//data_icon = CFUtil.toBase64FromBytes(buf);
				in = new ByteArrayInputStream(buf, 0, buf.length);
			}
			params.add(new FormBodyPart("user_icon", new InputStreamBody(in, "image.png") ) );

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		boolean suc_intro = CFUtil.postMultiPartData( SET_PROFILE_URL, params, null) != null;

		return suc_intro;

	}




	public synchronized void clear(){

		mProfile.clear();

	}


//	public boolean _update_mock(LoginInfo l){
//
//
//		try {
//			// Simulate network access.
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			//if(outResult!=null) outResult[0] = LOGIN_ERR_CONNECT;
//			return false;
//		}
//
//		Resources res = mContext.getResources();
//		Bitmap[] bmps = new Bitmap[2];
//		bmps[0] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
//		bmps[1] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
//
//		//Bitmap user_icon = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
//
//
//		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		ArrayList<MItem> list  = new ArrayList<ProfileManager.MItem>();
//
//		int num = ((int)(Math.random()*10)) + 10;
//
//		try{
//			for(int i=0;i<num;i++){
//				int rand = ((int)(Math.random()*100)) ;
//
//				MItem item = new MItem();
//				item.id = "user"+(i+1);
//				item.name = "投稿モック"+(i+1)+"さん";
//				item.icon = bmps[rand%2];
//
//				list.add(item);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		synchronized (ProfileManager.this) {
//			mList.clear();
//			mList = list;
//			sortByDate(false);
//		}
//
//		return true;
//	}


	public Profile getProfile(){
		return mProfile;
	}




	public static void log(String str){
		android.util.Log.d("test", str);
	}




}
