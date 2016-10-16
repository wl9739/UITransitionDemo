package me.qiushui.uitransition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import me.qiushui.uitransition.manual_transition.AnimeActivity;
import me.qiushui.uitransition.ui_trasition.ImageActivity;

public class MainActivity extends AppCompatActivity {

    private Button mAutoTransitionBtn;
    private Button mManualTransitionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutoTransitionBtn = (Button) findViewById(R.id.ui_transition_btn);
        mManualTransitionBtn = (Button) findViewById(R.id.manual_transition_btn);

        mAutoTransitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageActivity.class));
            }
        });

        mManualTransitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimeActivity.class));
            }
        });
    }
}
