package com.example.orland_appdev;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public abstract class DisplayDialog extends AppCompatDialogFragment {

    private String title, message = "", positiveButton, negativeButton;
    public DisplayDialog(String title, String message, String positiveButton, String negativeButton){
        this.title = title;
        this.message = message;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButton, (dialog, which) -> {

        }).setNegativeButton(negativeButton,(dialog, which) -> {

        });
        return builder.create();
    }
}
