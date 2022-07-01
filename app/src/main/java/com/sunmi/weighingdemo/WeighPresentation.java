package com.sunmi.weighingdemo;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

/**
 * 副屏展示
 */
public class WeighPresentation extends Presentation {

    public WeighPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    public WeighPresentation(Context outerContext, Display display, int theme) {
        super(outerContext, display, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_presentation);
    }
}
