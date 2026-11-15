package com.example.quizmania.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SubjectBarChartView extends View {

    private final Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bgBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Map<String, Integer> data = new LinkedHashMap<>();

    public SubjectBarChartView(Context context) {
        super(context);
        init();
    }

    public SubjectBarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubjectBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        barPaint.setColor(Color.parseColor("#2563EB")); // Primary Blue
        bgBarPaint.setColor(Color.parseColor("#EFF6FF")); // Light Blue track

        textPaint.setColor(Color.parseColor("#64748B")); // Secondary Slate
        textPaint.setTextSize(spToPx(12));
        textPaint.setTextAlign(Paint.Align.LEFT);

        valuePaint.setColor(Color.parseColor("#0F172A")); // Dark Slate
        valuePaint.setTextSize(spToPx(13));
        valuePaint.setFakeBoldText(true);
        valuePaint.setTextAlign(Paint.Align.RIGHT);
    }

    public void setData(Map<String, Integer> subjectAccuracyMap) {
        data.clear();
        if (subjectAccuracyMap != null && !subjectAccuracyMap.isEmpty()) {
            data.putAll(subjectAccuracyMap);
        } else {
            // Default empty placeholders
            data.put("Java", 0);
            data.put("Python", 0);
            data.put("DBMS", 0);
            data.put("Data Structures", 0);
        }
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = Math.max(data.size(), 1);
        int itemHeight = dpToPx(56);
        int desiredHeight = count * itemHeight + dpToPx(16);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, resolveSize(desiredHeight, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data.isEmpty()) return;

        int padding = dpToPx(12);
        int availableWidth = getWidth() - (padding * 2);
        int barHeight = dpToPx(14);
        int itemSpacing = dpToPx(56);

        int index = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int yOffset = padding + (index * itemSpacing);

            // Draw subject label
            String subject = entry.getKey();
            canvas.drawText(subject, padding, yOffset + dpToPx(14), textPaint);

            // Draw percentage text
            int percent = Math.min(Math.max(entry.getValue(), 0), 100);
            String valueText = percent + "%";
            canvas.drawText(valueText, getWidth() - padding, yOffset + dpToPx(14), valuePaint);

            // Draw background track
            float barTop = yOffset + dpToPx(24);
            float barBottom = barTop + barHeight;
            float barLeft = padding;
            float barRight = getWidth() - padding;
            RectF bgRect = new RectF(barLeft, barTop, barRight, barBottom);
            canvas.drawRoundRect(bgRect, barHeight / 2f, barHeight / 2f, bgBarPaint);

            // Draw filled progress bar
            float filledRight = barLeft + ((barRight - barLeft) * (percent / 100f));
            if (filledRight > barLeft) {
                RectF fillRect = new RectF(barLeft, barTop, filledRight, barBottom);
                canvas.drawRoundRect(fillRect, barHeight / 2f, barHeight / 2f, barPaint);
            }

            index++;
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private float spToPx(int sp) {
        return sp * getResources().getDisplayMetrics().scaledDensity;
    }
}
