package in.krharsh17.barview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

class Bar extends View {

    Bar(Context context) {
        super(context);
    }

    void setProgress(float progress) {
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.width = Math.round(params.width * (progress));
        this.setLayoutParams(params);
        invalidate();
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setClickable(true);
        this.setFocusable(true);
    }

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
}
