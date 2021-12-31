package eu.ase.ro.s10_database_foregroundthread.database.entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cinemas")
public class Cinema {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cinema")
    private int idCinema;

    @ColumnInfo(name = "cinema_name")
    private String cinemaName;
    @ColumnInfo(name = "no_rooms")
    private int noRooms;
    @ColumnInfo(name = "location")
    private String location;

    public Cinema(int idCinema, String cinemaName, int noRooms, String location) {
        this.idCinema = idCinema;
        this.cinemaName = cinemaName;
        this.noRooms = noRooms;
        this.location = location;
    }

    public int getIdCinema() {
        return idCinema;
    }

    public void setIdCinema(int idCinema) {
        this.idCinema = idCinema;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "idCinema=" + idCinema +
                ", cinemaName='" + cinemaName + '\'' +
                ", noRooms=" + noRooms +
                ", location='" + location + '\'' +
                '}';
    }
}
