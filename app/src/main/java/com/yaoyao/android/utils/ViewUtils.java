package com.yaoyao.android.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yaoyao.android.BaseApplication;

/**
 * Created by lane on 14/12/4.
 */
public class ViewUtils {
    private static Application application;
    private static Application getApplication(){
        if (application == null){
            application = BaseApplication.getInstance();
        }
        return application;
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
               getApplication().getResources().getDisplayMetrics());
    }
    public static int px2dp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().getDisplayMetrics());
    }
    public static int densityDpi(){
        return getApplication().getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenWidth(){
        return getApplication().getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(){
        return getApplication().getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取手机屏幕比例
     */
    public static float getScreenScale(){
        float scale=(float) getApplication().getResources().getDisplayMetrics().widthPixels/getApplication().getResources().getDisplayMetrics().heightPixels;
        return scale;
    }
    public static void onTouchStyleHelper(final View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        view.setAlpha(1f);
                }
                return false;
            }
        });
    }

    public static void onTouchStyleHelper(final View view, final float alpha){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setAlpha(alpha);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                        view.setAlpha(1f);
                }
                return false;
            }
        });
    }


    public static void setMargin(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = null;
        if (view.getLayoutParams() != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(left, top, right, bottom);
        }
    }

    public static ColorStateList createColorStateList(int normal, int pressed, int selected, int focused, int unable) {
        int[] colors = new int[]{pressed,selected, focused, normal, focused, unable, normal};
        int[][] states = new int[7][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_selected, android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[3] = new int[]{android.R.attr.state_enabled};
        states[4] = new int[]{android.R.attr.state_focused};
        states[5] = new int[]{android.R.attr.state_window_focused};
        states[6] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,
                                                int idUnable) {
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);

        return newSelector(normal,pressed,focused,unable);
    }
    public static StateListDrawable newSelector(Drawable normal, Drawable pressed, Drawable focused, Drawable unable){
        StateListDrawable bg = new StateListDrawable();

        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);

        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);

        bg.addState(new int[] { android.R.attr.state_enabled }, normal);

        bg.addState(new int[] { android.R.attr.state_focused }, focused);

        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);

        bg.addState(new int[] {}, normal);
        return bg;
    }

    public static void editTextClearHelper(final EditText editText, final View btnClear){
        btnClear.setVisibility(View.GONE);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().length() > 0) {
                    btnClear.setVisibility(View.VISIBLE);
                } else {
                    btnClear.setVisibility(View.GONE);
                }
            }
        });
    }
    //显示虚拟键盘
    public static void showKeyboard(EditText editText)
    {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        try {
            InputMethodManager inputManager =
                    (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void hideKeyboard(Context context){
        try{
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(
                    InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
