package jp.kait.swkoubou.prowiz;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jp.crudefox.chikara.util.CFCardUIAdapter;
import jp.crudefox.chikara.util.CFOverScrolledListView;
import jp.crudefox.chikara.util.CFUtil;
import jp.kait.swkoubou.prowiz.chikara.manager.DeckManager.DItem;
import jp.kait.swkoubou.prowiz.chikara.manager.EnemyManager.EItem;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class QuesutionFragment extends Fragment {



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

	//private DeckManager mDeckManager;
	//private EnemyManager mEnemyManager;

	private LayoutInflater mLayoutInflater;
	//private DateFormat mDateFormat;


	private GetMemberTask mGetMemberTask;

	//private LoginInfo mLoginInfo;

	private EnemyListViewAdapter mCAdapter;

	private WebView mWebView;
	private View mOkView;
	private View mCancelView;


	private LinearLayout mCodeArea;
	private LinearLayout mSelectArea;
	private TextView mHintTextView;

	private ArrayList<String> mSeitou = new ArrayList<String>();
	private ArrayList<String> mkaitougun = new ArrayList<String>();

	private ArrayList<TextView> mCodeTextViews = new ArrayList<TextView>();

	private EditText mFocusTextView;


	private DItem mDItem;


//	private Button[] mBtns = new Button[4];
//
//	private Gallery mGallery;
//
//	private View mNextPanel;
//	private View mBattlePanel;



	//private DeleteContributeTask mDelTask;



	public QuesutionFragment() {
		super();
		setHasOptionsMenu(true);
	}




	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreateView(inflater, container, savedInstanceState);

		CFUtil.Log("onCreateView "+this);


		setContentView(R.layout.fragment_question);

		//mWebView = (WebView) findViewById(R.id.webView);
		mCodeArea = (LinearLayout) findViewById(R.id.code_area);
		mSelectArea = (LinearLayout) findViewById(R.id.select_area);
		mHintTextView = (TextView) findViewById(R.id.hint);

		//CFUtil.Log("aa:"+mWebView);


		mOkView = findViewById(R.id.ok);
		mCancelView = findViewById(R.id.cancel);


//		mGallery = (Gallery) findViewById(R.id.gallery);
//
//		mBtns[0] = (Button) findViewById(R.id.btn1);
//		mBtns[1] = (Button) findViewById(R.id.btn2);
//		mBtns[2] = (Button) findViewById(R.id.btn3);
//		mBtns[3] = (Button) findViewById(R.id.btn4);
//
//		mBattlePanel =  findViewById(R.id.panel_battle);
//		mNextPanel =  findViewById(R.id.panel_next);

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


		mOkView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				okAttack();

			}
		});
		mCancelView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				BattleActivity ba = (BattleActivity) getActivity();
//				ba.setTab(0);
			}
		});



		//mGallery.setAdapter(mCAdapter);


		//updateListView();

		return mRootView;


	}


	private void okAttack(){


		int sei_num = 0;
		boolean is_empty = false;

		for(int i=0;i<mSeitou.size();i++){
			String sei = mSeitou.get(i);
			TextView tv = mCodeTextViews.get(i);

			if(tv.getText().toString().equals(sei)) sei_num++;
			is_empty = tv.getText().toString().length() == 0;

		}

		if(is_empty){
			toast("驚くべきことに選択していません！");
			return ;
		}

		boolean all_suc = false;
		if( mSeitou.size() == sei_num ) all_suc = true;

		if(all_suc){
			toast("攻撃成功！！");
		}else{
			toast("攻撃失敗！！");
		}

		mApp.setAttackOk(all_suc?1:0);

		BattleActivity ba = (BattleActivity) getActivity();
		//ba.setTab(0);
		ba.removeMyFragment(this);
		//ba.setTab(0);

		//backFragment();

		mApp.notifyStateChanged();

	}

	private void backFragment(){
//		BattleActivity ba = (BattleActivity) getActivity();
//		ba.setTab(0);

		FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow,
        	    R.anim.slide_in_from_bottom_slow,
        	    R.anim.slide_out_to_bottom_slow);
        ft.remove(QuesutionFragment.this);
//        // BackKey押下時に1つ前のFragmentを表示したい場合はaddToBackStackを記述する。
//        transaction.addToBackStack(null);
        ft.commit();

	}

	private LinearLayout makeHorizontal(){
		LinearLayout l = new LinearLayout(getActivity());
		l.setOrientation(LinearLayout.HORIZONTAL);
		return l;
	}

	private TextView makeTextView(String text){
		TextView tv = new TextView(getActivity());
		tv.setText(text);
		tv.setTextSize(10);
		return tv;
	}

	private View makeKouhoView(String text){
		Button v = (Button) getLayoutInflater().inflate(R.layout.code_select_parts, null);
		v.setText(text);
		v.setTextSize(11);
		return v;
	}

	private TextView makeEditText(String text){
		EditText et = new EditText(getActivity());
		et.setInputType(InputType.TYPE_NULL);
		et.setText(text);
		et.setTextSize(10);
		return et;
	}

	private void setExamination(int ca, int lv, int nn){

		mCodeArea.removeAllViews();
		mSelectArea.removeAllViews();
		mCodeTextViews.clear();

		String D_ST = "【--";
		String D_ET = "--】";

		try {
			AssetManager as = getResources().getAssets();
			String strp = String.format("ex/%d_%d_%d_holed.txt", ca, lv, nn);
			BufferedReader is = new BufferedReader( new InputStreamReader( as.open(strp) ) );
			//StringBuilder sb = new StringBuilder();

			CFUtil.Log("k:"+strp);

			String line;
			while((line=is.readLine())!=null){

				LinearLayout l = makeHorizontal();


				int st = line.indexOf(D_ST);

				if(st!=-1){
					int et = line.indexOf(D_ET);

					TextView eet;

					l.addView(makeTextView(line.substring(0, st)));
					l.addView(eet=makeEditText(line.substring(st, et+D_ET.length())));
					l.addView(makeTextView(line.substring(et+D_ET.length(), line.length())));

					mCodeTextViews.add(eet);

					eet.setMinWidth((int)(80*getResources().getDisplayMetrics().density));
					eet.setText("");

					eet.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mFocusTextView = (EditText) v;
						}
					});

					eet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							if(hasFocus){
								mFocusTextView = (EditText) v;
							}else{
								if( mFocusTextView == v) mFocusTextView = null;
							}
						}
					});

				}else{
					l.addView(makeTextView(line));
				}

				mCodeArea.addView(l);

				if(mCodeTextViews.size()>0){
					mCodeTextViews.get(0).requestFocus();
				}

				//CFUtil.Log("d: "+line);
			}


			//mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);


		} catch (IOException e) {
			e.printStackTrace();
			toast(""+e);
		}




		try {
			AssetManager as = getResources().getAssets();
			BufferedReader is = new BufferedReader( new InputStreamReader( as.open(String.format("ex/%d_%d_%d_answer.txt", ca, lv, nn)) ) );

			ArrayList<String> ans = mSeitou;
			ArrayList<String> kou = mkaitougun;
			String hin = null;

			ans.clear();
			kou.clear();


			String line;
			while((line=is.readLine())!=null){
				if(line.length()==0) break;

				ans.add(line);
			}
			if(line==null) return ;

			while((line=is.readLine())!=null){
				if(line.length()==0) break;

				kou.add(line);
			}
			if(line!=null){

				while((line=is.readLine())!=null){
					hin = line;
					break;
				}
			}

			for(int i=0;i<ans.size();i++){
				String str = ans.get(i);
				if(!mkaitougun.contains(str)){
					mkaitougun.add(str);
				}
			}

			Collections.shuffle(mkaitougun, new Random(System.currentTimeMillis()));


			for(int i=0;i<kou.size();i++){
				String str = kou.get(i);

				final View v = makeKouhoView(str);
				v.setOnClickListener( new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(mFocusTextView!=null){
							mFocusTextView.setText(((TextView)v).getText());
						}
					}
				});

				mSelectArea.addView( v );
			}



			mHintTextView.setText(hin);

			//mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);


		} catch (IOException e) {
			e.printStackTrace();
			toast(""+e);
		}





	}

	private void _setExamination(){

		toast("setExamination");

		try {
			AssetManager as = getResources().getAssets();
			BufferedReader is = new BufferedReader( new InputStreamReader( as.open("ex/1_1_1.html") ) );
			StringBuilder sb = new StringBuilder();

			String line;
			while((line=is.readLine())!=null){
				sb.append(line);
				sb.append("\n");
				CFUtil.Log("d: "+line);
			}


			CFUtil.Log(""+mWebView);
			mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);


		} catch (IOException e) {
			e.printStackTrace();
			toast("+e");
		}


	}

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



//	private void setSceneMode(int mode,boolean animation){
//		if(mode==0){
//
//			mBattlePanel.setVisibility(View.VISIBLE);
//			mNextPanel.setVisibility(View.GONE);
//
//			mNextPanel.bringToFront();
//
//			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_top);
//			mNextPanel.startAnimation(anim);
//
//
//
//		}else if(mode==1){
//
//			mBattlePanel.setVisibility(View.GONE);
//			mNextPanel.setVisibility(View.VISIBLE);
//
//			mNextPanel.bringToFront();
//
//			Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_top);
//			mNextPanel.startAnimation(anim);
//
//		}
//	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();

		mApp = (AppManager) getActivity().getApplication();

		mLayoutInflater = getLayoutInflater();

		mCAdapter = new EnemyListViewAdapter(mContext);

		//mDateFormat = DateFormat.getInstance();

//		if(mLoginManager==null){
//			mLoginManager = new LoginManager(getApplicationContext());
//		}
//		if(mMemberManager==null){
//			mMemberManager = new MemberManager(getApplicationContext());
//		}

		//mLoginManager = mApp.getLoginManager();
//		mDeckManager = new DeckManager(getActivity());
//		mEnemyManager = new EnemyManager(getActivity());

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

		//setSceneMode(1, true);

		mGetMemberTask = new GetMemberTask();
		mGetMemberTask.execute((Void)null);

	}

	public void updateListView(){



		mCAdapter.clearItems();

		setExamination(mDItem.category,mDItem.lv, (int)( Math.random()*3) % 3 +1 );

//		for(int i=0;i<mEnemyManager.getItemLength();i++){
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
//		//super.onBackPressed();
//	}




	@Override
	public void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		if(mGetMemberTask!=null){
			mGetMemberTask.cancel(true);
		}
	}



	@Override
	public void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();

		if(mIsFIrst){
			mIsFIrst = false;

			Bundle args = getArguments();
			DItem item = (DItem) args.getSerializable("skill");

			mDItem = item;

			attemptGetMember();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if(mGetMemberTask!=null){
			mGetMemberTask.cancel(true);
		}
	}




	/**
	 * doBack, Progress, postExecute
	 */
	private class GetMemberTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			//postToast("更新中...");


//			try{
//				Thread.sleep(2000);
//			}catch(InterruptedException e){
//
//			}

			//boolean result = mMemberManager._update_mock(mLoginInfo);
//			LoginInfo li = mApp.getLoginInfo();
//			boolean result = mEnemyManager.update(li);

			return true;

			//return result;

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			//showProgress(false);

			if(success){
				//toast("メンバー情報を更新しました。");
				updateListView();
				//setSceneMode(0, true);
			}else{
				//toast("メンバー情報を更新出来ませんでした\n(；´Д｀)");
				//setSceneMode(0, true);
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
