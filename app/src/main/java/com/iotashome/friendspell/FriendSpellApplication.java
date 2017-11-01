package com.iotashome.friendspell;

import android.app.Application;
import android.util.Log;

import com.iotashome.friendspell.dagger.AndroidModule;
import com.iotashome.friendspell.dagger.DaggerApplicationComponent;
import com.iotashome.friendspell.dagger.FriendSpellComponent;
import com.iotashome.friendspell.dagger.GoogleApiClientBridgeModule;

import timber.log.Timber;

public class FriendSpellApplication extends Application {
  private final FriendSpellComponent component = createComponent();

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      Timber.plant(new ErrorTree());
    }
  }

  public FriendSpellComponent component() {
    return component;
  }

  protected FriendSpellComponent createComponent() {
    return DaggerApplicationComponent.builder()
        .androidModule(new AndroidModule(this))
        .googleApiClientBridgeModule(new GoogleApiClientBridgeModule())
        .build();
  }

  private static class ErrorTree extends Timber.Tree {
    private static final String TAG = "FriendSpell";

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.ERROR) {
        Log.e(TAG, message, t);
      }
    }
  }
}