package eu.ase.ro.s10_database_backgroundthread.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "movies")
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_movie")
    private long idMovie;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "profit")
    private int profit;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "platform")
    private String platform;

    public Movie(long idMovie, String name, Date date, int profit, String genre, String platform) {
        this.idMovie = idMovie;
        this.name = name;
        this.date = date;
        this.profit = profit;
        this.genre = genre;
        this.platform = platform;
    }

    @Ignore
    public Movie(){}

    @Ignore
    public Movie(String name, Date date, int profit, String genre, String platform) {
        this.name = name;
        this.date = date;
        this.profit = profit;
        this.genre = genre;
        this.platform = platform;
    }


    public long getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(long idMovie) {
        this.idMovie = idMovie;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String  getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", profit=" + profit +
                ", genre='" + genre + '\'' +
                ", platform=" + platform +
                '}';
    }
}
