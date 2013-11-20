package jp.kait.swkoubou.prowiz;

import java.util.LinkedHashSet;

import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import jp.kait.swkoubou.prowiz.chikara.manager.LoginManager;
import jp.kait.swkoubou.prowiz.chikara.manager.MemberManager;
import jp.kait.swkoubou.prowiz.chikara.manager.ProfileManager;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager;
import android.app.Application;

public class AppManager extends Application{

	/**
	 *
	 * @author Chikara Funabashi.
	 *
	 */


	public interface OnStateChangeListener{
		public void onChanged();
	}




	private LoginInfo mLoginInfo;

	private LoginManager mLoginManger;


	private MemberManager mMemberManager;

	private ProfileManager mProfileManager;

	private StageManager mStageManager;


	private LinkedHashSet<OnStateChangeListener> mChangeListeners = new LinkedHashSet<OnStateChangeListener>();


	@Override
	public void onCreate() {
		super.onCreate();

		mLoginManger = new LoginManager(this);
		mMemberManager = new MemberManager(this);
		mProfileManager = new ProfileManager(this);
		mStageManager = new StageManager(this);

	}

	@Override
	public void onTerminate() {
		// TODO 自動生成されたメソッド・スタブ
		super.onTerminate();
	}

	public LoginInfo getLoginInfo(){
		return mLoginInfo;
	}

	public LoginManager getLoginManager(){
		return mLoginManger;
	}

	public MemberManager getMemberManager(){
		return mMemberManager;
	}

	public ProfileManager getProfileManager(){
		return mProfileManager;
	}
	public StageManager getStageManager(){
		return mStageManager;
	}




	public void setLoginInfo(LoginInfo info){
		mLoginInfo = info;
	}



	public boolean isLoggedIn(){
		if(mLoginInfo==null) return false;
		return mLoginInfo.isLoggedIn();
	}



	public void clearLogin(){
		setLoginInfo(null);

		mMemberManager.clear();
		mProfileManager.clear();

	}



	private int mAttackOk = -1;

	public int getAttackOk(){
		int a = mAttackOk;
		mAttackOk = -1;

		return a;
	}
	public void setAttackOk(int a){
		mAttackOk = a;
	}

//	public void attckProc(int ao){
//
//		if(ao>0){
//			//setSceneMode(2, true);
//		}else{
//			//setSceneMode(2, true);
//		}
//
//	}




	public void addOnStateChangedListener(OnStateChangeListener lis){
		mChangeListeners.add(lis);
	}
	public void removeOnStateChangedListener(OnStateChangeListener lis){
		mChangeListeners.remove(lis);
	}

	public void notifyStateChanged(){
		for(OnStateChangeListener lis : mChangeListeners){
			lis.onChanged();
		}
	}

}
