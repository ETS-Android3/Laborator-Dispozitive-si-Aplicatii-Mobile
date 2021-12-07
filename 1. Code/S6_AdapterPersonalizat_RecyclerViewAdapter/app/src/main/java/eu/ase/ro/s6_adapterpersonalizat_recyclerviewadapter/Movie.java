package eu.ase.ro.s6_adapterpersonalizat_recyclerviewadapter;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {
        public static final String DATE_PATTERN = "dd-mm-yyyy";
        DateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);


        private String name;
        private Date date;
        private int profit;
        private float rating;
        private String genre;
        private String platform;



        public Movie(String name, Date date, int profit,float rating, String genre, String platform) {
            this.name = name;
            this.date = date;
            this.profit = profit;
            this.rating = rating;
            this.genre = genre;
            this.platform = platform;

        }

        protected Movie(Parcel in) {
            name = in.readString();
            try {
                date = simpleDateFormat.parse(in.readString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            profit = in.readInt();
            rating = in.readFloat();
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
        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        @Override
        public String toString() {
            return "Movie{" +
                    "name='" + name + '\'' +
                    ", date=" + date +
                    ", profit=" + profit +
                    ", genre='" + genre + '\'' +
                    ", platform='" + platform + '\'' +
                    ", rating=" + rating +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(simpleDateFormat.format(date));
            parcel.writeInt(profit);
            parcel.writeFloat(rating);
            parcel.writeString(genre);
            parcel.writeString(platform);
        }

}
