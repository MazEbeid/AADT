package com.mebeidcreations.aadt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import static android.content.Context.NOTIFICATION_SERVICE;

public class UIFragment extends Fragment {


    public static UIFragment newInstance() {
        return new UIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_ui, container, false);

        final TextView count = view.findViewById(R.id.count_textview);
        Button countButton = view.findViewById(R.id.count_button);
        Button toastButton = view.findViewById(R.id.toast_button);

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c  = Integer.parseInt(count.getText().toString());
                c = c+1;
                count.setText(""+c);
                Log.d("count", ""+c);

            }
        });

        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"The count is "+count.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }




}
