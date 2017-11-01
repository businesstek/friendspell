package com.iotashome.friendspell;

import com.iotashome.friendspell.dagger.DaggerTestComponent;
import com.iotashome.friendspell.dagger.FriendSpellComponent;
import com.iotashome.friendspell.dagger.MockDatabaseApiModule;
import com.iotashome.friendspell.dagger.MockGoogleApiClientBridgeModule;

public class MockFriendSpellApplication extends FriendSpellApplication {
  @Override
  protected FriendSpellComponent createComponent() {
    return DaggerTestComponent.builder()
            .mockDatabaseApiModule(new MockDatabaseApiModule(this))
            .mockGoogleApiClientBridgeModule(new MockGoogleApiClientBridgeModule())
            .build();
  }
}