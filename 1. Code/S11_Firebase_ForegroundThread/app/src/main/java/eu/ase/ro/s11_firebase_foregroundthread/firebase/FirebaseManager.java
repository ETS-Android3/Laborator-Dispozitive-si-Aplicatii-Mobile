package eu.ase.ro.s11_firebase_foregroundthread.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import eu.ase.ro.s11_firebase_foregroundthread.clase.Movie;

public class FirebaseManager {

    //PAS 1: Referinta care ne ofera o conexiune catre Firebase
    private FirebaseDatabase firebaseDatabase;

    //PAS 2: Intanta unica a bazei de date
    private static FirebaseManager firebaseManager;

    //PAS 3: Referinta unui nod / tabele (Movies) - Asemanator unui DAO - daca avem mai multe noduri definim mai multe referinte
    private DatabaseReference databaseReference;

    //PAS 4: Numele tabelei pe care dorim definim
    private static final String MOVIES_TABLE_NAME = "Movies";

    //PAS 5: Constructor  - initializare legatura cu baza de date
    public FirebaseManager(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(MOVIES_TABLE_NAME);
    }

    //PAS 5: INSERT - Metoda prin care adaugam un film in Baza de date (instante)
    public void insertMovieInFirebase(Movie movie){
        if(movie != null){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    movie.setIdMovie(databaseReference.push().getKey()); //cheie autogenerata
                    databaseReference.child(movie.getIdMovie()).setValue(movie);
                    Log.i("Firebase Eveniment", "The movie has been added to firebase");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase Eveniment", "The insert did not work");
                }
            });
        }
    }

    //PAS 5: SELECT ALL - Metoda prin care facem operatia de citire din Firebase
    public void selectAllMoviesFromFirebase(ValueEventListener eventListener){
        if(eventListener != null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
