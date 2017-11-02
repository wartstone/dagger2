package com.vertical.app.module.work.salesvisit;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

import butterknife.BindView;

/**
 * Created by ls on 11/2/17.
 */

@AutoLayout(layout = R.layout.activity_salesvisit, title = "销售回访")
public class SalesVisitActivity extends BaseCatActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    ViewPager mViewPager;

    @Override
    protected void onViewCreated() {
        mViewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GratitudeVisitFragment();
                case 1:
                    return new MaintenanceFragment();
                case 2:
                    return new SatisficationFragment();
                default:
                    return new GratitudeVisitFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "感恩回访";
                case 1:
                    return "洗涤保养";
                case 2:
                    return "满意度回访";
                default:
                    return "感恩回访";
            }
        }
    }
}
