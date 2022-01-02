package eu.ase.ro.s6_adapterpersonalizat_recyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>  {
    //PAS 1: Cream lista de movie
    private Context context;
    private ArrayList<Movie> movieList;

    //PAS 2: Cream un nou constructor
    public MovieAdapter(Context context, ArrayList<Movie> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    //PAS 3: Creare a treia componenta a unui adaptor - Layout (facem legatura dint View Holder - Layout si Adapter)
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_movies_row, parent,false);
        return new MovieViewHolder(view);
    }

    //PAS 4: Initializam componentele vizuale din Layout - InitComponents()
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate, tvProfit, tvPlatforma, tvMovieGenre, tvRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.rv_tv_title);
            tvReleaseDate = itemView.findViewById(R.id.rv_tv_releaseDate);
            tvProfit = itemView.findViewById(R.id.rv_tv_profit);
            tvMovieGenre = itemView.findViewById(R.id.rv_tv_genre);
            tvPlatforma = itemView.findViewById(R.id.rv_tv_platforma);
            tvRating = itemView.findViewById(R.id.rv_tv_rating);
        }
    }

    //PAS 5: Facem legatura dintre atributele unui Movie din lista de la o anumita pozitie - componentele vizuale
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getName());
        holder.tvReleaseDate.setText("Release date: "+ movie.getDate());
        holder.tvProfit.setText("Profit: " + movie.getProfit() );
        holder.tvMovieGenre.setText(movie.getGenre());
        holder.tvPlatforma.setText(movie.getPlatform());
        holder.tvRating.setText("Rating: " + movie.getRating());
    }

    //PAS 6: Returnam lungimea listei
    @Override
    public int getItemCount() {
        return movieList.size();
    }

}