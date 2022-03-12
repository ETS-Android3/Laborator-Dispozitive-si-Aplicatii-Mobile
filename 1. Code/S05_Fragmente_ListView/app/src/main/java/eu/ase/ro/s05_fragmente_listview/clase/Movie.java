package eu.ase.ro.s05_fragmente_listview.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {
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

    protected Movie(Parcel in) {
        name = in.readString();
        date = DateConverter.fromString(in.readString());
        profit = in.readInt();
        genre = in.readString();
        platform = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
                ", date= " + DateConverter.toString(date) +
                ", profit=" + profit +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(DateConverter.toString(date));
        parcel.writeInt(profit);
        parcel.writeString(genre);
        parcel.writeString(platform);
    }
}