package in.krharsh17.barview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

class BarGroup extends ConstraintLayout implements Constants {
    Context context;

    TextView label;
    View initial;
    Bar bar;
    TextView value;

    ConstraintSet constraintSet;
    LayoutParams labelParams, initialParams;

    String labelText, color, valueText;
    float progress;

    private int BAR_MARGIN = 6, VERTICAL_SPACING = 48, BAR_HEIGHT = 20, LABEL_FONT_SIZE = 18, VALUE_FONT_SIZE = 9;
    private String LABEL_TEXT_COLOR = labelTextColor, VALUE_TEXT_COLOR = valueTextColor, RIPPLE_COLOUR = rippleColor;// has to be >2

    BarGroup(Context context, String labelText, String color, String valueText, float progress) {
        super(context);
        this.context = context;
        this.labelText = labelText;
        this.color = color;
        this.valueText = valueText;
        this.progress = progress;

        label = new TextView(context);
        initial = new View(context);
        bar = new Bar(context);
        value = new TextView(context);
    }


    public BarGroup(Context context, String labelText, String color, String valueText, float progress, int BAR_MARGIN, int VERTICAL_SPACING, int BAR_HEIGHT, int LABEL_FONT_SIZE, int VALUE_FONT_SIZE, String LABEL_TEXT_COLOR, String VALUE_TEXT_COLOR, String RIPPLE_COLOUR) {
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
        this.LABEL_TEXT_COLOR = LABEL_TEXT_COLOR;
        this.VALUE_TEXT_COLOR = VALUE_TEXT_COLOR;
        this.RIPPLE_COLOUR = RIPPLE_COLOUR;

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
        label.setTextColor(Color.parseColor(LABEL_TEXT_COLOR));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, LABEL_FONT_SIZE);
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
        initial.setBackgroundColor(Color.parseColor(color));
        Bar.setRippleDrawable(initial, Color.parseColor(color), Color.parseColor(RIPPLE_COLOUR));
        initial.setClickable(true);
        initial.setFocusable(true);
        this.addView(initial);
    }

    void setupBar() {
        bar.setLayoutParams(new LinearLayout.LayoutParams(
                dp(Math.round(context.getResources().getDisplayMetrics().widthPixels / 3.2)), dp(BAR_HEIGHT)
        ));
        bar.setPadding(0,dp(1), 0, dp(1));
        this.addView(bar);
        bar.setBackgroundColor(Color.parseColor(color));
//        bar.setBackground(ViewUtils.generateBackgroundWithShadow(this, Color.parseColor(color),
//                0, Color.parseColor(color), 1, Gravity.BOTTOM));
        Bar.setRippleDrawable(bar, Color.parseColor(color), Color.parseColor(rippleColor));
        bar.setProgress(progress);
    }

    void setupValue() {
        value.setText(valueText);
        value.setBackground(context.getResources().getDrawable(R.drawable.label_background));
        value.setRotation(90);
        value.setGravity(Gravity.CENTER);
        value.setPadding(0, 0, 0, dp(8));
        value.setTextColor(Color.parseColor(VALUE_TEXT_COLOR));
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, VALUE_FONT_SIZE);
        value.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/josefin_sans.ttf"));
        value.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        value.setClickable(true);
        value.setFocusable(true);
        Bar.setRippleDrawable(value, Color.parseColor(color), Color.parseColor(RIPPLE_COLOUR));
        this.addView(value);
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

    String parseLabel(String labelText) {
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
        finalizedString.deleteCharAt(finalizedString.length()-1);
        return finalizedString.toString();
    }
}
