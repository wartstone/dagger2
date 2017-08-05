package com.vertical.app.di;

import com.vertical.app.module.member.MemberActivity;
import com.vertical.app.module.member.MemberContract;
import com.vertical.app.module.member.MemberPresenter;
import com.vertical.core.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by katedshan on 17/8/5.
 */

@Module
public class CatDIModule {
    @Provides
    @PerActivity
    MemberContract.Presenter provideDetailPresenter(MemberContract.View view) {
        return new MemberPresenter(view);
    }

    @Provides
    @PerActivity
    MemberContract.View provideDetailView() {
        return new MemberActivity();
    }
}
