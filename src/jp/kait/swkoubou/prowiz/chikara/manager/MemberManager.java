package jp.kait.swkoubou.prowiz.chikara.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

public class MemberManager {


	/*		Auth: Chikara Funabashi
	 * 		Date: 2013/08/09
	 *
	 */


	//static boolean LOCAL_DEBUG = true;


//	public static int LOGIN_RESULT_OK = 0;
//	public static int LOGIN_RESULT_FAILED = -1;


	private static final String FREDNS_OK = "OK";



	private static String FRENDS_URL = CFConst.SERVER + CFConst.FREDNS;
	private static String SEARCH_FRENDS_URL = CFConst.SERVER + CFConst.SEARCH_FRENDS;

	private Context mContext;

	//private ArrayList<ConItem>mList = new ArrayList<ConItem>();

	private ArrayList<MItem> mList = new ArrayList<MemberManager.MItem>();


	public static class MItem implements Serializable{
		public String id;
		public String name;
		public Bitmap icon;
		//public String body;
	}




	public MemberManager(Context context){
		mContext = context;
	}


	//http://hellin.dip.jp/m/MemberList.php

	public synchronized boolean update_mirai(LoginInfo l){

		if(!l.isLoggedIn()) return false;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();


		ArrayList<Cookie> cookie = new ArrayList<Cookie>();
		cookie.add(new BasicClientCookie("PHPSESSID", l.sid.sid));

		for(int i=0;i<cookie.size();i++){
			BasicClientCookie c = (BasicClientCookie) cookie.get(i);
			c.setDomain("hellin.dip.jp");
			c.setPath("/");
		}

		log("member 001");


		HttpResponseData rd;

		rd = CFUtil.postData( "http://hellin.dip.jp/m/MemberList.php", params, cookie);

		if(rd == null || rd.data == null) return false;

		log(rd.data);

		mList.clear();

		log("frends 002");

		try {

			log("frends 003");

			JSONObject json = new JSONObject(rd.data);

//			if(result.equalsIgnoreCase(LoginManager.BOARD_ERR_OVER_TIME_TOKEN )){
//				return false;
//			}

//			Resources res = mContext.getResources();
//			Bitmap[] bmps = new Bitmap[2];
//			bmps[0] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
//			bmps[1] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();

			JSONArray jdata = json.getJSONArray("friendlist");

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int  num = jdata.length();
			for(int i=0;i<num;i++){
				JSONObject jc = jdata.getJSONObject(i);

				MItem item = new MItem();
				item.id = jc.getString("friend");

				item.name = item.id;

//				if(!jc.isNull("user_name"))
//					item.name = jc.getString("user_name");
//				else item.name = null;
//
				item.icon = CFUtil.getImage(jc.getString("icon"), 500, 500);

				mList.add(item);
			}

			CFUtil.Log("num="+mList.size());

			return true;
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			CFUtil.Log("member ", e);
			return false;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			CFUtil.Log("member ", e);
			return false;
		}


	}



	public synchronized boolean update(LoginInfo l){

		if(CFConst.IS_MIRAI){
			return update_mirai(l);
		}

		if(!l.isLoggedIn()) return false;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", l.sid.sid));
//		params.add(new BasicNameValuePair("range_start", ""+start_time));
//		params.add(new BasicNameValuePair("range_end", ""+end_time));

		log("member 001");

		String data = null;

		//JSONObject json;
//		if(CFUtil.LOCAL_DEBUG){
//			//json = CUtil.getRawTextJson(mContext, R.raw.board_resp_sample);
//		}else{
//
//			//json = CUtil.postDataReturnJson(BOARD_URL, params);
//		}

		data = CFUtil.postData( FRENDS_URL, params);

		if(data==null) return false;

		log(data);

		mList.clear();

		log("frends 002");

		try {

			log("frends 003");

			JSONObject json = new JSONObject(data);

			String result = json.getString("result");

			log("frends 004");

			if(!result.equalsIgnoreCase("OK") ){
				return false;
			}

//			if(result.equalsIgnoreCase(LoginManager.BOARD_ERR_OVER_TIME_TOKEN )){
//				return false;
//			}

//			Resources res = mContext.getResources();
//			Bitmap[] bmps = new Bitmap[2];
//			bmps[0] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
//			bmps[1] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();

			JSONArray jdata = json.getJSONArray("data_list");

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int  num = jdata.length();
			for(int i=0;i<num;i++){
				JSONObject jc = jdata.getJSONObject(i);

				MItem item = new MItem();
				item.id = jc.getString("user_id");

				if(!jc.isNull("user_name"))
					item.name = jc.getString("user_name");
				else item.name = null;

				item.icon = CFUtil.getImage(CFConst.SERVER+CFConst.PROFILE_IMEGES_FROM_USER_ID_DIR+item.id, 500, 500);

				mList.add(item);
			}

			CFUtil.Log("num="+mList.size());

			return true;
		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			CFUtil.Log("member ", e);
			return false;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			CFUtil.Log("member ", e);
			return false;
		}


	}


	public synchronized boolean search(LoginInfo l, String q){

		if(!l.isLoggedIn()) return false;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", l.sid.sid));
		params.add(new BasicNameValuePair("q", q));
//		params.add(new BasicNameValuePair("range_start", ""+start_time));
//		params.add(new BasicNameValuePair("range_end", ""+end_time));


		String data = null;

		//JSONObject json;
//		if(CFUtil.LOCAL_DEBUG){
//			//json = CUtil.getRawTextJson(mContext, R.raw.board_resp_sample);
//		}else{
//
//			//json = CUtil.postDataReturnJson(BOARD_URL, params);
//		}

		data = CFUtil.postData( SEARCH_FRENDS_URL, params);

		if(data==null) return false;

		log(data);

		mList.clear();


		try {

			JSONObject json = new JSONObject(data);

			String result = json.getString("result");

			if(!result.equalsIgnoreCase("OK") ){
				return false;
			}

			JSONArray jdata = json.getJSONArray("data_list");

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int  num = jdata.length();
			for(int i=0;i<num;i++){
				JSONObject jc = jdata.getJSONObject(i);

				MItem item = new MItem();
				item.id = jc.getString("user_id");

				if(!jc.isNull("user_name"))
					item.name = jc.getString("user_name");
				else item.name = null;

				item.icon = CFUtil.getImage(CFConst.SERVER+CFConst.PROFILE_IMEGES_FROM_USER_ID_DIR+item.id, 500, 500);

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


//	public synchronized boolean deleteContoribute(LoginManager lf,LoginInfo li, int id){
//
//		if( lf.deleteContribute(li, id) ){
//			removeItemById(id);
//			return true;
//		}
//
//		return false;
//	}



	public synchronized MItem removeItemById(String id){
		MItem mitem = getItemById(id);
		if(mitem==null) return null;
		if( mList.remove(mitem) ) return mitem;
		return null;
	}


	public synchronized void clear(){

		mList.clear();

	}




	public synchronized MItem getItemById(String id){
		for(int i=0;i<mList.size();i++){
			MItem item = mList.get(i);
			if( item.id.equals(id) ) return item;
		}
		return null;
	}

	public synchronized MItem getItemByIndex(int index){
		if(index>=mList.size() || index<0) return null;
		return mList.get(index);
	}
	public synchronized int getItemLength(){
		return mList.size();
	}





	public boolean _update_mock(LoginInfo l){


		try {
			// Simulate network access.
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			//if(outResult!=null) outResult[0] = LOGIN_ERR_CONNECT;
			return false;
		}

		Resources res = mContext.getResources();
		Bitmap[] bmps = new Bitmap[2];
		bmps[0] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();
		bmps[1] = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();

		//Bitmap user_icon = ((BitmapDrawable) res.getDrawable(R.drawable.ic_launcher)).getBitmap();


		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ArrayList<MItem> list  = new ArrayList<MemberManager.MItem>();

		int num = ((int)(Math.random()*10)) + 10;

		try{
			for(int i=0;i<num;i++){
				int rand = ((int)(Math.random()*100)) ;

				MItem item = new MItem();
				item.id = "user"+(i+1);
				item.name = "投稿モック"+(i+1)+"さん";
				item.icon = bmps[rand%2];

				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		synchronized (MemberManager.this) {
			mList.clear();
			mList = list;
			sortByDate(false);
		}

		return true;
	}


	private synchronized void sortByDate(final boolean asc){

		Comparator<MItem> comp;

		if(asc){
			comp = new Comparator<MemberManager.MItem>() {
				@Override
				public int compare(MItem lhs, MItem rhs) {
//					if(lhs.name<rhs.time) return -1;
//					if(lhs.time>rhs.time) return +1;
//					return 0;
					return lhs.name.compareTo(rhs.name);
				}
			};
		}else{
			comp = new Comparator<MemberManager.MItem>() {
				@Override
				public int compare(MItem lhs, MItem rhs) {
//					if(lhs.time<rhs.time) return +1;
//					if(lhs.time>rhs.time) return -1;
//					return 0;
					return lhs.name.compareTo(rhs.name);
				}
			};
		}


		Collections.sort(mList, comp);


	}




	public static void log(String str){
		android.util.Log.d("test", str);
	}




}
