package me.qiushui.uitransition.manual_transition;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import me.qiushui.uitransition.R;
import me.qiushui.uitransition.ui_trasition.DetailActivity;

/**
 * Created by Qiushui on 2016/10/13.
 */

public class AnimeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = DetailActivity.class.getSimpleName() + ".IMAGE";
    public static final int DURATION = 300;
    private static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final String SCALE_WIDTH = "SCALE_WIDTH";
    private static final String SCALE_HEIGHT = "SCALE_HEIGHT";
    private static final String TRANSITION_X = "TRANSITION_X";
    private static final String TRANSITION_Y = "TRANSITION_Y";
    private ImageView mImageView;
    private FrameLayout mContainer;
    private Palette mImagePalette;

    /**
     * 存储图片缩放比例和位移距离
     */
    private Bundle mScaleBundle = new Bundle();
    private Bundle mTransitionBundle = new Bundle();

    /**
     * 屏幕宽度和高度
     */
    private int mScreenWidth;
    private int mScreenHeight;

    /**
     * 图片资源 ID
     */
    private int mRescourceId;

    /**
     * 上一个界面图片的宽度和高度
     */
    private int mOriginWidth;
    private int mOriginHeight;

    /**
     * 上一个界面图片的位置信息
     */
    private Rect mRect;

    @Override
    public void onBackPressed() {
        // 使用退场动画
        runExitAnim();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        // 获得屏幕尺寸
        getScreenSize();

        // 初始化界面
        mImageView = (ImageView) findViewById(R.id.activity_anime_detail_img);
        mContainer = (FrameLayout) findViewById(R.id.activity_anime_detail_container);

        // 初始化场景
        initial();

        // 设置入场动画
        runEnterAnim();
    }

    /**
     * 初始化场景
     */
    private void initial() {
        // 获取上一个界面传入的信息
        mRect = getIntent().getSourceBounds();
        mRescourceId = getIntent().getExtras().getInt(EXTRA_IMAGE);

        // 获取上一个界面中，图片的宽度和高度
        mOriginWidth = mRect.right - mRect.left;
        mOriginHeight = mRect.bottom - mRect.top;

        // 设置 ImageView 的位置，使其和上一个界面中图片的位置重合
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mOriginWidth, mOriginHeight);
        params.setMargins(mRect.left, mRect.top - getStatusBarHeight(), mRect.right, mRect.bottom);
        mImageView.setLayoutParams(params);

        // 设置 ImageView 的图片和缩放类型
        mImageView.setImageResource(mRescourceId);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // 根据上一个界面传入的图片资源 ID，获取图片的 Bitmap 对象。
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(mRescourceId);
        Bitmap bitmap = bitmapDrawable.getBitmap();

        // 计算图片缩放比例和位移距离
        getBundleInfo(bitmap);

        // 创建一个 Pallette 对象
        mImagePalette = Palette.from(bitmap).generate();
        // 使用 Palette 设置背景颜色
        mContainer.setBackgroundColor(
                mImagePalette.getVibrantColor(ContextCompat.getColor(this, android.R.color.black)));
    }

    /**
     * 计算图片缩放比例，以及位移距离
     *
     * @param bitmap
     */
    private void getBundleInfo(Bitmap bitmap) {
        // 计算图片缩放比例，并存储在 bundle 中
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            mScaleBundle.putFloat(SCALE_WIDTH, (float) mScreenWidth / mOriginWidth);
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) bitmap.getHeight() / mOriginHeight);
        } else {
            mScaleBundle.putFloat(SCALE_WIDTH, (float) bitmap.getWidth() / mOriginWidth);
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) mScreenHeight / mOriginHeight);
        }
        // 计算位移距离，并将数据存储到 bundle 中
        mTransitionBundle.putFloat(TRANSITION_X, mScreenWidth / 2 - (mRect.left + (mRect.right - mRect.left) / 2));
        mTransitionBundle.putFloat(TRANSITION_Y, mScreenHeight / 2 - (mRect.top + (mRect.bottom - mRect.top) / 2));
    }

    /**
     * 模拟入场动画
     */
    private void runEnterAnim() {
        mImageView.animate()
                  .setInterpolator(DEFAULT_INTERPOLATOR)
                  .setDuration(DURATION)
                  .scaleX(mScaleBundle.getFloat(SCALE_WIDTH))
                  .scaleY(mScaleBundle.getFloat(SCALE_HEIGHT))
                  .translationX(mTransitionBundle.getFloat(TRANSITION_X))
                  .translationY(mTransitionBundle.getFloat(TRANSITION_Y))
                  .start();
        mImageView.setVisibility(View.VISIBLE);
    }

    /**
     * 模拟退场动画
     */
    private void runExitAnim() {
        mImageView.animate()
                  .setInterpolator(DEFAULT_INTERPOLATOR)
                  .setDuration(DURATION)
                  .scaleX(1)
                  .scaleY(1)
                  .translationX(0)
                  .translationY(0)
                  .withEndAction(new Runnable() {
                      @Override
                      public void run() {
                          finish();
                          overridePendingTransition(0, 0);
                      }
                  })
                  .start();
    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }
}
