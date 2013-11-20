package jp.kait.swkoubou.prowiz.chikara.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import jp.crudefox.chikara.util.CFUtil;
import jp.crudefox.chikara.util.CFUtil.HttpResponseData;
import jp.kait.swkoubou.prowiz.R;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * 		@author Chikara Funabashi
 * 		@date 2013/08/09
 *
 */

public class TimeLineManager {




	private static String TIMELINE_URL = CFConst.SERVER + CFConst.TIMELINE;

	private Context mContext;

	//private ArrayList<ConItem>mList = new ArrayList<ConItem>();

	private ArrayList<TItem> mList = new ArrayList<TItem>();


	public static class TItem implements Serializable{
		public long id;

//		public Date time_s;
//		public Date time_e;

		public Date time;

		public String title;
		public String summary;

		public String user_id;
		public String user_name;
		public Bitmap user_icon;

		public Bitmap image;
		//public String body;
		public int good_num;
		public int bad_num;

		public boolean enable_good;
		public boolean enable_bad;
	}




	public TimeLineManager(Context context){
		mContext = context;
	}


//	public synchronized boolean updateByRange(LoginInfo l,long start_time, long end_time){
//
//		if(!l.isLoggedIn()) return false;
//
//		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//
//		params.add(new BasicNameValuePair("token", l.sid.sid));
////		params.add(new BasicNameValuePair("range_start", ""+start_time));
////		params.add(new BasicNameValuePair("range_end", ""+end_time));
//
//		log("board 001");
//
//		String data = null;
//
//		//JSONObject json;
//		if(CFUtil.LOCAL_DEBUG){
//			//json = CUtil.getRawTextJson(mContext, R.raw.board_resp_sample);
//		}else{
//			data = CFUtil.postData(BOARD_URL, params);
//			//json = CUtil.postDataReturnJson(BOARD_URL, params);
//		}
//		if(data==null) return false;
//
//		log(data);
//
//		mList.clear();
//
//		log("board 002");
//
//		try {
//
//			log("board 003");
//
//			JSONObject json = new JSONObject(data);
//
//			String result = json.getString("result");
//
//			log("board 004");
//
//			if(!result.equalsIgnoreCase(LoginManager.BOARD_OK)){
//				return false;
//			}
//
//			log("board 005");
//
//			if(result.equalsIgnoreCase(LoginManager.BOARD_ERR_OVER_TIME_TOKEN)){
//				return false;
//			}
//
//			log("board 006");
//
//			JSONArray jdata = json.getJSONArray("data");
//
//			log("board 007");
//
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//			int  num = jdata.length();
//			for(int i=0;i<num;i++){
//				JSONObject jc = jdata.getJSONObject(i);
//				BItem item = new BItem();
//				item.id = jc.getInt("post_id");
//				item.user_name = jc.getString("name");
//				item.user_id = jc.getString("id");
//				item.time =  sdf.parse( jc.getString("post_time") ).getTime();
//				//item.body = jc.getString("body");
//				mList.add(item);
//			}
//
//			log("board 008");
//
//			return true;
//		} catch (JSONException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//			return false;
//		} catch (ParseException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//			return false;
//		}
//
//
//
//	}


//	public synchronized boolean deleteContoribute(LoginManager lf,LoginInfo li, int id){
//
//		if( lf.deleteContribute(li, id) ){
//			removeItemById(id);
//			return true;
//		}
//
//		return false;
//	}



	public synchronized TItem removeItemById(int id){
		TItem bitem = getItemById(id);
		if(bitem==null) return null;
		if( mList.remove(bitem) ) return bitem;
		return null;
	}





	public synchronized TItem getItemById(int id){
		for(int i=0;i<mList.size();i++){
			TItem item = mList.get(i);
			if( item.id == id ) return item;
		}
		return null;
	}

	public synchronized TItem getItemByIndex(int index){
		return mList.get(index);
	}
	public synchronized int getItemLength(){
		return mList.size();
	}


	public synchronized boolean update(LoginInfo l){

		if(CFConst.IS_MIRAI){
			return update_mirai(l);
		}



		if(!l.isLoggedIn()) return false;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", l.sid.sid));

		String data = null;

		data = CFUtil.postData( TIMELINE_URL, params);

		if(data==null) return false;

		log(data);

		mList.clear();

		try {

			JSONObject json = new JSONObject(data);

			String result = json.getString("result");

			if(!result.equalsIgnoreCase( "OK" ) ){
				return false;
			}

//			if(result.equalsIgnoreCase(LoginManager.BOARD_ERR_OVER_TIME_TOKEN )){
//				return false;
//			}

			Resources res = mContext.getResources();
			Bitmap[] bmps = new Bitmap[2];
			bmps[0] = ((BitmapDrawable) res.getDrawable(R.drawable.sample_picture_01)).getBitmap();
			bmps[1] = ((BitmapDrawable) res.getDrawable(R.drawable.sample_picture_02)).getBitmap();

			JSONArray jdata = json.getJSONArray("data_list");

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int  num = jdata.length();
			for(int i=0;i<num;i++){
				int rand = ((int)(Math.random()*100)) ;
				JSONObject jc = jdata.getJSONObject(i);

				TItem item = new TItem();
				//item.id = jc.getString("frend_id");
				item.id = CFUtil.getLongN(jc, "post_id", -1);

				item.title = CFUtil.getStringH(jc, "title", null);
				item.summary = CFUtil.getStringH(jc, "summary", null);

//				item.time_s = CFUtil.parseDateTmeOrDate( CFUtil.getStringH(jc, "times0_s", null) );
//				item.time_e = CFUtil.parseDateTmeOrDate( CFUtil.getStringH(jc, "times0_e", null) );

				item.time = CFUtil.parseDateTmeOrDate( CFUtil.getStringH(jc, "datetime", null) );

//				if(!jc.isNull("time0"))
//					item.time = CFUtil.pa jc.getString("time0");
//				else item.title = null

				item.user_id = CFUtil.getStringH(jc, "user_id", null);
				item.user_name = CFUtil.getStringH(jc, "user_name", null);

				item.good_num = (int) CFUtil.getLongN(jc, "good_num", 0);
				item.bad_num = (int) CFUtil.getLongN(jc, "bad_num", 0);

				item.enable_good = true;
				item.enable_bad = true;

				String image_url = CFUtil.getStringN(jc, "image_url", null);

//				item.picture = bmps[rand%2];

				//item.image = CFUtil.getImage(CFConst.SERVER + CFConst.POSTS_IMEGES_FROM_USER_ID_DIR+item.id, 500, 500);
				item.image = image_url!=null ? CFUtil.getImage(image_url, 500, 500) : null;

				mList.add(item);
			}


			return true;
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		}


	}



	public synchronized boolean update_mirai(LoginInfo l){

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		//params.add(new BasicNameValuePair("sid", l.sid.sid));

		ArrayList<Cookie> cookie = new ArrayList<Cookie>();
		cookie.add(new BasicClientCookie("PHPSESSID", l.sid.sid));

		for(int i=0;i<cookie.size();i++){
			BasicClientCookie c = (BasicClientCookie) cookie.get(i);
			c.setDomain("hellin.dip.jp");
			c.setPath("/");
		}


		HttpResponseData rd;

		rd = CFUtil.postData( "http://hellin.dip.jp/m/Timeline.php", params, cookie);

		if(rd==null || rd.data==null) return false;
		if(rd.data.equalsIgnoreCase("NG")) return false;

		log(rd.data);

		mList.clear();

		try {

			JSONObject json = new JSONObject(rd.data);


			JSONArray jdata = json.getJSONArray("timeline");

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int  num = jdata.length();
			for(int i=0;i<num;i++){
				//int rand = ((int)(Math.random()*100)) ;

				JSONObject jc = jdata.getJSONObject(i);

				TItem item = new TItem();
				item.id = CFUtil.getLongN(jc, "timelineid", -1);

				item.title = CFUtil.getStringH(jc, "timelinecontent", null);
				item.summary = CFUtil.getStringH(jc, "timelinecontent", null);

//				item.time_s = CFUtil.parseDateTmeOrDate( CFUtil.getStringH(jc, "times0_s", null) );
//				item.time_e = CFUtil.parseDateTmeOrDate( CFUtil.getStringH(jc, "times0_e", null) );

				item.time = CFUtil.parseDateTmeOrDate( CFUtil.getStringH(jc, "timelinetime", null) );

//				if(!jc.isNull("time0"))
//					item.time = CFUtil.pa jc.getString("time0");
//				else item.title = null

				item.user_id = CFUtil.getStringH(jc, "timelineuserid", null);
				item.user_name = CFUtil.getStringH(jc, "timelineusername", null);
				String user_image_url = CFUtil.getStringN(jc, "timelineicon", null);

				item.good_num = (int) CFUtil.getLongN(jc, "timelinegood", 0);
				item.bad_num = (int) CFUtil.getLongN(jc, "timelinebad", 0);

				item.enable_good = ((int) CFUtil.getLongN(jc, "timelinegoodbad", 0)) == 0 ? true : false ;
				item.enable_bad = item.enable_good;

				String image_url = CFUtil.getStringN(jc, "timelinepicture", null);

//				item.picture = bmps[rand%2];

				//item.image = CFUtil.getImage(CFConst.SERVER + CFConst.POSTS_IMEGES_FROM_USER_ID_DIR+item.id, 500, 500);
				item.image = image_url!=null ? CFUtil.getImage(image_url, 500, 500) : null;
				item.user_icon = image_url!=null ? CFUtil.getImage(user_image_url, 500, 500) : null;

				mList.add(item);
			}


			return true;
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		}


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
//		bmps[0] = ((BitmapDrawable) res.getDrawable(R.drawable.sample_picture_01)).getBitmap();
//		bmps[1] = ((BitmapDrawable) res.getDrawable(R.drawable.sample_picture_02)).getBitmap();
//
//		Bitmap user_icon = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
//
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		ArrayList<BItem> list  = new ArrayList<TimeLineManager.BItem>();
//
//		int num = ((int)(Math.random()*10)) + 10;
//
//		try{
//			for(int i=0;i<num;i++){
//				int rand = ((int)(Math.random()*100)) ;
//
//				BItem item = new BItem();
//				item.id = 1000+i;
//				item.user_name = "投稿モック"+(i+1)+"さん";
//				item.user_id = "user"+(i+1);
//				item.user_icon = user_icon;
//				item.time =  sdf.parse( "2020-08-09 00:00:00" ).getTime() + 1000 * 60 * 60 * i;
//				item.picture = bmps[rand%2];
//
//				list.add(item);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		synchronized (TimeLineManager.this) {
//			mList.clear();
//			mList = list;
//			sortByDate(false);
//		}
//
//		return true;
//	}


	private synchronized void sortByDate(final boolean asc){

		Comparator<TItem> comp;

		if(asc){
			comp = new Comparator<TItem>() {
				@Override
				public int compare(TItem lhs, TItem rhs) {
					if( lhs.time!=null ) return lhs.time.compareTo( rhs.time );
					return 1;
				}
			};
		}else{
			comp = new Comparator<TItem>() {
				@Override
				public int compare(TItem lhs, TItem rhs) {
					if( lhs.time!=null ) return lhs.time.compareTo( rhs.time ) * -1;
					return -1;
				}
			};
		}


		Collections.sort(mList, comp);


	}




	public static void log(String str){
		android.util.Log.d("test", str);
	}




}
