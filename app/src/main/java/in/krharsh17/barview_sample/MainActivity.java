package in.krharsh17.barview_sample;

import android.os.Bundle;

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
                "Kumar Harsh",
                "3.0",
                getRandomColor(),
                0.6f
        ));

        barModels.add(new BarModel(
                "Raj Kothari",
                "4.0",
                getRandomColor(),
                0.8f
        ));

        barModels.add(new BarModel(
                "Garima Singh",
                "3.6",
                getRandomColor(),
                0.72f
        ));

        barModels.add(new BarModel(
                "Anushka Chandel",
                "4.2",
                getRandomColor(),
                0.84f
        ));

        barModels.add(new BarModel(
                "Parth Sharma",
                "5.0",
                getRandomColor(),
                1.0f
        ));

        barModels.add(new BarModel(
                "Rakshita Jain",
                "2.0",
                getRandomColor(),
                0.4f
        ));


        barView.setData(barModels);


    }


}
