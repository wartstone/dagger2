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

//        HomeWorkFragment singleChatFragment = (HomeWorkFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
//
//        if (singleChatFragment == null) {
//            singleChatFragment = HomeWorkFragment.newInstance();
//            mView = singleChatFragment;
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.contentFrame, singleChatFragment);
//            transaction.commitNow();
//        }
//
//        // Create the presenter
//        mPresenter = new HomeWorkPresenter(new HomeWorkModel(), new HomeWorkFragment(), this);

        initUI();
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mBottomNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_bottom);

        initFragment();

//        mViewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return 4;
//            }
//
//            @Override
//            public boolean isViewFromObject(final View view, final Object object) {
//                return view.equals(object);
//            }
//
//            @Override
//            public void destroyItem(final View container, final int position, final Object object) {
//                ((ViewPager) container).removeView((View) object);
//            }
//
//            @Override
//            public Object instantiateItem(final ViewGroup container, final int position) {
//                container.addView((View)fragmentList.get(position));
//                return viewLists.get(position);
//
////                final View view = new View(getBaseContext());
////                container.addView(view);
////                return view;
//            }
//        });

        mViewPager.setAdapter(mPagerAdapter);

        mBottomNavigationTabStrip.setViewPager(mViewPager, 1);
        mBottomNavigationTabStrip.setTabIndex(1, true);
    }

    private void initFragment() {
        mMemberFragment = new HomeMemberFragment();
        mMineFragment = new HomeMineFragment();
        mIntelFragment = new HomeIntelFragment();
        mWorkFragment = new HomeWorkFragment();



//        fragmentList.add();
//        fragmentList.add(new HomeWorkFragment());
//        fragmentList.add(new HomeMineFragment());
//        fragmentList.add(new HomeIntelFragment());
//        FragmentTransaction transaction = fm.beginTransaction();
//        for (int i = 0; i < fragmentList.size(); i++) {
//            Fragment fragment = fragmentList.get(i);
//            transaction.add(R.id.contentFrame, fragment);
//            if (i != 0) {
//                transaction.hide(fragment);
//            }
//        }
//        transaction.commitAllowingStateLoss();
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
                    return mMineFragment;
                case 2:
                    return mIntelFragment;
                case 3:
                    return mWorkFragment;
                default:
                    return null;
            }
        }
    }
}
