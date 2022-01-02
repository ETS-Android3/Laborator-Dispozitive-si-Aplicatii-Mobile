package eu.ase.ro.s10_database_backgroundthread.database.service;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.s10_database_backgroundthread.asyncTask.AsyncTaskRunner;
import eu.ase.ro.s10_database_backgroundthread.asyncTask.Callback;
import eu.ase.ro.s10_database_backgroundthread.database.DatabaseManager;
import eu.ase.ro.s10_database_backgroundthread.database.dao.MovieDao;
import eu.ase.ro.s10_database_backgroundthread.database.entities.Movie;

public class MovieService {

    //PAS 1: O instanta a Dao-ului clasei si o instanta a clasei AsyncTaskRunner
    private final MovieDao movieDao;
    private final AsyncTaskRunner taskRunner;

    //PAS 2: Constructor (generat) si il modificam
    public MovieService(Context context) {
        movieDao = DatabaseManager.getInstance(context).getMovieDao();
        taskRunner = new AsyncTaskRunner();
    }

    //PAS 3: Operatia de Insert
    public void insert(final Movie movie, Callback<Movie> mainThreadZone) {
        Callable<Movie> insertOperation = new Callable<Movie>() {
            @Override
            public Movie call() {
                if (movie == null || movie.getIdMovie() > 0) {
                    return null;
                }
                long idMovie = movieDao.insert(movie);
                if (idMovie == -1) {
                    return null;
                }
                movie.setIdMovie(idMovie);
                return movie;
            }
        };
        taskRunner.executeAsync(insertOperation, mainThreadZone);
    }


    // PAS 4: Pentru fiecare metoda din DAO respectiv, realizam cate o metoda care sa aiba Callable si Callback
    //SELECT:  Callable e realizat de tipul celui din Dao - returneaza o Lista de <Expense>
    public void selectAll(Callback<List<Movie>> mainThreadZone) {
        Callable<List<Movie>> selectOperation = new Callable<List<Movie>>() {
            @Override
            public List<Movie> call() {
                return movieDao.selectAll();
            }
        };
        taskRunner.executeAsync(selectOperation, mainThreadZone);
    }

    public List<Movie> selectAllVersion2(){
        return movieDao.selectAll();
    }


    //PAS 5: Operatia de Update
    public void update(final Movie movie, Callback<Movie> mainThreadZone) {
        Callable<Movie> updateOperation = new Callable<Movie>() {
            @Override
            public Movie call() {
                if (movie == null || movie.getIdMovie() <= 0) {
                    return null;
                }
                int count = movieDao.update(movie);
                if (count <= 0) {
                    return null;
                }
                return movie;
            }
        };
        taskRunner.executeAsync(updateOperation, mainThreadZone);
    }

    //PAS 6: Operatia de Delete
    public void delete(final Movie movie, Callback<Boolean> mainThreadZone) {
        Callable<Boolean> deleteOperation = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                if (movie == null || movie.getIdMovie() <= 0) {
                    return false;
                }
                int count = movieDao.delete(movie);
                return count > 0;
            }
        };
        taskRunner.executeAsync(deleteOperation, mainThreadZone);
    }


}
