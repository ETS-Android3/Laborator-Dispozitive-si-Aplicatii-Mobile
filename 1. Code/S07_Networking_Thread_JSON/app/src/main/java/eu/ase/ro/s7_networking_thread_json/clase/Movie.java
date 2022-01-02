package eu.ase.ro.s7_networking_thread_json.clase;
import java.io.Serializable;
import java.util.Date;

public class Movie {
    private String name;
    private Date date;
    private int profit;
    private String genre;
    private String platform;

    public Movie(String name, Date date, int profit, String genre, String platform) {
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

    public String getPlatform() {

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