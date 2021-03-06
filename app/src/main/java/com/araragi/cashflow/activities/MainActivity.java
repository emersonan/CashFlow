package com.araragi.cashflow.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.araragi.cashflow.CashFlowApp;
import com.araragi.cashflow.R;
import com.araragi.cashflow.entity.CashTransact;
import com.araragi.cashflow.fragments.DataManagerFragment;
import com.araragi.cashflow.fragments.NewCashTransactionFragment;
import com.araragi.cashflow.fragments.RecyclerViewCashFragment;
import com.araragi.cashflow.fragments.StatisticsFragment;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Box<CashTransact> cashBox;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public NewCashTransactionFragment newCashTransactionFragment;
    public RecyclerViewCashFragment listFragment;
    public StatisticsFragment statFragment;
    public DataManagerFragment dataManagerFragment;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(savedInstanceState != null){

            String title = savedInstanceState.getString("Title");
            toolbar.setTitle(title);

//            newCashTransactionFragment = (NewCashTransactionFragment) getSupportFragmentManager().
//                        getFragment(savedInstanceState, NewCashTransactionFragment.TAG);


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        BoxStore boxStore = ((CashFlowApp) getApplication()).getBoxStore();
        cashBox = boxStore.boxFor(CashTransact.class);

        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            newCashTransactionFragment = new NewCashTransactionFragment();

            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragments_container, newCashTransactionFragment, newCashTransactionFragment.TAG);
            fragmentTransaction.addToBackStack(newCashTransactionFragment.TAG);
            fragmentTransaction.commit();
        }
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                Fragment fragment = fragmentManager.findFragmentById(R.id.fragments_container);
                if(fragment instanceof NewCashTransactionFragment){
                        navigationView.setCheckedItem(R.id.nav_add_new);
                        toolbar.setTitle("Add");
                    }
                if(fragment instanceof RecyclerViewCashFragment){
                        navigationView.setCheckedItem(R.id.nav_list);
                        toolbar.setTitle("List");
                    }
                if(fragment instanceof StatisticsFragment){
                    navigationView.setCheckedItem(R.id.nav_statistics);
                    toolbar.setTitle("Statistics");
                    }
              if(fragment instanceof DataManagerFragment){
                    navigationView.setCheckedItem(R.id.nav_database);
                    toolbar.setTitle("Data");
                }


            }
        });


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {

//        getSupportFragmentManager().putFragment(outState, "myFragmentName", newCashTransactionFragment);
//        outState.putString("Last fragment", );

        outState.putString("Title", toolbar.getTitle().toString());

        super.onSaveInstanceState(outState);




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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_add_new:
                onAddNewClicked();
                break;
            case R.id.nav_list:
                onListClicked();
                break;
            case R.id.nav_statistics:
                onStatisticsClicked();
                break;
            case R.id.nav_database:
                onDatabaseClicked();
                break;

            case R.id.nav_privacy:
                onPrivacyClicked();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onListClicked() {


        listFragment = new RecyclerViewCashFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, listFragment, listFragment.TAG);
        fragmentTransaction.addToBackStack(listFragment.TAG);
        fragmentTransaction.commit();

    }
    public void onAddNewClicked() {

        newCashTransactionFragment = new NewCashTransactionFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, newCashTransactionFragment);
        fragmentTransaction.addToBackStack(NewCashTransactionFragment.TAG);
        fragmentTransaction.commit();

    }

    public void onDatabaseClicked() {

        dataManagerFragment = new DataManagerFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, dataManagerFragment);
        fragmentTransaction.addToBackStack(DataManagerFragment.TAG);
        fragmentTransaction.commit();


    }

    public void onStatisticsClicked() {

        statFragment = new StatisticsFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, statFragment);
        fragmentTransaction.addToBackStack(StatisticsFragment.TAG);
        fragmentTransaction.commit();

    }
    public void onPrivacyClicked(){

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/alexey-dev/privacy-policy"));
        startActivity(browserIntent);

    }

    public void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }


}
