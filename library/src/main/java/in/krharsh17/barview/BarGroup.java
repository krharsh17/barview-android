package in.krharsh17.barview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

import java.util.Hashtable;

/**
 * this class is the custom view which appears as bars in the BarView
 * extends ConstraintLayout and implements Constants interface
 */
class BarGroup extends ConstraintLayout implements Constants {
    private Context context;
    private TextView label;
    private View initial;
    private Bar bar;
    private TextView value;
    public static Hashtable<String, Typeface> fontCache = new Hashtable<>();
    private float increaseHeight;
    private int increaseWidth;
    private String labelText;
    private String color;
    private String valueText;
    private float progress;
    public int elevation;
    public int radius;
    public int numberOfLayers;
    private int animationType;
    private int animationDuration = Constants.DEFAULT_ANIMATION_DURATION;
    private int BAR_MARGIN = 6;
    private int VERTICAL_SPACING = 48;
    private int BAR_HEIGHT = 20;
    private int LABEL_FONT_SIZE = 18;
    private int VALUE_FONT_SIZE = 9;
    private String labelTextColor = LABEL_TEXT_COLOR;
    private String valueTextColor = VALUE_TEXT_COLOR, VALUE_FONT = null, LABEL_FONT = null;
    private String rippleColor = RIPPLE_COLOR;
    private int CORNER_RADIUS;
    /**
     * one bar has different drawable stacked together with same solid color but different alpha value
     * this array of string defined different alpha value from 0 to 1
     * we will use maximum of 18 layers for shadow
     * numbers of layers can very depending on elevation value
     */
    public String alphaSet[] = {

            "#00",
            "#02",
            "#04",
            "#06",
            "#08",
            "#10",
            "#12",
            "#14",
            "#16",
            "#18",
            "#20",
            "#22",
            "#24",
            "#26",
            "#28",
            "#30",
            "#32",
            "#35",
            "#"
    };


    /**
     * parameterized constructor
     *
     * @param context of the activity
     * @param labelText for the barGroup instance
     * @param color hex color value for the fill of barGroup instance
     * @param valueText for approximating the length of Bargroup instance
     * @param progress marking the progress of the bar
     */
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
        bar.setVisibility(GONE);
        value = new TextView(context);
    }

    /**
     * parameterized constructor
     *
     * @param context of the activity
     * @param labelText for the barGroup instance
     * @param color hex color value for the fill of barGroup instance
     * @param valueText for approximating the length of Bargroup instance
     * @param progress marking the progress of the bar
     *     self explanatory constants
     * @param BAR_MARGIN
     * @param VERTICAL_SPACING
     * @param BAR_HEIGHT
     * @param LABEL_FONT_SIZE
     * @param VALUE_FONT_SIZE
     * @param labelTextColor
     * @param VALUE_TEXT_COLOR
     * @param RIPPLE_COLOUR
     * @param CORNER_RADIUS
     * @param LABEL_FONT
     * @param VALUE_FONT
     */
    public BarGroup(
            Context context,
            String labelText,
            String color,
            String valueText,
            float progress,
            int animationType,
            int animationDuration,
            int BAR_MARGIN,
            int VERTICAL_SPACING,
            int BAR_HEIGHT,
            int LABEL_FONT_SIZE,
            int VALUE_FONT_SIZE,
            String labelTextColor,
            String VALUE_TEXT_COLOR,
            String RIPPLE_COLOUR,
            int CORNER_RADIUS,
            String LABEL_FONT,
            String VALUE_FONT,
            int elevation,
            int radius) {
        super(context);
        this.context = context;
        this.labelText = labelText;
        this.color = color;
        this.valueText = valueText;
        this.progress = progress;
        this.animationType = animationType;
        this.animationDuration = animationDuration;
        this.BAR_MARGIN = BAR_MARGIN;
        this.VERTICAL_SPACING = VERTICAL_SPACING;
        this.BAR_HEIGHT = BAR_HEIGHT;
        this.LABEL_FONT_SIZE = LABEL_FONT_SIZE;
        this.VALUE_FONT_SIZE = VALUE_FONT_SIZE;
        this.labelTextColor = labelTextColor;
        this.valueTextColor = VALUE_TEXT_COLOR;
        this.rippleColor = RIPPLE_COLOUR;
        this.LABEL_FONT = LABEL_FONT;
        this.VALUE_FONT = VALUE_FONT;
        this.CORNER_RADIUS = CORNER_RADIUS;
        label = new TextView(context);
        initial = new View(context);
        bar = new Bar(context);
        value = new TextView(context);
        this.elevation = elevation;
        this.radius = radius;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        label.setId(View.generateViewId());
        initial.setId(View.generateViewId());
        bar.setId(View.generateViewId());
        value.setId(View.generateViewId());
        if (this.elevation > 0) {
            switch (this.elevation) {
                case 1:
                case 2:
                case 3:
                    this.numberOfLayers = 5;
                    break;
                case 4:
                    this.numberOfLayers = 6;
                    break;
                case 5:
                    this.numberOfLayers = 7;
                    break;
                case 6:
                    this.numberOfLayers = 8;
                    break;
                case 7:
                    this.numberOfLayers = 9;
                    break;
                case 8:
                    this.numberOfLayers = 10;
                    break;
                case 9:
                    this.numberOfLayers = 11;
                    break;
                case 10:
                    this.numberOfLayers = 12;
                    break;
                case 11:
                case 12:
                case 13:
                    this.numberOfLayers = 14;
                    break;
                case 14:
                case 15:
                case 16:
                    this.numberOfLayers = 16;
                    break;
                default:
                    this.numberOfLayers = 18;
            }
        }
        setupLabel();
        setupInitial();
        setupBar();
        setupValue();
        applyConstraints();
    }

    /**
     * Initializer function for the label segment
     */
    private void setupLabel() {
        LayoutParams labelParams = new Constraints.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelParams.setMargins(
                dp(8),
                dp(VERTICAL_SPACING / 2),
                dp(8),
                dp(VERTICAL_SPACING / 2)
        );

        label.setText(parseLabel(labelText));
        label.setTextColor(Color.parseColor(labelTextColor));
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, LABEL_FONT_SIZE);
        if (LABEL_FONT != null) {
            label.setTypeface(Typeface.createFromAsset(context.getAssets(), LABEL_FONT));
        }
        label.setLayoutParams(labelParams);
        label.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        label.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(label);
    }

    /**
     * Initializer function for the initial block
     */
    public void setupInitial() {
        Drawable[] layers = new Drawable[this.numberOfLayers + 1];
        setLayerForInitial(this.numberOfLayers, layers);
        LayoutParams initialParams = new LayoutParams(dp(12) + increaseWidth, (int) (dp(BAR_HEIGHT) * increaseHeight));
        initialParams.rightMargin = dp(12);
        initial.setLayoutParams(initialParams);
        LayerDrawable splash_test = new LayerDrawable(layers);
        initial.setBackground(splash_test);
        initial.setVisibility(GONE);
        Bar.setRippleDrawable(initial, Color.parseColor(color), Color.parseColor(rippleColor));
        initial.setClickable(true);
        initial.setFocusable(true);
        this.addView(initial);
        if (animationType == BarView.INTRO_ANIM_NONE) {
            initial.setVisibility(VISIBLE);
            initialParams = new LayoutParams(dp(12), dp(BAR_HEIGHT));
            initialParams.rightMargin = dp(12);
            initial.setLayoutParams(initialParams);
        } else if (animationType == BarView.INTRO_ANIM_EXPAND) {
            int screen_width = Math.round((160 * context.getResources().getDisplayMetrics().widthPixels) / (context.getResources().getDisplayMetrics().xdpi));
            initialParams = new LayoutParams(screen_width, dp(BAR_HEIGHT));
            initialParams.rightMargin = dp(12);
            initial.setLayoutParams(initialParams);
            expand(initial, animationDuration, dp(12));
        }
    }

    /**
     * Initializer function for the main bar
     */
    public void setupBar() {
        Drawable[] layers = new Drawable[this.numberOfLayers + 1];
        setLayerForBar(this.numberOfLayers, layers);
        int screen_width = Math.round((160 * context.getResources().getDisplayMetrics().widthPixels) / (context.getResources().getDisplayMetrics().xdpi));
        bar.setLayoutParams(new LinearLayout.LayoutParams(
                screen_width, (int) (dp(BAR_HEIGHT) * increaseHeight)
        ));
        LayerDrawable splash_test = new LayerDrawable(layers);
        bar.setBackground(splash_test);
        this.addView(bar);
        bar.setVisibility(GONE);
        Bar.setRippleDrawable(bar, Color.parseColor(color), Color.parseColor(rippleColor));
        bar.setProgress(progress, increaseWidth, animationType, animationDuration);
        bar.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                addOptions();
                return true;
            }
        });
    }

    /**
     * Initializer function for the value tooltip
     */
    private void setupValue() {
        value.setText(valueText);
        value.setBackground(context.getResources().getDrawable(R.drawable.label_background));
        value.setRotation(90);
        value.setGravity(Gravity.CENTER);
        value.setPadding(0, 0, 0, dp(8));
        value.setTextColor(Color.parseColor(valueTextColor));
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, VALUE_FONT_SIZE);
        value.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (VALUE_FONT != null) {
            Typeface tf = get(VALUE_FONT, context);
            value.setTypeface(tf);
        }
        value.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        value.setClickable(true);
        value.setFocusable(true);
        Bar.setRippleDrawable(value, Color.parseColor(color), Color.parseColor(rippleColor));
        this.addView(value);
    }

    /**
     * Loads font from assets
     *
     * @param name
     * @param context
     */
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

    /**
     * Sets constraints for all pieces of a BarGroup instance
     */
    private void applyConstraints() {
        ConstraintSet constraintSet = new ConstraintSet();
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

    /**
     * Converts density independent pixel units (dp) to pixel units (px)
     *
     * @param dp
     */
    private int dp(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    /**
     * elevation
     * Function for creating drawable of with solid color and with specific alpha
     *
     * @param color
     * @param currentLayer
     * @param totalLayer
     * @param radius
     */

    public Drawable getRoundRect(String color, int currentLayer, int totalLayer, int radius) {
        int localRadius = radius;
        int leftTopPadding = 1;
        if (currentLayer % 3 == 0) {
            leftTopPadding = 2;
        }
        int rightPadding = 3;
        int bottomPadding = 3;
        String shadowalpha = null;
        int increment = 16 / (totalLayer - 2);
        int layernumber = 0;

        if (radius < 0) {
            localRadius = 0;
        }
        if (radius > 20) {
            localRadius = 20;
        }

        if (currentLayer % 3 == 0) {
            leftTopPadding = 2;
        }

        if (currentLayer == totalLayer) {
            shadowalpha = "#";
        } else {
            layernumber = increment * currentLayer;
            if (layernumber >= 17) {
                layernumber = 17;
            }
            shadowalpha = alphaSet[layernumber];
        }
        RoundRectShape rectShape = new RoundRectShape(new float[]{
                localRadius * 2, localRadius * 2, localRadius * 2, localRadius * 2,
                localRadius * 2, localRadius * 2, localRadius * 2, localRadius * 2
        }, null, null);
        if (totalLayer == currentLayer) {
            if (localRadius == 0) {
                localRadius = 2;
            }
            rectShape = new RoundRectShape(new float[]{
                    localRadius * 2, localRadius * 2, localRadius * 2, localRadius * 2,
                    localRadius * 2, localRadius * 2, localRadius * 2, localRadius * 2
            }, null, null);
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.setPadding(leftTopPadding, leftTopPadding, rightPadding, bottomPadding);
        shapeDrawable.getPaint().setColor(Color.parseColor(shadowalpha + color));
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        return shapeDrawable;
    }

    /**
     * Animator function for the 'expand' intro animation
     *
     * @param v
     * @param duration
     * @param targetWidth
     */
    public static void expand(final View v, int duration, int targetWidth) {

        //int prevWidth  = v.getWidth();
        int prevWidth = 0;

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().width = (int) animation.getAnimatedValue();
                //v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * Parses the label text to truncate or hyphenize the string to fit in the given space
     *
     * @param labelText
     */
    private String parseLabel(String labelText) {
        String[] tokens = labelText.split(" ");
        StringBuilder finalizedString = new StringBuilder();
        for (String s : tokens) {
            if (s.length() < 8) {
                finalizedString.append(s + "\n");
            } else if (s.length() >= 8 && s.length() < 12) {
                finalizedString.append(s.substring(0, 7) + "\n");
                finalizedString.append("-" + s.substring(7) + "\n");
            } else if (s.length() >= 12 && s.length() < 15) {
                finalizedString.append(s.substring(0, 7) + "\n");
                finalizedString.append("-" + s.substring(7, 12) + "\n");
                if (s.length() > 13) {
                    finalizedString.append("-" + s.substring(13) + "\n");
                }
            } else {
                finalizedString.append(s.substring(0, 5) + "..\n");
            }
        }
        finalizedString.deleteCharAt(finalizedString.length() - 1);
        return finalizedString.toString();
    }

    /**
     * The animation function takes the actual view-group, animation duration and target width in dp as it's arguments
     * and takes initial width as 0 dp. After this, using ValueAnimator, the barview is made to expand to provided width
     * in the specified time duration and it's visibility is set to visible during animation.
     */
    public static class ResizeAnimation extends Animation {
        public final int targetHeight;
        public View view;
        public int startHeight;

        public ResizeAnimation(View view, int targetHeight, int startHeight) {
            this.view = view;
            this.targetHeight = targetHeight;
            this.startHeight = startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight = (int) (startHeight + targetHeight * interpolatedTime);
            //to support decent animation, change new heigt as Nico S. recommended in comments
            //int newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
    /**
     * This creates a gradient drawable background for the bars - sets a shape, a color and radius for the corners.
     *
     * @param color is the color in int which is used as the background color.
     */
    public GradientDrawable setUpRoundBars(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(CORNER_RADIUS);
        return gradientDrawable;
    }

    public GradientDrawable setUpRoundBars(int color, int highlightColor) {

        /**
         * This creates a gradient drawable background for the bars - sets a shape, a color and radius for the corners.
         * @param color is the color in int which is used as the background color.
         */
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(5, highlightColor);
        gradientDrawable.setCornerRadius(CORNER_RADIUS);
        return gradientDrawable;
    }

    /**
     * Generates a modified color for highlighting.
     *
     * @param backColor is the backcolor which will be used to highlight.
     * @param factor    factor by which backcolor is to be changed.
     * @return modified color which is the color used as the backgroundcolor.
     */

    public String changeColor(int backColor, double factor) {
        String modifiedColor;
        float[] hsv = new float[3];
        Color.colorToHSV(backColor, hsv);
        hsv[2] *= factor;
        modifiedColor = String.format("#%06X", (0xFFFFFF & Color.HSVToColor(hsv)));
        return modifiedColor;

    }

    /**
     * to add shadow.
     *
     * @param numberOfLayers param to add shadow behind the bars.
     * @param layers         param to add shadow behind the bars.
     */

    public void setLayerForBar(int numberOfLayers, Drawable[] layers) {
        for (int i = 0; i <= numberOfLayers; i++) {
            layers[i] = null;
        }
        for (int i = 0; i <= numberOfLayers; i++) {
            layers[i] = getRoundRect(color.substring(1), i, numberOfLayers, this.radius);
        }
        increaseHeight = 1;
        increaseWidth = 0;
        if (this.numberOfLayers > 0) {
            increaseHeight = 1.2f + (numberOfLayers - 5) * 0.1f;
            increaseWidth = 20 + (numberOfLayers - 5) * 4;
        }
    }

    /**
     * to add shadow
     *
     * @param numberOfLayers param to add shadow behind the bars.
     * @param layers         param to add shadow behind the bars.
     */

    public void setLayerForInitial(int numberOfLayers, Drawable[] layers) {
        for (int i = 0; i <= numberOfLayers; i++) {
            layers[i] = null;
        }
        for (int i = 0; i <= numberOfLayers; i++) {
            layers[i] = getRoundRect(color.substring(1), i, numberOfLayers, this.radius);
        }
        increaseHeight = 1;//To give some extra height of bar for shadow
        increaseWidth = 0;//To give some extra width of bar for shadow
        if (this.numberOfLayers > 0) {
            increaseHeight = 1.2f + (numberOfLayers - 5) * 0.1f;
            increaseWidth = 15 + (numberOfLayers - 5) * 4;
        }
    }

    /**
     * adds highlight, fade to the bars or deletes a bar completely after showing an alert dialogue.
     */

    public void addOptions() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View dialogueView = layoutInflater.inflate(R.layout.alert_dialogue, null);
        alertDialog.setView(dialogueView);
        final RadioButton highlight = dialogueView.findViewById(R.id.highlight);
        final RadioGroup highlightColor = dialogueView.findViewById(R.id.highlightColor);
        final RadioButton fade = dialogueView.findViewById(R.id.fade);
        final RadioButton delete = dialogueView.findViewById(R.id.delete);
        highlight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightColor.setVisibility(VISIBLE);

            }
        });
        fade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightColor.setVisibility(GONE);
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                highlightColor.setVisibility(GONE);
            }
        });
        final RadioButton green = dialogueView.findViewById(R.id.green);
        final RadioButton blue = dialogueView.findViewById(R.id.blue);
        final RadioButton pink = dialogueView.findViewById(R.id.pink);
        final RadioButton yellow = dialogueView.findViewById(R.id.yellow);
        alertDialog.setPositiveButton("okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int colorOfHighlight;
                if (highlight.isChecked()) {
                    colorOfHighlight = assignColor(green, blue, pink, yellow);
                    if (colorOfHighlight == 0) {
                        Toast.makeText(context, "Please Choose a Color", Toast.LENGTH_SHORT).show();
                    } else {
                        String myColor = changeColor(Color.parseColor(color), 0.7);
                        bar.setBackground(setUpRoundBars(Color.parseColor(myColor), colorOfHighlight));
                        initial.setBackground(setUpRoundBars(Color.parseColor(myColor), colorOfHighlight));
                        bar.setOnLongClickListener(new OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                removeOptions(highlight, fade);
                                return true;
                            }
                        });
                    }
                }
                if (fade.isChecked()) {
                    setAlphaValue((float) 0.13);
                    bar.setOnLongClickListener(new OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            removeOptions(highlight, fade);
                            return true;
                        }
                    });
                }
                if (delete.isChecked()) {
                    removeViews();
                }
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing
            }
        }).show();


    }

    /**
     * assigns a color which will be used as the highlight color.
     *
     * @param green  Radio Button of the Alert Dialogue view which sets green color as highlight color if checked.
     * @param blue   Radio Button of the Alert Dialogue view which sets blue color as highlight color if checked.
     * @param pink   Radio Button of the Alert Dialogue view which sets pink color as highlight color if checked.
     * @param yellow Radio Button of the Alert Dialogue view which sets yellow color as highlight color if checked.
     * @return color which will be used to highlight.
     */
    public int assignColor(RadioButton green, RadioButton blue, RadioButton pink, RadioButton
            yellow) {
        int colorOfHighlight = 0;
        if (green.isChecked()) {
            colorOfHighlight = getResources().getColor(R.color.greenHighlight);
        }
        if (blue.isChecked()) {
            colorOfHighlight = getResources().getColor(R.color.blueHighlight);
        }
        if (pink.isChecked()) {
            colorOfHighlight = getResources().getColor(R.color.pinkHighlight);
        }
        if (yellow.isChecked()) {
            colorOfHighlight = getResources().getColor(R.color.yellowHighlight);
        }
        return colorOfHighlight;
    }

    /**
     * changes the alpha value so that the views are only slightly visible.
     *
     * @param alphaValue is used to assign an Alpha value.
     */
    public void setAlphaValue(float alphaValue) {
        bar.setAlpha(alphaValue);
        initial.setAlpha(alphaValue);
        label.setAlpha(alphaValue);
        value.setAlpha(alphaValue);
    }

    /**
     * sets the original background for bars - after highlight is removed.
     */
    public void setOriginalBackground() {
        Drawable[] layers = new Drawable[numberOfLayers + 1];
        setLayerForBar(numberOfLayers, layers);
        LayerDrawable splash_test_bar = new LayerDrawable(layers);
        bar.setBackground(splash_test_bar);
        setLayerForInitial(numberOfLayers, layers);
        LayerDrawable splash_test_initial = new LayerDrawable(layers);
        initial.setBackground(splash_test_initial);
    }

    /**
     * Removes all the views.
     */
    public void removeViews() {
        BarGroup.this.removeView(bar);
        BarGroup.this.removeView(initial);
        BarGroup.this.removeView(label);
        BarGroup.this.removeView(value);
    }

    /**
     * Generates an alert dialogue to remove the selected option.
     *
     * @param highlight Radio Button in dialogue view which highlights the bar if selected.
     * @param fade      Radio Button in dialogue view which fades the bar if selected.
     */
    public void removeOptions(final RadioButton highlight, final RadioButton fade) {
        String message = null;
        if (highlight.isChecked()) {
            message = getResources().getString(R.string.removeHighlight);
        }
        if (fade.isChecked()) {
            message = getResources().getString(R.string.removeFade);
        }
        new AlertDialog.Builder(context).setMessage(message).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (highlight.isChecked()) {
                    setOriginalBackground();
                }
                if (fade.isChecked()) {
                    setAlphaValue(1);
                }
                bar.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        addOptions();
                        return true;
                    }
                });

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing
            }
        }).show();
    }
}

