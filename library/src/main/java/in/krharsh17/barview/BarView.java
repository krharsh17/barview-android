package in.krharsh17.barview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class BarView extends ScrollView implements Constants {
    private LinearLayout containerLayout;
    private Context context;

    private List<BarGroup> barGroups;
    private List<BarModel> data;

    private int barMargin = 6;
    private int verticalSpacing = 48;
    private int barHeight = 20;
    private int labelFontSize = 18;
    private int valueFontSize = 9;

    private String labelTextColor = Constants.LABEL_TEXT_COLOR;
    private String valueTextColor = Constants.VALUE_TEXT_COLOR;
    private String rippleColor = Constants.RIPPLE_COLOR;                   // has to be >2

    public int getBarMargin() {
        return barMargin;
    }

    public void setBarMargin(int barMargin) {
        this.barMargin = barMargin;
    }

    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public int getLabelFontSize() {
        return labelFontSize;
    }

    public void setLabelFontSize(int labelFontSize) {
        this.labelFontSize = labelFontSize;
    }

    public int getValueFontSize() {
        return valueFontSize;
    }

    public void setValueFontSize(int valueFontSize) {
        this.valueFontSize = valueFontSize;
    }

    public String getLabelTextColor() {
        return labelTextColor;
    }

    public void setLabelTextColor(String labelTextColor) {
        this.labelTextColor = labelTextColor;
    }

    public String getValueTextColor() {
        return valueTextColor;
    }

    public void setValueTextColor(String valueTextColor) {
        this.valueTextColor = valueTextColor;
    }

    public String getRippleColor() {
        return rippleColor;
    }

    public void setRippleColor(String rippleColor) {
        this.rippleColor = rippleColor;
    }



    public void setData(List<BarModel> data) {
        this.data = data;
        populateBarView();
    }

    private void populateBarView() {
        for (BarModel b : data) {
            addBar(b);
        }
    }

    private void addBar(BarModel data) {
        BarGroup barGroup = new BarGroup(
                context,
                data.getLabel(),
                data.getColor(),
                data.getValue(),
                data.getFillRatio(),
            barMargin,
            verticalSpacing,
            barHeight,
            labelFontSize,
            valueFontSize,
            labelTextColor,
            valueTextColor,
            rippleColor
        );
        barGroup.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        barGroups.add(barGroup);

        containerLayout.addView(barGroup);
        invalidate();
        requestLayout();
    }

    public BarView(Context context) {
        super(context);
        this.context = context;
        barGroups = new ArrayList<>();
        containerLayout = new LinearLayout(context);

        containerLayout.setLayoutParams(new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        containerLayout.setOrientation(LinearLayout.VERTICAL);

        this.addView(containerLayout);

    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        barGroups = new ArrayList<>();
        containerLayout = new LinearLayout(context);

        containerLayout.setLayoutParams(new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        containerLayout.setOrientation(LinearLayout.VERTICAL);

        this.addView(containerLayout);

        if (attrs != null) {

            final TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.BarView, 0, 0);
            verticalSpacing = a.getInteger(R.styleable.BarView_barGroupSpacing, verticalSpacing);
            barHeight = a.getInteger(R.styleable.BarView_barHeight, barHeight);
            labelFontSize = a.getInteger(R.styleable.BarView_labelTextSize, labelFontSize);
            valueFontSize = a.getInteger(R.styleable.BarView_valueTextSize, valueFontSize);
            labelTextColor = a.getString(R.styleable.BarView_labelTextColor);
            valueTextColor = a.getString(R.styleable.BarView_valueTextColor);
            rippleColor = a.getString(R.styleable.BarView_rippleColor);
            if (labelTextColor == null)
                labelTextColor = Constants.LABEL_TEXT_COLOR;
            if (valueTextColor == null)
                valueTextColor = Constants.VALUE_TEXT_COLOR;
            if (rippleColor == null)
                rippleColor = RIPPLE_COLOR;
            a.recycle();
        }
    }

    public static String getRandomColor() {
        char[] letters = CHAR_ARRAY.toCharArray();
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            color.append(letters[(int) Math.round(Math.floor(Math.random() * 16))]);
        }
        return color.toString();
    }
}
