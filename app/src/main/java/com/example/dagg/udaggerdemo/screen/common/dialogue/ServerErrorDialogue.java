package com.example.dagg.udaggerdemo.screen.common.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.dagg.udaggerdemo.R;

public class ServerErrorDialogue extends DialogFragment{

    public static ServerErrorDialogue newInstance() {
        return new ServerErrorDialogue();
    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(R.string.server_error_dialog_title);

        alertDialogBuilder.setMessage(R.string.server_error_dialog_message);

        alertDialogBuilder.setPositiveButton(
            R.string.server_error_dialog_button_caption,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            }
        );

        return alertDialogBuilder.create();

    }
}
