package com.example.orland_appdev;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

public class InputFilterCharacter implements InputFilter {
    private char[] chars;
    private String original, addToMessage;
    private Context context;

    public InputFilterCharacter(Context context, String chars){
        this.context = context;
        original = chars.toUpperCase();
        this.chars = original.toCharArray();
        addToMessage = "";
        for(int i=0; i<chars.length(); i++){
            if(i != chars.length()-1){
                addToMessage += this.chars[i] + ", ";
            }else{
                addToMessage += "or "+this.chars[i];
            }
        }
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        source = source.toString().toUpperCase();
        for (int i=start; i < end; i++){
            if(!original.contains(source.charAt(i)+"")){
                String message = "Invalid code. Only allowed codes are "+addToMessage+".";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                return "";
            }
        }
        return null;
    }
}
