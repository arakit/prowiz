package jp.kait.swkoubou.prowiz;


import jp.crudefox.chikara.util.CFCardUIAdapter;
import jp.crudefox.chikara.util.CFOverScrolledListView;
import jp.crudefox.chikara.util.CFUtil;
import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager.SItem;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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

public class StageFragment extends Fragment {



	private AppManager mApp;

//com.handmark.pulltorefresh.library.PullToRefreshListView

	boolean mIsFIrst = true;;

	private Context mContext;
	private View mRootView;

	private Handler mHandler = new Handler();

	//private CFOverScrolledListView mListView;
	private ListView mListView;
	private PullToRefreshListView mPullToRefreshListView;

	//private LoginManager mLoginManager;
	//private TimeLineManager mTLManager;
	//private MemberManager mMemberManager;

	private StageManager mStageManager;

	private LayoutInflater mLayoutInflater;
	//private DateFormat mDateFormat;


	private GetMemberTask mGetMemberTask;

	//private LoginInfo mLoginInfo;

	private MemberListViewAdapter mCAdapter;



	//private DeleteContributeTask mDelTask;



	public StageFragment() {
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


		setContentView(R.layout.fragment_stage);

		//mListView  = (CFOverScrolledListView) findViewById(R.id.member_frends_listView);
		mPullToRefreshListView  = (PullToRefreshListView) findViewById(R.id.stage_listview);
		ListView actualListView = mListView = mPullToRefreshListView.getRefreshableView();

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

		mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				SItem mitem = mStageManager.getItemByIndex(position-1);
				if(mitem==null) return ;
				showSelectStageDialog(mitem);

			}
		});


		//mListView.setOverscrollHeaderFooter(R.drawable.update_over_scrolled, 0);
//		mListView.setOverscrollHeaderEx(getResources().getDrawable(R.drawable.update_over_scrolled));
//		mListView.setOverscrollFooterEx(null);
//		mListView.setOnPullToRefreshListener(mPullToRefreshListener);


		mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				//new GetDataTask().execute();
				attemptGetMember();
			}
		});

		// Add an end-of-list listener
		mPullToRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				//Toast.makeText(PullToRefreshListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

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
		mPullToRefreshListView.setOnPullEventListener(soundListener);

		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		//actualListView.setAdapter(mCAdapter);

		mPullToRefreshListView.setAdapter(mCAdapter);


		updateListView();

		return mRootView;

	}


	private void showSelectStageDialog(final SItem mitem){
		if(mitem==null) return ;


		StageSelectDlgFragment dlgf = new StageSelectDlgFragment();
		Bundle args = new Bundle();
		args.putInt("stage_id", mitem.id);
		dlgf.setArguments(args);
		dlgf.show(getFragmentManager(), "StageSelectDlgFragment");

//
//
//
//		MemberFragment.showFrendsDialog(mContext, mApp, mLayoutInflater, mitem);
//
//		final AlertDialog[] dlg = new AlertDialog[1];
//
//		AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//
//		//ab.setTitle(""+mitem.name);
//		//ab.setMessage("id="+mitem.id+" / name="+mitem.name+"");
//
//		View view = mLayoutInflater.inflate(R.layout.frend_simple_dialog, null);
//
//		ImageView icon_icon = (ImageView) view.findViewById(R.id.frend_simple_dlg_icon);
//		TextView text_id = (TextView) view.findViewById(R.id.frend_simple_dlg_id);
//		TextView text_name = (TextView) view.findViewById(R.id.frend_simple_dlg_name);
//		Button btn_del = (Button) view.findViewById(R.id.frend_simple_dlg_do_frend);
//
//		icon_icon.setImageBitmap(mitem.icon);
//		text_id.setText(""+mitem.id);
//		text_name.setText(""+mitem.name);
//
//		btn_del.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				dlg[0].dismiss();
//
//				mMemberManager.removeItemById(mitem.id);
//				toast("サトシ"+"は \n"+
//						mitem.name+"を外に逃がしてあげた！\n"+
//						"バイバイ！"+mitem.name+"！");
//				updateListView();
//			}
//		});
//
//		ab.setView(view);
//
//		ab.setPositiveButton(android.R.string.ok, null);
//
//		dlg[0] = ab.create();
//		dlg[0].show();
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();

		mApp = (AppManager) getActivity().getApplication();

		mLayoutInflater = getLayoutInflater();

		mCAdapter = new MemberListViewAdapter(mContext);

		//mDateFormat = DateFormat.getInstance();

//		if(mLoginManager==null){
//			mLoginManager = new LoginManager(getApplicationContext());
//		}
//		if(mMemberManager==null){
//			mMemberManager = new MemberManager(getApplicationContext());
//		}

		//mLoginManager = mApp.getLoginManager();
		mStageManager = mApp.getStageManager();

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

		mGetMemberTask = new GetMemberTask();
		mGetMemberTask.execute((Void)null);

	}

	public void updateListView(){

		mCAdapter.clearItems();

		for(int i=0;i<mStageManager.getItemLength();i++){
			SItem item = mStageManager.getItemByIndex(i);
			mCAdapter.addItem(item, 0);
		}

//		MItem mi = new MItem();
//		mi.name = "aaa";
//		mi.id = "iii";
//		mCAdapter.addItem(mi, 0);

		mCAdapter.notifyDataSetChanged();


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

			//boolean result = mMemberManager._update_mock(mLoginInfo);
			LoginInfo li = mApp.getLoginInfo();
			boolean result = mStageManager.update(li);

			return result;

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			//showProgress(false);

			if(success){
				//toast("メンバー情報を更新しました。");
				updateListView();
			}else{
				//toast("メンバー情報を更新出来ませんでした\n(；´Д｀)");
			}

			mPullToRefreshListView.onRefreshComplete();
			mGetMemberTask = null;

		}

		@Override
		protected void onCancelled() {
			mGetMemberTask = null;
			//showProgress(false);
		}
	}


	private class MemberListViewAdapter extends CFCardUIAdapter<SItem>{


		public MemberListViewAdapter(Context context) {
			super(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View line = convertView;
			if(line==null){
				line =  mLayoutInflater.inflate(R.layout.stage_list_item, null);
			}

			SItem mitem = getItem(position);

			//ListData data = mListData.get(position);

			TextView text1 = (TextView) line.findViewById(R.id.text1);
			TextView text2 = (TextView) line.findViewById(R.id.text2);
			ImageView icon_icon = (ImageView) line.findViewById(R.id.icon);

			text1.setText(""+mitem.title);
			text2.setText("");

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
