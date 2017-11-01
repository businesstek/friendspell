package com.iotashome.friendspell.dagger;

import com.iotashome.friendspell.activity.BaseNearbyActivity;
import com.iotashome.friendspell.activity.MainActivity;
import com.iotashome.friendspell.activity.PeopleActivity;
import com.iotashome.friendspell.activity.SpellActivity;
import com.iotashome.friendspell.activity.WordSetActivity;

public interface FriendSpellComponent {
  void inject(MainActivity activity);
  void inject(WordSetActivity activity);
  void inject(SpellActivity activity);
  void inject(BaseNearbyActivity activity);
  void inject(PeopleActivity activity);
}