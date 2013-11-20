package jp.kait.swkoubou.prowiz;


import jp.crudefox.chikara.util.CFCardUIAdapter;
import jp.crudefox.chikara.util.CFOverScrolledListView;
import jp.crudefox.chikara.util.CFUtil;
import jp.kait.swkoubou.prowiz.AppManager.OnStateChangeListener;
import jp.kait.swkoubou.prowiz.chikara.manager.DeckManager;
import jp.kait.swkoubou.prowiz.chikara.manager.DeckManager.DItem;
import jp.kait.swkoubou.prowiz.chikara.manager.EnemyManager;
import jp.kait.swkoubou.prowiz.chikara.manager.EnemyManager.EItem;
import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager.SItem;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;


/**
 * 		@author Chikara Funabashi
 * 		@date 2013/08/09
 *
 */

/**
 *
 *
 */

public class BattleFragment extends Fragment {



	private AppManager mApp;

//com.handmark.pulltorefresh.library.PullToRefreshListView

	boolean mIsFIrst = true;;

	private Context mContext;
	private View mRootView;

	private Handler mHandler = new Handler();

	//private CFOverScrolledListView mListView;
//	private ListView mListView;
//	private PullToRefreshListView mPullToRefreshListView;

	//private LoginManager mLoginManager;
	//private TimeLineManager mTLManager;
	//private MemberManager mMemberManager;

	private DeckManager mDeckManager;
	private EnemyManager mEnemyManager;
	private StageManager mStageManager;


	private LayoutInflater mLayoutInflater;
	//private DateFormat mDateFormat;


	private GetMemberTask mGetMemberTask;

	//private LoginInfo mLoginInfo;

	private EnemyListViewAdapter mCAdapter;


	private Button[] mBtns = new Button[4];

	private Gallery mGallery;

	private View mNextPanel;
	private View mBattlePanel;

	private ImageView mEffectView;

	private View mBattleBGView;

	private TextView mHPView;
	private ProgressBar mHPProgressBar;;



	private int mStageId;
	private SItem mStageItem;
	private int mCurBattle;
	private EItem mCurEnemy;

	private int mMyMaxHP;
	private int mMyHP;

	//private DeleteContributeTask mDelTask;


	private DItem[] mSKills = new DItem[4];


	public BattleFragment() {
		super();
		setHasOptionsMenu(true);
	}



//	private class Skill{
//		int id;
//		int name;
//		int ca;
//		int lv;
//	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreateView(inflater, container, savedInstanceState);

		CFUtil.Log("onCreateView "+this);



		Bundle args = getArguments();

		int stage_id = args.getInt("stage_id");
		SItem item = mStageManager.getItemById(stage_id);

		setContentView(R.layout.fragment_battle);

		mGallery = (Gallery) findViewById(R.id.gallery);

		mBtns[0] = (Button) findViewById(R.id.btn1);
		mBtns[1] = (Button) findViewById(R.id.btn2);
		mBtns[2] = (Button) findViewById(R.id.btn3);
		mBtns[3] = (Button) findViewById(R.id.btn4);

		mBattlePanel =  findViewById(R.id.panel_battle);
		mNextPanel =  findViewById(R.id.panel_next);

		mEffectView = (ImageView)  findViewById(R.id.effect);

		mHPView = (TextView)  findViewById(R.id.hp_text);
		mHPProgressBar = (ProgressBar)  findViewById(R.id.hp_progress);

		mBattleBGView = findViewById(R.id.battle_bg);


		mBattleBGView.setBackgroundDrawable(item.battle_bg);

		//mListView  = (CFOverScrolledListView) findViewById(R.id.member_frends_listView);
//		mPullToRefreshListView  = (PullToRefreshListView) findViewById(R.id.deck_listview);
//		ListView actualListView = mListView = mPullToRefreshListView.getRefreshableView();

//		Bundle bundle = getArguments();

		//Intent intent = getIntent();
		//mLoginInfo = (LoginInfo) bundle.getSerializable(Const.AK_LOGIN_INFO);
		//mLoginInfo = mApp.getLoginInfo();

//		if(CFUtil.isOk_SDK(9)){
//			mListView.setOverscrollHeader(
//					getResources().getDrawable(R.drawable.update_over_scrolled));
//		}


//		mListView.setAdapter(mCAdapter);

//		if(mLoginInfo!=null){
//			//CFUtil.Log("length = "+mTLManager.getItemLength());
//			if(mMemberManager.getItemLength()==0){
//				postAttemptGetBorad(250);
//			}
//		}else{
//			toast("ログインに失敗しています。");
//			finish();
//		}

//		mListView.setOnOverScrolledListener(mOverScrolledListener);

//		mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//				DItem mitem = mDeckManager.getItemByIndex(position-1);
//				if(mitem==null) return ;
//				//showFrendsDialog(mitem);
//
//			}
//		});


		//mListView.setOverscrollHeaderFooter(R.drawable.update_over_scrolled, 0);
//		mListView.setOverscrollHeaderEx(getResources().getDrawable(R.drawable.update_over_scrolled));
//		mListView.setOverscrollFooterEx(null);
//		mListView.setOnPullToRefreshListener(mPullToRefreshListener);


//		mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
//
//		// Set a listener to be invoked when the list should be refreshed.
//		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//				// Update the LastUpdatedLabel
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//
//				// Do work to refresh the list here.
//				//new GetDataTask().execute();
//				attemptGetMember();
//			}
//		});
//
//		// Add an end-of-list listener
//		mPullToRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//			@Override
//			public void onLastItemVisible() {
//				//Toast.makeText(PullToRefreshListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
//			}
//		});

		// Need to use the Actual ListView when registering for Context Menu
		//registerForContextMenu(actualListView);

		/**
		 * Add Sound Event Listener
		 */
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
//		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
//		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
//		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pyo1);
		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(State.REFRESHING, R.raw.cat5);
//		mPullToRefreshListView.setOnPullEventListener(soundListener);

		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		//actualListView.setAdapter(mCAdapter);

//		mPullToRefreshListView.setAdapter(mCAdapter);


		for(int i=0;i<4;i++){
			final int index = i;
			mBtns[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					selectAtk(index);
				}
			});
		}



		mGallery.setAdapter(mCAdapter);


		updateListView();
		setHP(mMyHP);

		return mRootView;

	}


	private void setHP(int hp){

		mMyHP = hp;
		int max = mMyMaxHP;

		mHPView.setText(String.format("myHP:%-2d/%-2d", hp, max));
		mHPProgressBar.setMax(max);
		mHPProgressBar.setProgress(hp);

		if(mMyHP<=0){
			BattleActivity ba = (BattleActivity)getActivity();
			ba.finish();
			toast("体力が０になりました。\n負けです！！");
		}

	}

	private void selectAtk(int index){

		BattleActivity ba = (BattleActivity)getActivity();
//		ba.setTab(1);

		//attemptGetMember();

		final Fragment f = new QuesutionFragment();
		Bundle args = new Bundle();

		args.putSerializable("skill", mSKills[index]);

		f.setArguments(args);

		FragmentManager manager = getActivity().getSupportFragmentManager();
//		manager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
//			@Override
//			public void onBackStackChanged() {
//				FragmentManager manager = getActivity().getSupportFragmentManager();
//				manager.
//			}
//		});
///		manager.addOnBackStackChangedListener(mBackStackChangedListener);

		ba.addMyFragment(f);
		//ba.replaceMyFragment(f);

	}


	final OnStateChangeListener mOnStateChangeListener = new OnStateChangeListener() {
		@Override
		public void onChanged() {

			int ao = mApp.getAttackOk();
			if(ao!=-1){
				if(ao>0){
					setSceneMode(SCENE_MODE_ATTACK_EFFECT, true);
				}else{
					setSceneMode(SCENE_MODE_DAMAGE_EFFECT, true);
				}
			}

		}
	};

//	final OnBackStackChangedListener mBackStackChangedListener = new OnBackStackChangedListener() {
//		@Override
//		public void onBackStackChanged() {
//			CFUtil.Log("onBackStackChanged");
//
//			int ao = mApp.getAttackOk();
//			if(ao!=-1){
//				if(ao>0){
//					setSceneMode(SCENE_MODE_ATTACK_EFFECT, true);
//				}else{
//					setSceneMode(SCENE_MODE_NORMAL, true);
//				}
//			}
//		}
//	};


//	private void showFrendsDialog(final MItem mitem){
//		if(mitem==null) return ;
//
//		MemberFragment.showFrendsDialog(mContext, mApp, mLayoutInflater, mitem);
//
////		final AlertDialog[] dlg = new AlertDialog[1];
////
////		AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
////
////		//ab.setTitle(""+mitem.name);
////		//ab.setMessage("id="+mitem.id+" / name="+mitem.name+"");
////
////		View view = mLayoutInflater.inflate(R.layout.frend_simple_dialog, null);
////
////		ImageView icon_icon = (ImageView) view.findViewById(R.id.frend_simple_dlg_icon);
////		TextView text_id = (TextView) view.findViewById(R.id.frend_simple_dlg_id);
////		TextView text_name = (TextView) view.findViewById(R.id.frend_simple_dlg_name);
////		Button btn_del = (Button) view.findViewById(R.id.frend_simple_dlg_do_frend);
////
////		icon_icon.setImageBitmap(mitem.icon);
////		text_id.setText(""+mitem.id);
////		text_name.setText(""+mitem.name);
////
////		btn_del.setOnClickListener(new View.OnClickListener() {
////			@Override
////			public void onClick(View v) {
////
////				dlg[0].dismiss();
////
////				mMemberManager.removeItemById(mitem.id);
////				toast("サトシ"+"は \n"+
////						mitem.name+"を外に逃がしてあげた！\n"+
////						"バイバイ！"+mitem.name+"！");
////				updateListView();
////			}
////		});
////
////		ab.setView(view);
////
////		ab.setPositiveButton(android.R.string.ok, null);
////
////		dlg[0] = ab.create();
////		dlg[0].show();
//	}


	final int SCENE_MODE_BATTLE_START = 0;
	final int SCENE_MODE_CODE = 1;
	final int SCENE_MODE_ATTACK_EFFECT = 2;
	final int SCENE_MODE_NORMAL = 3;
	final int SCENE_MODE_DAMAGE_EFFECT = 4;
	final int SCENE_MODE_BATTLE_READY = 5;

	public void setSceneMode(int mode,boolean animation){

		if(mode==SCENE_MODE_BATTLE_START){

			mBattlePanel.setEnabled(true);

			mBattlePanel.setVisibility(View.VISIBLE);
			mNextPanel.setVisibility(View.GONE);

			mNextPanel.bringToFront();

			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_top_slow);
			mNextPanel.startAnimation(anim);
			
			updateListView();

			setSkillCard();


		}
		else if(mode==SCENE_MODE_BATTLE_READY){

			mBattlePanel.setEnabled(true);

			mBattlePanel.setVisibility(View.VISIBLE);
			mNextPanel.setVisibility(View.VISIBLE);

			mNextPanel.bringToFront();

			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_top_slow);
			mNextPanel.startAnimation(anim);

			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					setSceneMode(SCENE_MODE_BATTLE_START, true);
				}
			},2000);

			
		}
		else if(mode==SCENE_MODE_CODE){

			mBattlePanel.setEnabled(true);

			mBattlePanel.setVisibility(View.GONE);
			mNextPanel.setVisibility(View.VISIBLE);

			mNextPanel.bringToFront();

			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_top_slow);
			mNextPanel.startAnimation(anim);

		}
		else if(mode==SCENE_MODE_ATTACK_EFFECT){
			AnimationDrawable ad = (AnimationDrawable) getResources().getDrawable(R.drawable.atk03);
			mEffectView.setImageDrawable(ad);
			ad.start();

			mBattlePanel.setEnabled(false);

			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {


					if(mCurEnemy.hp<=0){
						setSceneMode(SCENE_MODE_NORMAL, true);
						nextBattle();
					}else{
						setSceneMode(SCENE_MODE_NORMAL, true);
					}
				}
			}, 2500);

//			mCAdapter.get
//			mCAdapter.getItem(0).hp--;
			mCurEnemy.hp -= 1;
			if(mCurEnemy.
					hp<=0){
				mCurEnemy.hp = 0;
			}
			//updateListView();

		}
		else if(mode==SCENE_MODE_DAMAGE_EFFECT){
			AnimationDrawable ad = (AnimationDrawable) getResources().getDrawable(R.drawable.explosion);
			mEffectView.setImageDrawable(ad);
			ad.start();

			mBattlePanel.setEnabled(false);

			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if(mMyHP<=0){
						setSceneMode(SCENE_MODE_NORMAL, true);
					}else{
						setSceneMode(SCENE_MODE_NORMAL, true);
					}
					setHP(mMyHP);
				}
			}, 2500);

//			mCAdapter.get
//			mCAdapter.getItem(0).hp--;
			mMyHP -= 1;
			if(mMyHP<=0){
				mMyHP = 0;
			}

			//updateListView();

		}

		else if(mode==SCENE_MODE_NORMAL){

			setSkillCard();

			mCAdapter.notifyDataSetChanged();

			//AnimationDrawable ad = (AnimationDrawable) getResources().getDrawable(R.drawable.explosion);
			mEffectView.setImageDrawable(null);
		}
	}


	private void setSkillCard(){

		DItem s;

		mDeckManager.update(mApp.getLoginInfo());

		for(int i=0;i<4;i++){
			mSKills[i] = mDeckManager.getItemByIndex( ((int)(Math.random()*mDeckManager.getItemLength()))%mDeckManager.getItemLength());

			mBtns[i].setText(mSKills[i].title);

		}

//		s = new DItem();
//		mSKills[0] = s;
//		s.ca = 1;
//		s.lv = 1;
//		s.name = "aaaa";
//
//		s = new Skill();
//		mSKills[1] = s;
//		s.ca = 1;
//		s.lv = 1;
//		s.name = "bbb";
//
//		s = new Skill();
//		mSKills[2] = s;
//		s.ca = 1;
//		s.lv = 1;
//		s.name = "ccc";
//
//		s = new Skill();
//		mSKills[3] = s;
//		s.ca = 1;
//		s.lv = 1;
//		s.name = "ddd";



	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();

		mApp = (AppManager) getActivity().getApplication();

		mLayoutInflater = getLayoutInflater();

		mCAdapter = new EnemyListViewAdapter(mContext);

		Bundle args = getArguments();


		mApp.addOnStateChangedListener(mOnStateChangeListener);

		//mDateFormat = DateFormat.getInstance();

//		if(mLoginManager==null){
//			mLoginManager = new LoginManager(getApplicationContext());
//		}
//		if(mMemberManager==null){
//			mMemberManager = new MemberManager(getApplicationContext());
//		}

		//mLoginManager = mApp.getLoginManager();
		mDeckManager = new DeckManager(getActivity());
		mEnemyManager = new EnemyManager(getActivity());
		mStageManager = mApp.getStageManager();




		int stage_id = args.getInt("stage_id");
		SItem item = mStageManager.getItemById(stage_id);

		mStageId = stage_id;
		mStageItem = mStageManager.getItemById(mStageId);

		mCurBattle = 0;
		mCurEnemy = mStageItem.enemys[mCurBattle].clone();

		mMyMaxHP = 3;
		mMyHP = mMyMaxHP;
		//setHP(15);

	}

	private void nextBattle(){
		if(mCurBattle>=mStageItem.battle_num-1){
			final BattleActivity ba = (BattleActivity)getActivity();
//			//ba.removeMyFragment(BattleFragment.this);
//			ba.onBackPressed();

//			ba.removeMyFragment(BattleFragment.this);
//			ba.onSuperBackPressed();
//
//			ba.

//			Intent i = new Intent( getActivity(), HomeActivity.class);
//			startActivity(i);

			AlertDialog.Builder ab = new AlertDialog.Builder(ba);
			ab.setMessage("\nおめでとうございます！\n\n  ＼ステージクリア／  \n\n");
			ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ba.finish();
				}
			});
			ab.setOnCancelListener(new AlertDialog.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					ba.finish();
				}
			});
			ab.setCancelable(false);
			
			ab.create().show();

			return ;
		}

		mCurBattle++;
		mCurEnemy = mStageItem.enemys[mCurBattle].clone();

		

		setSceneMode(SCENE_MODE_BATTLE_READY, true);

	}

	private View findViewById(int id){
		return mRootView.findViewById(id);
	}
	private LayoutInflater getLayoutInflater(){
		return getActivity().getLayoutInflater();
	}
	private void setContentView(int id){
		mRootView = getLayoutInflater().inflate(id, null);
	}
	private Context getApplicationContext(){
		return mContext.getApplicationContext();
	}

	private void finish(){
		Activity act = getActivity();
		if(act!=null){
			act.finish();
		}else{

		}
	}




//	private void postAttemptGetBorad(long delayed){
//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				Activity activity = getActivity();
//				if(activity==null) return ;
//				if(activity.isFinishing()) return ;
//				attemptGetBorad();
//			}
//		}, delayed);
//	}

	private void attemptGetMember(){
		if(mGetMemberTask!=null){
			return ;
		}

		setSceneMode(1, true);

		mGetMemberTask = new GetMemberTask();
		mGetMemberTask.execute((Void)null);

	}




	public void updateListView(){

		if( mCurBattle >= mStageItem.battle_num ) return ;

		mCAdapter.clearItems();


		mCAdapter.addItem(mCurEnemy, 0);

//		for(int i=0;i<m;i++){
//			EItem item = mEnemyManager.getItemByIndex(i);
//			mCAdapter.addItem(item, 0);
//		}

//		MItem mi = new MItem();
//		mi.name = "aaa";
//		mi.id = "iii";
//		mCAdapter.addItem(mi, 0);

		mCAdapter.notifyDataSetChanged();



//		mCAdapter.clearItems();
//
//		for(int i=0;i<mDeckManager.getItemLength();i++){
//			DItem item = mDeckManager.getItemByIndex(i);
//			mCAdapter.addItem(item, 0);
//		}
//
////		MItem mi = new MItem();
////		mi.name = "aaa";
////		mi.id = "iii";
////		mCAdapter.addItem(mi, 0);
//
//		mCAdapter.notifyDataSetChanged();


	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.board, menu);
//		return true;
//	}



//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		//Intent intent;
//
//		int id = item.getItemId();
//		switch(id){
//		case R.id.action_write:
//
//			//showContiributeDlg();
//			showContiributeActivity();
//
//			return true;
//		case R.id.action_update:
//
//			attemptGetBorad();
//
//			return true;
//		}
//
//		return false;
//	}

//	private static final int MENU_ID_UPDATE = 200;



	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//super.onCreateOptionsMenu(menu, inflater);

		MenuItem mi;
		int order = 30;

//		mi = menu.add(Menu.NONE, Const.MENU_ID_UPDATE_MEMBER,order++,"更新");
//		mi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		super.onCreateOptionsMenu(menu, inflater);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		return super.onOptionsItemSelected(item);

		int id = item.getItemId();

//		if(id==Const.MENU_ID_UPDATE_MEMBER){
//			attemptGetBorad();
//			return true;
//		}

		return false;
	}




//	@Override
//	public void onBackPressed() {
//		// TODO 自動生成されたメソッド・スタブ
//		super.onBackPressed();
//	}




	@Override
	public void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		if(mGetMemberTask!=null){
			mGetMemberTask.cancel(true);
		}

		if(mApp!=null){
			mApp.removeOnStateChangedListener(mOnStateChangeListener);
		}
	}



	@Override
	public void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		CFUtil.Log("*********onPause");
	}

	@Override
	public void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		CFUtil.Log("*********onResume");
	}

	@Override
	public void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();

		mEffectView.setImageBitmap(null);

		if(mIsFIrst){
			mIsFIrst = false;
			attemptGetMember();
		}

		CFUtil.Log("*********onStart");

	}

	@Override
	public void onStop() {
		super.onStop();
		if(mGetMemberTask!=null){
			mGetMemberTask.cancel(true);
		}
		CFUtil.Log("*********onStop");
	}




	/**
	 * doBack, Progress, postExecute
	 */
	private class GetMemberTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			//postToast("更新中...");


			try{
				Thread.sleep(3000);
			}catch(InterruptedException e){

			}

			//boolean result = mMemberManager._update_mock(mLoginInfo);
			LoginInfo li = mApp.getLoginInfo();
			boolean result = mEnemyManager.update(li);

			return result;

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			//showProgress(false);

			if(success){
				//toast("メンバー情報を更新しました。");
				updateListView();
				setSceneMode(SCENE_MODE_BATTLE_START, true);
			}else{
				//toast("メンバー情報を更新出来ませんでした\n(；´Д｀)");
				setSceneMode(SCENE_MODE_BATTLE_START, true);
			}

			//mPullToRefreshListView.onRefreshComplete();
			mGetMemberTask = null;

		}

		@Override
		protected void onCancelled() {
			mGetMemberTask = null;
			//showProgress(false);
		}
	}


	private class DeckListViewAdapter extends CFCardUIAdapter<DItem>{


		public DeckListViewAdapter(Context context) {
			super(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View line = convertView;
			if(line==null){
				line =  mLayoutInflater.inflate(R.layout.enemy_list_item, null);
			}

			DItem mitem = getItem(position);

			//ListData data = mListData.get(position);

			TextView text_1 = (TextView) line.findViewById(R.id.text1);
			TextView text_2 = (TextView) line.findViewById(R.id.text2);


			ImageView icon_icon = (ImageView) line.findViewById(R.id.icon);

			text_1.setText(mitem.title + " Lv"+mitem.lv);
			text_2.setText("所持数："+mitem.num);

			icon_icon.setImageResource(mitem.icon);

			if(isCardMotion(position)){
				doCardMotion(line, R.anim.card_ui_motion_from_right);
				setCardMotion(position, false);
			}

			return line;
		}
	}

	private class EnemyListViewAdapter extends CFCardUIAdapter<EItem>{


		public EnemyListViewAdapter(Context context) {
			super(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View line = convertView;
			if(line==null){
				line =  mLayoutInflater.inflate(R.layout.enemy_list_item, null);
			}

			EItem mitem = getItem(position);

			//ListData data = mListData.get(position);

			TextView text_1 = (TextView) line.findViewById(R.id.text1);
			TextView text_2 = (TextView) line.findViewById(R.id.text2);


			ImageView icon_icon = (ImageView) line.findViewById(R.id.icon);

			text_1.setText(mitem.name);
			text_2.setText("HP："+mitem.hp);

			icon_icon.setImageBitmap(mitem.icon);

			if(isCardMotion(position)){
				doCardMotion(line, R.anim.card_ui_motion_from_right);
				setCardMotion(position, false);
			}

			return line;
		}
	}








//	private class OnDelBtnClickListener implements View.OnClickListener{
//
//		int mmId;
//
//		public OnDelBtnClickListener(int id) {
//			mmId = id;
//		}
//
//		@Override
//		public void onClick(View v) {
//
//			if( mDelTask!=null ) return ;
//
//			final BItem bitem = mTLManager.getItemById(mmId);
//
//			AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//			ab.setTitle("確認");
//			ab.setMessage("削除しますか。");
//
//			ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					mDelTask = new DeleteContributeTask();
//					mDelTask.execute(bitem.id);
//				}
//			});
//			ab.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//				}
//			});
//
//			ab.create().show();
//
//		}
//	}





//	public class DeleteContributeTask extends AsyncTask<Integer, Void, Boolean> {
//
//		@Override
//		protected Boolean doInBackground(Integer... params) {
//
//
//			//Toast.makeText(LoginActivity.this, "バックグラウンド処理中です！", Toast.LENGTH_SHORT).show();
//
//			//通信処理
//
//			//LoginInfo lf = mLoginManager.login(mId, mPassword);
//			boolean result = mMemberManager.deleteContoribute(mLoginManager , mLoginInfo, params[0]);
//
//			return result;
//		}
//
//		@Override
//		protected void onPostExecute(final Boolean result) {
//			mDelTask = null;
////			mAuthTask = null;
////			showProgress(false);
//
//
////			if (lf!=null) {
////
////				Intent data = new Intent();
////				data.putExtra(Const.AK_LOGIN_INFO, lf);
////				setResult(RESULT_OK, data);
////				finish();
////			} else {
////				mPasswordView
////						.setError(getString(R.string.error_incorrect_password));
////				mPasswordView.requestFocus();
////			}
//
//			if(result){
//				toast("削除しました。");
//				//mBoradManager.removeItemById(id)
//				mAdapter.notifyDataSetChanged();
//			}
//		}
//
//		@Override
//		protected void onCancelled() {
//			mDelTask = null;
//			//mAuthTask = null;
//			//showProgress(false);
//		}
//
//	}


//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if(requestCode==REQCODE_CONTRIBUTE_SUBMIT){
//			if(resultCode==RESULT_OK){
//
//				int id = data.getIntExtra(Const.AK_SUBMIT_CONTRIBUTE_ID, -1);
//				LoginInfo lf = (LoginInfo) data.getSerializableExtra(Const.AK_LOGIN_INFO);
//				if(lf!=null){
//					mLoginInfo = lf;
//				}
//
////				LoginInfo lf = (LoginInfo) data.getSerializableExtra(Const.AK_LOGIN_INFO);
////
////				Intent intent = new Intent(StartActivity.this, BoardActivity.class);
////				intent.putExtra(Const.AK_LOGIN_INFO, lf);
////
////				mMode = S_MODE_NONE;
////				startActivity(intent);
//
//				//attemptGetBorad();
//				postAttemptGetBorad(100);
//
//			}
//		}
//
//	}





	//private boolean mIsOverScrooling = false;


	private final CFOverScrolledListView.OnOverScrolledListener mOverScrolledListener = new CFOverScrolledListView.OnOverScrolledListener() {
		@Override
		public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {

			//CFUtil.Log(String.format("%d %d %s %s", scrollX, scrollY, clampedX, clampedY));

//			int thored = mListView.getMaxOverScrolledY()*50/100;
//			if(scrollY<-thored && !mIsOverScrooling && clampedY){
//				mIsOverScrooling = true;
//				attemptGetBorad();
//			}else if(scrollY>=0){
//				mIsOverScrooling = false;
//			}

		}
	};

	private final CFOverScrolledListView.OnPullToRefreshListener mPullToRefreshListener = new CFOverScrolledListView.OnPullToRefreshListener() {

		@Override
		public void onHeadRefresh() {
			attemptGetMember();
		}

		@Override
		public void onFootRefresh() {


		}
	};




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
