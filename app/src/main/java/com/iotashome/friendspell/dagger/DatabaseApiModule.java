package com.iotashome.friendspell.dagger;

import android.content.Context;

import com.iotashome.friendspell.storage.DatabaseApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AndroidModule.class)
public class DatabaseApiModule {
  @Provides
  @Singleton
  DatabaseApi provideDatabaseApi(Context context) {
    return new DatabaseApi(context);
  }
}