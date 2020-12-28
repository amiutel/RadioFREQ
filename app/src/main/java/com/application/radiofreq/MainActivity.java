package com.application.radiofreq;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView country, city;

//    private RecyclerView recyclerView;
//    private ParseAdapter adapter;
//    private ArrayList<ParseItem> parseItems = new ArrayList<>();
//    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        progressBar = findViewById(R.id.progressBar);
//        recyclerView = findViewById(R.id.recyclerView);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new ParseAdapter(parseItems, this); //??
//        recyclerView.setAdapter(adapter);
//
//        Content content = new Content();
//        content.execute();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

//    private class Content extends AsyncTask<Void,Void,Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in)); //??
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressBar.setVisibility(View.GONE);
//            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out)); //??
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                String url = "https://www.cinemaqatar.com/";
//                Document doc = Jsoup.connect(url).get();
//
//                Elements data = doc.select("span.thumbnail");
//
//                int size = data.size();
//
//                for (int i = 0; i < size; i++) {
//                    String imgUrl = data.select("span.thumbnail")
//                            .select("img")
//                            .eq(i)
//                            .attr("src");
//
//                    String title = data.select("h4.gridminfotitle")
//                            .select("span")
//                            .eq(i)
//                            .text();
//                    parseItems.add(new ParseItem(imgUrl, title));
//                    Log.d("items", "img" + imgUrl + " . title: " + title);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            MainActivity mainActivity = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_playing:
                    selectedFragment = new PlayingFragment();
                    break;

                case R.id.nav_history:
                    selectedFragment = new HistoryFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public void onBackPressed() {
        Fragment selectedFragment = new HomeFragment();
        Fragment  f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof PlayingFragment || f instanceof HistoryFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.commit();
        }
    }

//    @Override
//    public void onLocationChanged(Location location) {
//
//        try {
//            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//
//            country.setText(addresses.get(0).getCountryName());
//            city.setText(addresses.get(0).getLocality());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}