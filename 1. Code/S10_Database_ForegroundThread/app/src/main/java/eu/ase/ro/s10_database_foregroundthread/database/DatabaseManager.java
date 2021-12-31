package eu.ase.ro.s10_database_foregroundthread.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.ro.s10_database_foregroundthread.clase.DateConverter;
import eu.ase.ro.s10_database_foregroundthread.database.entities.Cinema;
import eu.ase.ro.s10_database_foregroundthread.database.entities.Movie;
import eu.ase.ro.s10_database_foregroundthread.database.dao.CinemaDao;
import eu.ase.ro.s10_database_foregroundthread.database.dao.MovieDao;

@Database(entities = {Movie.class, Cinema.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
    public abstract class DatabaseManager extends RoomDatabase {

        //PAS 1: Setam numele clasei si cream o instanta statica privata a clasei
        private static final String DATABASE_NAME = "database.db";
        private static DatabaseManager databaseManager;


        //PAS 2: Metoda statica ce intoarce instanta statica (gen constructor care v-a intoarce aceeasi instanta mereu)
        public static DatabaseManager getInstance(Context context) {
            if (databaseManager == null) {
                synchronized (DatabaseManager.class) {
                    if (databaseManager == null) {
                        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DATABASE_NAME)

                                //Metoda care lasa query-urile sa se realizeze pe MainThread
                                .allowMainThreadQueries()

                                //Metoda care reconstruieste baza de date in momentul in care schema bazei de date se modifica
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return databaseManager;
        }

        //PAS 3: Facem legatura cu DAO
        public abstract MovieDao getMovieDao();
        public abstract CinemaDao getCinemaDao();
}
