package me.qiushui.uitransition.ui_trasition;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.widget.ImageView;

import me.qiushui.uitransition.R;

/**
 * Created by Qiushui on 2016/10/10.
 */

public class DetailActivity extends AppCompatActivity {

    public static final java.lang.String EXTRA_IMAGE = DetailActivity.class.getSimpleName() + ".IMAGE";
    private ImageView mImageView;
    private FloatingActionButton mFloatingActionButton;

    @Override
    public void onBackPressed() {
        mFloatingActionButton.animate().scaleX(0).scaleY(0).setListener(new CustomTransitionListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                supportFinishAfterTransition();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        int imageResId = getIntent().getExtras().getInt(EXTRA_IMAGE);
        mImageView = (ImageView) findViewById(R.id.image);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fabbtn);
        mImageView.setImageResource(imageResId);

        if (savedInstanceState == null) {
            mFloatingActionButton.setScaleX(0);
            mFloatingActionButton.setScaleY(0);
            getWindow().getEnterTransition().addListener(new CustomTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    getWindow().getEnterTransition().removeListener(this);
                    mFloatingActionButton.animate().scaleX(1).scaleY(1);
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    class CustomTransitionListener implements Transition.TransitionListener, Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }

        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    }
}
