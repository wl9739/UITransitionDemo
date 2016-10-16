package me.qiushui.uitransition.manual_transition;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import me.qiushui.uitransition.R;
import me.qiushui.uitransition.ui_trasition.CustomImage;

/**
 * Created by Qiushui on 2016/10/11.
 */

public class AnimeActivity extends AppCompatActivity {


    private static final String TAG = "TESTTTTTT";

    public void imageClick(View view) {
        Intent intent = new Intent(AnimeActivity.this, AnimeDetailActivity.class);
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        intent.setSourceBounds(rect);
        Log.d(TAG, "onCreate: rect : left" + rect.left + " right: " + rect.right + " top:" + rect.top + " bottom:" + rect.bottom);
        CustomImage customImage = (CustomImage) view;
        intent.putExtra(AnimeDetailActivity.EXTRA_IMAGE, customImage.getImageId());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);
    }
}
