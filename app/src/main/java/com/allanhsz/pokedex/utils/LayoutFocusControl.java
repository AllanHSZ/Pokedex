package com.allanhsz.pokedex.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.allanhsz.pokedex.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;

public class LayoutFocusControl extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener {

    private int ocultArea;
    private final int screenHeight;
    private final Activity activity;

    private TextInputEditText lastFocus;

    private final AppBarLayout appbar;
    private final AppBarLayout.Behavior behavior;
    private final int appbarInitialHeight;

    private final View rootView;
    private int heightMax; // Record the maximum height of the pop content area

    public LayoutFocusControl(final Activity activity, AppBarLayout appbar, int screenHeight, int appbarInitialHeight) {
        super(activity);
        this.appbar = appbar;
        this.activity = activity;
        this.screenHeight = screenHeight;
        this.appbarInitialHeight = appbarInitialHeight;

        rootView = new View(activity);
        setContentView(rootView);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        setBackgroundDrawable(new ColorDrawable(0));

        setWidth(0);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (!isShowing()) {
            final View view = activity.getWindow().getDecorView();

            view.post(new Runnable() {
                @Override
                public void run() {
                    showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
                }
            });
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);

        if (rect.bottom > heightMax)
            heightMax = rect.bottom;

        focusIn(activity.getCurrentFocus(), heightMax - rect.bottom);
    }


    public void focusIn(View view, int ocultArea) {
        this.ocultArea = ocultArea;

        if (!(view instanceof TextInputEditText)) return;

        lastFocus = (TextInputEditText) view;
        view = ((View) view.getParent().getParent());

        int[] l = new int[2];
        view.getLocationInWindow(l);

        int y = (int) (l[1] + view.getHeight() / 2 + activity.getResources().getDimension(R.dimen.large));
        int visibleArea = screenHeight - ocultArea;
        int range = appbar.getTotalScrollRange();
        int offset;

        if (ocultArea > 0) {
            if (y > visibleArea) {
                Log.i("TESTEREPORT", "Y = " + y);
                offset = -(int) ((float) (y - visibleArea) / appbarInitialHeight * range);

                if (behavior != null) {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt();
                    valueAnimator.setInterpolator(new DecelerateInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            behavior.setTopAndBottomOffset((Integer) animation.getAnimatedValue());
                            appbar.requestLayout();
                        }
                    });
                    valueAnimator.setIntValues(0, offset);
                    valueAnimator.setDuration(300);
                    valueAnimator.start();
                }
            }
//            else {
//                Toast.makeText(activity, "-50", Toast.LENGTH_SHORT).show();
//                offset = 0;
//                return;
//                offset = (int) +((float)(visibleArea-y)/appbarInitialHeight*range);
//            }
        } else {
            appbar.setExpanded(true, true);
        }
    }

    public int getOcultArea() {
        return ocultArea;
    }

    public View getLastFlocus() {
        return lastFocus;
    }
}