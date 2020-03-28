package in.krharsh17.barview;

import android.animation.ValueAnimator;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;

 
import java.util.Hashtable;

/**
 * this class is the custom view which appears as bars in the BarView
 * extends ConstraintLayout and implements Constants interface
 */
class BarGroup extends ConstraintLayout implements Constants {
    Context context;
    TextView label;
    View initial;
    Bar bar;
    TextView value;
    ConstraintSet constraintSet;
    LayoutParams labelParams;
    LayoutParams initialParams;
    String labelText;
    String color;
    String valueText;
    float progress;
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
    private String valueTextColor = VALUE_TEXT_COLOR,VALUE_FONT=null,LABEL_FONT=null;
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

    public  static Hashtable<String, Typeface> fontCache = new Hashtable<>();

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
     *
     * self explanatory constants
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
        this.LABEL_FONT=LABEL_FONT;
        this.VALUE_FONT=VALUE_FONT;
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
        if(this.elevation>0) {
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
        label.setLayoutParams(labelParams);
        label.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        label.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(label);
    }

    /**
     * Initializer function for the initial block
     *
     */
    void setupInitial() {
        Drawable[] layers = new Drawable[this.numberOfLayers+1];
        for(int i =0 ;i<=this.numberOfLayers;i++){
            layers[i] = null;
        }
        for(int i=0;i<=this.numberOfLayers;i++){
            layers[i] = getRoundRect(color.substring(1),i,this.numberOfLayers,this.radius);
        }
        float increaseHeight = 1;//To give some extra height of bar for shadow
        int increaseWidth = 0;//To give some extra width of bar for shadow
        if(this.numberOfLayers>0){
            increaseHeight = 1.2f + (this.numberOfLayers - 5) * 0.1f;
            increaseWidth = 15 + (this.numberOfLayers - 5) * 4;
        }
        initialParams = new LayoutParams(dp(12) + increaseWidth, (int) (dp(BAR_HEIGHT) * increaseHeight));
        initialParams.rightMargin = dp(12);
        initial.setLayoutParams(initialParams);
        LayerDrawable splash_test = new LayerDrawable(layers);
        initial.setBackground(splash_test);
        initial.setVisibility(GONE);
        Bar.setRippleDrawable(initial, Color.parseColor(color), Color.parseColor(rippleColor));
        initial.setClickable(true);
        initial.setFocusable(true);
        this.addView(initial);
        if(animationType == BarView.INTRO_ANIM_NONE){
            initial.setVisibility(VISIBLE);
            initialParams = new LayoutParams(dp(12), dp(BAR_HEIGHT));
            initialParams.rightMargin = dp(12);
            initial.setLayoutParams(initialParams);
        }
        else if (animationType == BarView.INTRO_ANIM_EXPAND){
            int screen_width = Math.round((160*context.getResources().getDisplayMetrics().widthPixels)/(context.getResources().getDisplayMetrics().xdpi));
            initialParams = new LayoutParams(screen_width, dp(BAR_HEIGHT));
            initialParams.rightMargin = dp(12);
            initial.setLayoutParams(initialParams);
            expand(initial,animationDuration,dp(12));
        }

    }

    /**
     * Initializer function for the main bar
     *
     */
    public void setupBar() {
        Drawable[] layers = new Drawable[this.numberOfLayers+1];
        for(int i =0 ;i<=this.numberOfLayers;i++){
            layers[i] = null;
        }
        for(int i=0;i<=this.numberOfLayers;i++){
            layers[i] = getRoundRect(color.substring(1),i,this.numberOfLayers,this.radius);
        }
        float increaseHeight = 1;
        int increaseWidth = 0;
        if(this.numberOfLayers>0){
            increaseHeight = 1.2f + (this.numberOfLayers - 5) * 0.1f;
            increaseWidth = 20 + (this.numberOfLayers - 5) * 4;
        }
        int screen_width = Math.round((160*context.getResources().getDisplayMetrics().widthPixels)/(context.getResources().getDisplayMetrics().xdpi)) ;
        bar.setLayoutParams(new LinearLayout.LayoutParams(
                screen_width, (int) (dp(BAR_HEIGHT) * increaseHeight)
        ));
        LayerDrawable splash_test = new LayerDrawable(layers);
        bar.setBackground(splash_test);
        this.addView(bar);
        bar.setVisibility(GONE);
        Bar.setRippleDrawable(bar, Color.parseColor(color), Color.parseColor(rippleColor));
        bar.setProgress(progress,increaseWidth,animationType,animationDuration);
    }

    /**
     * Initializer function for the value tooltip
     *
     */
    void setupValue() {

        value.setText(valueText);
        value.setBackground(context.getResources().getDrawable(R.drawable.label_background));
        value.setRotation(90);
        value.setGravity(Gravity.CENTER);
        value.setPadding(0, 0, 0, dp(8));
        value.setTextColor(Color.parseColor(valueTextColor));
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, VALUE_FONT_SIZE);
        value.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(VALUE_FONT !=null) {
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
     * @return
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
     *
     */
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

    /**
     * Converts density independent pixel units (dp) to pixel units (px)
     *
     * @param dp
     * @return
     */
    int dp(float dp) {
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
     * @return
     */
    public Drawable getRoundRect(String color,int currentLayer,int totalLayer,int radius) {

        if(radius<0){
            radius = 0;
        }
        if(radius>20){
            radius = 20;
        }

        int leftTopPadding = 1;
        if(currentLayer%3==0){
            leftTopPadding = 2;
        }
        int rightPadding = 3;
        int bottomPadding = 3;

        String shadowalpha = null;
        int increment = 16 / (totalLayer - 2);
        int layernumber = 0;
        if (currentLayer == totalLayer) {
            shadowalpha = "#";
        }else{
            layernumber = increment * currentLayer;
            if (layernumber >= 17) {
                layernumber = 17;
            }
            shadowalpha = alphaSet[layernumber] ;
        }
        RoundRectShape rectShape = new RoundRectShape(new float[]{
                radius * 2, radius * 2, radius * 2, radius * 2,
                radius * 2, radius * 2, radius * 2, radius * 2
        }, null, null);
        if (totalLayer == currentLayer) {
            if (radius == 0) {
                radius = 2;
            }
            rectShape = new RoundRectShape(new float[]{
                    radius * 2, radius * 2, radius * 2, radius * 2,
                    radius * 2, radius * 2, radius * 2, radius * 2
            }, null, null);
        }

        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.setPadding(leftTopPadding,leftTopPadding,rightPadding,bottomPadding);
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
     * @param labelText
     * @return
     */
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
     * @param color is the color in int which is used as the background color.
     */
    public GradientDrawable setUpRoundBars(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(CORNER_RADIUS);
        return gradientDrawable;
    }

}
