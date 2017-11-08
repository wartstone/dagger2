package com.vertical.app.module.work.bizanalysis.salesquery;

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

@AutoLayout(layout = R.layout.activity_salesvisit, title = "销售查询")
public class SalesQueryActivity extends BaseCatActivity {
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
                    return new YesterdaySaleQueryFragment();
                case 1:
                    return new TodaySaleQueryFragment();
                case 2:
                    return new WeekSaleQueryFragment();
                case 3:
                    return new MonthSaleQueryFragment();
                case 4:
                    return new CustomSaleQueryFragment();
                default:
                    return new YesterdaySaleQueryFragment();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "昨日";
                case 1:
                    return "今日";
                case 2:
                    return "本周";
                case 3:
                    return "本月";
                case 4:
                    return "自定义";
                default:
                    return "昨日";
            }
        }
    }
}
