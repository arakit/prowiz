package jp.kait.swkoubou.prowiz;

import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import jp.kait.swkoubou.prowiz.chikara.manager.LoginManager;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager;
import jp.kait.swkoubou.prowiz.chikara.manager.StageManager.SItem;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StageSelectDlgFragment extends DialogFragment{




	private final static int REQCODE_CAMERA = 2000;
	private final static int REQCODE_GALLEY = 2001;

	private AppManager mAppManager;
	private LoginManager mLoginManager;

	//private ImageView mImageView;
	//private EditText mEditText;

	//private RadioGroup mRadioGroup;
	//private final RadioButton[] mRadioButtons = new RadioButton[3];

	//private Uri mDataUri;
	//private Bitmap mBitmap;

	private AlertDialog mDlg;

	private PostTask mPostTask;

	private Handler mHandler = new Handler();;


	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCancel(dialog);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);

		mAppManager = (AppManager) getActivity().getApplication();
		mLoginManager = mAppManager.getLoginManager();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Bundle args = getArguments();
		final int stage_id = args.getInt("stage_id");

		AppManager app = (AppManager) getActivity().getApplication();
		StageManager sm = app.getStageManager();

		SItem item = sm.getItemById(stage_id);

		//AlertDialog.Builder ab = new AlertDialog.Builder(getSherlockActivity().getSupportActionBar().getThemedContext());
		AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
		//LayoutInflater inflater = getLayoutInflater(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cv = inflater.inflate(R.layout.fragment_stage_select_dlg, null);
		View cancel = cv.findViewById(R.id.cancel);
		View ok = cv.findViewById(R.id.ok);
		TextView body = (TextView) cv.findViewById(R.id.body);
		ImageView pircture = (ImageView) cv.findViewById(R.id.picture);

		pircture.setImageDrawable(item.battle_bg);

		//mImageView = pircture;
		//mEditText = body;

		ab.setView(cv);

		final AlertDialog dlg = mDlg = ab.create();

		//dlg.setContentView(cv);

		pircture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//showChoosImageDlg();
			}
		});

		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attemptStartStage(stage_id);
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.cancel();
			}
		});

		return dlg;
	}




	private class PostTaskData{
		int stage_id;
	}

//	private void attemptPostPosts(){
//
//		if(mPostTask!=null) return ;
//
//		LoginInfo lf = mAppManager.getLoginInfo();
//		if(lf==null) return ;
//
//		//if(mBitmap==null && mDataUri==null) return ;
//
//		PostTaskData data = new PostTaskData();
//		data.lf = lf;
//		data.title = mEditText.getText().toString();
//		data.summary = mEditText.getText().toString();
//		data.dlg = mDlg;
//
//		switch(rid){
//		case R.id.radio0: data.eat_time = 1; break;
//		case R.id.radio1: data.eat_time = 2; break;
//		case R.id.radio2: data.eat_time = 3; break;
//		default: return ;
//		}
//
//		if(mDataUri!=null){
//			try {
//				data.in = getActivity().getContentResolver().openInputStream(mDataUri);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//				toast("画像データが読み込めません。");
//				return ;
//			}
//			data.name = "a.png";//mDataUri.toString();
//		}else{
////			Outp
////			mBitmap.compress(format, quality, stream);
////
////			data.in = mBitmap.geti;
////			data.name = "image.png";
//			return ;
//		}
//
//		mPostTask = new PostTask();
//		mPostTask.execute(data);
//
//	}




	private void attemptStartStage(int stage_id){

		if(mPostTask!=null) return ;

		LoginInfo lf = mAppManager.getLoginInfo();
		if(lf==null) return ;

		//if(mBitmap==null && mDataUri==null) return ;

		PostTaskData data = new PostTaskData();
		data.stage_id = stage_id;
//		data.lf = lf;
//		data.title = mEditText.getText().toString();
//		data.summary = mEditText.getText().toString();
//		data.dlg = mDlg;


		mDlg.dismiss();

		mPostTask = new PostTask();
		mPostTask.execute(data);

	}

//	Bundle args = getArguments();
//	int stage_id = args.getInt("stage_id");
//
//	AppManager app = (AppManager) getActivity().getApplication();
//	StageManager sm = app.getStageManager();
//
//	SItem item = sm.getItemById(stage_id);






//	private void showChoosImageDlg(){
//
//		AlertDialog.Builder ab = new AlertDialog.Builder(getSherlockActivity());
//
//		String[] items = new String[]{
//			"ファイルから",
//			"カメラから"
//		};
//
//		ab.setItems(items, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				if(which==0){
//					Intent target = new Intent(Intent.ACTION_GET_CONTENT);
//					//i_open.setDataAndType(data, "image/*");
//					target.setType("image/*");
//					try{
//						Intent iii = Intent.createChooser(target, "ギャラリー選択");
//						startActivityForResult(iii, REQCODE_GALLEY);
//					}catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				else if(which==1){
//					Intent target = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//					//target.setType("image/jpg");
//					try{
//						Intent iii = Intent.createChooser(target, "カメラ選択");
//						startActivityForResult(iii, REQCODE_CAMERA);
//					}catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//
//		AlertDialog dlg = ab.create();
//		dlg.show();
//
//	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

//		if(requestCode==REQCODE_GALLEY){
//			if(resultCode == Activity.RESULT_OK){
//				Bitmap bitmap = null;
//				try {
//					InputStream is = getActivity().getContentResolver().openInputStream(mDataUri = data.getData());
//					bitmap = BitmapFactory.decodeStream(is);
//					is.close();
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				mImageView.setImageBitmap(mBitmap = bitmap);
//			}
//		}
//
//		else if(requestCode==REQCODE_CAMERA){
//			if(resultCode == Activity.RESULT_OK){
//				Bitmap bitmap = null;
//				Uri uri = mDataUri = data.getData();
//				if(uri!=null){
//					try {
//						InputStream is = getActivity().getContentResolver().openInputStream(uri);
//						bitmap = BitmapFactory.decodeStream(is);
//						is.close();
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				if(bitmap==null){
//					bitmap = (Bitmap) data.getExtras().get("data");
//				}
//				mImageView.setImageBitmap(mBitmap = bitmap);
//			}
//		}



	}


	/**
	 * doBack, Progress, postExecute
	 */
	private class PostTask extends AsyncTask<PostTaskData, Void, Boolean> {



		PostTaskData mData;

		@Override
		protected Boolean doInBackground(PostTaskData... params) {
			// TODO: attempt authentication against a network service.

			//postToast("投稿中...");

			PostTaskData d = mData = params[0];



//			long post_id = mLoginManager.submitPost(
//					d.lf, d.title, d.summary, d.in, d.name, d.eat_time
//					);

			return true;

		}

		@Override
		protected void onPostExecute(final Boolean success) {

			//showProgress(false);

//			if(success){
//				toast("投稿しました。");
//				mData.dlg.dismiss();
//			}else{
//				toast("投稿できませんでした。");
//			}

			Intent intent = new Intent(getActivity(), BattleActivity.class);
			intent.putExtra("stage_id", mData.stage_id);
			startActivity(intent);

			mPostTask = null;

		}

		@Override
		protected void onCancelled() {
			mPostTask = null;
			//showProgress(false);
		}
	}








	private Toast mToast;
	private void toast(String str){
		if(mToast==null){
			mToast = Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT);
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
