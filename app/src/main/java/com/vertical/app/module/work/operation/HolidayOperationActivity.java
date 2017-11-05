package com.vertical.app.module.work.operation;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.module.work.salesvisit.GratitudeVisitFragment;
import com.vertical.app.module.work.salesvisit.MaintenanceFragment;
import com.vertical.app.module.work.salesvisit.SatisficationFragment;

import butterknife.BindView;

/**
 * Created by ls on 11/2/17.
 */

@AutoLayout(layout = R.layout.activity_salesvisit, title = "销售回访")
public class HolidayOperationActivity extends BaseCatActivity {
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
                    return new TodayHolidayFragment();
                case 1:
                    return new ComingHolidayFragment();
                default:
                    return new TodayHolidayFragment();
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
                    return "今日节日";
                case 1:
                    return "临近节日";
                default:
                    return "今日节日";
            }
        }
    }
}
