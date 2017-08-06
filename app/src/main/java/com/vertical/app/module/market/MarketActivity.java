package com.vertical.app.module.market;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.vertical.annotation.LSBindView;
//import com.vertical.api.LSCViewBinder;
import com.vertical.annotation.LSBindView;
import com.vertical.api.LSCViewBinder;
import com.vertical.app.R;

public class MarketActivity extends Activity {

    @LSBindView(R.id.market_button)
    public Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        LSCViewBinder.bind(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setText("Annoted");
            }
        });
    }

    @Override
    protected void onDestroy() {
        LSCViewBinder.unBind(this);
    }
}
