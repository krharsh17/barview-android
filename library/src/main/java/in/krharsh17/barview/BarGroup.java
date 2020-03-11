package in.krharsh17.barview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

 
import java.util.Hashtable;
 
class BarGroup extends ConstraintLayout implements Constants {
 
    Context context;

    TextView label;
    View initial;
    Bar bar;
    TextView value;

    ConstraintSet constraintSet;
 
  
    public  static Hashtable<String, Typeface> fontCache = new Hashtable<>();
    LayoutParams labelParams;
    LayoutParams initialParams;

    String labelText;
    String color;
    String valueText;
    float progress;

    private int BAR_MARGIN = 6;
    private int VERTICAL_SPACING = 48;
    private int BAR_HEIGHT = 20;
    private int LABEL_FONT_SIZE = 18;
    private int VALUE_FONT_SIZE = 9;
    private String labelTextColor = LABEL_TEXT_COLOR;
    private String valueTextColor = VALUE_TEXT_COLOR,VALUE_FONT=null,LABEL_FONT=null;
    private String rippleColor = RIPPLE_COLOR;                        // has to be >2
    private int CORNER_RADIUS;
    public BarGroup(
        Context context, 
        String labelText, 
        String color, 
        String valueText, 
        float progress, 
        int BAR_MARGIN, 
        int VERTICAL_SPACING, 
        int BAR_HEIGHT, 
        int LABEL_FONT_SIZE, 
        int VALUE_FONT_SIZE, 
        String labelTextColor,
        String VALUE_TEXT_COLOR, 
        String RIPPLE_COLOUR,int CORNER_RADIUS,String LABEL_FONT,String VALUE_FONT) {
 
 
        super(context);
        this.context = context;
        this.labelText = labelText;
        this.color = color;
        this.valueText = valueText;
        this.progress = progress;
        this.BAR_MARGIN = BAR_MARGIN;
        this.VERTICAL_SPACING = VERTICAL_SPACING;
        this.BAR_HEIGHT = BAR_HEIGHT;
        this.LABEL_FONT_SIZE = LABEL_FONT_SIZE;
        this.VALUE_FONT_SIZE = VALUE_FONT_SIZE;
        this.labelTextColor = labelTextColor;
        this.valueTextColor = VALUE_TEXT_COLOR;
        this.rippleColor = RIPPLE_COLOUR;
        this.LABEL_FONT=LABEL_FONT;
        this.VALUE_FONT=VALUE_FONT;
        this.CORNER_RADIUS = CORNER_RADIUS;
        label = new TextView(context);
        initial = new View(context);
        bar = new Bar(context);
        value = new TextView(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        label.setId(View.generateViewId());
        initial.setId(View.generateViewId());
        bar.setId(View.generateViewId());
        value.setId(View.generateViewId());

        setupLabel();
        setupInitial();
        setupBar();
        setupValue();

        applyConstraints();

    }

    void setupLabel() {
        labelParams = new Constraints.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelParams.setMargins(
                dp(8),
                dp(VERTICAL_SPACING / 2),
                dp(8),
                dp(VERTICAL_SPACING / 2)
        );
        label.setText(parseLabel(labelText));
        label.setTextColor(Color.parseColor(labelTextColor));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, LABEL_FONT_SIZE);
        if(LABEL_FONT!=null)
            label.setTypeface(Typeface.createFromAsset(context.getAssets(), LABEL_FONT));
        else
            label.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/josefin_sans.ttf"));
        label.setLayoutParams(labelParams);
        label.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        label.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(label);
    }

    void setupInitial() {
        initialParams = new LayoutParams(dp(12), dp(BAR_HEIGHT));
        initialParams.rightMargin = dp(12);
        initial.setLayoutParams(initialParams);
        initial.setBackground(setUpRoundBars(Color.parseColor(color)));
        Bar.setRippleDrawable(initial, Color.parseColor(color), Color.parseColor(rippleColor));
        initial.setClickable(true);
        initial.setFocusable(true);
        this.addView(initial);
    }

    public void setupBar() {
        int screen_width = Math.round((160 * context.getResources().getDisplayMetrics().widthPixels) / (context.getResources().getDisplayMetrics().xdpi));
        bar.setLayoutParams(new LinearLayout.LayoutParams(
                screen_width, dp(BAR_HEIGHT)
        ));
        bar.setPadding(0, dp(1), 0, dp(1));
        this.addView(bar);
        bar.setBackground(setUpRoundBars(Color.parseColor(color)));
        Bar.setRippleDrawable(bar, Color.parseColor(color), Color.parseColor(rippleColor));
        bar.setProgress(progress);
    }

    void setupValue() {

        value.setText(valueText);
        value.setBackground(context.getResources().getDrawable(R.drawable.label_background));
        value.setRotation(90);
        value.setGravity(Gravity.CENTER);
        value.setPadding(0, 0, 0, dp(8));
        value.setTextColor(Color.parseColor(valueTextColor));
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, VALUE_FONT_SIZE);
        Typeface tf=get(VALUE_FONT,context);
        if(tf!=null)
            value.setTypeface(tf);
        value.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        value.setClickable(true);
        value.setFocusable(true);
        Bar.setRippleDrawable(value, Color.parseColor(color), Color.parseColor(rippleColor));
        this.addView(value);
    }
    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
    void applyConstraints() {
        constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        constraintSet.setHorizontalBias(initial.getId(), 0.30f);

        constraintSet.connect(initial.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(initial.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(initial.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dp(BAR_MARGIN));
        constraintSet.connect(initial.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dp(BAR_MARGIN));

        constraintSet.connect(bar.getId(), ConstraintSet.START, initial.getId(), ConstraintSet.END, dp(BAR_MARGIN));
        constraintSet.connect(bar.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(bar.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        constraintSet.connect(value.getId(), ConstraintSet.START, bar.getId(), ConstraintSet.END, dp(BAR_MARGIN));
        constraintSet.connect(value.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(value.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        constraintSet.connect(label.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(label.getId(), ConstraintSet.END, initial.getId(), ConstraintSet.END, 0);
        constraintSet.connect(label.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(label.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);


        constraintSet.applyTo(this);
    }

    int dp(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    private String parseLabel(String labelText) {
        String[] tokens = labelText.split(" ");
        StringBuilder finalizedString = new StringBuilder();
        for (String s : tokens) {
            if (s.length() < 8)
                finalizedString.append(s + "\n");
            else if (s.length() >= 8 && s.length() < 12) {
                finalizedString.append(s.substring(0, 7) + "\n");
                finalizedString.append("-" + s.substring(7) + "\n");
            } else if (s.length() >= 12 && s.length() < 15) {
                finalizedString.append(s.substring(0, 7) + "\n");
                finalizedString.append("-" + s.substring(7, 12) + "\n");
                if (s.length() > 13)
                    finalizedString.append("-" + s.substring(13) + "\n");
            } else {
                finalizedString.append(s.substring(0, 5) + "..\n");
            }
        }
        finalizedString.deleteCharAt(finalizedString.length() - 1);
        return finalizedString.toString();
    }

    public GradientDrawable setUpRoundBars(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(CORNER_RADIUS);
        return gradientDrawable;
    }

}
