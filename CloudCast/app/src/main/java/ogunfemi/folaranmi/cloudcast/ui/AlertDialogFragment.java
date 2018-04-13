package ogunfemi.folaranmi.cloudcast.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import ogunfemi.folaranmi.cloudcast.R;

/**
 * Created by FOLARANMI on 12/19/2017.
 */

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new  AlertDialog.Builder(context);

        builder.setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.error_ok_button_text, null);
                //null onclicklistener just deletes the dialog

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
