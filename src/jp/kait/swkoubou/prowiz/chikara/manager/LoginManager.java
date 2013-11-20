package jp.kait.swkoubou.prowiz.chikara.manager;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import jp.crudefox.chikara.util.CFUtil;
import jp.crudefox.chikara.util.CFUtil.HttpResponseData;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;


/**
 * 		@auth: Chikara Funabashi
 * 		@date: 2013/07/06
 *
 */

public class LoginManager {






	private static String LOGIN_URL = CFConst.SERVER + CFConst.LOGIN;
	private static String CREATE_ACCOUNT_URL = CFConst.SERVER + CFConst.CREATE_ACCOUNT;
//	private static String CONTRIBUTE_URL = CFConst.SERVER + CFConst.CONTRIBUTE;
//	private static String DEL_CONTRIBUTE_URL = CFConst.SERVER + CFConst.DEL_CONTRIBUTE;
	private static String POST_POSTS_URL = CFConst.SERVER + CFConst.POST_POSTS;
	private static String DEL_POSTS_URL = CFConst.SERVER + CFConst.DELETE_POSTS;
	private static String SET_GOODS_URL = CFConst.SERVER + CFConst.SET_GOODS;
	private static String FOLLOW_URL = CFConst.SERVER + CFConst.FOLLOW;

	private static String HEALLIN_COMMENT_URL = CFConst.SERVER + CFConst.HEALLIN_COMMENT;


	private Context mContext;

	public LoginManager(Context context){
		mContext = context;
	}


	public static final String DEL_OK = "OK";

	public static final String BOARD_OK = "OK";
	public static final String BOARD_ERR_OVER_TIME_TOKEN = "E04";

	public static final String CONTOBUTE_OK = "OK";

	public static final String SIGN_UP_OK = "OK";
	public static final String SIGN_UP_ERR_CONNECT = "Err_Connect";

	public static final String LOGIN_OK = "OK";
	public static final String LOGIN_ERR_CONNECT = "Err_Connect";
	public static final String LOGIN_ERR_DATA = "Err_Data";
	public static final String LOGIN_ERR_DB = "E01";
	public static final String LOGIN_ERR_SELECT = "E02";
	public static final String LOGIN_ERR_NOT_REG_ID = "E03";
	public static final String LOGIN_ERR_NOT_MATCH_PASS = "E04";


	//E01 データベースの接続不良
	//E02 select失敗
	//E03 ID未登録
	//E04 : パスワード不一位


	public LoginInfo login_mirai(String id, String password,String[] outResult){
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("userid", id));
		params.add(new BasicNameValuePair("password", password));

		log("00001");

		HttpResponseData rd = null;
		//String data = null;

//		ArrayList<Cookie> cookie = new ArrayList<Cookie>();
//		cookie.add(new DefaultC);

		rd = CFUtil.postData("http://hellin.dip.jp/m/Login.php", params, null);

		if(rd==null || rd.data==null){
			if(outResult!=null) outResult[0] = LOGIN_ERR_CONNECT;
			return null;
		}

		log("a="+rd.data);

		log("login 00002");

	    for (int i = 0; i < rd.cookies.size(); i++) {
			StringBuffer sb = new StringBuffer();
			Cookie c = rd.cookies.get(i);
			sb.append(c);
			sb.append(" / ");
			sb.append(c.getName());
			sb.append(" / ");
			sb.append(c.getValue());
			sb.append(" / ");
			sb.append(c.getDomain());
			sb.append(" / ");
			sb.append(c.getVersion());
			sb.append(" / ");
			sb.append(c.getExpiryDate());
			sb.append(" / ");
			sb.append(c.getPath());
			sb.append(" / ");
			sb.append(c.getComment());
			sb.append("\n");
			log(sb.toString());
	    }

	    if(!rd.data.equalsIgnoreCase("OK")){
			if(outResult!=null) outResult[0] = rd.data;
			return null;//LOGIN_RESULT_FAILED;
	    }

		Cookie session_cookie = null;
	    for (int i = 0; i < rd.cookies.size(); i++) {
			Cookie c = rd.cookies.get(i);
			if( c.getName().equalsIgnoreCase("PHPSESSID") ){
				session_cookie = c;
				break;
			}
	    }

	    if(session_cookie==null){
			if(outResult!=null) outResult[0] = rd.data;
			return null;//LOGIN_RESULT_FAILED;
	    }

	    String sid = session_cookie.getValue();

		LoginInfo lf = new LoginInfo();
		lf.sid = new SessionID(sid);
		lf.id = id;
		lf.password = password;

		return lf;

	}

	//ログインをします。
	public LoginInfo login(String id, String password,String[] outResult){

		if(true){
			LoginInfo lf = new LoginInfo();
			lf.sid = new SessionID("feskfsujfd");
			lf.id = id;
			lf.password = password;
			return lf;
		}

//		if(CFConst.IS_MIRAI){
//			return login_mirai(id, password, outResult);
//		}

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("user_id", id));
		params.add(new BasicNameValuePair("user_password", password));

		log("00001");

		String data = null;
		if(CFUtil.LOCAL_DEBUG){
			//data = CUtil.getRawText(mContext, R.raw.login_resp_sample);

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				if(outResult!=null) outResult[0] = LOGIN_ERR_CONNECT;
				return null;
			}

		}else{
			data = CFUtil.postData(LOGIN_URL, params);
		}

		if(data==null){
			if(outResult!=null) outResult[0] = LOGIN_ERR_CONNECT;
			return null;
		}



		log("a="+data);

		log("00002");


		try {
			JSONObject json = new JSONObject(data);

			String result = json.getString("result");
			String sid = json.getString("sid");
			//String name = json.getString("user_name");

			log("00004");

			if(!result.equalsIgnoreCase(LOGIN_OK)){
				if(outResult!=null) outResult[0] = result;
				return null;//LOGIN_RESULT_FAILED;
			}
			if(TextUtils.isEmpty(sid)){
				if(outResult!=null) outResult[0] = LOGIN_ERR_DATA;
				return null;//LOGIN_RESULT_FAILED;
			}
//			if(TextUtils.isEmpty(name)){
//				if(outResult!=null) outResult[0] = LOGIN_ERR_DATA;
//				return null;//LOGIN_RESULT_FAILED;
//			}

			log("00005");

			LoginInfo lf = new LoginInfo();
			lf.sid = new SessionID(sid);
			lf.id = id;
			lf.password = password;
			//lf.name = name;

			return lf;

		} catch (JSONException e) {
			e.printStackTrace();
			log("00003");
			if(outResult!=null) outResult[0] = LOGIN_ERR_DATA;
			return null;
		}
		 catch (Exception e) {
			e.printStackTrace();
			if(outResult!=null) outResult[0] = LOGIN_ERR_DATA;
			log("00003-b");
			return null;
		}

	}

	//ログインをし直します。
	public boolean retryLogin(LoginInfo lf,String[] outResult){

		LoginInfo new_lf = login(lf.id, lf.password,outResult);

		if(new_lf==null) return false;

		lf.id = new_lf.id;
		lf.password = new_lf.password;
		//lf.name = new_lf.name;
		lf.sid = new_lf.sid;

		return true;
	}

	//アカウントを作成します
	public boolean createUser(String id, String password,String name, String[] outResult){

		if(CFConst.IS_MIRAI){
			return createUser_mirai(id, password, name, outResult);
		}

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("user_id", id));
		params.add(new BasicNameValuePair("user_password", password));
		//params.add(new BasicNameValuePair("repass", password));
		params.add(new BasicNameValuePair("user_name", name));

		String data = null;
		if(CFUtil.LOCAL_DEBUG){
			//data = CUtil.getRawText(mContext, R.raw.sign_up_resp_sample);
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				if(outResult!=null) outResult[0] = SIGN_UP_ERR_CONNECT;
				return false;
			}
		}else{
			data = CFUtil.postData(CREATE_ACCOUNT_URL, params);
		}

		if(data==null) {
			if(outResult!=null) outResult[0] = SIGN_UP_ERR_CONNECT;
			return false;
		}

		try {
			JSONObject json = new JSONObject(data);

			String result = json.getString("result");
//			String id = json.getString("id");
//			String name = json.getString("name");
//			String pass = json.getString("pass");

//			boolean result = json.getBoolean("result");

			if(!result.equalsIgnoreCase(SIGN_UP_OK)){
				if(outResult!=null) outResult[0] = result;
				return false;
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			if(outResult!=null) outResult[0] = SIGN_UP_ERR_CONNECT;
			return false;
		}


	}

	//アカウントを作成します
	public boolean createUser_mirai(String id, String password,String name, String[] outResult){

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("newuserid", id));
		params.add(new BasicNameValuePair("newpassword", password));
		params.add(new BasicNameValuePair("newusername", name));

		HttpResponseData rd = null;

		rd = CFUtil.postData("http://hellin.dip.jp/m/NewRegistation.php", params, null);


		if(rd==null || rd.data ==null) {
			if(outResult!=null) outResult[0] = SIGN_UP_ERR_CONNECT;
			return false;
		}

		if(!rd.data.equalsIgnoreCase("OK")) return false;

		return true;


	}



	public class SetGoodInfo{
		public long post_id;
		public int good_num;
		public int bad_num;
	}

	//いいねする
	public SetGoodInfo setGood(LoginInfo lf, long post_id, String good_value){

		if(CFConst.IS_MIRAI){
			return setGood_mirai(lf, post_id, good_value);
		}

		if(TextUtils.isEmpty(good_value) || !lf.isLoggedIn()){
			return null;
		}

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", lf.sid.sid));
		params.add(new BasicNameValuePair("post_id", ""+post_id));
		params.add(new BasicNameValuePair("value", good_value));

		String data = null;

		data = CFUtil.postData(SET_GOODS_URL, params);



		try {
			JSONObject json = new JSONObject(data);

			SetGoodInfo info = new SetGoodInfo();

			String result = json.getString("result");
//			int res_post_id = json.getInt("post_id");
			info.post_id = post_id;
			info.good_num = json.getInt("good_num");
			info.bad_num = json.getInt("bad_num");
//			long res_post_time = json.getLong("post_time");
//			int res_body = json.getInt("body");
//			String res_token = json.getString("");

			if(!result.equalsIgnoreCase("OK")){
				return null;
			}

			return info;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		 catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	//いいねする
	public SetGoodInfo setGood_mirai(LoginInfo l, long post_id, String good_value){

		if(TextUtils.isEmpty(good_value) || !l.isLoggedIn()){
			return null;
		}

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("contentnumber", ""+post_id));

		ArrayList<Cookie> cookie = new ArrayList<Cookie>();
		cookie.add(new BasicClientCookie("PHPSESSID", l.sid.sid));

		for(int i=0;i<cookie.size();i++){
			BasicClientCookie c = (BasicClientCookie) cookie.get(i);
			c.setDomain("hellin.dip.jp");
			c.setPath("/");
		}

		HttpResponseData rd;
		if(good_value.equals("good")){
			rd = CFUtil.postData("http://hellin.dip.jp/m/Good.php", params, cookie);
		}else{
			rd = CFUtil.postData("http://hellin.dip.jp/m/Bad.php", params, cookie);
		}


		if(rd==null || rd.data==null) return null;

		log(rd.data);


		SetGoodInfo info = new SetGoodInfo();

		info.post_id = post_id;

		return info;

	}



	//投稿をします
	public long submitPost_mirai(LoginInfo l, String title, String summary, InputStream in, String name, int eat_time){

//		if(TextUtils.isEmpty(body)){
//			return -1;
//		}

		ArrayList<FormBodyPart> params = new ArrayList<FormBodyPart>();

		try {
			Charset charset = Charset.forName("utf-8");

			params.add(new FormBodyPart("postcontent", new StringBody(title, charset)));
			params.add(new FormBodyPart("timezone", new StringBody(""+eat_time)));
			params.add(new FormBodyPart("postpicture", new InputStreamBody(in, name)));


		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return -1;
		}


		ArrayList<Cookie> cookie = new ArrayList<Cookie>();
		cookie.add(new BasicClientCookie("PHPSESSID", l.sid.sid));

		for(int i=0;i<cookie.size();i++){
			BasicClientCookie c = (BasicClientCookie) cookie.get(i);
			c.setDomain("hellin.dip.jp");
			c.setPath("/");
		}


		HttpResponseData rd = new HttpResponseData();

		rd = CFUtil.postMultiPartData("http://hellin.dip.jp/m/PostTimelineContent.php", params, cookie);


		if(rd==null || rd.data==null) return -1;

		log(rd.data);

		if( rd.data.equalsIgnoreCase("NG") ) return -1;

		return 1;

//		try {
//			JSONObject json = new JSONObject(rd.data);
//
//			String result = json.getString("result");
//
//			if(!result.equalsIgnoreCase("OK")){
//				return -1;
//			}
//
//			long post_id = json.getLong("post_id");
//
//
//			return post_id;
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//			return -1;
//		}
	}

	//投稿をします
	public long submitPost(LoginInfo lf, String title, String summary, InputStream in, String name, int eat_time){

		if(CFConst.IS_MIRAI){
			return submitPost_mirai(lf, title, summary, in, name, eat_time);
		}

//		if(TextUtils.isEmpty(body)){
//			return -1;
//		}

		ArrayList<FormBodyPart> params = new ArrayList<FormBodyPart>();

		//Charset charset = Charset.forName("shift_jis");
		Charset charset = Charset.forName("utf-8");

		try {
			params.add(new FormBodyPart("sid", new StringBody(lf.sid.sid)));
			params.add(new FormBodyPart("title", new StringBody(title, charset)));
			params.add(new FormBodyPart("summary", new StringBody(summary, charset)));
			params.add(new FormBodyPart("image", new InputStreamBody(in, name)));


		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return -1;
		}


		HttpResponseData rd=null;

		rd = CFUtil.postMultiPartData(POST_POSTS_URL, params, null);

		if(rd==null || TextUtils.isEmpty(rd.data)) return -1;

		try {
			JSONObject json = new JSONObject(rd.data);

			String result = json.getString("result");

			if(!result.equalsIgnoreCase("OK")){
				return -1;
			}

			long post_id = json.getLong("post_id");


			return post_id;

		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	}

	//投稿を削除します
	public boolean deletePost(LoginInfo lf, long post_id){

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", lf.sid.sid));
		params.add(new BasicNameValuePair("post_id", ""+post_id));

		String data = null;

		data = CFUtil.postData(DEL_POSTS_URL, params);

		try {
			JSONObject json = new JSONObject(data);

			String result = json.getString("result");

			if(!result.equalsIgnoreCase("OK")){
				return false;
			}

			return true;

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	//友達になる
	public boolean followFrend(LoginInfo lf, String frend_id, boolean follow){

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", lf.sid.sid));
		params.add(new BasicNameValuePair("frend_id", ""+frend_id));
		params.add(new BasicNameValuePair("follow", ""+follow));

		String data = null;

		data = CFUtil.postData(FOLLOW_URL, params);

		try {
			JSONObject json = new JSONObject(data);

			String result = json.getString("result");

			if(!result.equalsIgnoreCase("OK")){
				return false;
			}

			return true;

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}




	//ヘルリンコメントを取得
	public String heallinComment_mirai(LoginInfo l){

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

		rd = CFUtil.postData("http://hellin.dip.jp/m/Hellin.php", params, cookie);

		if(rd==null || rd.data==null) return null;

		try {
			JSONObject json = new JSONObject(rd.data);

			String heallin_comment = json.getString("hellinmessage");


			return heallin_comment;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	//ヘルリンコメントを取得
	public String HeallinComment(LoginInfo lf){

		if(CFConst.IS_MIRAI){
			return heallinComment_mirai(lf);
		}

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("sid", lf.sid.sid));

		String data = null;

		data = CFUtil.postData(HEALLIN_COMMENT_URL, params);

		try {
			JSONObject json = new JSONObject(data);

			String result = json.getString("result");

			if(!result.equalsIgnoreCase("OK")){
				return null;
			}

			String heallin_comment = json.getString("comment");


			return heallin_comment;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}







	//アカウントを作成します
	public boolean _createUser_mock(String id, String password,String name, String[] outResult){

		try {
			// Simulate network access.
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			if(outResult!=null) outResult[0] = SIGN_UP_ERR_CONNECT;
			return false;
		}

		return true;

	}



	public LoginInfo _login_mock(String id, String password,String[] outResult){

		try {
			// Simulate network access.
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			if(outResult!=null) outResult[0] = LOGIN_ERR_CONNECT;
			return null;
		}

		LoginInfo li = new LoginInfo();
		li.id = id;
		//li.name = "モック"+id+"さん";
		li.password = password;
		li.sid = new SessionID("daskldjaskldjasdjska");

		return li;
	}



	public static void log(String str){
		android.util.Log.d("test", str);
	}





}
