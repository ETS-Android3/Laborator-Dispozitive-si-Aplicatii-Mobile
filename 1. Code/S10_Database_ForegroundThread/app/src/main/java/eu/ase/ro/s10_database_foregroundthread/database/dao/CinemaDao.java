package eu.ase.ro.s10_database_foregroundthread.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.s10_database_foregroundthread.database.entities.Cinema;

@Dao
public interface CinemaDao {

    @Insert
    long insert(Cinema cinema);

    @Update
    int update(Cinema cinema);

    @Query("SELECT * FROM cinemas")
    List<Cinema> getAll();

    @Query("DELETE FROM cinemas")
    void deleteAll();

    @Query("DELETE FROM cinemas WHERE cinema_name = :nameCinema")
    void delete(String nameCinema);
}