package in.krharsh17.barview_sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

import in.krharsh17.barview.BarModel;
import in.krharsh17.barview.BarView;

import static in.krharsh17.barview.BarView.getRandomColor;


public class MainActivity extends AppCompatActivity {

    BarView barView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
        barView = findViewById(R.id.barview);
        ArrayList<BarModel> barModels = new ArrayList<>();

        barModels.add(new BarModel(
                "Samsung",
                "30.91",
                getRandomColor(),
                0.31f
        ));

        barModels.add(new BarModel(
                "Apple",
                "25.89",
                getRandomColor(),
                1f
        ));

        barModels.add(new BarModel(
                "Huawei",
                "10.98",
                getRandomColor(),
                0.11f
        ));

        barModels.add(new BarModel(
                "Xiaomi",
                "7.8",
                getRandomColor(),
                0.07f
        ));

        barModels.add(new BarModel(
                "Oppo",
                "4.31",
                getRandomColor(),
                0.04f
        ));

        barModels.add(new BarModel(
                "Others",
                "20.11",
                getRandomColor(),
                0.20f
        ));


        barView.setData(barModels);
        barView.setOnBarClickListener(new BarView.OnBarClickListener() {
            @Override
            public void onBarClicked(int pos) {
                Toast.makeText(MainActivity.this, "Bar at position " + pos, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
