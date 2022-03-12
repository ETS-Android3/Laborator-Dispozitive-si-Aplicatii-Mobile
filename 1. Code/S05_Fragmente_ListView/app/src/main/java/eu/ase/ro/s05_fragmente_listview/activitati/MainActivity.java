package eu.ase.ro.s05_fragmente_listview.activitati;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import eu.ase.ro.s05_fragmente_listview.R;
import eu.ase.ro.s05_fragmente_listview.clase.Movie;
import eu.ase.ro.s05_fragmente_listview.fragmente.ContactFragment;
import eu.ase.ro.s05_fragmente_listview.fragmente.HomeFragment;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 200;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    FloatingActionButton fabOpenForm;

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Fragment fragmentCurent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initNavigationDrawerMenu();
        clickNavigationMenuItem();
        clickFabOpenForm();

        //este NULL doar daca am intrat prima data in aplicatie, la rotirea dispozitivului mobil nu mai este NULL
        if(savedInstanceState == null){
            //deschidere fragment
            fragmentCurent = HomeFragment.newInstance((ArrayList<Movie>) listMovies);
            openFragment();

            //marcare optiune aleasa
            navigationView.setCheckedItem(R.id.navigationDrawerMenu_item_home);
        }
    }

    private void initComponents(){
        fabOpenForm = findViewById(R.id.appBar_fab_openForm);
    }
    private void initNavigationDrawerMenu(){
        drawerLayout = findViewById(R.id.drawerLayout);

        toolbar = findViewById(R.id.navigation_drawer_toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_toggle,R.string.close_toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
    }
    private void clickNavigationMenuItem() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigationDrawerMenu_item_home:
                        fragmentCurent = HomeFragment.newInstance((ArrayList<Movie>) listMovies);
                        Toast.makeText(getApplicationContext(), R.string.home, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navigationDrawerMenu_item_contact:
                        fragmentCurent = new ContactFragment();
                        Toast.makeText(getApplicationContext(), R.string.contact, Toast.LENGTH_LONG).show();
                        break;
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void clickFabOpenForm(){
        fabOpenForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data !=null){
            Movie movie = data.getParcelableExtra(SecondActivity.KEY_MOVIE);
            listMovies.add(movie);

            //Notificare Adapter
            if(fragmentCurent instanceof HomeFragment){
                ((HomeFragment) fragmentCurent).notiyAdapter();
            }
        }
    }

    private void openFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentMain_frameLayout, fragmentCurent)
                .commit();
    }
}