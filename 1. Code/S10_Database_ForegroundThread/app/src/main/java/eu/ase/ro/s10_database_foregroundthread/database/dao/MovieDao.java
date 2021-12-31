package eu.ase.ro.s10_database_foregroundthread.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.s10_database_foregroundthread.database.entities.Movie;

@Dao
public interface MovieDao {

    @Insert
    long insert(Movie movie);

    @Update
    int update(Movie movie);

    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("DELETE FROM movies")
    void deleteAll();

    @Query("DELETE FROM movies WHERE name = :name")
    void deleteTitle(String name);

}
