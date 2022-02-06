package eu.ase.ro.s7_networking_executor_json.parsatori;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.s7_networking_executor_json.clase.Constants;
import eu.ase.ro.s7_networking_executor_json.clase.DateConverter;
import eu.ase.ro.s7_networking_executor_json.clase.Movie;

public class MovieJsonParser {

    public static List<Movie> fromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.MOVIES);
            return readArrayMoviesFromJson(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Movie> readArrayMoviesFromJson(JSONArray moviesArray) throws JSONException {
        List<Movie> listMovies = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            Movie movie = readObjectMovieFromJson(movieObject);
            listMovies.add(movie);
        }
        return listMovies;
    }

    private static Movie readObjectMovieFromJson(JSONObject movieObject) throws JSONException {
        String title = movieObject.getString(Constants.TITLE);
        String releaseDate = movieObject.getString(Constants.RELEASE_DATE);
        int profit = movieObject.getInt(Constants.PROFIT);
        String movieGenre = movieObject.getString(Constants.MOVIE_GENRE);
        String platform = movieObject.getString(Constants.PLATFORM);

        Date date = new DateConverter().fromString(releaseDate);
        return new Movie(title,date, profit, movieGenre, platform);

    }

}