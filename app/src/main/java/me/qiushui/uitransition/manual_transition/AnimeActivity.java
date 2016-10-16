package me.qiushui.uitransition.manual_transition;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.qiushui.uitransition.R;
import me.qiushui.uitransition.ui_trasition.CustomImage;

/**
 * Created by Qiushui on 2016/10/11.
 */

public class AnimeActivity extends AppCompatActivity {

    public void imageClick(View view) {
        Intent intent = new Intent(AnimeActivity.this, AnimeDetailActivity.class);
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        intent.setSourceBounds(rect);
        CustomImage customImage = (CustomImage) view;
        intent.putExtra(AnimeDetailActivity.EXTRA_IMAGE, customImage.getImageId());
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);
    }
}
