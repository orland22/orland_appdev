package com.example.orland_appdev;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleCalculator extends BaseActivity {
    //Declare Variables
    EditText etNum1, etNum2;
    Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnRemainder, btnClose;
    TextView tvResult;
    private static int num1, num2;
    private static String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate Components
        tvResult = findViewById(R.id.tvResult);
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnRemainder = findViewById(R.id.btnRemainder);
        btnClose = findViewById(R.id.btnClose);
        // Add Action Listener
        btnAdd.setOnClickListener(v->add());
        btnSubtract.setOnClickListener(v->subtract());
        btnMultiply.setOnClickListener(v->multiply());
        btnDivide.setOnClickListener(v->divide());
        btnRemainder.setOnClickListener(v->remainder());
        btnClose.setOnClickListener(v->close());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple_calculator;
    }

    @Override
    protected String getActivityName() {
        return "Activity 1";
    }

    private void add(){
        num1 = Integer.parseInt(etNum1.getText().toString());
        num2 = Integer.parseInt(etNum2.getText().toString());
        message = "The sum is " + (num1+num2);
//        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        tvResult.setText(message);
    }

    private void subtract(){
        num1 = Integer.parseInt(etNum1.getText().toString());
        num2 = Integer.parseInt(etNum2.getText().toString());
        message = "The difference is " + (num1-num2);
//        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        tvResult.setText(message);
    }

    private void multiply(){
        num1 = Integer.parseInt(etNum1.getText().toString());
        num2 = Integer.parseInt(etNum2.getText().toString());
        message = "The product is " + (num1*num2);
//        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        tvResult.setText(message);
    }

    private void divide(){
        num1 = Integer.parseInt(etNum1.getText().toString());
        num2 = Integer.parseInt(etNum2.getText().toString());
        message = "The quotient is " + (num1/num2);
//        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        tvResult.setText(message);
    }

    private void remainder(){
        num1 = Integer.parseInt(etNum1.getText().toString());
        num2 = Integer.parseInt(etNum2.getText().toString());
        message = "The remainder is " + (num1%num2);
//        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        tvResult.setText(message);
    }

    private void close(){
        finish();
        System.exit(0);
    }
}