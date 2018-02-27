package com.geely.mars.mvpdemo.activity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.lang.reflect.Field;

public class OverLineTextView extends TextView {
    private boolean isOverSize;
    private OnOverLineChangerListener listener;

    public OverLineTextView(Context context) {
        super(context);
        init();
    }

    public OverLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        // invalidate when layout end
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    listener.onOverLine(checkOverLine());
                }
            }
        });

    }

    public boolean isOverSize() {
        return isOverSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (listener != null) {
            listener.onOverLine(checkOverLine());
        }
    }

    public boolean checkOverLine() {
        //获取当前textview的行数
        int maxLine = getLineCount();
        try {
            Field field = getClass().getSuperclass().getDeclaredField("mLayout");
            field.setAccessible(true);
            Layout mLayout = (Layout) field.get(this);
            if (mLayout == null) {
                return false;
            }
            //mLayout.getEllipsisCount获取textView对应行数索引出的省略的字符数,没有省略返回0
            isOverSize = mLayout.getEllipsisCount(maxLine - 1) > 0 ? true : false;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return isOverSize;
    }
    interface OnOverLineChangerListener{
        void onOverLine(boolean inOverLine);
    }
    public void setOnOverLineChangerListener(OnOverLineChangerListener listener){
        this.listener=listener;
    }
}
