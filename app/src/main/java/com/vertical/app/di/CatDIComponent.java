package com.vertical.app.di;

import com.vertical.app.module.member.MemberActivity;
import com.vertical.core.di.PerActivity;

import dagger.Component;

/**
 * Created by katedshan on 17/8/5.
 */

@PerActivity
@Component(modules = CatDIModule.class)
public interface CatDIComponent {
    void inject(MemberActivity activity);
}
