package com.iotashome.friendspell.util;

import android.app.Activity;
import android.content.Intent;

import com.iotashome.friendspell.activity.NearbyActivity;
import com.iotashome.friendspell.activity.PeopleActivity;

public abstract class NavigationUtil {
  public static void goToNearbyActivity(Activity activity) {
    Intent intent = new Intent(activity, NearbyActivity.class);
    activity.startActivityForResult(intent, Constants.REQUEST_CODE_NEARBY);
  }
  public static void goToPeopleActivity(Activity activity) {
    Intent intent = new Intent(activity, PeopleActivity.class);
    activity.startActivityForResult(intent, Constants.REQUEST_CODE_PEOPLE);
  }
}