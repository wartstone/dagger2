package com.vertical.app.module.devtool;

import com.vertical.app.module.member.CreateMemberActivity;
import com.vertical.app.module.member.MemberActivity;
import com.vertical.app.module.transaction.CreateOrderActivity;

import dagger.Component;

/**
 * Created by ls on 11/22/17.
 */

@Component(
        modules = HnServiceModule.class
)
public interface HnDIService {
    void inject(CatDevService catDevService);
}
