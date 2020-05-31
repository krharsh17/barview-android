package `in`.krharsh17.barview_sample

import `in`.krharsh17.barview.BarModel
import `in`.krharsh17.barview.BarView
import `in`.krharsh17.barview.BarView.Companion.randomColor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var barView: BarView? = null
    var cornerRadius = 20 //in dp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barView = findViewById(R.id.barview)
        val barModels: ArrayList<BarModel> = ArrayList<BarModel>()
        barModels.add(BarModel(
                "Samsung",
                "30.91",
                randomColor,
                0.31f, 0, 0
        ))
        barModels.add(BarModel(
                "Apple",
                "25.89",
                randomColor,
                1f, 5, 5
        ))
        barModels.add(BarModel(
                "Huawei",
                "10.98",
                randomColor,
                0.11f, 8, 4
        ))
        barModels.add(BarModel(
                "Xiaomi",
                "7.8",
                randomColor,
                0.07f, 12, 7
        ))
        barModels.add(BarModel(
                "Oppo",
                "4.31",
                randomColor,
                0.04f, 7, 18
        ))
        barModels.add(BarModel(
                "Others",
                "20.11",
                randomColor,
                0.20f, 8, 10
        ))
        barView!!.setData(barModels)
        barView!!.setCornerRadius(cornerRadius)
        barView!!.onBarClickListener = object : BarView.OnBarClickListener {
            override fun onBarClicked(pos: Int) {
                // Do Something
            }
        }
    }
}