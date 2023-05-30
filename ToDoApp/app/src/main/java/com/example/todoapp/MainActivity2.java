

package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {
    BottomNavigationView BottomNavi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        BottomNavi = findViewById(R.id.bnavi);
        //ilk home gelcek
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new TakvimF()).commit();
        BottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.takvim:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new TakvimF()).commit();
                        break;

                    case R.id.gorev:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new GorevF()).commit();
                        break;

                    case R.id.ben:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentler,new BenF()).commit();
                        break;



                }

                return true;
            }
        });

    }
}




