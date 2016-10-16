package me.qiushui.uitransition.manual_transition;

import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.ImageView;

import me.qiushui.uitransition.R;
import me.qiushui.uitransition.ui_trasition.DetailActivity;

/**
 * Created by Qiushui on 2016/10/13.
 */

public class AnimeDetailActivity extends AppCompatActivity {

    public static final java.lang.String EXTRA_IMAGE = DetailActivity.class.getSimpleName() + ".IMAGE";
    private static final String TAG = "TESTTTTTT";
    private static final String SCALE_WIDTH = "SCALE_WIDTH";
    private static final String SCALE_HEIGHT = "SCALE_HEIGHT";

    private ImageView mImageView;
    private FrameLayout mContainer;
    private Palette mImagePalette;

    private Bundle mScaleBundle = new Bundle();
    private int screenWidth;
    private int screenHeight;
    private int mRescourceId;
    private Rect mRect;

    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detail);
        getScreenSize();

        mImageView = (ImageView) findViewById(R.id.activity_anime_detail_img);
        mContainer = (FrameLayout) findViewById(R.id.activity_anime_detail_container);

        mRescourceId = getIntent().getExtras().getInt(EXTRA_IMAGE);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(mRescourceId);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        getScale(bitmap);

        mImageView.setImageResource(mRescourceId);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap originBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        mImagePalette = Palette.from(originBitmap).generate();

        mRect = getIntent().getSourceBounds();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dpToPx(this, 120), dpToPx(this, 120));
        params.setMargins(mRect.left, mRect.top - getStatusBarHeight(), mRect.right, mRect.bottom);
        mImageView.setLayoutParams(params);

        mContainer.setBackgroundColor(
                mImagePalette.getLightVibrantColor(ContextCompat.getColor(this, android.R.color.black)));
        runEnterAnim();
    }

    /**
     * 计算图片缩放比例
     *
     * @param bitmap
     */
    private void getScale(Bitmap bitmap) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            mScaleBundle.putFloat(SCALE_WIDTH, (float) screenWidth / (float) dpToPx(this, 120));
            mScaleBundle.putFloat(SCALE_HEIGHT, (float) bitmap.getHeight() / (float) dpToPx(this, 120));
        }
    }

    /**
     * 模拟入场动画
     */
    private void runEnterAnim() {
        mImageView.animate()
                  .setDuration(1000)
                  .scaleX(mScaleBundle.getFloat(SCALE_WIDTH))
                  .scaleY(mScaleBundle.getFloat(SCALE_HEIGHT))
                  .translationX(screenWidth / 2 - (mRect.left + (mRect.right - mRect.left) / 2))
                  .translationY(screenHeight / 2 - (mRect.top + (mRect.bottom - mRect.top) / 2))
                  .start();
        mImageView.setImageResource(mRescourceId);

    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
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
