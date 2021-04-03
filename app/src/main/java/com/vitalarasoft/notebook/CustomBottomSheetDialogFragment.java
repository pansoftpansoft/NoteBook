package com.vitalarasoft.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private OnDialogListener mDialogListener;

    public static CustomBottomSheetDialogFragment newInstance(){
        return new CustomBottomSheetDialogFragment();
    }

    public void setOnDialogListener(OnDialogListener dialogListener){
        mDialogListener = dialogListener;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        setCancelable(false);

        MaterialButton buttonOk = view.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener((clickedView) -> {
            if (mDialogListener != null){
                mDialogListener.onDialogOk();
            }
            dismiss();
        });

        MaterialButton buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener((clickedView) -> {
            if (mDialogListener != null){
                mDialogListener.onDialogCancel();
            }
            dismiss();
        });
        return view;
    }
}
