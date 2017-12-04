package com.gmail.at.connorglennon.weekendassignment3;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;

import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BaseActivity;
import com.gmail.at.connorglennon.weekendassignment3.view.search.SearchFragment;
import com.gmail.at.connorglennon.weekendassignment3.view.reservations.ReservationsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigationBar)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUnBinder(ButterKnife.bind(this));

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.frameContent, new SearchFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_search:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameContent, new SearchFragment())
                            .commit();
                    break;
                case R.id.navigation_reservation:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameContent, new ReservationsFragment())
                            .addToBackStack("")
                            .commit();
                    break;
            }
            return true;
        });
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }
}