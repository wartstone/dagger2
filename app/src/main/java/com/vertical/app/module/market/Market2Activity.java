package com.vertical.app.module.market;

import android.app.Activity;
import android.os.Bundle;

import com.vertical.annotation.LSYao;
import com.vertical.app.R;

@LSYao(
        name = "Zyao",
        text = "Hello !!! Welcome")
public class Market2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market2);
    }
}
