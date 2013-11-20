package jp.kait.swkoubou.prowiz;

import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



/**
 * 		@auth Chikara Funabashi
 * 		@date 2013/08/08
 *
 */

/**
 * 		ホーム画面です。

 *		みんなはフラグメントとしてコンテンツを作成してね！
 */

public class BattleActivity extends FragmentActivity  {


	public static int REQCODE_CONTRIBUTE_SUBMIT = 1003;


	Handler mHandler = new Handler();

	LoginInfo mLoginInfo;


	AppManager mAppManager;


	RadioButton mTabStargeBtn;
	RadioButton mTabDeckBtn;
	RadioGroup mTabGropus;

	Fragment[] mFragments = new Fragment[1];


	//ViewPager mViewPager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);

		setContentView(R.layout.activity_battle);


		Bundle args = getIntent().getExtras();

		int stage_id = args.getInt("stage_id");

		//mViewPager = (ViewPager) findViewById(R.id.pager);

		//mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));


		mFragments[0] = new BattleFragment();
		mFragments[0].setArguments(args);

		//mFragments[1] = new QuesutionFragment();


		// Set up the action bar.
//		final ActionBar actionBar = getActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mAppManager = (AppManager) getApplication();


		//Bundle b = getIntent().getExtras();

		//LoginInfo li = mLoginInfo = (LoginInfo) b.getSerializable(Const.AK_LOGIN_INFO);
		LoginInfo li = mLoginInfo = mAppManager.getLoginInfo();

		if(li==null){
			toast("ログインしていません。");
			finish();
			return ;
		}

//		Bundle args;


//		com.actionbarsherlock.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//
//		Tab tab;
//
//		tab = actionBar.newTab();
//		tab.setText("タイム\nライン");
//		args = new Bundle();
//		args.putSerializable(Const.AK_LOGIN_INFO, li);
//		tab.setTabListener(new TabListener<TimeLineFragment>(
//                this, "tag1", TimeLineFragment.class, android.R.id.content, args));
//		actionBar.addTab(tab);
//
//		tab = actionBar.newTab();
//		tab.setText("ヘルリン\nの部屋");
//		args = new Bundle();
//		args.putSerializable(Const.AK_LOGIN_INFO, li);
//		//args.putString(DummySectionFragment.ARG_SECTION_MSG, "へるりんがいるはず！");
//		tab.setTabListener(new TabListener<HeallinRoomFragment>(
//                this, "tag2", HeallinRoomFragment.class, android.R.id.content, args));
//		actionBar.addTab(tab);
//
//		tab = actionBar.newTab();
//		tab.setText("メンバ");
//		args = new Bundle();
//		args.putSerializable(Const.AK_LOGIN_INFO, li);
//		//args.putString(DummySectionFragment.ARG_SECTION_MSG, "ゆかいな仲間たち");
//		tab.setTabListener(new TabListener<MemberFragment>(
//                this, "tag3", MemberFragment.class, android.R.id.content, args));
//		actionBar.addTab(tab);
//
//		tab = actionBar.newTab();
//		tab.setText("グラフ");
//		args = new Bundle();
//		args.putSerializable(Const.AK_LOGIN_INFO, li);
//		args.putString(DummySectionFragment.ARG_SECTION_MSG, "りくのグラフが入るのかなー！");
//		tab.setTabListener(new TabListener<DummySectionFragment>(
//                this, "tag4", DummySectionFragment.class, android.R.id.content, args));
//		actionBar.addTab(tab);





		setTab(0);

	}


	public void replaceMyFragment(Fragment f){
		FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.setCustomAnimations(
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow,
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow);

        ft.replace(R.id.content, f);
        // BackKey押下時に1つ前のFragmentを表示したい場合はaddToBackStackを記述する。
        //ft.addToBackStack(null);
        ft.commit();
	}

	public void addMyFragment(Fragment f){
		FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.setCustomAnimations(
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow,
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow);

        ft.add(R.id.content, f);
        // BackKey押下時に1つ前のFragmentを表示したい場合はaddToBackStackを記述する。
        ft.addToBackStack(null);
        ft.commit();
	}

	public void removeMyFragment(Fragment f){
		FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.setCustomAnimations(
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow,
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow);

        ft.remove(f);
        // BackKey押下時に1つ前のFragmentを表示したい場合はaddToBackStackを記述する。
        //ft.addToBackStack(null);
        ft.commit();
	}

//	public void setTab(Fragment f){
//		//toast("tab"+index);
//		if(mFragments[index]==null) return ;
//		addFragmentToStack(index);
//	}
	public void setTab(int index){
		//toast("tab"+index);
		//if(mFragments[index]==null) return ;
		replaceMyFragment(mFragments[index]);

//		if(mFragments[index] instanceof BattleFragment){
//			BattleFragment bf = (BattleFragment) mFragments[index];
//
//			int ao = mAppManager.getAttackOk();
//			if(ao!=-1){
//				bf.setSceneMode(2, true);
//			}
//		}


	}


//	interface OnBatLis{
//		public void on(BattleActivity act);
//	}

//	void addFragmentToStack(int index) {
//		//toast(""+f);
////	    // フラグメントのインスタンスを生成する。
//	    Fragment newFragment = mFragments[index];
//
//	    addMyFragment(newFragment);
////
////	    // ActivityにFragmentを登録する。
////	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////	    // Layout位置先の指定
////	    ft.replace(android.R.id.content, newFragment);
////	    // Fragmentの変化時のアニメーションを指定
////	    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////	    ft.addToBackStack(null);
////	    ft.commit();
//
//		//mViewPager.setCurrentItem(index);
//
//
//	}







	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

//			DummySectionFragment fragment = new DummySectionFragment();
//
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
//
//			return fragment;

			return mFragments[position];

//			if(position==0){
//				return mMem;
//			}
//			if(position==1){
//				return mMemerOfFrendsFragment2;
//			}
//			if(position==2){
//				return mSearchFrendsResultFragment;
//			}

//			return null;
//			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			//args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
//			return fragment;
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			//Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "バトル";
			case 1:
				return "問題";
			}
			return null;
		}
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		return super.onCreateOptionsMenu(menu);

		MenuItem mi;
		//SubMenu sm;
		int order = 1;

////		SubMenu sm_etc;
//
//		mi = menu.add(Menu.NONE, Const.MENU_ID_CONTROBUTE,order++,"投稿");
//		mi.setIcon(android.R.drawable.ic_menu_upload);
//		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
////		sm_etc = menu.addSubMenu(1100,Menu.NONE,order++,"…");
////		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//
////		mi = sm_etc.add(Menu.NONE,MENU_ID_PROFIELE, order++,"プロフィール");
////		mi = sm_etc.add(Menu.NONE,MENU_ID_ABOUT, order++,"About");
//
//
//		mi = menu.add(Menu.NONE, Const.MENU_ID_PROFILE, order++,"プロフィール");
//		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//
//		mi = menu.add(Menu.NONE, Const.MENU_ID_LOGOUT,order++,"ログアウト");
//		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//
//		mi = menu.add(Menu.NONE, Const.MENU_ID_SETTINGS,order++,"設定");
//		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//
//
//		mi = menu.add(Menu.NONE, Const.MENU_ID_ABOUT,order++,"About");
//		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);


		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		return super.onOptionsItemSelected(item);

		int id = item.getItemId();

//		if(id==Const.MENU_ID_CONTROBUTE){
//			showSubmitScreen();
//			return true;
//		}
//		else if(id==Const.MENU_ID_PROFILE){
//			Intent intent  = new Intent(HomeActivity.this, ProfileActivity.class);
//			startActivity(intent);
//			return true;
//		}
//		else if(id==Const.MENU_ID_ABOUT){
//
//			return true;
//		}
//		else if(id==Const.MENU_ID_LOGOUT){
//			finish();
//			return true;
//		}
//		else if(id==Const.MENU_ID_SETTINGS){
//			Intent intent  = new Intent(HomeActivity.this, SettingsActivity.class);
//			startActivity(intent);
//			return true;
//		}

		return false;
	}

	private void showSubmitScreen(){

//		Intent intent  = new Intent(this, ContributionActivity.class);
//		intent.putExtra(Const.AK_LOGIN_INFO, mLoginInfo);
//		startActivityForResult(intent, REQCODE_CONTRIBUTE_SUBMIT);

//		toast("はるひの写真投稿画面がくるよ！");

//		FragmentManager fg = getSupportFragmentManager();
//		SubmitDlgFragment dlgf = new SubmitDlgFragment();
//
//		dlgf.show(fg, "submit");


	}










//	/**
//	 * A dummy fragment representing a section of the app, but that simply
//	 * displays dummy text.
//	 */
//	public static class DummySectionFragment extends Fragment {
//		/**
//		 * The fragment argument representing the section number for this
//		 * fragment.
//		 */
//		public static final String ARG_SECTION_NUMBER = "section_number";
//		public static final String ARG_SECTION_MSG = "section_msg";
//
//		private String mmMsg;
//
//		public DummySectionFragment() {
//
//		}
////		public DummySectionFragment(String title) {
////			mmTitle = title;
////		}
//		public void setMsg(String msg){
//			mmMsg = msg;
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_home_dummy, container, false);
//			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
//			//dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
//			if(mmMsg==null) dummyTextView.setText(getArguments().getString(ARG_SECTION_MSG));
//			else dummyTextView.setText(mmMsg);
//
//			//dummyTextView.setText(mmMsg);
//			return rootView;
//		}
//	}



	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		AlertDialog.Builder ab = new AlertDialog.Builder(this);

		ab.setMessage("バトルを終了しますか？");

		ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BattleActivity.this.finish();
			}
		});
		ab.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		ab.create().show();
	}

	public final void onSuperBackPressed(){
		super.onBackPressed();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==REQCODE_CONTRIBUTE_SUBMIT){
			if(resultCode==Activity.RESULT_OK){

//				int id = data.getIntExtra(Const.AK_SUBMIT_CONTRIBUTE_ID, -1);
//				LoginInfo lf = (LoginInfo) data.getSerializableExtra(Const.AK_LOGIN_INFO);
//				if(lf!=null){
//					mLoginInfo = lf;
//				}

	//			LoginInfo lf = (LoginInfo) data.getSerializableExtra(Const.AK_LOGIN_INFO);
	//
	//			Intent intent = new Intent(StartActivity.this, BoardActivity.class);
	//			intent.putExtra(Const.AK_LOGIN_INFO, lf);
	//
	//			mMode = S_MODE_NONE;
	//			startActivity(intent);

				//attemptGetBorad();
//				postAttemptGetBorad(100);

			}
		}

	}






	private Toast mToast;
	private void toast(String str){
		if(mToast==null){
			mToast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
		}else{
			mToast.setText(str);
		}
		mToast.show();
	}
	private void postToast(final String str){
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				toast(str);
			}
		});
	}
}
