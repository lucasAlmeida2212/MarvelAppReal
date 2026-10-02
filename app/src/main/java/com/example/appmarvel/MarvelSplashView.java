package com.example.appmarvel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

public class MarvelSplashView extends View {

    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private long startTime;

    public MarvelSplashView(Context context) {
        super(context);
        init();
    }

    public MarvelSplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarvelSplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        p.setStrokeCap(Paint.Cap.SQUARE);
        startTime = SystemClock.uptimeMillis();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);

        float w = getWidth();
        float h = getHeight();

        if (w <= 0 || h <= 0) return;

        float cx = w * 0.5f;
        float cy = h * 0.48f;
        float min = Math.min(w, h);
        float t = (SystemClock.uptimeMillis() - startTime) / 1000f;

        drawGrid(c, w, h, t);
        drawGlow(c, cx, cy, min, t);
        drawRings(c, cx, cy, min, t);
        drawTicks(c, cx, cy, min, t);
        drawCrosshair(c, cx, cy, min, t);
        drawOrbitArcs(c, cx, cy, min, t);

        postInvalidateOnAnimation();
    }

    private void drawGlow(Canvas c, float cx, float cy, float min, float t) {
        float pulse = 0.88f + 0.12f * (float)Math.sin(t * 2.0);
        p.setStyle(Paint.Style.FILL);
        p.setShader(new RadialGradient(cx, cy, min * .30f,
                new int[]{0x3000F0FF, 0x1200F0FF, 0x0000F0FF},
                new float[]{0f, .45f, 1f}, Shader.TileMode.CLAMP));
        c.drawCircle(cx, cy, min * .30f * pulse, p);
        p.setShader(null);
    }

    private void drawRings(Canvas c, float cx, float cy, float min, float t) {
        float r1 = min * (.255f + .008f * (float)Math.sin(t));
        float r2 = min * (.285f + .012f * (float)Math.cos(t * .8f));
        float r3 = min * .335f;

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1.4f);
        p.setColor(0x6600F0FF);
        p.setShadowLayer(10, 0, 0, 0xAA00F0FF);
        c.drawCircle(cx, cy, r1, p);
        p.setShadowLayer(7, 0, 0, 0x8800F0FF);
        c.drawCircle(cx, cy, r2, p);
        p.clearShadowLayer();

        p.setStrokeWidth(.8f);
        p.setColor(0x3000F0FF);
        c.drawCircle(cx, cy, r3, p);

        p.setColor(0x2500F0FF);
        c.drawCircle(cx, cy, min * .19f, p);
    }

    private void drawTicks(Canvas c, float cx, float cy, float min, float t) {
        float r = min * .315f;
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1.3f);
        p.setColor(0xB800F0FF);

        for (int i = 0; i < 72; i++) {
            double a = Math.toRadians(i * 5.0 + t * 9.0);
            float len = (i % 6 == 0) ? 10f : 5f;
            float x1 = cx + (float)Math.cos(a) * r;
            float y1 = cy + (float)Math.sin(a) * r;
            float x2 = cx + (float)Math.cos(a) * (r + len);
            float y2 = cy + (float)Math.sin(a) * (r + len);
            c.drawLine(x1, y1, x2, y2, p);
        }
    }

    private void drawCrosshair(Canvas c, float cx, float cy, float min, float t) {
        float r = min * .20f;
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1f);
        p.setColor(0x5500F0FF);

        c.drawLine(cx-r, cy, cx+r, cy, p);
        c.drawLine(cx, cy-r, cx, cy+r, p);

        float s = 7f;
        p.setColor(0xAA00F0FF);
        c.drawLine(cx-r-s, cy-r, cx-r, cy-r, p);
        c.drawLine(cx-r, cy-r, cx-r, cy-r-s, p);
        c.drawLine(cx+r, cy-r, cx+r+s, cy-r, p);
        c.drawLine(cx+r, cy-r, cx+r, cy-r-s, p);
        c.drawLine(cx-r-s, cy+r, cx-r, cy+r, p);
        c.drawLine(cx-r, cy+r, cx-r, cy+r+s, p);
        c.drawLine(cx+r, cy+r, cx+r+s, cy+r, p);
        c.drawLine(cx+r, cy+r, cx+r, cy+r+s, p);
    }

    private void drawOrbitArcs(Canvas c, float cx, float cy, float min, float t) {
        float r = min * .335f;
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2f);
        p.setColor(0xAA00F0FF);
        p.setShadowLayer(9, 0, 0, 0x8800F0FF);

        c.drawArc(cx-r, cy-r, cx+r, cy+r, t*35f, 62f, false, p);
        c.drawArc(cx-r, cy-r, cx+r, cy+r, 180f-t*24f, 35f, false, p);
        c.drawArc(cx-r, cy-r, cx+r, cy+r, 285f+t*18f, 24f, false, p);

        p.clearShadowLayer();
    }

    private void drawGrid(Canvas c, float w, float h, float t) {
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1.2f);
        p.setColor(0x1800F0FF);
        float cellSize = 42f * getResources().getDisplayMetrics().density;
        for (float x = 0; x < w; x += cellSize) c.drawLine(x, 0, x, h, p);
        for (float y = 0; y < h; y += cellSize) c.drawLine(0, y, w, y, p);
    }
}

