package com.iotashome.friendspell.dagger;

import com.iotashome.friendspell.activity.BaseTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    MockDatabaseApiModule.class,
    MockGoogleApiClientBridgeModule.class,
    MockSharedPreferencesModule.class
})
public interface TestComponent extends FriendSpellComponent {
  void inject(BaseTest test);
}