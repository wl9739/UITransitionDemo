package me.qiushui.uitransition.manual_transition;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.qiushui.uitransition.R;
import me.qiushui.uitransition.CustomImage;

/**
 * Created by Qiushui on 2016/10/11.
 */

public class AnimeActivity extends AppCompatActivity {

    public void imageClick(View view) {
        Intent intent = new Intent(AnimeActivity.this, AnimeDetailActivity.class);
        // 创建一个 rect 对象来存储共享元素位置信息
        Rect rect = new Rect();
        // 获取元素位置信息
        view.getGlobalVisibleRect(rect);
        // 将位置信息附加到 intent 上
        intent.setSourceBounds(rect);
        CustomImage customImage = (CustomImage) view;
        intent.putExtra(AnimeDetailActivity.EXTRA_IMAGE, customImage.getImageId());
        startActivity(intent);
        // 屏蔽 Activity 默认转场效果
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);
        setSupportActionBar((Toolbar) findViewById(R.id.activity_anime_toolbar));
    }
}
