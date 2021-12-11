package com.cinamidea.natour;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SignupFragment extends Fragment {

    //TO-DO Non far andare a capo nelle edittext se si preme invio su tastiera android
    //TO-DO Sistemare il textview di Terms & Privacy Policy
    //TO-DO Sistemare il logo e controllare le dimensioni

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }



}