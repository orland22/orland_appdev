package com.example.orland_appdev;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterUniqueInteger implements InputFilter {
    private int min, max, array[];

    public InputFilterUniqueInteger(int min, int max, int[] array) {
        this.min = min;
        this.max = max;
        this.array = array;

    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input) && isUnique(input)){
                return null;
            }
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    private boolean isUnique(int num){
        for (int i = 0; i < array.length; i++) {
            if (array[i] == num) return false;
        }
        return true;
    }
}