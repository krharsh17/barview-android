package in.krharsh17.barview_sample;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import in.krharsh17.barview.BarModel;
import in.krharsh17.barview.BarView;

import static in.krharsh17.barview.BarView.getRandomColor;

public class MainActivity extends AppCompatActivity {
    BarView barView;
    int cornerRadius = 20;//in dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barView = findViewById(R.id.barview);
        ArrayList<BarModel> barModels = new ArrayList<>();

        barModels.add(new BarModel(
            "Samsung",
            "30.91",
            getRandomColor(new Color().rgb(0,128,0)),
            0.31f, 0, 0
        ));

        barModels.add(new BarModel(
            "Apple",
            "25.89",
            getRandomColor(),
            1f, 5, 5
        ));

        barModels.add(new BarModel(
            "Huawei",
            "10.98",
            getRandomColor(),
            0.11f, 8, 4
        ));

        barModels.add(new BarModel(
            "Xiaomi",
            "7.8",
            getRandomColor(),
            0.07f, 12, 7
        ));

        barModels.add(new BarModel(
            "Oppo",
            "4.31",
            getRandomColor(),
            0.04f, 7, 18
        ));

        barModels.add(new BarModel(
            "Others",
            "20.11",
            getRandomColor(),
            0.20f, 8, 10
        ));
        barView.setData(barModels);

        barView.setCornerRadius(cornerRadius);

        barView.setOnBarClickListener(new BarView.OnBarClickListener() {
            @Override
            public void onBarClicked(int pos) {
                Toast.makeText(MainActivity.this, "Bar at position " + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
