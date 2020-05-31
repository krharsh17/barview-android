package `in`.krharsh17.barview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Transformation
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Constraints
import java.util.*

/**
 * this class is the custom view which appears as bars in the BarView
 * extends ConstraintLayout and implements Constants interface
 */
internal class BarGroup : ConstraintLayout, Constants {
    private var mcontext: Context
    private var label: TextView
    private var initial: View
    private var bar: Bar
    private var value: TextView
    private var increaseHeight = 0f
    private var increaseWidth = 0
    private var labelText: String?
    private var color: String?
    private var valueText: String?
    private var progress: Float
    var elevation = 0
    var radius = 0
    var numberOfLayers = 0
    private var animationType = 0
    private var animationDuration: Int = Constants.Companion.DEFAULT_ANIMATION_DURATION
    private var BAR_MARGIN = 6
    private var VERTICAL_SPACING = 48
    private var BAR_HEIGHT = 20
    private var LABEL_FONT_SIZE = 18
    private var VALUE_FONT_SIZE = 9
    private var labelTextColor: String? = Constants.Companion.LABEL_TEXT_COLOR
    private var valueTextColor: String? = Constants.Companion.VALUE_TEXT_COLOR
    private var VALUE_FONT: String? = null
    private var LABEL_FONT: String? = null
    private var rippleColor: String? = Constants.Companion.RIPPLE_COLOR
    private var CORNER_RADIUS = 0
    private var VALUE_TOOLTIP_CORNER_RADIUS = 0

    /**
     * one bar has different drawable stacked together with same solid color but different alpha value
     * this array of string defined different alpha value from 0 to 1
     * we will use maximum of 18 layers for shadow
     * numbers of layers can very depending on elevation value
     */
    var alphaSet = arrayOf(
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
    )

    /**
     * parameterized constructor
     *
     * @param context of the activity
     * @param labelText for the barGroup instance
     * @param color hex color value for the fill of barGroup instance
     * @param valueText for approximating the length of Bargroup instance
     * @param progress marking the progress of the bar
     */
    constructor(context: Context, labelText: String?, color: String?, valueText: String?, progress: Float) : super(context) {
        this.mcontext = context
        this.labelText = labelText
        this.color = color
        this.valueText = valueText
        this.progress = progress
        label = TextView(context)
        initial = View(context)
        bar = Bar(context)
        bar.visibility = View.GONE
        value = TextView(context)
    }

    /**
     * parameterized constructor
     *
     * @param context of the activity
     * @param labelText for the barGroup instance
     * @param color hex color value for the fill of barGroup instance
     * @param valueText for approximating the length of Bargroup instance
     * @param progress marking the progress of the bar
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
    constructor(
            context: Context,
            labelText: String?,
            color: String?,
            valueText: String?,
            progress: Float,
            animationType: Int,
            animationDuration: Int,
            BAR_MARGIN: Int,
            VERTICAL_SPACING: Int,
            BAR_HEIGHT: Int,
            LABEL_FONT_SIZE: Int,
            VALUE_FONT_SIZE: Int,
            labelTextColor: String?,
            VALUE_TEXT_COLOR: String?,
            RIPPLE_COLOUR: String?,
            CORNER_RADIUS: Int,
            VALUE_TOOLTIP_CORNER_RADIUS: Int,
            LABEL_FONT: String?,
            VALUE_FONT: String?,
            elevation: Int,
            radius: Int) : super(context) {
        this.mcontext = context
        this.labelText = labelText
        this.color = color
        this.valueText = valueText
        this.progress = progress
        this.animationType = animationType
        this.animationDuration = animationDuration
        this.BAR_MARGIN = BAR_MARGIN
        this.VERTICAL_SPACING = VERTICAL_SPACING
        this.BAR_HEIGHT = BAR_HEIGHT
        this.LABEL_FONT_SIZE = LABEL_FONT_SIZE
        this.VALUE_FONT_SIZE = VALUE_FONT_SIZE
        this.labelTextColor = labelTextColor
        valueTextColor = VALUE_TEXT_COLOR
        rippleColor = RIPPLE_COLOUR
        this.LABEL_FONT = LABEL_FONT
        this.VALUE_FONT = VALUE_FONT
        this.CORNER_RADIUS = CORNER_RADIUS
        this.VALUE_TOOLTIP_CORNER_RADIUS = VALUE_TOOLTIP_CORNER_RADIUS
        label = TextView(context)
        initial = View(context)
        bar = Bar(context)
        value = TextView(context)
        this.elevation = elevation
        this.radius = radius
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        label.id = View.generateViewId()
        initial.id = View.generateViewId()
        bar.id = View.generateViewId()
        value.id = View.generateViewId()
        if (elevation > 0) {
            when (elevation) {
                1, 2, 3 -> numberOfLayers = 5
                4 -> numberOfLayers = 6
                5 -> numberOfLayers = 7
                6 -> numberOfLayers = 8
                7 -> numberOfLayers = 9
                8 -> numberOfLayers = 10
                9 -> numberOfLayers = 11
                10 -> numberOfLayers = 12
                11, 12, 13 -> numberOfLayers = 14
                14, 15, 16 -> numberOfLayers = 16
                else -> numberOfLayers = 18
            }
        }
        setupLabel()
        setupInitial()
        setupBar()
        setupValue()
        applyConstraints()
    }

    /**
     * Initializer function for the label segment
     */
    private fun setupLabel() {
        val labelParams: LayoutParams = Constraints.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
        labelParams.setMargins(
                dp(8f),
                dp(VERTICAL_SPACING / 2.toFloat()),
                dp(8f),
                dp(VERTICAL_SPACING / 2.toFloat())
        )
        label.text = parseLabel(labelText)
        label.setTextColor(Color.parseColor(labelTextColor))
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, LABEL_FONT_SIZE.toFloat())
        if (LABEL_FONT != null) {
            label.typeface = Typeface.createFromAsset(mcontext.assets, LABEL_FONT)
        }
        label.layoutParams = labelParams
        label.textAlignment = View.TEXT_ALIGNMENT_CENTER
        label.gravity = Gravity.CENTER_VERTICAL
        this.addView(label)
    }

    /**
     * Initializer function for the initial block
     */
    fun setupInitial() {
        val layers = arrayOfNulls<Drawable>(numberOfLayers + 1)
        setLayerForInitial(numberOfLayers, layers)
        var initialParams = LayoutParams(dp(12f) + increaseWidth, (dp(BAR_HEIGHT.toFloat()) * increaseHeight).toInt())
        initialParams.rightMargin = dp(12f)
        initial.layoutParams = initialParams
        val splash_test = LayerDrawable(layers)
        initial.background = splash_test
        initial.visibility = View.GONE
        Bar.setRippleDrawable(initial, Color.parseColor(color), Color.parseColor(rippleColor))
        initial.isClickable = true
        initial.isFocusable = true
        this.addView(initial)
        if (animationType == BarView.Companion.INTRO_ANIM_NONE) {
            initial.visibility = View.VISIBLE
            initialParams = LayoutParams(dp(12f), dp(BAR_HEIGHT.toFloat()))
            initialParams.rightMargin = dp(12f)
            initial.layoutParams = initialParams
        } else if (animationType == BarView.Companion.INTRO_ANIM_EXPAND) {
            val screen_width = Math.round(160 * mcontext.resources.displayMetrics.widthPixels / mcontext.resources.displayMetrics.xdpi)
            initialParams = LayoutParams(screen_width, dp(BAR_HEIGHT.toFloat()))
            initialParams.rightMargin = dp(12f)
            initial.layoutParams = initialParams
            expand(initial, animationDuration, dp(12f))
        }
    }

    /**
     * Initializer function for the main bar
     */
    fun setupBar() {
        val layers = arrayOfNulls<Drawable>(numberOfLayers + 1)
        setLayerForBar(numberOfLayers, layers)
        val screen_width = Math.round(160 * mcontext.resources.displayMetrics.widthPixels / mcontext.resources.displayMetrics.xdpi)
        bar.layoutParams = LinearLayout.LayoutParams(
                screen_width, (dp(BAR_HEIGHT.toFloat()) * increaseHeight).toInt()
        )
        val splash_test = LayerDrawable(layers)
        bar.background = splash_test
        this.addView(bar)
        bar.visibility = View.GONE
        Bar.setRippleDrawable(bar, Color.parseColor(color), Color.parseColor(rippleColor))
        bar.setProgress(progress, increaseWidth, animationType, animationDuration)
        bar.setOnLongClickListener {
            addOptions()
            true
        }
    }

    /**
     * Initializer function for the value tooltip. By default the corner radius of tooltips is zero
     * and if user specifies a value for corner radius then the changes ar applied in the tooltips.
     *
     *
     */
    private fun setupValue() {
        value.text = valueText
        //value.setBackground(context.getResources().getDrawable(R.drawable.label_background));
        val bitmap = Bitmap.createBitmap(
                110,  // Width
                60,  // Height
                Bitmap.Config.ARGB_8888 // Config
        )
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        val offset = 5
        val rectF = RectF(
                offset.toFloat(),  // left
                offset.toFloat(),  // top
                (canvas.width - offset).toFloat(),  // right
                (canvas.height - offset).toFloat() // bottom
        )
        val cornersRadius = VALUE_TOOLTIP_CORNER_RADIUS
        canvas.drawRoundRect(
                rectF,  // rect
                cornersRadius.toFloat(),  // rx
                cornersRadius.toFloat(),  // ry
                paint // Paint
        ) // method to draw rectangular tooltip with specified corner radius
        val d: Drawable = BitmapDrawable(resources, bitmap) // conversion of bitmap into drawable
        value.background = d
        value.rotation = 90f
        value.gravity = Gravity.CENTER
        value.setPadding(0, dp(8f), 0, dp(8f))
        value.setTextColor(Color.parseColor(valueTextColor))
        value.setTextSize(TypedValue.COMPLEX_UNIT_SP, VALUE_FONT_SIZE.toFloat())
        value.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (VALUE_FONT != null) {
            val tf = get(VALUE_FONT!!, mcontext)
            value.typeface = tf
        }
        value.layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        value.isClickable = true
        value.isFocusable = true
        Bar.setRippleDrawable(value, Color.parseColor(color), Color.parseColor(rippleColor))
        this.addView(value)
    }

    /**
     * Sets constraints for all pieces of a BarGroup instance
     */
    private fun applyConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.setHorizontalBias(initial.id, 0.30f)
        constraintSet.connect(initial.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constraintSet.connect(initial.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        constraintSet.connect(initial.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, dp(BAR_MARGIN.toFloat()))
        constraintSet.connect(initial.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dp(BAR_MARGIN.toFloat()))
        constraintSet.connect(bar.id, ConstraintSet.START, initial.id, ConstraintSet.END, dp(BAR_MARGIN.toFloat()))
        constraintSet.connect(bar.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(bar.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.connect(value.id, ConstraintSet.START, bar.id, ConstraintSet.END, dp(BAR_MARGIN.toFloat()))
        constraintSet.connect(value.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(value.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.connect(label.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constraintSet.connect(label.id, ConstraintSet.END, initial.id, ConstraintSet.END, 0)
        constraintSet.connect(label.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(label.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.applyTo(this)
    }

    /**
     * Converts density independent pixel units (dp) to pixel units (px)
     *
     * @param dp
     */
    private fun dp(dp: Float): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mcontext.resources.displayMetrics))
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
    fun getRoundRect(color: String, currentLayer: Int, totalLayer: Int, radius: Int): Drawable {
        var localRadius = radius
        var leftTopPadding = 1
        if (currentLayer % 3 == 0) {
            leftTopPadding = 2
        }
        val rightPadding = 3
        val bottomPadding = 3
        var shadowalpha: String? = null
        val increment = 16 / (totalLayer - 2)
        var layernumber = 0
        if (radius < 0) {
            localRadius = 0
        }
        if (radius > 20) {
            localRadius = 20
        }
        if (currentLayer % 3 == 0) {
            leftTopPadding = 2
        }
        if (currentLayer == totalLayer) {
            shadowalpha = "#"
        } else {
            layernumber = increment * currentLayer
            if (layernumber >= 17) {
                layernumber = 17
            }
            shadowalpha = alphaSet[layernumber]
        }
        var rectShape = RoundRectShape(floatArrayOf(
                localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2.toFloat(),
                localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2
                .toFloat()), null, null)
        if (totalLayer == currentLayer) {
            if (localRadius == 0) {
                localRadius = 2
            }
            rectShape = RoundRectShape(floatArrayOf(
                    localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2.toFloat(),
                    localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2.toFloat(), localRadius * 2
                    .toFloat()), null, null)
        }
        val shapeDrawable = ShapeDrawable(rectShape)
        shapeDrawable.setPadding(leftTopPadding, leftTopPadding, rightPadding, bottomPadding)
        shapeDrawable.paint.color = Color.parseColor(shadowalpha + color)
        shapeDrawable.paint.style = Paint.Style.FILL
        shapeDrawable.paint.isAntiAlias = true
        shapeDrawable.paint.flags = Paint.ANTI_ALIAS_FLAG
        return shapeDrawable
    }

    /**
     * Parses the label text to truncate or hyphenize the string to fit in the given space
     *
     * @param labelText
     */
    private fun parseLabel(labelText: String?): String {
        val tokens = labelText!!.split(" ".toRegex()).toTypedArray()
        val finalizedString = StringBuilder()
        for (s in tokens) {
            if (s.length < 8) {
                finalizedString.append("""
    $s

    """.trimIndent())
            } else if (s.length >= 8 && s.length < 12) {
                finalizedString.append("""
    ${s.substring(0, 7)}

    """.trimIndent())
                finalizedString.append("""
    -${s.substring(7)}

    """.trimIndent())
            } else if (s.length >= 12 && s.length < 15) {
                finalizedString.append("""
    ${s.substring(0, 7)}

    """.trimIndent())
                finalizedString.append("""
    -${s.substring(7, 12)}

    """.trimIndent())
                if (s.length > 13) {
                    finalizedString.append("""
    -${s.substring(13)}

    """.trimIndent())
                }
            } else {
                finalizedString.append("""
    ${s.substring(0, 5)}..

    """.trimIndent())
            }
        }
        finalizedString.deleteCharAt(finalizedString.length - 1)
        return finalizedString.toString()
    }

    /**
     * The animation function takes the actual view-group, animation duration and target width in dp as it's arguments
     * and takes initial width as 0 dp. After this, using ValueAnimator, the barview is made to expand to provided width
     * in the specified time duration and it's visibility is set to visible during animation.
     */
    class ResizeAnimation(var view: View, val targetHeight: Int, var startHeight: Int) : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val newHeight = (startHeight + targetHeight * interpolatedTime).toInt()
            //to support decent animation, change new heigt as Nico S. recommended in comments
            //int newHeight = (int) (startHeight+(targetHeight - startHeight) * interpolatedTime);
            view.layoutParams.height = newHeight
            view.requestLayout()
        }

        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
        }

        override fun willChangeBounds(): Boolean {
            return true
        }

    }

    /**
     * This creates a gradient drawable background for the bars - sets a shape, a color and radius for the corners.
     *
     * @param color is the color in int which is used as the background color.
     */
    fun setUpRoundBars(color: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(color)
        gradientDrawable.cornerRadius = CORNER_RADIUS.toFloat()
        return gradientDrawable
    }

    fun setUpRoundBars(color: Int, highlightColor: Int): GradientDrawable {
        /**
         * This creates a gradient drawable background for the bars - sets a shape, a color and radius for the corners.
         * @param color is the color in int which is used as the background color.
         */
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(color)
        gradientDrawable.setStroke(5, highlightColor)
        gradientDrawable.cornerRadius = CORNER_RADIUS.toFloat()
        return gradientDrawable
    }

    /**
     * Generates a modified color for highlighting.
     *
     * @param backColor is the backcolor which will be used to highlight.
     * @param factor    factor by which backcolor is to be changed.
     * @return modified color which is the color used as the backgroundcolor.
     */
    fun changeColor(backColor: Int, factor: Double): String {
        val modifiedColor: String
        val hsv = FloatArray(3)
        Color.colorToHSV(backColor, hsv)
        hsv[2] = hsv[2] * factor.toFloat()
        modifiedColor = String.format("#%06X", 0xFFFFFF and Color.HSVToColor(hsv))
        return modifiedColor
    }

    /**
     * to add shadow.
     *
     * @param numberOfLayers param to add shadow behind the bars.
     * @param layers         param to add shadow behind the bars.
     */
    fun setLayerForBar(numberOfLayers: Int, layers: Array<Drawable?>) {
        for (i in 0..numberOfLayers) {
            layers[i] = null
        }
        for (i in 0..numberOfLayers) {
            layers[i] = getRoundRect(color!!.substring(1), i, numberOfLayers, radius)
        }
        increaseHeight = 1f
        increaseWidth = 0
        if (this.numberOfLayers > 0) {
            increaseHeight = 1.2f + (numberOfLayers - 5) * 0.1f
            increaseWidth = 20 + (numberOfLayers - 5) * 4
        }
    }

    /**
     * to add shadow
     *
     * @param numberOfLayers param to add shadow behind the bars.
     * @param layers         param to add shadow behind the bars.
     */
    fun setLayerForInitial(numberOfLayers: Int, layers: Array<Drawable?>) {
        for (i in 0..numberOfLayers) {
            layers[i] = null
        }
        for (i in 0..numberOfLayers) {
            layers[i] = getRoundRect(color!!.substring(1), i, numberOfLayers, radius)
        }
        increaseHeight = 1f //To give some extra height of bar for shadow
        increaseWidth = 0 //To give some extra width of bar for shadow
        if (this.numberOfLayers > 0) {
            increaseHeight = 1.2f + (numberOfLayers - 5) * 0.1f
            increaseWidth = 15 + (numberOfLayers - 5) * 4
        }
    }

    /**
     * adds highlight, fade to the bars or deletes a bar completely after showing an alert dialogue.
     */
    fun addOptions() {
        val alertDialog = AlertDialog.Builder(mcontext)
        val layoutInflater = LayoutInflater.from(mcontext)
        val dialogueView = layoutInflater.inflate(R.layout.alert_dialogue, null)
        alertDialog.setView(dialogueView)
        val highlight = dialogueView.findViewById<RadioButton>(R.id.highlight)
        val highlightColor = dialogueView.findViewById<RadioGroup>(R.id.highlightColor)
        val fade = dialogueView.findViewById<RadioButton>(R.id.fade)
        val delete = dialogueView.findViewById<RadioButton>(R.id.delete)
        highlight.setOnClickListener { highlightColor.visibility = View.VISIBLE }
        fade.setOnClickListener { highlightColor.visibility = View.GONE }
        delete.setOnClickListener { highlightColor.visibility = View.GONE }
        val green = dialogueView.findViewById<RadioButton>(R.id.green)
        val blue = dialogueView.findViewById<RadioButton>(R.id.blue)
        val pink = dialogueView.findViewById<RadioButton>(R.id.pink)
        val yellow = dialogueView.findViewById<RadioButton>(R.id.yellow)
        alertDialog.setPositiveButton("okay") { dialogInterface, i ->
            val colorOfHighlight: Int
            if (highlight.isChecked) {
                colorOfHighlight = assignColor(green, blue, pink, yellow)
                if (colorOfHighlight == 0) {
                    Toast.makeText(mcontext, "Please Choose a Color", Toast.LENGTH_SHORT).show()
                } else {
                    val myColor = changeColor(Color.parseColor(color), 0.7)
                    bar.background = setUpRoundBars(Color.parseColor(myColor), colorOfHighlight)
                    initial.background = setUpRoundBars(Color.parseColor(myColor), colorOfHighlight)
                    bar.setOnLongClickListener {
                        removeOptions(highlight, fade)
                        true
                    }
                }
            }
            if (fade.isChecked) {
                setAlphaValue(0.13.toFloat())
                bar.setOnLongClickListener {
                    removeOptions(highlight, fade)
                    true
                }
            }
            if (delete.isChecked) {
                removeViews()
            }
        }.setNegativeButton("cancel") { dialogInterface, i ->
            //Do nothing
        }.show()
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
    fun assignColor(green: RadioButton, blue: RadioButton, pink: RadioButton, yellow: RadioButton): Int {
        var colorOfHighlight = 0
        if (green.isChecked) {
            colorOfHighlight = resources.getColor(R.color.greenHighlight)
        }
        if (blue.isChecked) {
            colorOfHighlight = resources.getColor(R.color.blueHighlight)
        }
        if (pink.isChecked) {
            colorOfHighlight = resources.getColor(R.color.pinkHighlight)
        }
        if (yellow.isChecked) {
            colorOfHighlight = resources.getColor(R.color.yellowHighlight)
        }
        return colorOfHighlight
    }

    /**
     * changes the alpha value so that the views are only slightly visible.
     *
     * @param alphaValue is used to assign an Alpha value.
     */
    fun setAlphaValue(alphaValue: Float) {
        bar.alpha = alphaValue
        initial.alpha = alphaValue
        label.alpha = alphaValue
        value.alpha = alphaValue
    }

    /**
     * sets the original background for bars - after highlight is removed.
     */
    fun setOriginalBackground() {
        val layers = arrayOfNulls<Drawable>(numberOfLayers + 1)
        setLayerForBar(numberOfLayers, layers)
        val splash_test_bar = LayerDrawable(layers)
        bar.background = splash_test_bar
        setLayerForInitial(numberOfLayers, layers)
        val splash_test_initial = LayerDrawable(layers)
        initial.background = splash_test_initial
    }

    /**
     * Removes all the views.
     */
    fun removeViews() {
        removeView(bar)
        removeView(initial)
        removeView(label)
        removeView(value)
    }

    /**
     * Generates an alert dialogue to remove the selected option.
     *
     * @param highlight Radio Button in dialogue view which highlights the bar if selected.
     * @param fade      Radio Button in dialogue view which fades the bar if selected.
     */
    fun removeOptions(highlight: RadioButton, fade: RadioButton) {
        var message: String? = null
        if (highlight.isChecked) {
            message = resources.getString(R.string.removeHighlight)
        }
        if (fade.isChecked) {
            message = resources.getString(R.string.removeFade)
        }
        AlertDialog.Builder(mcontext).setMessage(message).setPositiveButton("yes") { dialogInterface, i ->
            if (highlight.isChecked) {
                setOriginalBackground()
            }
            if (fade.isChecked) {
                setAlphaValue(1f)
            }
            bar.setOnLongClickListener {
                addOptions()
                true
            }
        }.setNegativeButton("cancel") { dialogInterface, i ->
            //Do nothing
        }.show()
    }

    companion object {
        var fontCache = Hashtable<String, Typeface?>()

        /**
         * Loads font from assets
         *
         * @param name
         * @param context
         */
        operator fun get(name: String, context: Context): Typeface? {
            var tf = fontCache[name]
            if (tf == null) {
                tf = try {
                    Typeface.createFromAsset(context.assets, name)
                } catch (e: Exception) {
                    return null
                }
                fontCache[name] = tf
            }
            return tf
        }

        /**
         * Animator function for the 'expand' intro animation
         *
         * @param v
         * @param duration
         * @param targetWidth
         */
        fun expand(v: View, duration: Int, targetWidth: Int) {

            //int prevWidth  = v.getWidth();
            val prevWidth = 0
            v.visibility = View.VISIBLE
            val valueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
            valueAnimator.addUpdateListener { animation ->
                v.layoutParams.width = animation.animatedValue as Int
                //v.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                v.requestLayout()
            }
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = duration.toLong()
            valueAnimator.start()
        }
    }
}