package `in`.krharsh17.barview

import android.R
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View
import android.view.animation.DecelerateInterpolator

internal class Bar
/**
 * It can be called by anyone directly from code to create a new instance of the view.
 * This constructor doesn’t have access to XML attributes, so you have to fill the parameters manually, using setters.
 *
 * @param context of the activity
 */
(context: Context?) : View(context) {
    /**
     * this fuction sets up the relative width of the Bar with respect to the progress
     * value given to the BarModel.
     *
     * @param progress
     */
    //increaseWidth is used to give some extra width to bar so that this extra width is used by shadow
    fun setProgress(progress: Float, increaseWidth: Int, animationType: Int, animationDuration: Int) {
        val params = this.layoutParams
        if (animationType == BarView.INTRO_ANIM_EXPAND) {
            expand(this, animationDuration, Math.round(params.width * progress))
        } else if (animationType == BarView.INTRO_ANIM_NONE) {
            this.visibility = VISIBLE
            params.width = Math.round(params.width * progress) + increaseWidth
            this.layoutParams = params
        }
        invalidate()
        requestLayout()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.isClickable = true
        this.isFocusable = true
    }

    companion object {
        /**
         * Sets a ripple drawable as the background of the bar
         * The background can display ripples on touch
         *
         * @param view - The view to set the background to
         * @param normalColor - Color when unpressed
         * @param touchColor - Color when pressed
         */
        fun setRippleDrawable(view: View, normalColor: Int, touchColor: Int) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val rippleDrawable = RippleDrawable(ColorStateList.valueOf(touchColor), view.background, null)
                    view.background = rippleDrawable
                } else {
                    val stateListDrawable = StateListDrawable()
                    stateListDrawable.addState(intArrayOf(R.attr.state_pressed), ColorDrawable(touchColor))
                    stateListDrawable.addState(intArrayOf(R.attr.state_focused), ColorDrawable(touchColor))
                    stateListDrawable.addState(intArrayOf(), ColorDrawable(normalColor))
                    view.background = stateListDrawable
                }
            } catch (e: Exception) {
            }
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
            v.visibility = VISIBLE
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