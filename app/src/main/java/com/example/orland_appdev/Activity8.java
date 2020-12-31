package com.example.orland_appdev;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Activity8 extends BaseActivity {

    RadioButton rdoYes, rdoNo, rdoNevermind;
    Button btnSubmit;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_8;
    }

    @Override
    protected String getActivityName() {
        return "Activity 8";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rdoYes = findViewById(R.id.rdoYes);
        rdoNo = findViewById(R.id.rdoNo);
        rdoNevermind = findViewById(R.id.rdoNevermind);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> onSubmit());
    }

    public void onSubmit(){
        String message = "";
        if(rdoYes.isChecked()) message = "Are you kidding me?";
        else if(rdoNo.isChecked()) message = "You are so honest.";
        if(rdoNevermind.isChecked()) message = "The truth shall prevail!";
        MsgBox(message);
    }

    public void rdoIsClicked(View view) {
        RadioButton btn = (RadioButton) view;
        String message = "";
        switch (btn.getId()){
            case R.id.rdoYes:
                if(btn.isChecked()) message = "Amazing!";
                break;
            case R.id.rdoNo:
                if(btn.isChecked()) message = "Wonderful!";
                break;
            case R.id.rdoNevermind:
                if(btn.isChecked()) message = "Okies!";
                break;
        }
        MsgBox(message);
    }
}