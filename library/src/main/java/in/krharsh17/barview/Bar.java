package in.krharsh17.barview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

class Bar extends View {
    /**
     * It can be called by anyone directly from code to create a new instance of the view.
     * This constructor doesn’t have access to XML attributes, so you have to fill the parameters manually, using setters.
     *
     * @param context of the activity
     */
    Bar(Context context) {
        super(context);
    }

    /**
     * this fuction sets up the relative width of the Bar with respect to the progress
     * value given to the BarModel.
     *
     * @param progress
     */
    //increaseWidth is used to give some extra width to bar so that this extra width is used by shadow
    public void setProgress(float progress, int increaseWidth, int animationType, int animationDuration) {
        ViewGroup.LayoutParams params = this.getLayoutParams();
        if (animationType == BarView.INTRO_ANIM_EXPAND) {
            expand(this, animationDuration, Math.round(params.width * (progress)));
        } else if (animationType == BarView.INTRO_ANIM_NONE) {
            this.setVisibility(VISIBLE);
            params.width = Math.round(params.width * (progress)) + increaseWidth;
            this.setLayoutParams(params);
        }
        invalidate();
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setClickable(true);
        this.setFocusable(true);
    }

    /**
     * Sets a ripple drawable as the background of the bar
     * The background can display ripples on touch
     *
     * @param view - The view to set the background to
     * @param normalColor - Color when unpressed
     * @param touchColor - Color when pressed
     */
    public static void setRippleDrawable(View view, int normalColor, int touchColor) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(touchColor), view.getBackground(), null);
                view.setBackground(rippleDrawable);
            } else {
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(touchColor));
                stateListDrawable.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(touchColor));
                stateListDrawable.addState(new int[]{}, new ColorDrawable(normalColor));
                view.setBackground(stateListDrawable);
            }
        } catch (Exception e) {
        }
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
}
