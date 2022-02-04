package eu.ase.ro.s14_googlemaps;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.s14_googlemaps.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //PAS 1: Creare lisa de intrari pentru care vom face pin-ul respectiv.
        List<Coordonate> listaCoordonateOrase = new ArrayList<>();
        listaCoordonateOrase.add(new Coordonate(44.44,26.09, "Bucuresti"));
        listaCoordonateOrase.add(new Coordonate(51.50,-0.11, "Londra"));
        listaCoordonateOrase.add(new Coordonate(45.58,2.34, "Paris"));
        listaCoordonateOrase.add(new Coordonate(52.51,13.40, "Berlin"));
        listaCoordonateOrase.add(new Coordonate(53.47,-2.25, "Manchester"));

        //PAS 2: Parcurgem lista si pentru fiecare intrare din lista o sa punem un pic pe Harta
        for(Coordonate coordonata: listaCoordonateOrase){

            //PAS 3: Lating = obiectul care ne ajuta sa construim un Pin pe baza celor 2 coordonate
            LatLng pozitiePeHarta = new LatLng(coordonata.getLatitudine(),coordonata.getLongitudine());

            //PAS 4: Creare Pin
            Marker markerPin = mMap.addMarker(new MarkerOptions()
                    .position(pozitiePeHarta)
                    .icon(crearePinFromVector(getApplicationContext(),R.drawable.ic_baseline_fastfood_24))
                    .title(coordonata.getNumeOras()));
            markerPin.showInfoWindow();  //Metoda care afiseaza Pinul pe Harta
        }



    }

    private BitmapDescriptor crearePinFromVector(Context context, int vectorResourseId){
        //PAS 1: Convertire vector in BitMap
        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResourseId);

        //PAS 2: Setare dimensiune maxima pentru Pin - pentru ca lucram cu imagine folosim doar RIGHT si BOTTOM
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        //PAS 3:Initializam obiectul de tip BitMap
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        //PAS 4: Desenam imaginea pe panza
        Canvas canvas = new Canvas(bitmap);

        //PAS 5:Desenam bitmapul pe baza vectorului nostru
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}