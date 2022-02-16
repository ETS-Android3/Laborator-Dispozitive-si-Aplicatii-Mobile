package eu.ase.ro.s07_networking_asynctask_json_xml.Parsare;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.s07_networking_asynctask_json_xml.clase.Constants;
import eu.ase.ro.s07_networking_asynctask_json_xml.clase.DateConverter;
import eu.ase.ro.s07_networking_asynctask_json_xml.clase.Movie;


public class MovieXmlParser {

    public static List<Movie> fromXml(String xml) {
        if (xml == null) {
            return new ArrayList<>();
        }
        //  xml = xml.replace("\"", "");   => Fara linia asta daca vrei sa mearga
        List<Movie> results = new ArrayList<>();
        try {
            readMovies(xml, results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private static void readMovies(String xml, List<Movie> results) throws XmlPullParserException, IOException {
        String value = null;
        int event;
        Movie movie = new Movie();

        XmlPullParser xmlParser = Xml.newPullParser();
        xmlParser.setInput(new StringReader(xml));
        event = xmlParser.getEventType();

        while (event != XmlPullParser.END_DOCUMENT) {
            String tagName = xmlParser.getName();
            if (XmlPullParser.START_TAG == event && tagName.equals(Constants.MOVIE_XML)) {

                movie = new Movie();
            }
            if (XmlPullParser.TEXT == event) {
                value = xmlParser.getText();
            }
            if (XmlPullParser.END_TAG == event && tagName.equals(Constants.MOVIE_XML)) {
                results.add(movie);
            }
            if (XmlPullParser.END_TAG == event && value != null && !value.trim().isEmpty()) {
                creareMovie(movie, tagName, value);
            }
            event = xmlParser.next();
        }
    }

    private static void creareMovie(Movie movie, String tagName, String value) {
        switch (tagName) {
            case Constants.TITLE:
                movie.setName(value);
                break;
            case Constants.RELEASE_DATE:
                movie.setDate(new DateConverter().fromString(value));
                break;
            case Constants.PROFIT:
                movie.setProfit(Integer.parseInt(value));
                break;
            case Constants.MOVIE_GENRE:
                movie.setGenre(value);
                break;
            case Constants.PLATFORM:
                movie.setPlatform(value);
                break;
        }
    }
}