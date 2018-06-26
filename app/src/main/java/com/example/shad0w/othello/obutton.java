package com.example.shad0w.othello;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;


public class obutton extends android.support.v7.widget.AppCompatButton {
    private int value;
    boolean empty;
    int r;
    int c;
    boolean click;


    public obutton(Context context) {
        super(context);
    }

    public void setValue(int value) {
        this.value = value;
        setColor(value);
    }

    public int getValue() {
        return value;
    }

    public void setColor(int i) {
        if (i == 0 && click) {
            setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable, null));
        } else if (i == 0) {
            setBackgroundDrawable(getResources().getDrawable(R.drawable.blank, null));
        } else if (i == -1) {
            setBackgroundDrawable(getResources().getDrawable(R.drawable.black, null));
        } else if (i == 1) {
            setBackgroundDrawable((getResources().getDrawable(R.drawable.white, null)));
        }
    }
}

