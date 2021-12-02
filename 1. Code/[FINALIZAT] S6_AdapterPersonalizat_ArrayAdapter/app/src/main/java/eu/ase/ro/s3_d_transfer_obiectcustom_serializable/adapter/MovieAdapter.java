package eu.ase.ro.s3_d_transfer_obiectcustom_serializable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.R;
import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Movie;

//PAS 1: Extindem Array Adapter
public class MovieAdapter extends ArrayAdapter<Movie> {

    //PAS 3: Declaram atribute pentru parametrii din constructor
   private Context context;
   private int resource;
   private List<Movie> movieList;
   private LayoutInflater inflater;

   //PAS 5: Declari atribute componente pentru partea vizuala
   private Movie movie;
   private View view;
   private TextView tvName, tvDate, tvProfit, tvGenre, tvPlatform;


   //PAS 2: Implementam constructorul sugerat (penultimul) + adaugam un Layout Inflater
    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        //PAS 4: Initializam atributele in constructor
        this.context = context;
        this.resource = resource;
        this.movieList = objects;
        this.inflater = inflater;
    }

    //PAS 6: Suprascriem metoda care face conversia unui obiect de tip Movie -> instanta de tip View
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        view = inflater.inflate(resource,parent,false);
        movie = movieList.get(position);
        if(movie != null){
            initComponentsAdapter();
            validate(tvName,movie.getName());
            validate(tvDate, String.valueOf(movie.getDate()));
            validate(tvProfit, String.valueOf(movie.getProfit()));
            validate(tvGenre, movie.getGenre());
            validate(tvPlatform, String.valueOf(movie.getPlatform()));
            return view;
        }

        return view;
    }

    //PAS 7: Initializare componente adapter
    private void initComponentsAdapter(){
         tvName = view.findViewById(R.id.adapterLV_tv_name);
         tvDate = view.findViewById(R.id.adapterLV_tv_date);
         tvProfit = view.findViewById(R.id.adapterLV_tv_profit);
         tvGenre = view.findViewById(R.id.adapterLV_tv_genre);
         tvPlatform = view.findViewById(R.id.adapter_tv_platform);
    }

    //PAS 8: Facem validari
    private void validate(TextView tvAll, String valueAtribut){
        if(valueAtribut != null &&!valueAtribut.trim().isEmpty()){
            tvAll.setText(valueAtribut);
        } else{
            tvAll.setText(R.string.movieAdaptor_tv_valueDefault);
        }
    }
}
