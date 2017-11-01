package com.iotashome.friendspell.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.iotashome.friendspell.FriendSpellApplication;
import com.iotashome.friendspell.R;
import com.iotashome.friendspell.api.GoogleApiClientBridge;
import com.iotashome.friendspell.storage.DatabaseApi;
import com.iotashome.friendspell.storage.LetterSource;
import com.iotashome.friendspell.storage.NearbyPerson;
import com.iotashome.friendspell.util.NavigationUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleActivity extends AppCompatActivity implements
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
  @Inject
  DatabaseApi databaseApi;

  @Inject
  GoogleApiClientBridge googleApiClientBridge;
  private String googleApiClientToken;

  @BindView(R.id.list)
  ListView listView;

  @BindView(R.id.empty)
  View emptyView;

  @BindView(R.id.nearby_tip)
  View nearbyTip;

  private NearbyAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_people);

    ((FriendSpellApplication) getApplication()).component().inject(this);
    ButterKnife.bind(this);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    googleApiClientToken = googleApiClientBridge.init(this, this, this);

    adapter = new NearbyAdapter(this, R.layout.nearby_person);
    listView.setAdapter(adapter);
    listView.setEmptyView(emptyView);
    nearbyTip.setVisibility(View.VISIBLE);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NearbyPerson person = adapter.getItem(position);
        Uri uri = Uri.parse("https://plus.google.com/" + person.googlePlusId);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
      }
    });
  }

  @Override public void onStart() {
    super.onStart();
    googleApiClientBridge.connect(googleApiClientToken);
  }

  @Override
  protected void onResume() {
    super.onResume();

    List<String> userIds = new ArrayList<>();

    List<LetterSource> people = databaseApi.getAvailableLetters();
    adapter.clear();
    for (int i = 0; i < people.size(); ++i) {
      NearbyPerson person = new NearbyPerson(people.get(i));
      adapter.add(person);
      userIds.add(person.googlePlusId);
    }

    if (!userIds.isEmpty()) {
      googleApiClientBridge.getProfileImages(
          googleApiClientToken, userIds, new GoogleApiClientBridge.GetProfileImagesCallback() {
            @Override
            public void onSuccess() {
              List<NearbyPerson> people = new ArrayList<>(adapter.getCount());
              for (int i = 0; i < adapter.getCount(); ++i) {
                NearbyPerson person = adapter.getItem(i);
                person.imageUrl = googleApiClientBridge.getProfileImage(person.googlePlusId);
                people.add(person);
              }
              adapter.clear();
              adapter.addAll(people);
            }
          });
    }
  }

  @Override public void onStop() {
    googleApiClientBridge.disconnect(googleApiClientToken);
    super.onStop();
  }

  @Override public void onDestroy() {
    googleApiClientBridge.destroy(googleApiClientToken);
    super.onDestroy();
  }

  @Override
  public void onConnected(Bundle bundle) {
  }

  @Override
  public void onConnectionSuspended(int i) {
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_people, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_nearby_add:
        NavigationUtil.goToNearbyActivity(this);
        return true;
      case android.R.id.home:
        setResult(RESULT_OK);
        finish();
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}