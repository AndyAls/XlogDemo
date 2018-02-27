package com.geely.mars.mvpdemo.activity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;


public class OverLineEdixView extends android.support.v7.widget.AppCompatEditText {
    private OnOverLineChangerListener listener;
    private AppCompatEditText normalStatu;

    public OverLineEdixView(Context context) {
        super(context);
        init();
    }


    public OverLineEdixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverLineEdixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        normalStatu=this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener!=null){
                    listener.onOverLine(isOverLine());
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (listener!=null){
            listener.onOverLine(isOverLine());
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean isOverLine() {
        int lineCount = getLineCount();
        int maxLines = getMaxLines();
        return lineCount > maxLines;
    }

    interface OnOverLineChangerListener{
        void onOverLine(boolean inOverLine);
    }
    public void setOnOverLineChangerListener(OnOverLineChangerListener listener){
        this.listener=listener;
    }

}
