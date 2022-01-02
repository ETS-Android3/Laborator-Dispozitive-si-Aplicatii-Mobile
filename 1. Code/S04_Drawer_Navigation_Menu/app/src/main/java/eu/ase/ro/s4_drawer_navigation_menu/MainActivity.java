package eu.ase.ro.s4_drawer_navigation_menu;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

        toolbar = findViewById(R.id.navigation_drawer_toolBar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.toggle_open, R.string.toggle_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_item_message:
                        Toast.makeText(getApplicationContext(), R.string.toast_message,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_item_chat:
                        Toast.makeText(getApplicationContext(), R.string.toast_chat, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_item_profil:
                        Toast.makeText(getApplicationContext(), R.string.toast_profile,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_item_send:
                        Toast.makeText(getApplicationContext(), R.string.toast_send, Toast.LENGTH_LONG).show();
                        break;
                    case R.id.menu_item_share:
                        Toast.makeText(getApplicationContext(), R.string.toast_share, Toast.LENGTH_LONG).show();
                        break;
                    default:
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}