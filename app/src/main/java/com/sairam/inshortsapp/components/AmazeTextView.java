package com.sairam.inshortsapp.components;

/**
 * Created by rajan.kali on 16/06/16.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import com.sairam.inshortsapp.R;

public class AmazeTextView extends android.support.v7.widget.AppCompatTextView {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    public String FontsRootPath = "fonts/";
    public String font;
    public boolean shadow, underline;
    private Context context;

    public AmazeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.AmazeTextView);

        underline = attributeArray.getBoolean(R.styleable.AmazeTextView_underline, false);

        if (underline) {
            String underlinedText = "<u>" + this.getText().toString() + "</u>";
            this.setText(Html.fromHtml(underlinedText));
        }

        shadow = attributeArray.getBoolean(R.styleable.AmazeTextView_shadow, false);
        if (shadow) {
            this.setShadowLayer(2, 1, 1, Color.DKGRAY);
        }

        font = attributeArray.getString(R.styleable.AmazeTextView_font);
        if (font != null && font.trim().length() > 0 && !font.equalsIgnoreCase("none")) {
            Typeface customFont = selectTypeface(context, font);
            setTypeface(customFont);
        }
        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName) {
        try {
            return getFontWithName(context, fontName);
        } catch (Exception e) {
            return null;
        }
    }

    public Typeface getFontWithName(Context context, String fontName) {
        String assetPath = FontsRootPath + fontName + ".ttf";
        return Typeface.createFromAsset(context.getAssets(), assetPath);
    }

    public String getFontsRootPath() {
        return FontsRootPath;
    }

    public void setFontsRootPath(String fontsRootPath) {
        FontsRootPath = fontsRootPath;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
        if (font != null && font.trim().length() > 0 && !font.equalsIgnoreCase("none")) {
            Typeface customFont = selectTypeface(context, font);
            setTypeface(customFont);
        } else {
            setTypeface(Typeface.DEFAULT);
        }
    }

    public boolean hasShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
        if (shadow) {
            this.setShadowLayer(2, 1, 1, Color.DKGRAY);
        } else {
            this.setShadowLayer(0, 0, 0, Color.parseColor("#00000000"));
        }
    }

    public boolean isUnderlined() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
        String formattedText = this.getText().toString();
        if (underline) {
            formattedText = "<u>" + this.getText().toString() + "</u>";
            ;
        }
        this.setText(Html.fromHtml(formattedText));
    }
}
