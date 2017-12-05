package com.vertical.app.module.devtool;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ToggleButton;


import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 10/18/17.
 */

//@AutoLayout(layout = R.layout.activity_devconfig)
public class DevConfigActivity extends BaseCatActivity {
//    private final String TAG = getClass().getSimpleName();
//
//    private int mSelectedServer = 0; // 1为开发， 2为测试， 3为正式
//    private boolean bSpinInited = false;
//
//    @BindView(R.id.menu_switch_serverlog_toggle)
//    ToggleButton mServerLogToggle;
//    @BindView(R.id.menu_switch_server_spinner)
//    Spinner mServerSpinner;
//    @BindView(R.id.test_push)
//    HnTextView mTestPushView;
//
//    @Override
//    protected void onViewCreated() {
//        mSelectedServer = GlobalPreference.getInstance().getInt("SelectedServer", 0);
//        if(mSelectedServer != 0) {
//            if(mSelectedServer == 1) {
//                mServerSpinner.setSelection(0);
//            } else if(mSelectedServer == 2) {
//                mServerSpinner.setSelection(1);
//            } else if(mSelectedServer == 3) {
//                mServerSpinner.setSelection(2);
//            }
//        }
//
//        mServerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(!bSpinInited) {
//                    bSpinInited = true;
//                    return;
//                }
//
//                if("开发服务器".equalsIgnoreCase((String)parent.getItemAtPosition(position))) {
//                    ApplicationConfigImpl.SERVERPATH = "139.198.0.17";
//                    ApplicationConfigImpl.JTALKSERVERURL = "139.198.0.17:8080";
//                    GlobalPreference.getInstance().saveInt("SelectedServer", 1);
//                } else if("测试服务器".equalsIgnoreCase((String)parent.getItemAtPosition(position))) {
//                    ApplicationConfigImpl.SERVERPATH = "139.198.5.139";
//                    ApplicationConfigImpl.JTALKSERVERURL = "139.198.0.39";
//                    GlobalPreference.getInstance().saveInt("SelectedServer", 2);
//                } else if("正式服务器".equalsIgnoreCase((String)parent.getItemAtPosition(position))) {
//                    ApplicationConfigImpl.SERVERPATH = "www.nongdingwang.net";
//                    ApplicationConfigImpl.JTALKSERVERURL = "www.nongdingwang.net";
//                    GlobalPreference.getInstance().saveInt("SelectedServer", 3);
//                }
//
//                //PreferenceConstants里太多引用的。 下一步再解依赖
//                PreferenceConstants.HTTP_OTHER_URL = "http://" + ApplicationConfigImpl.JTALKSERVERURL + "/JtalkManager/resteasy/Other/";
//                PreferenceConstants.HTTP_AGENT_URL = "http://" + ApplicationConfigImpl.JTALKSERVERURL + "/JtalkManager/resteasy/Agent/";
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//
//    @OnClick(R.id.test_push)
//    void clickTestPush() {
//        launchScreen(PushTestActivity.class);
//    }
}
