package com.gmail.at.connorglennon.weekendassignment3;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BaseActivity;
import com.gmail.at.connorglennon.weekendassignment3.view.search.MapFragment;
import com.gmail.at.connorglennon.weekendassignment3.view.ReservationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
                    .add(R.id.frameContent, new MapFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_search:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameContent, new MapFragment())
                            .commit();
                    break;
                case R.id.navigation_reservation:
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameContent, new ReservationFragment())
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