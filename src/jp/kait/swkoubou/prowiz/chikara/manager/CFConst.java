package jp.kait.swkoubou.prowiz.chikara.manager;

public class CFConst {

	//4K3MU2EC3QGYaSBG



	//private static String LOGIN_URL = "http://****/login.php";

	//http://172.21.31.199/bbs/login.php
	//public static final String SERVER = "http://172.21.31.199/";
	//public static final String SERVER = "http://180.0.56.207:15060/";
	//public static final String SERVER = "http://192.168.1.5:8080/";
	//public static final String SERVER = "http://192.168.1.6:8080/";
	//public static final String SERVER = "http://192.168.1.107:8080/";
	//public static final String SERVER = "http://localhost:8080/";
	//public static final int PORT = 15060;
	//public static final String SERVER = "http://192.168.232.1:8080/";
	public static final String SERVER = "http://180.0.51.75:8080/";



//	public static final String LOGIN = "HeallinServer0/Login";
//	public static final String CREATE_ACCOUNT = "HeallinServer0/CreateAccount";
//	public static final String BOARD = "HeallinServer0/Board";
//	public static final String CONTRIBUTE = "HeallinServer0/Contribute";
//	public static final String DEL_CONTRIBUTE = "HeallinServer0/DeleteContribute";

	public static final String ROOTDIR = "HeallinServer0/";
	public static final String LOGIN = ROOTDIR + "api/login";
	public static final String CREATE_ACCOUNT = ROOTDIR + "api/create_account";
	//public static final String CONTRIBUTE = ROOTDIR + "Contribute";
	//public static final String DEL_CONTRIBUTE = ROOTDIR + "api/delete_contribute";
	public static final String FREDNS = ROOTDIR + "api/frends";
	public static final String PROFILE = ROOTDIR + "api/profile";
	public static final String SET_PROFILE = ROOTDIR + "api/set_profile";
	public static final String TIMELINE = ROOTDIR + "api/timeline";
	public static final String MESSAGES = ROOTDIR + "api/messages";
	public static final String UPLOAD_MESSAGE = ROOTDIR + "api/upload_message";
	public static final String CALENDAR = ROOTDIR + "api/calendar";
	public static final String SCHEDULE = ROOTDIR + "api/schedule";
	public static final String POST_POSTS = ROOTDIR + "api/post_posts";
	public static final String DELETE_POSTS = ROOTDIR + "api/delete_posts";
	public static final String SET_GOODS = ROOTDIR + "api/set_goods";
	public static final String SEARCH_FRENDS = ROOTDIR + "api/search_frends";
	public static final String FOLLOW = ROOTDIR + "api/follow";
	public static final String HEALLIN_COMMENT = ROOTDIR + "api/heallin_comment";



	public static final String PROFILE_IMEGES_FROM_USER_ID_DIR = ROOTDIR + "api/profile_images/user/";
	public static final String POSTS_IMEGES_FROM_USER_ID_DIR = ROOTDIR + "api/posts_images/a/";

	
	
	
	public static final boolean IS_MIRAI = true;
	
}
