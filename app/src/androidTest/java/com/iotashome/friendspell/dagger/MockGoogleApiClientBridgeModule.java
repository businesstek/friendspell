package com.iotashome.friendspell.dagger;

import com.iotashome.friendspell.api.GoogleApiClientBridge;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockGoogleApiClientBridgeModule {
  @Provides
  @Singleton
  GoogleApiClientBridge provideGoogleApiClientBridgeModule() {
    return Mockito.mock(GoogleApiClientBridge.class);
  }
}