package yohan.jkskingdom.com.jokesterskingdom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Yohan on 29/06/2018.
 */

public class JokesFeed extends AppCompatActivity {

    TextView t1;
    private FloatingActionButton fabAddJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokesfeed);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        fabAddJoke = findViewById(R.id.floatingActionButton);




        //AFFICHE LE FRAGMENT DU JOKES FEED DES LE DEBUT
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new JokesFeedFragment()).commit();

        //START FRAGMENT ON CLICK OF THE FAB
        fabAddJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddJokeFragment()).commit();
            }
        });





    }



    //AFFICHE LES FRAGMENT CORRESPONDANT A CHAQUE CLIQUE SUR DES ITEM DE LA NAVBAR
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_feed:
                            selectedFragment = new JokesFeedFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;

                }
            };









}
