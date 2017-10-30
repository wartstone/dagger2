package com.vertical.app.module.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.NavigationTabStrip;

import java.util.ArrayList;
import java.util.List;

@AutoLayout(layout = R.layout.activity_home)
public class HomeActivity extends BaseCatActivity {
    private HomeWorkContract.Presenter mPresenter;
    private HomeWorkContract.View mView;

    private ViewPager mViewPager;
    private NavigationTabStrip mBottomNavigationTabStrip;

    private Fragment mMemberFragment, mWorkFragment, mMineFragment, mIntelFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fm = getSupportFragmentManager();
    private HomeFragmentPagerAdapter mPagerAdapter = new HomeFragmentPagerAdapter(fm);

    @Override
    protected void onViewCreated() {
        initUI();
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mBottomNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_bottom);

        initFragment();

        mViewPager.setAdapter(mPagerAdapter);

        mBottomNavigationTabStrip.setViewPager(mViewPager, 0);
        mBottomNavigationTabStrip.setTabIndex(0, true);
    }

    private void initFragment() {
        mMemberFragment = new HomeMemberFragment();
        mMineFragment = new HomeMineFragment();
        mIntelFragment = new HomeIntelFragment();
        mWorkFragment = new HomeWorkFragment();
    }

    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mMemberFragment;
                case 1:
                    return mWorkFragment;
                case 2:
                    return mIntelFragment;
                case 3:
                    return mMineFragment;
                default:
                    return null;
            }
        }
    }
}
