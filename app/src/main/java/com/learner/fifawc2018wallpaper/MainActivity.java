package com.learner.fifawc2018wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.learner.fifawc2018wallpaper.Adapter.ViewPagerAdapter;
import com.learner.fifawc2018wallpaper.Interface.FragmentLoader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentLoader {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPagerForTabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),MainActivity.this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       //loadFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_category:
                viewPager.setCurrentItem(0,true);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_recent:
                viewPager.setCurrentItem(1,true);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_trending:
                viewPager.setCurrentItem(2,true);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_favourite:
                startActivity(new Intent(this,FavouriteActivity.class));
                break;
            case R.id.nav_share:
                shareThisApp();
                break;
            case R.id.nav_feedback:
                break;
            case R.id.nav_rate:
                rateThisApp();
                break;
            case R.id.nav_other:
                break;
        }
        return false;
    }

    private void shareThisApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String subject = "FIFA WC-2018 Wallpaper";
        String body = "this is amazing app for setting wallpaper of Fiafa World Cup 2018 picture";
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        startActivity(Intent.createChooser(intent,"Share With"));
    }

    private void rateThisApp() {

    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
