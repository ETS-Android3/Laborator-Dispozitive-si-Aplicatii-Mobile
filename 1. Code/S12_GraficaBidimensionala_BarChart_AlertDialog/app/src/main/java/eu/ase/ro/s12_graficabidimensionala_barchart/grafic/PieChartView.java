package eu.ase.ro.s12_graficabidimensionala_barchart.grafic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PieChartView extends View {
    private Context context;
    private Paint paint;
    private Random culoarerRandom;
    private Map<String, Integer> sursaDateGrafic;
    private List<String> etichete;

    RectF rectf = new RectF(120, 120, 380, 380);
    float temp = 0;

    public PieChartView(Context context,  Map<String, Integer> sursaDateGrafic) {
        super(context);
        this.context = context;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.culoarerRandom = new SecureRandom();
        this.sursaDateGrafic = sursaDateGrafic;
        this.etichete = new ArrayList<>(sursaDateGrafic.keySet());

        value_degree = new float[values.length];
        for (int i = 0; i &lt values.length; i++) {
            value_degree[i] = values[i];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < value_degree.length; i++) {
            if (i == 0) {
                paint.setColor(defineColorRandom());
                canvas.drawArc(rectf, 0, value_degree[i], true, paint);
            } else {
                temp += value_degree[i - 1];
                paint.setColor(defineColorRandom());
                canvas.drawArc(rectf, temp, value_degree[i], true, paint);

            }
        }
    }

    private int defineColorRandom(){
        int color = Color.argb(100,
                culoarerRandom.nextInt(256),
                culoarerRandom.nextInt(256),
                culoarerRandom.nextInt(256));
        return color;
    }


}
