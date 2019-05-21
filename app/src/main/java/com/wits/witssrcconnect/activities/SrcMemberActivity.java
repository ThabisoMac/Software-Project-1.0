package com.wits.witssrcconnect.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wits.witssrcconnect.R;
import com.wits.witssrcconnect.fragments.HomeFragment;
import com.wits.witssrcconnect.fragments.SrcMemberActivitiesFragment;
import com.wits.witssrcconnect.fragments.SrcPollFragment;
import com.wits.witssrcconnect.managers.UiManager;

public class SrcMemberActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_base);

        toolbar = findViewById(R.id.toolbar_src);

        //load the default fragment (Home Fragment)
        loadFragment(new HomeFragment(), getString(R.string.home));

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_src);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //populate the navigation view with username and user type stored in shared preferences
        //this was stored the moment the user successfully logged in
        UiManager.populateNavHead(navigationView.getHeaderView(0).findViewById(R.id.header_username),
                navigationView.getHeaderView(0).findViewById(R.id.header_user_type));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            //opens Home fragment after user presses the home option on the navigation view
            case R.id.nav_home:
                loadFragment(new HomeFragment(), getString(R.string.home));
                break;

            //opens src activities fragment after user presses the src activities
            //option on the navigation view
            case R.id.nav_activity_timeline:
                loadFragment(new SrcMemberActivitiesFragment(), getString(R.string.src_activity_time_line));
                break;

            //opens polls fragment
            case R.id.nav_polls:
                loadFragment(new SrcPollFragment(), getString(R.string.src_polls));
                break;

            //logs user out and clears all data generated by user
            case R.id.nav_log_out:
                UiManager.logOut(this);
                break;

        }

        //after pressing any option, the navigation automatically closes
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //This function is responsible for displaying fragments
    //and switching them around
    //it also updates the title of the toolbar
    private void loadFragment(Fragment fragment, String title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.parentLayout_src, fragment).commit();
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
