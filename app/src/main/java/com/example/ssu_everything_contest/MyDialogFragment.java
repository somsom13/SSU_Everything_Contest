package com.example.ssu_everything_contest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class MyDialogFragment extends DialogFragment {

    private Fragment fragment;
    String result;
    public MyDialogFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_fragment,container,false);
        view.bringToFront();
        TextView DialogText=(TextView) view.findViewById(R.id.dialogText);
        Bundle args=getArguments();
        result=args.getString("answerResult");
        Log.v("checkGame","got result: "+result);
        if(result.equals("correct")){
            DialogText.setText("+10p");
        }else{
            DialogText.setText("-3P");
        }

        onCreateDialog(args);

        fragment=getActivity().getSupportFragmentManager().findFragmentByTag("test");

        if(fragment!=null){
            DialogFragment dialogFragment=(DialogFragment) fragment;
            dialogFragment.dismiss();
        }

        return view;
    }

}
