package `in`.krharsh17.barview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import java.util.*

/**
 * This is the custom view which is the cumulation of various individual barGroups
 * extends from a ScrollView and implementing Constant interface
 */
class BarView : ScrollView, Constants {
    private var containerLayout: LinearLayout
    private var mcontext: Context
    /**
     * Returns a reference to the attached Listener
     */
    /**
     * Attaches a onBarClickListener
     */
    var onBarClickListener: OnBarClickListener? = null
    private var barGroups: MutableList<BarGroup>
    private var data: List<BarModel>? = null
    private var isDataPopulated = false
    var barMargin = 6
    var verticalSpacing = 48
    var barHeight = 20
    var labelFontSize = 18
    var valueFontSize = 9
    var valueTooltipCornerRadius = 0

    /**
     * setters and getters
     */
    var animationType: Int = Constants.Companion.DEFAULT_INTRO_ANIMATION
    var animationDuration: Int = Constants.Companion.DEFAULT_ANIMATION_DURATION
    private var backgroundColor: String? = null
    private var gradientStart: String? = null
    private var gradientEnd: String? = null
    private var gradientDirection: String? = null
    private var cornerRadius = 0
    var labelTextColor: String? = Constants.Companion.LABEL_TEXT_COLOR
    var valueTextColor: String? = Constants.Companion.VALUE_TEXT_COLOR
    private var LABEL_FONT: String? = null
    private var VALUE_FONT: String? = null
    var rippleColor: String? = Constants.Companion.RIPPLE_COLOR

    /**
     * parameterized constructors
     * It can be called by a anyone directly from code to create a new instance of the view.
     * This constructor doesn’t have access to XML attributes, so you have to fill the parameters manually, using setters.
     *
     * @param context of the activity
     */
    constructor(context: Context) : super(context) {
        this.mcontext = context
        barGroups = ArrayList()
        containerLayout = LinearLayout(context)
        containerLayout.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        containerLayout.orientation = LinearLayout.VERTICAL
        this.addView(containerLayout)
    }

    /**
     * parameterized constructors
     * This is the basic XML constructor. Without it, the layout inflater will crash.
     * The AttributeSet parameter contains all attribute values provided via XML.
     *
     * @param context is the context of the activity
     * @param attrs are the attribute values which are saved as an AttributeSet
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mcontext = context
        barGroups = ArrayList()
        containerLayout = LinearLayout(context)
        containerLayout.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        containerLayout.orientation = LinearLayout.VERTICAL
        this.addView(containerLayout)
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs,
                    R.styleable.BarView, 0, 0)
            valueTooltipCornerRadius = a.getInteger(R.styleable.BarView_valueTooltipCornerRadius, valueTooltipCornerRadius)
            animationType = a.getInteger(R.styleable.BarView_introAnimationType, animationType)
            animationDuration = a.getInteger(R.styleable.BarView_introAnimationDuration, animationDuration)
            verticalSpacing = a.getInteger(R.styleable.BarView_barGroupSpacing, verticalSpacing)
            barHeight = a.getInteger(R.styleable.BarView_barHeight, barHeight)
            labelFontSize = a.getInteger(R.styleable.BarView_labelTextSize, labelFontSize)
            valueFontSize = a.getInteger(R.styleable.BarView_valueTextSize, valueFontSize)
            VALUE_FONT = a.getString(R.styleable.BarView_labelFont)
            LABEL_FONT = a.getString(R.styleable.BarView_labelFont)
            labelTextColor = a.getString(R.styleable.BarView_labelTextColor)
            valueTextColor = a.getString(R.styleable.BarView_valueTextColor)
            rippleColor = a.getString(R.styleable.BarView_rippleColor)
            backgroundColor = a.getString(R.styleable.BarView_backgroundColor)
            gradientStart = a.getString(R.styleable.BarView_gradientStart)
            gradientEnd = a.getString(R.styleable.BarView_gradientEnd)
            gradientDirection = a.getString(R.styleable.BarView_gradientDirection)
            if (labelTextColor == null) {
                labelTextColor = Constants.Companion.LABEL_TEXT_COLOR
            }
            if (valueTextColor == null) {
                valueTextColor = Constants.Companion.VALUE_TEXT_COLOR
            }
            if (rippleColor == null) {
                rippleColor = Constants.Companion.RIPPLE_COLOR
            }
            if (gradientDirection == null) {
                gradientDirection = "horizontal"
            }
            if (backgroundColor != null) {
                setBackgroundColor(backgroundColor)
            }
            if (gradientStart != null && gradientEnd != null) {
                setBackgroundGradient(gradientStart, gradientEnd, gradientDirection)
            }
            a.recycle()
        }
    }

    interface OnBarClickListener {
        fun onBarClicked(pos: Int)
    }

    fun setData(data: List<BarModel>?) {
        this.data = data
        if (animationType == INTRO_ANIM_NONE) {
            populateBarView(INTRO_ANIM_NONE, animationDuration)
        } else if (animationType == INTRO_ANIM_EXPAND) {
            populateBarView(INTRO_ANIM_EXPAND, animationDuration)
        }
        isDataPopulated = true
    }

    fun setData(data: List<BarModel>?, isAnimationEnabled: Boolean) {
        this.data = data
        if (isAnimationEnabled) {
            if (animationType == INTRO_ANIM_NONE) {
                populateBarView(INTRO_ANIM_NONE, animationDuration)
            } else if (animationType == INTRO_ANIM_EXPAND) {
                populateBarView(INTRO_ANIM_EXPAND, animationDuration)
            }
        } else {
            populateBarView(INTRO_ANIM_NONE, animationDuration)
        }
    }

    /**
     * This method iterates over various BarModels and adds a BarGroup for each
     * of the BarModels inside private member data
     */
    private fun populateBarView(animationType: Int, animationDuration: Int) {
        for (b in data!!) {
            addBar(b, animationType, animationDuration)
        }
    }

    /**
     * This method actuall instanciates BarGroups based on the BarModel passed in as
     * a param and cumulates it into barGroups.
     *
     * @param data is a BarModel that contains all the required to
     * construct a BarGroup instance.
     */
    private fun addBar(data: BarModel, animationType: Int, animationDuration: Int) {
        val barGroup = BarGroup(
                mcontext,
                data.label,
                data.color,
                data.value,
                data.fillRatio,
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
                valueTooltipCornerRadius,
                LABEL_FONT,
                VALUE_FONT,
                data.elevation,
                data.radius
        )
        barGroup.setOnTouchListener(object : OnTouchListener {
            private val CLICK_ACTION_THRESHOLD = 200
            private var startX = 0f
            private var startY = 0f
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                    }
                    MotionEvent.ACTION_UP -> {
                        val endX = event.x
                        val endY = event.y
                        if (isAClick(startX, endX, startY, endY)) {
                            Log.d("BarView", "you clicked!")
                            onBarClickListener!!.onBarClicked(barGroups.indexOf(v))
                        }
                    }
                    else -> Log.d("BarView", "onTouch:Unknown Event ")
                }
                return true
            }

            private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
                val differenceX = Math.abs(startX - endX)
                val differenceY = Math.abs(startY - endY)
                return !(differenceX > CLICK_ACTION_THRESHOLD /* =5 */ || differenceY > CLICK_ACTION_THRESHOLD)
            }
        })
        barGroups.add(barGroup)
        containerLayout.addView(barGroup)
        invalidate()
        requestLayout()
    }

    fun setBackgroundColor(color: String?) {
        containerLayout.setBackgroundColor(Color.parseColor(color))
    }

    fun setBackgroundGradient(startColor: String?, endColor: String?, direction: String?) {
        val gd: GradientDrawable?
        gd = when (direction) {
            "horizontal" -> GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(Color.parseColor(startColor), Color.parseColor(endColor)))
            "vertical" -> GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.parseColor(startColor), Color.parseColor(endColor)))
            else -> null
        }
        if (gd != null) {
            containerLayout.background = gd
        }
    }

    fun setCornerRadius(radius: Int) {
        cornerRadius = radius
        if (isDataPopulated) {
            containerLayout.removeAllViews()
            populateBarView(animationType, animationDuration)
        }
    }

    companion object {
        const val INTRO_ANIM_NONE = 0
        const val INTRO_ANIM_EXPAND = 1

        /**
         * This function returns a random color based on a constant {@value #CHAR_ARRAY}
         * It builds up the random hex value by randomly choosing between any of the 16
         * literals in the char array and appending them one after another
         *
         * @return a random color as a randomized hex val
         */
        @JvmStatic
        val randomColor: String
            get() {
                val letters: CharArray = Constants.Companion.CHAR_ARRAY.toCharArray()
                val color = StringBuilder("#")
                for (i in 0..5) {
                    color.append(letters[Math.round(Math.floor(Math.random() * 16)).toInt()])
                }
                return color.toString()
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
        fun getRandomColor(approximateColor: Int): String {
            val tolerance = 5
            var blue = approximateColor and 0x000000ff
            var green = approximateColor and 0x0000ff00 shr 8
            var red = approximateColor and 0x00ff0000 shr 16
            blue = Math.min(Math.max(0, blue + getRandomNumberInRange(-tolerance, tolerance)), 255)
            green = Math.min(Math.max(0, green + getRandomNumberInRange(-tolerance, tolerance)), 255)
            red = Math.min(Math.max(0, red + getRandomNumberInRange(-tolerance, tolerance)), 255)
            val newApproximateColor = -0x1000000 or (red shl 16) or (green shl 8) or blue
            val approximateColorHexLiteral = Integer.toHexString(newApproximateColor)
            return "#" + approximateColorHexLiteral.substring(2)
        }

        /**
         * Helper function returns any random integer between the specified bounds
         *  both inclusive
         *
         * @param min variance the user wants in the approximate color
         * @param max variance the user wants in the approximate color
         * @return
         */
        private fun getRandomNumberInRange(min: Int, max: Int): Int {
            val r = Random()
            return r.nextInt(max - min + 1) + min
        }
    }
}