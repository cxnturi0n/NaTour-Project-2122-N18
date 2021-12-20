package com.cinamidea.natour_2022;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {

    private Button button;
    private Animation anim_scale_up, anim_scale_down;
    private Intent intent;
    private EditText edit_user;
    private EditText edit_email;
    private EditText edit_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Identifica le varie componenti assegnandole.
        setupViewComponents(view);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonAnimator(button);
//                handleSignUp();
                runIntent(intent);

            }
        });

    }

    private void setupViewComponents(View view) {

        button = view.findViewById(R.id.fragmentSignup_signup);
        anim_scale_up = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        anim_scale_down = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        edit_user = view.findViewById(R.id.fragmentSignup_username);
        edit_email = view.findViewById(R.id.fragmentSignup_email);
        edit_password = view.findViewById(R.id.fragmentSignup_password);

        intent = new Intent(getActivity(), PinActivity.class);

    }

    private void buttonAnimator(Button button) {

        button.startAnimation(anim_scale_up);
        button.startAnimation(anim_scale_down);

    }

    private void runIntent(Intent intent) {

        intent.putExtra("email", edit_email.getText().toString());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        },170);

    }

    private JSONObject getPostRequest() {

        String username = edit_user.getText().toString();
        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();

        Map<String, String>params = new HashMap<>();

        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

        JSONObject body = new JSONObject(params);

        return body;

    }

    private void handleSignUp() {

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = "";
        JSONObject body = getPostRequest();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("OK",response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR1",error.getMessage());
                    }
                });

        Volley.newRequestQueue(this.getContext()).add(jsonObjectRequest);
    }
}