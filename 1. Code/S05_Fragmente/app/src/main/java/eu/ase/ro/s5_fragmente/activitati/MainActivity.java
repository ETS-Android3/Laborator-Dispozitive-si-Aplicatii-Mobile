package eu.ase.ro.s5_fragmente.activitati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import eu.ase.ro.s5_fragmente.Constante;
import eu.ase.ro.s5_fragmente.R;
import eu.ase.ro.s5_fragmente.fragmente.ChatFragment;
import eu.ase.ro.s5_fragmente.fragmente.MessageFragment;
import eu.ase.ro.s5_fragmente.fragmente.ProfileFragment;
import eu.ase.ro.s5_fragmente.fragmente.SendFragment;
import eu.ase.ro.s5_fragmente.fragmente.ShareFragment;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    //PAS 1:Creare obiecte asociate fragmentelor
        /*Nu scriem nimic in OnCreare pentru ceare lor doarece acest lucru se face in cadrul fiecarui
       fragment in metoda OnCreateView() */
    MessageFragment messageFragment = new MessageFragment();
    ChatFragment chatFragment = new ChatFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    SendFragment sendFragment = new SendFragment();
    ShareFragment shareFragment = new ShareFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        clickNavigationViewMenuItem();

        //Setarea unui fragment default atunci cand se deschide aplicatia.
        /*Fragmentul trebuie sa se seteze doar daca avem pagina alba, asa ca ne asigura ca nu avem o stare
        anteior salvata */
        if(savedInstanceState == null) {
            setFragmentDefault();
        }
    }

    private void initComponents(){
        drawerLayout = findViewById(R.id.drawerLayout);

        toolbar = findViewById(R.id.navigation_drawer_toolBar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_toggle,R.string.close_toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
    }
    private void clickNavigationViewMenuItem(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationDrawerMenu_item_message:
                        openFragment(messageFragment);
                        Toast.makeText(getApplicationContext(), R.string.message, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigationDrawerMenu_item_chat:
                        openFragment(chatFragment);
                        Toast.makeText(getApplicationContext(), R.string.chat, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigationDrawerMenu_item_profile:
                        openFragment(profileFragment);
                        sendStringInteger();
                        Toast.makeText(getApplicationContext(), R.string.profil, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigationDrawerMenu_item_send:
                        openFragment(sendFragment);
                        Toast.makeText(getApplicationContext(), R.string.send, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigationDrawerMenu_item_share:
                        openFragment(shareFragment);
                        Toast.makeText(getApplicationContext(), R.string.share, Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    private void openFragment(Fragment fragment){
        //PAS 1: Creare FragmentManager si luarea Fragment- Managerul asociat activitatii
        FragmentManager fragmentManager = getSupportFragmentManager();

        //PAS 2: Fragment-Managerul manipuleaza fragmentele cu ajutorul unui FragmentTranzation
        //Cream tranzatia - tranzatia incepe
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Operatia pe care vrem sa o executam asupra fragmentului
        fragmentTransaction.replace(R.id.main_frameLayout_container,fragment);

        //Dam commit ca efectele tranzatiei noastre sa se vada
        fragmentTransaction.commit();
    }

    private void setFragmentDefault(){
            //PAS 1: Creare FragmentManager si luarea Fragment-Managerul asociat activitatii
            FragmentManager fragmentManager = getSupportFragmentManager();

            //PAS 2: FragmentManagerul manipuleaza fragmentele cu ajutorul unui FragmentTranzation
            //Cream tranzatia - tranzatia incepe
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            //Operatia pe care vrem sa o executam asupra fragmentului
            fragmentTransaction.add(R.id.main_frameLayout_container,messageFragment);

            //Dam commit ca efectele tranzatiei noastre sa se vada
            fragmentTransaction.commit();

    }

    private void sendStringInteger(){
        //PAS 1: Cream clasa Bundle care ne transporta informatia
        Bundle bundle = new Bundle();

        //PAS 2: Cream informatia pe care vrem sa o transmitem
        String stringSend = "Eu ma numesc Adriana Giol si am varsta ";
        int integerSend = 24;

        //PAS 3: Incarcam informatia in Bundle
        bundle.putString(Constante.SEND_MAIN_PROFILE_STRING, stringSend);
        bundle.putInt(Constante.SEND_MAIN_PROFILE_INTEGER, integerSend);

        //PAS 4:Anuntam fragmentul caruia ii trimitem informatia, ca o sa vina un Bundle
        profileFragment.setArguments(bundle);

        //PAS 5: Odata ce am trimis informatia, fragmentul solicitat se deschide automat
        openFragment(profileFragment);

    }
}