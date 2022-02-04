package eu.ase.ro.s12_graficabidimensionala_barchart.firebase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.s12_graficabidimensionala_barchart.clase.Movie;


public class FirebaseManager {

    //PAS 1: Referinta care ne ofera o conexiune catre Firebase
    private FirebaseDatabase firebaseDatabase;

    //PAS 2: Intanta unica a bazei de date
    private static FirebaseManager firebaseManager;

    //PAS 3: Referinta unui nod principal / tabele (Movies) - Echivalentul lui DAO - daca avem mai multe noduri definim mai multe referinte
    private DatabaseReference databaseReference;

    //PAS 4: Numele tabelei pe care dorim definim
    private static final String MOVIES_TABLE_NAME = "Movies";

    //PAS 5: Constructor PRIVAT - initializare legatura cu baza de date
    private FirebaseManager(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(MOVIES_TABLE_NAME);
    }

    //PAS 6: Metoda care returneaza unica instanta in exterior
    public  static FirebaseManager getInstance(){
        if(firebaseManager == null){
            synchronized (FirebaseManager.class){
                if(firebaseManager == null){
                    firebaseManager = new FirebaseManager();
                }
            }
        }
        return  firebaseManager;
    }

    //------------------------------------- OPERATII SIMPLE FIREBASE -------------------------------
    //PAS 7: Operatia de INSERT
    public void insert(Movie movie){
        if(movie == null || (movie.getIdMovie() != null && !movie.getIdMovie().trim().isEmpty())) {
            return;
        }
        String id = databaseReference.push().getKey();
        movie.setIdMovie(id);
        databaseReference.child(movie.getIdMovie()).setValue(movie);

    }

    //PAS 8: Operatia de UPDATE
    public void update(Movie movie){
        if(movie == null || movie.getIdMovie() == null || movie.getIdMovie().trim().isEmpty()){
            return;
        }
        databaseReference.child(movie.getIdMovie()).setValue(movie);

    }

    //PAS 9: Operatia de DELETE
    public void deleteSimple(Movie movie){
        if(movie == null ||movie.getIdMovie() == null || movie.getIdMovie().trim().isEmpty()){
            return;

        }
        databaseReference.child(movie.getIdMovie()).removeValue();
    }

    //-------------------------------- OPERATIII MAI PERFORMANTE FIREBASE --------------------------
    //PAS 10: Operatia de UPSERT
    public void upsert(Movie movie){
        if(movie == null){
            return;
        }
        //Insert: Verific daca obiectul are cheia creata sau daca este goala cheia respectiva, atunci inserez o cheie
        if(movie.getIdMovie() == null || movie.getIdMovie().trim().isEmpty()){
            String idMovie = databaseReference.push().getKey();
            movie.setIdMovie(idMovie);
        }
        //Update: Daca cheia este deja creata, atunci punem valori pentru atributele obiectului respectiv de la aceea cheie sau
        databaseReference.child(movie.getIdMovie()).setValue(movie);
    }


    //PAS 11: Operatia de DELETE CU EVENIMENT
    public void deleteCuEveniment(Movie movie){
        if(movie == null || movie.getIdMovie() == null ||movie.getIdMovie().trim().isEmpty())
        {
            return;
        }
        databaseReference.child(movie.getIdMovie()).removeValue();
        databaseReference.child(movie.getIdMovie()).removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FirebaseManager", "Remove is working");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseManager","Remove is not woking");

            }
        });
    }

    //------------------------- ATASAREA DE EVENIMENTE FIREBASE - SELECT ALL ---------------------------
    //PAS 12: Operatia de SELECT ALL - Orice modificare asupra nodului Movies, obliga Firebase-ul sa imi trimita semnale in acele metode
    public void atachDataChangeEventListener(final Callback<List<Movie>> mainThreadZone){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Conversia DataSnapshot-ului la Lista de Movie
                List<Movie> movies = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Movie movie = data.getValue(Movie.class);
                    if(movie != null){
                        movies.add(movie);
                    }
                }
                mainThreadZone.runResultOnUiThread(movies);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseManager", "Data is no available");
            }
        });
    }




}
