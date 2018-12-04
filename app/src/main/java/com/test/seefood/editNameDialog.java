package com.test.seefood;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class editNameDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    EditText name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.imageinflator_dialog, container, false);

        //attach to the button and name field in the popup dialog.
        Button submit = v.findViewById(R.id.submit);
        name = v.findViewById(R.id.editText);

        name.setHint("Name");

        //emit the new name of the image on button click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(name.getText().toString());
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    /**
     * attach the editName Dialog to a listener to call on button clicked
     * @return
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (editNameDialog.BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
