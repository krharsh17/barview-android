package in.krharsh17.barview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class BarView extends ScrollView implements Constants {
    private LinearLayout containerLayout;
    private Context context;
    private OnBarClickListener onBarClickListener;
    private List<BarGroup> barGroups;
    private List<BarModel> data;
    private int barMargin = 6;
    private int verticalSpacing = 48;
    private int barHeight = 20;
    private int labelFontSize = 18;
    private int valueFontSize = 9;
    private String backgroundColor;
    private String gradientStart;
    private String gradientEnd;
    private String gradientDirection;
    private int cornerRadius;
    private String labelTextColor = Constants.LABEL_TEXT_COLOR;
    private String valueTextColor = Constants.VALUE_TEXT_COLOR;
    private String LABEL_FONT=null,VALUE_FONT=null;
    private String rippleColor = Constants.RIPPLE_COLOR; // has to be >2
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

    /**
     * Returns a reference to the attached Listener
     */
    public OnBarClickListener getOnBarClickListener() {
        return onBarClickListener;
    }

    /**
     * Attaches a onBarClickListener
     */
    public void setOnBarClickListener(OnBarClickListener onBarClickListener) {
        this.onBarClickListener = onBarClickListener;
    }

    public interface OnBarClickListener {
        void onBarClicked(int pos);
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

    public void setBackgroundColor(String color) {
        containerLayout.setBackgroundColor(Color.parseColor(color));
    }

    public void setBackgroundGradient(String startColor, String endColor, String direction) {
        GradientDrawable gd;
        switch (direction) {
        case "horizontal":
            gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[] { Color.parseColor(startColor), Color.parseColor(endColor) });
            break;
        case "vertical":
            gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] { Color.parseColor(startColor), Color.parseColor(endColor) });
            break;
        default:
            gd = null;
            break;
        }
        if (gd != null)
            containerLayout.setBackground(gd);
    }

    private void addBar(BarModel data) {
        BarGroup barGroup = new BarGroup(context, data.getLabel(), data.getColor(), data.getValue(),
                data.getFillRatio(), barMargin, verticalSpacing, barHeight, labelFontSize, valueFontSize,
                labelTextColor, valueTextColor, rippleColor, cornerRadius,LABEL_FONT,VALUE_FONT);
        barGroup.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        barGroup.setOnTouchListener(new OnTouchListener() {
            private int CLICK_ACTION_THRESHOLD = 200;
            private float startX;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float endX = event.getX();
                    float endY = event.getY();
                    if (isAClick(startX, endX, startY, endY)) {
                        Log.d("BarView", "you clicked!");
                        onBarClickListener.onBarClicked(barGroups.indexOf(v));
                    }
                    break;
                default:
                    Log.d("BarView", "onTouch:Unknown Event ");
                    break;
                }
                return true;
            }

            private boolean isAClick(float startX, float endX, float startY, float endY) {
                float differenceX = Math.abs(startX - endX);
                float differenceY = Math.abs(startY - endY);
                return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
            }
        });
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

        containerLayout.setLayoutParams(
                new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        containerLayout.setOrientation(LinearLayout.VERTICAL);

        this.addView(containerLayout);

    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        barGroups = new ArrayList<>();
        containerLayout = new LinearLayout(context);

        containerLayout.setLayoutParams(
                new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        containerLayout.setOrientation(LinearLayout.VERTICAL);

        this.addView(containerLayout);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BarView, 0, 0);
            verticalSpacing = a.getInteger(R.styleable.BarView_barGroupSpacing, verticalSpacing);
            barHeight = a.getInteger(R.styleable.BarView_barHeight, barHeight);
            labelFontSize = a.getInteger(R.styleable.BarView_labelTextSize, labelFontSize);
            valueFontSize = a.getInteger(R.styleable.BarView_valueTextSize, valueFontSize);
            VALUE_FONT=a.getString(R.styleable.BarView_labelFont);
            LABEL_FONT=a.getString(R.styleable.BarView_labelFont);
            labelTextColor = a.getString(R.styleable.BarView_labelTextColor);
            valueTextColor = a.getString(R.styleable.BarView_valueTextColor);
            rippleColor = a.getString(R.styleable.BarView_rippleColor);
            backgroundColor = a.getString(R.styleable.BarView_backgroundColor);
            gradientStart = a.getString(R.styleable.BarView_gradientStart);
            gradientEnd = a.getString(R.styleable.BarView_gradientEnd);
            gradientDirection = a.getString(R.styleable.BarView_gradientDirection);
            if (labelTextColor == null)
                labelTextColor = Constants.LABEL_TEXT_COLOR;
            if (valueTextColor == null)
                valueTextColor = Constants.VALUE_TEXT_COLOR;
            if (rippleColor == null)
                rippleColor = RIPPLE_COLOR;
            if (gradientDirection == null)
                gradientDirection = "horizontal";
            if (backgroundColor != null)
                setBackgroundColor(backgroundColor);
            if (gradientStart != null && gradientEnd != null)
                setBackgroundGradient(gradientStart, gradientEnd, gradientDirection);
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

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        containerLayout.removeAllViews();
        populateBarView();
    }
}
