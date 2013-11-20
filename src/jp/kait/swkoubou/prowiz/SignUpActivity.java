package jp.kait.swkoubou.prowiz;


import jp.crudefox.chikara.util.CFUtil;
import jp.crudefox.library.help.LibUtil;
import jp.kait.swkoubou.prowiz.chikara.manager.LoginInfo;
import jp.kait.swkoubou.prowiz.chikara.manager.LoginManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class SignUpActivity extends Activity {

	/**
	 * 		@author Chikara Funabashi
	 * 		@Date 2013/08/08
	 *
	 */


	/**
	 *
	 * 		新規登録画面っす。
	 * 		ユーザ作成したら、元の画面にログイン情報を戻す
	 *
	 */



	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
//	private static final String[] DUMMY_CREDENTIALS = new String[] {
//			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	//public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */

	private AppManager mApp;

	private UserLoginTask mAuthTask = null;

	private LoginManager mLoginManager = null;

	// Values for email and password at the time of the login attempt.
	private String mId;
	private String mPassword;
	private String mName;

	// UI references.
	private EditText mIdView;
	private EditText mPasswordView;
	private EditText mPasswordView2;
	private EditText mNameView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private Button mSignUpBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);

		setContentView(R.layout.activity_sign_up);

		mApp = (AppManager) getApplication();

		// Set up the login form.
		//mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mId = "";
		mPassword = "";
		mName = "";

		mIdView = (EditText) findViewById(R.id.signup_login_id);
		mPasswordView = (EditText) findViewById(R.id.signup_login_password);
		mPasswordView2 = (EditText) findViewById(R.id.signup_login_password2);
		mNameView = (EditText) findViewById(R.id.signup_name);
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_login_status_message);
		mSignUpBtn = (Button) findViewById(R.id.signup_register_btn);

		mIdView.setText(mId);
		mPasswordView.setText(mPassword);
		mPasswordView2.setText(mPassword);
		mNameView.setText(mName);

		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});



		mSignUpBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		super.onCreateOptionsMenu(menu);
//		getMenuInflater().inflate(R.menu.login, menu);
//		return true;
//	}



	@Override
	public void onBackPressed() {
		// TODO 自動生成されたメソッド・スタブ
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(mAuthTask!=null){
			mAuthTask.cancel(true);
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mIdView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mId = mIdView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		String pass2 = mPasswordView2.getText().toString();
		mName = mNameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid email address.
		if (TextUtils.isEmpty(mId)) {
			mIdView.setError(getString(R.string.error_field_required));
			focusView = mIdView;
			cancel = true;
		}
		else if( mId.length() > 15 ) {
			mIdView.setError("15文字以下にしてください");
			focusView = mIdView;
			cancel = true;
		}
		else if( !CFUtil.isEisuuzi(mId) ) {
			mIdView.setError("半角英数字のみです！");
			focusView = mIdView;
			cancel = true;
		}


		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}
		else if ( mPassword.length() > 15 ) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}
		else if( !CFUtil.isEisuuzi(mPassword) ) {
			mPasswordView.setError("半角英数字のみです！");
			focusView = mPasswordView;
			cancel = true;
		}
		else if ( !mPassword.equals(pass2) ) {
			mPasswordView2.setError("パスワードが異なります。");
			focusView = mPasswordView2;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mName)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		}
		else if ( mName.length() > 15 ) {
			mNameView.setError(getString(R.string.error_invalid_password));
			focusView = mNameView;
			cancel = true;
		}
		else if( !LibUtil.isEisuuzi(mName) ) {
//			mNameView.setError("半角英数字のみです！");
//			focusView = mNameView;
//			cancel = true;
		}


		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mLoginManager = new LoginManager(getApplicationContext());
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}


	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}





	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, LoginInfo> {

		String mmErr = null;

		@Override
		protected LoginInfo doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			String[] result = new String[]{null};
			boolean create = mLoginManager.createUser(mId, mPassword, mName, result);
			if( !create && result[0]!=null ){
				mmErr = result[0];
			}
			if(!create) return null;

			result = new String[]{null};
			LoginInfo lf = mLoginManager.login(mId, mPassword, result);

			if( lf==null && result[0]!=null ){
				mmErr = result[0];
			}

			return lf;


			// TODO: register the new account here.
//			return true;
		}

		@Override
		protected void onPostExecute(final LoginInfo lf) {
			mAuthTask = null;
			showProgress(false);

			if (lf!=null) {
//				Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra(Const.AK_LOGIN_INFO, lf);
//				startActivity(intent);

				mApp.setLoginInfo(lf);

				Intent data = new Intent();
				data.putExtra(Const.AK_LOGIN_INFO, lf);
				setResult(RESULT_OK, data);
				finish();
			} else {

				if(mmErr.equals(LoginManager.LOGIN_ERR_CONNECT) || mmErr.equals(LoginManager.SIGN_UP_ERR_CONNECT)){
					toast("通信エラー");
				}else{
					mIdView.setError("登録出来ませんでした。");
				}
				mIdView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
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





}
