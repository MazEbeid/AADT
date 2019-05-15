package com.mebeidcreations.aadt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

        return view;
    }




}
