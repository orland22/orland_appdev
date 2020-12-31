package com.example.orland_appdev;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Selection extends BaseActivity {

    EditText txtName, txtSalary, txtCode;
    TextView lblNet;
    Button btnCalculate;
    double dblDeduction, dblSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate variables
        txtName = findViewById(R.id.txtName);
        txtSalary = findViewById(R.id.txtSalary);
        txtCode = findViewById(R.id.txtCode);
        lblNet = findViewById(R.id.lblNet);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Create filter for txtSalary
        txtSalary.setFilters(new InputFilter[]{ new InputFilterMinMaxDouble(1, 100000) });

        // Create filter for txtCode
        txtCode.setFilters(new InputFilter[]{new InputFilterCharacter(getApplicationContext(), "FBKE"), new InputFilter.LengthFilter(1)});

        // Add Watcher to Edit Texts
        txtName.addTextChangedListener(watcher);
        txtSalary.addTextChangedListener(watcher);
        txtCode.addTextChangedListener(watcher);

        // Add Action Listener to Button
        btnCalculate.setOnClickListener(v->{
            dblSalary = Double.parseDouble(txtSalary.getText().toString());
            switch (txtCode.getText().toString().toUpperCase().charAt(0)){
                case 'F':
                    dblDeduction = 0;
                    break;
                case 'B':
                    dblDeduction = 150.65;
                    break;
                case 'K':
                    dblDeduction = 357.85;
                    break;
                case 'E':
                    dblDeduction = 500.50;
                    break;
            }
            DisplayOutput(dblSalary - dblDeduction);
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_string2;
    }

    @Override
    protected String getActivityName() {
        return "Activity 2";
    }

    // Detect if edittext has changed
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            DisplayOutput(0);
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    // Display Output
    private void DisplayOutput(double salary){
        lblNet.setText(String.format("%,3.2f", salary));
    }
}