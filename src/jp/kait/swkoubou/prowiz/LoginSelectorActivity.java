package jp.kait.swkoubou.prowiz;

import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class LoginSelectorActivity extends Activity {

	/*		Auth: Chikara Funabashi
	 * 		Date: 2013/08/08
	 *
	 */


	private Handler mHandler = new Handler();


	private static int REQCODE_LOGIN = 1001;
	//private static int REQCODE_LOGIN = 1001;
	private static int REQCODE_SIGN_UP = 1002;
//
//
//	private Handler mHandler;
//
//	//private boolean mIsFirst = true;
//
//
//	private static int S_MODE_NONE = 0;
//	private static int S_MODE_START = 1;
//	private static int S_MODE_LOGIN_OR_REGISTER = 2;
//	//private static int S_MODE_LOGIN_SUCCESS = 3;
//
//
//	int mMode = S_MODE_NONE;


	private Button mLoginBtn;
	private Button mRegisterBtn;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_selector);

		mLoginBtn = (Button) findViewById(R.id.login_selector_login_btn);
		mRegisterBtn = (Button) findViewById(R.id.login_selector_register_btn);




		mLoginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent  = new Intent(LoginSelectorActivity.this, LoginActivity.class);
				//mMode = S_MODE_LOGIN_OR_REGISTER;
				startActivityForResult(intent, REQCODE_LOGIN);
				//startActivity(intent);
			}
		});
		mRegisterBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(false){
					Intent intent  = new Intent(LoginSelectorActivity.this, SignUpActivity.class);
					//mMode = S_MODE_LOGIN_OR_REGISTER;
					startActivityForResult(intent, REQCODE_SIGN_UP);
				}else{
					toast("現在登録は必要ありません。");
				}
			}
		});





	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==REQCODE_LOGIN){
			if(resultCode==RESULT_OK){
				LoginInfo lf = (LoginInfo) data.getSerializableExtra(Const.AK_LOGIN_INFO);

				Intent intent = new Intent(LoginSelectorActivity.this, HomeActivity.class);
				intent.putExtra(Const.AK_LOGIN_INFO, lf);

				//mMode = S_MODE_NONE;
				startActivity(intent);
			}else{
				finish();
			}
		}

		if(requestCode==REQCODE_SIGN_UP){
			if(resultCode==RESULT_OK){
				LoginInfo lf = (LoginInfo) data.getSerializableExtra(Const.AK_LOGIN_INFO);

				Intent intent = new Intent(LoginSelectorActivity.this, HomeActivity.class);
				intent.putExtra(Const.AK_LOGIN_INFO, lf);

				//mMode = S_MODE_NONE;
				startActivity(intent);
			}else{
				finish();
			}
		}



	}




//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.login_selector, menu);
//		return true;
//	}

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
