package in.krharsh17.barview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is the custom view which is the cumulation of various individual barGroups
 * extends from a ScrollView and implementing Constant interface
 */
public class BarView extends ScrollView implements Constants {
    public static final int INTRO_ANIM_NONE = 0;
    public static final int INTRO_ANIM_EXPAND = 1;
    private LinearLayout containerLayout;
    private Context context;
    private OnBarClickListener onBarClickListener;
    private List<BarGroup> barGroups;
    private List<BarModel> data;
    private boolean isDataPopulated;
    private int barMargin = 6;
    private int verticalSpacing = 48;
    private int barHeight = 20;
    private int labelFontSize = 18;
    private int valueFontSize = 9;
    private int animationType = Constants.DEFAULT_INTRO_ANIMATION;
    private int animationDuration = Constants.DEFAULT_ANIMATION_DURATION;
    private String backgroundColor;
    private String gradientStart;
    private String gradientEnd;
    private String gradientDirection;
    private int cornerRadius;
    private String labelTextColor = Constants.LABEL_TEXT_COLOR;
    private String valueTextColor = Constants.VALUE_TEXT_COLOR;
    private String LABEL_FONT = null;
    private String VALUE_FONT = null;
    private String rippleColor = Constants.RIPPLE_COLOR;

    /**
     * parameterized constructors
     * It can be called by a anyone directly from code to create a new instance of the view.
     * This constructor doesn’t have access to XML attributes, so you have to fill the parameters manually, using setters.
     *
     * @param context of the activity
     */
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

    /**
     * parameterized constructors
     * This is the basic XML constructor. Without it, the layout inflater will crash.
     * The AttributeSet parameter contains all attribute values provided via XML.
     *
     * @param context is the context of the activity
     * @param attrs are the attribute values which are saved as an AttributeSet
     */
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

            final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.BarView, 0, 0);
            animationType = a.getInteger(R.styleable.BarView_introAnimationType, animationType);
            animationDuration = a.getInteger(R.styleable.BarView_introAnimationDuration, animationDuration);
            verticalSpacing = a.getInteger(R.styleable.BarView_barGroupSpacing, verticalSpacing);
            barHeight = a.getInteger(R.styleable.BarView_barHeight, barHeight);
            labelFontSize = a.getInteger(R.styleable.BarView_labelTextSize, labelFontSize);
            valueFontSize = a.getInteger(R.styleable.BarView_valueTextSize, valueFontSize);
            VALUE_FONT = a.getString(R.styleable.BarView_labelFont);
            LABEL_FONT = a.getString(R.styleable.BarView_labelFont);
            labelTextColor = a.getString(R.styleable.BarView_labelTextColor);
            valueTextColor = a.getString(R.styleable.BarView_valueTextColor);
            rippleColor = a.getString(R.styleable.BarView_rippleColor);
            backgroundColor = a.getString(R.styleable.BarView_backgroundColor);
            gradientStart = a.getString(R.styleable.BarView_gradientStart);
            gradientEnd = a.getString(R.styleable.BarView_gradientEnd);
            gradientDirection = a.getString(R.styleable.BarView_gradientDirection);
            if (labelTextColor == null) {
                labelTextColor = Constants.LABEL_TEXT_COLOR;
            }
            if (valueTextColor == null) {
                valueTextColor = Constants.VALUE_TEXT_COLOR;
            }
            if (rippleColor == null) {
                rippleColor = RIPPLE_COLOR;
            }
            if (gradientDirection == null) {
                gradientDirection = "horizontal";
            }
            if (backgroundColor != null) {
                setBackgroundColor(backgroundColor);
            }
            if (gradientStart != null && gradientEnd != null) {
                setBackgroundGradient(gradientStart, gradientEnd, gradientDirection);
            }
            a.recycle();
        }
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
        if (animationType == BarView.INTRO_ANIM_NONE) {
            populateBarView(BarView.INTRO_ANIM_NONE, animationDuration);
        } else if (animationType == BarView.INTRO_ANIM_EXPAND) {
            populateBarView(BarView.INTRO_ANIM_EXPAND, animationDuration);
        }
        isDataPopulated = true;
    }

    public void setData(List<BarModel> data, boolean isAnimationEnabled) {
        this.data = data;
        if (isAnimationEnabled) {
            if (animationType == BarView.INTRO_ANIM_NONE) {
                populateBarView(BarView.INTRO_ANIM_NONE, animationDuration);
            } else if (animationType == BarView.INTRO_ANIM_EXPAND) {
                populateBarView(BarView.INTRO_ANIM_EXPAND, animationDuration);
            }
        } else {
            populateBarView(BarView.INTRO_ANIM_NONE, animationDuration);
        }
    }

    /**
     * This method iterates over various BarModels and adds a BarGroup for each
     * of the BarModels inside private member data
     */
    private void populateBarView(int animationType, int animationDuration) {
        for (BarModel b : data) {
            addBar(b, animationType, animationDuration);
        }
    }

    /**
     * This method actuall instanciates BarGroups based on the BarModel passed in as
     * a param and cumulates it into barGroups.
     *
     * @param data is a BarModel that contains all the required to
     *     construct a BarGroup instance.
     */
    private void addBar(BarModel data, int animationType, int animationDuration) {
        BarGroup barGroup = new BarGroup(
            context,
            data.getLabel(),
            data.getColor(),
            data.getValue(),
            data.getFillRatio(),
            animationType,
            animationDuration,
            barMargin,
            verticalSpacing,
            barHeight,
            labelFontSize,
            valueFontSize,
            labelTextColor,
            valueTextColor,
            rippleColor,
            cornerRadius,
            LABEL_FONT,
            VALUE_FONT,
            data.getElevation(),
            data.getRadius()
        );

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

    public void setBackgroundColor(String color) {
        containerLayout.setBackgroundColor(Color.parseColor(color));
    }

    public void setBackgroundGradient(String startColor, String endColor, String direction) {
        GradientDrawable gd;
        switch (direction) {
            case "horizontal":
                gd = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{Color.parseColor(startColor), Color.parseColor(endColor)});
                break;
            case "vertical":
                gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{Color.parseColor(startColor), Color.parseColor(endColor)});
                break;
            default:
                gd = null;
                break;
        }
        if (gd != null) {
            containerLayout.setBackground(gd);
        }
    }

    /**
     * This function returns a random color based on a constant {@value #CHAR_ARRAY}
     * It builds up the random hex value by randomly choosing between any of the 16
     * literals in the char array and appending them one after another
     *
     * @return a random color as a randomized hex val
     */
    public static String getRandomColor() {
        char[] letters = CHAR_ARRAY.toCharArray();
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            color.append(letters[(int) Math.round(Math.floor(Math.random() * 16))]);
        }
        return color.toString();
    }

    /**
     * This function approximates the passed in color based on the tolerance value defined in
     * the method . It first deconstructs the passed in @ColorInt into its components and then
     * randomly adding or subtracting integers in the range of tolerance and then finally
     * constructing them all up together and returning a hex literal of the format #XXXXXX
     *
     * @param approximateColor is the color user wants the bar to be approximately like
     * @return
     */
    public static String getRandomColor(Integer approximateColor) {
        Integer tolerance=5;
        Integer blue=approximateColor&0x000000ff;
        Integer green=(approximateColor&0x0000ff00)>>8;
        Integer red=(approximateColor&0x00ff0000)>>16;

        blue = Math.min(Math.max(0,blue+getRandomNumberInRange(-tolerance,tolerance)),255);
        green = Math.min(Math.max(0,green+getRandomNumberInRange(-tolerance,tolerance)),255);
        red = Math.min(Math.max(0,red+getRandomNumberInRange(-tolerance,tolerance)),255);

        Integer newApproximateColor=0xff000000 | (red << 16) | (green << 8) | blue;

        String approximateColorHexLiteral =Integer.toHexString(newApproximateColor);
        return ("#"+approximateColorHexLiteral.substring(2));
    }

    /**
     * Helper function returns any random integer between the specified bounds
     * [min..max] both inclusive
     *
     * @param min variance the user wants in the approximate color
     * @param max variance the user wants in the approximate color
     * @return
     */
    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        if (isDataPopulated) {
            containerLayout.removeAllViews();
            populateBarView(animationType, animationDuration);
        }
    }

    /**
     * setters and getters
     */
    public int getAnimationType() {
        return animationType;
    }

    public void setAnimationType(int animationType) {
        this.animationType = animationType;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

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
}
