package com.example.mycanvas;

import android.content.Intent;

import com.example.mycanvas.ui.base.BaseActivity;
import com.example.mycanvas.ui.canvas.CanvasActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getRootViewId() {
        return R.id.main;
    }

    @Override
    protected void onViewReady() {
        Intent intent = new Intent(MainActivity.this, CanvasActivity.class);
        startActivity(intent);

        finish();
    }

}