package com.miketucker.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

public class LineEditText extends AppCompatEditText {

    private Rect rect;
    private Paint paint;

    // create lines on note

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(0xFFFFD966);
    }


    // determine height and number of lines, add line to the bottom of each

    @Override
    protected void onDraw(Canvas canvas) {

        int height = ((View)this.getParent()).getHeight();

        int lineHeight = getLineHeight();

        int numberOfLines = height / lineHeight;

        Rect r = rect;
        Paint p = paint;

        int baseline = getLineBounds(0, r);

        for(int i = 0; i < numberOfLines; i++){

            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, p);

            baseline += lineHeight;
        }

        super.onDraw(canvas);
    }
}
