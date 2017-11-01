package com.iotashome.friendspell.dagger;

import com.iotashome.friendspell.api.GoogleApiClientBridge;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleApiClientBridgeModule {
  @Provides
  @Singleton
  GoogleApiClientBridge provideGoogleApiClientBridge() {
    return new GoogleApiClientBridge();
  }
}