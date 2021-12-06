package eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable {
    private String name;
    private Date date;
    private int profit;
    private String genre;
    private Platform platform;

    public Movie(String name, Date date, int profit, String genre, Platform platform) {
        this.name = name;
        this.date = date;
        this.profit = profit;
        this.genre = genre;
        this.platform = platform;
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

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
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
