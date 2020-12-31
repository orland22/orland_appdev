package com.example.orland_appdev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    TextView viewUser, viewPass;
    EditText txtUser, txtPass;
    Button btnProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Java-based UI
        /**LinearLayout ll = new LinearLayout(this);
        Button btnOk = new Button(this);
        btnOk.setText("OK");
        ll.addView(btnOk);
        setContentView(ll);
         */

        //Instantiate and Bind
        viewUser = findViewById(R.id.viewUser);
        viewPass = findViewById(R.id.viewPass);
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnProcess = findViewById(R.id.btnProcess);

        //Process
        btnProcess.setOnClickListener(v -> click()); //Use Lambda expression - v can be any variable name for View
    }

    // Create Click Method for Button Process
    public void click() {
        String user = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        if(user.equals("admin") && pass.equals("admin")){
            Toast.makeText(getApplicationContext(), "You have successfully LOGGED IN!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "You have FAILED SUCCESSFULLY!", Toast.LENGTH_LONG).show();
        }
    }
}
