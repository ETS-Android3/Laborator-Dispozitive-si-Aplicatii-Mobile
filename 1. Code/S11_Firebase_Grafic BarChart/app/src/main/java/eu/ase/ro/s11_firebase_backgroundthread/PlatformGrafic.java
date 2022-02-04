package eu.ase.ro.s11_firebase_backgroundthread;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlatformGrafic extends View {

    //PAS 1: Obiectul cu care desenam
    private Paint paint;

    //PAS 2: Sursa de date pentru Grafic
    private Map<String, Integer> sursaDate;

    //PAS 3: Etichetele pentru bare
    private List<String> etichete;

    //PAS 4: Obiect Random pentru generarea culorii barelor aleator.
    private Random culoareRandom;


    //PAS 5: Initializarea campurilor clasei in Constructor.
    public PlatformGrafic(Context context, Map<String, Integer> sursaDate) {
        super(context);
        this.sursaDate = sursaDate;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.etichete = new ArrayList<>(sursaDate.keySet());
        this.culoareRandom = new SecureRandom();
    }

    //PAS 6: Desenarea efectiva a graficului

    @Override
    protected void onDraw(Canvas canvas) {
        if (sursaDate != null && sursaDate.size() > 0) {

            //PAS 7: Setarea caracteristicilor pensulei
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth((float) (getHeight() * 0.01));

            //PAS 8: Setare distanta fata de margini
            float margineInterioaraPeLatimeDreapta = (float) (getWidth() * 0.025);
            float margineInterioaraPeLatimeStanga = (float) (getWidth() * 0.025);
            float margineInterioaraPeLatimeTotal = margineInterioaraPeLatimeDreapta + margineInterioaraPeLatimeStanga;

            float margineInterioraPeInaltimeSus = (float) (getHeight() * 0.025);
            float margineInterioraPeInaltimeJos = (float) (getHeight() * 0.025);
            float margineInterioraPeInaltimeTotal = margineInterioraPeInaltimeSus + margineInterioraPeInaltimeJos;

            //PAS 9: Calculare SUPRAFATA EFECTIVA DE LUCRU
            float latimeDisponibilaPentruDesenat = getWidth() - margineInterioaraPeLatimeTotal;
            float inaltimeDisponibilaPentruDesenat = getHeight() - margineInterioraPeInaltimeTotal;

            //PAS 10: Desenare AXE GRAFIC - xOy
            //Desenare axa oY
            canvas.drawLine(margineInterioaraPeLatimeStanga,margineInterioraPeInaltimeSus, margineInterioaraPeLatimeStanga, margineInterioraPeInaltimeSus + inaltimeDisponibilaPentruDesenat, paint);

            //Desenare axa oX
            canvas.drawLine(margineInterioaraPeLatimeStanga, margineInterioraPeInaltimeSus + inaltimeDisponibilaPentruDesenat, margineInterioaraPeLatimeStanga + latimeDisponibilaPentruDesenat, margineInterioraPeInaltimeSus + inaltimeDisponibilaPentruDesenat, paint);


            // DESENARE BARE GRAFIC
            // PAS 11: Pentru desenare INALTIME BARE avem nevoie de:
            //Pentru inaltime avem nevoie de nr maxim de filme pe care le avem in sursa de date pentru a putea
            //determina mai apoi inaltimea fiecarei bare in functie de cate filme are fiecare platforma.
            double nrTotalFilme = calculareMaxim();


            //PAS 12: Desenare LATIME BARA
            float latimeBara = latimeDisponibilaPentruDesenat / sursaDate.size();

            //DESENAREA EFECTIVA A GRAFICULUI
            for (int i = 0; i < etichete.size(); i++) {
                double inaltimeBaraCurenta = sursaDate.get(etichete.get(i)) / nrTotalFilme;
                //unde i reprezinta platformele, iar numaratoarea lor incepe de la 0

                //PAS 13: Se seteaza o culoare random pentru fiecare coloana
                paint.setColor(calculareCuloareRandom());

                //PAS 14: DESENARE BARE
                float x1 =  margineInterioaraPeLatimeStanga + i * latimeBara;
                float x2 = x1 + latimeBara;
                float y1 = (float) ((1- inaltimeBaraCurenta) * inaltimeDisponibilaPentruDesenat + margineInterioraPeInaltimeJos);
                float y2 = margineInterioraPeInaltimeJos + inaltimeDisponibilaPentruDesenat;

                // drawRect -> metoda prin intermediul careia desenam un dreptunghi odata ce avem toate punctele
                canvas.drawRect(x1, y1, x2, y2, paint);

                //PAS 15: Desenare etichete pe bare
                drawLabel(canvas, x1, latimeBara, margineInterioraPeInaltimeJos, inaltimeDisponibilaPentruDesenat, etichete.get(i));
            }
        }
    }

    //PAS 11: Pentru desenare inaltime grafic
    //Calculare numarul total de filme rulate pe ambele platforme
    private double calculareMaxim() {
        double nrMaximFilme = 0;
        for (double value : sursaDate.values()) {
            if (nrMaximFilme < value) {
                nrMaximFilme = value;
            }
        }
        return nrMaximFilme;
    }

    //PAS 13:Calculare valoare Random
    private int calculareCuloareRandom() {
        int color = Color.argb(100,
                1 + culoareRandom.nextInt(254),
                1 + culoareRandom.nextInt(254),
                1 + culoareRandom.nextInt(254));
        return color;
    }

    //PAS 15: metoda folosita pentru a desena eticheta de pe fiecare bara
    private void drawLabel(Canvas canvas, float x1, float latimeBara, float margineInterioraPeInaltimeJos, float inaltimeDisponibilaPentruDesenat, String eticheta) {
        //Setare culoarea pentru etichete si grosimea textului
        paint.setColor(Color.RED);
        paint.setTextSize((float) (0.2 * latimeBara));

        float x = x1 + (latimeBara / 2);
        float y = 1 / 2 * margineInterioraPeInaltimeJos + inaltimeDisponibilaPentruDesenat;

        canvas.rotate(270, x, y);
        canvas.drawText(eticheta + " - " + sursaDate.get(eticheta), x, y, paint);
        canvas.rotate(-270, x, y); // readucem canvasul in pozitia initiala. ce se scrie inainte de rotatie ramane in aceeasi pozitie.


    }
}
