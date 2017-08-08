package com.vertical.app.module.market;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vertical.annotation.LSYao;
import com.vertical.app.R;

@LSYao
public class Market2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market2);
    }

    public void changeText(View view) {
//        LS$$ZYAO zyao$$ZYAO = new LS$$ZYAO();
//        String message = zyao$$ZYAO.getMessage();
        ((TextView)view).setText("GENERATED");
    }
}
