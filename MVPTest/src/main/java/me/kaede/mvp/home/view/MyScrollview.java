package me.kaede.mvp.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Shuai.Li13 on 2017/12/11.
 */

public class MyScrollview extends ScrollView {
    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_DOWN||ev.getAction()==MotionEvent.ACTION_MOVE){
            this.requestDisallowInterceptTouchEvent(false);
            this.requestFocus();
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
