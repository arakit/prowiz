package jp.crudefox.chikara.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

public class CFOverScrolledListView extends ListView{


	/*		Auth: Chikara Funabashi
	 * 		Date:
	 *
	 */


	public interface OnOverScrolledListener{
		public void onOverScrolled(
				int scrollX, int scrollY,
				boolean clampedX, boolean clampedY);
	}
	public interface OnPullToRefreshListener{
		public void onHeadRefresh();
		public void onFootRefresh();
	}




	private final float mDensity;

	private int mMaxOverSclolledTopY;
	private int mMaxOverSclolledBottomY;

	private OnOverScrolledListener mOnOverScrolledListener;
	private OnPullToRefreshListener mOnPullToRefreshListener;


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public CFOverScrolledListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDensity = context.getResources().getDisplayMetrics().density;

		mMaxOverSclolledTopY = mMaxOverSclolledBottomY = (int)( 150 * mDensity );

		if( CFUtil.isOk_SDK(9) ){
			setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);
		}
	}


//	boolean mIsCOverScrooling = false;
//	boolean mIsRefreshed = false;
	int mOverScrolledState = 0;

	boolean mIsHeadOverScrooling = false;
	boolean mIsFootOverScrooling = false;

	//TextView mPullRefreshHeaderView;

	@SuppressLint("NewApi")
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		// TODO 自動生成されたメソッド・スタブ
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);


		if(mOnOverScrolledListener!=null){
			mOnOverScrolledListener.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		}


		//CFUtil.Log("onOverScrolled "+scrollY);

		if(!mIsFootOverScrooling && !mIsHeadOverScrooling){
			if(scrollY>0) mIsFootOverScrooling = true;
			else if(scrollY<0) mIsHeadOverScrooling = true;
		}

		if(mIsFootOverScrooling){
			//makePullRefreshView();

			int thored = getMaxOverScrolledBottomY()*50/100;
			if(mOverScrolledState==0){
				if(scrollY>thored && clampedY){
					mOverScrolledState = 1;
				}
				//mPullRefreshHeaderView.setText("離すと更新");
			}
			else if(mOverScrolledState==1){
				if(!clampedY){
					if(mOnPullToRefreshListener!=null){
						mOnPullToRefreshListener.onFootRefresh();
					}
					mOverScrolledState = 2;
				}
			}

			if(scrollY<=0){
				mIsFootOverScrooling = false;
				mOverScrolledState = 0;
			}
		}
		else if(mIsHeadOverScrooling){
			//makePullRefreshView();

			int thored = getMaxOverScrolledTopY()*50/100;
			if(mOverScrolledState==0){
				if(-scrollY>thored && clampedY){
					mOverScrolledState = 1;
				}
				//mPullRefreshHeaderView.setText("離すと更新");
			}
			else if(mOverScrolledState==1){
				if(!clampedY){
					if(mOnPullToRefreshListener!=null){
						mOnPullToRefreshListener.onHeadRefresh();
					}
					mOverScrolledState = 2;
				}
			}

			if(-scrollY<=0){
				mIsHeadOverScrooling = false;
				mOverScrolledState = 0;
			}
		}
//		else{
//			mIsHeadOverScrooling = mIsFootOverScrooling = false;
//			mOverScrolledState = 0;
//		}

	}

//	private void makePullRefreshView(){
//		if(mPullRefreshHeaderView==null){
//			mPullRefreshHeaderView = new TextView(getContext());
//		}
//	}


//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		// TODO 自動生成されたメソッド・スタブ
//		return super.onTouchEvent(ev);
//	}



	@SuppressLint("NewApi")
	@Override
	protected boolean overScrollBy(
			int deltaX, int deltaY,
			int scrollX, int scrollY,
			int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY,
			boolean isTouchEvent) {

		int py = scrollY + deltaY;

		if(py<0){
			maxOverScrollY = mMaxOverSclolledTopY;
		}else if(py>0){
			maxOverScrollY = mMaxOverSclolledBottomY;
		}else{
			//maxOverScrollY = mMaxOverSclolledTopY;
		}

		//CFUtil.Log("py "+scrollY);

		return super.overScrollBy(
				0, deltaY,
				0, scrollY,
				0, scrollRangeY,
				0, maxOverScrollY,
				isTouchEvent);
	}


	public void setOnOverScrolledListener(OnOverScrolledListener lis){
		mOnOverScrolledListener = lis;
	}
	public void setOnPullToRefreshListener(OnPullToRefreshListener lis){
		mOnPullToRefreshListener = lis;
	}



	public void setMaxOverScrolledY(int top,int bottom){
		mMaxOverSclolledTopY = top;
		mMaxOverSclolledBottomY = bottom;
	}
	public int getMaxOverScrolledTopY(){
		return mMaxOverSclolledTopY;
	}
	public int getMaxOverScrolledBottomY(){
		return mMaxOverSclolledTopY;
	}



//	@Override
//	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//		super.onScrollChanged(l, t, oldl, oldt);
//
//		CFUtil.Log("onScrollChanged " +t);
//	}

	public void setOverscrollFooterEx(Drawable d){
		if(CFUtil.isOk_SDK(9)){
			super.setOverscrollFooter(d);
		}
	}
	public void setOverscrollHeaderEx(Drawable d){
		if(CFUtil.isOk_SDK(9)){
			super.setOverscrollHeader(d);
		}
	}
//	public void setOverscrollFooterEx(int d){
//		if(CFUtil.isOk_SDK(9)){
//			super.setOverscrollFooter();
//		}
//	}
//	public void setOverscrollHeaderEx(int d){
//		if(CFUtil.isOk_SDK(9)){
//			super.setOverscrollHeader(d);
//		}
//	}

	public void setOverscrollHeaderFooter(Drawable head, Drawable foot){
		setOverscrollHeaderEx(head);
		setOverscrollFooterEx(foot);
	}
//	public void setOverscrollHeaderFooter(int head, int foot){
//		setOverscrollHeaderFooter(
//				getResources().getDrawable(head),
//				getResources().getDrawable(foot)
//				);
//	}






}
