package eu.ase.ro.s10_database_backgroundthread.database.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.s10_database_backgroundthread.database.entities.Movie;

@Dao
public interface MovieDao {

    @Insert
    long insert(Movie movie);          //long reprezinta valoarea id-ului, iar daca returneaza -1 inseamna ca avem eroare.

    @Update
    int update(Movie movie);          //int reprezinta nr de randuri afectate in urma update-ului (ar trebui sa fie 1)

    @Delete
    int delete(Movie movie);          //int reprezinta nr de randuri sterse (0 inseamna nr de randuri afectate, dar fara probleme de

    @Query("SELECT * FROM movies")    //CTRL + cursor pe "expenses"
    List<Movie> selectAll();

    @Query("DELETE FROM movies")
    void deleteAll();                 //int reprezinta nr de randuri sterse (0 inseamna nr de randuri afectate, dar fara probleme de SQL, pe cand -1 reprezinta ca avem o eroare de SQL)

    @Query("DELETE FROM movies WHERE name = :name")
    void deleteTitle(String name);

}