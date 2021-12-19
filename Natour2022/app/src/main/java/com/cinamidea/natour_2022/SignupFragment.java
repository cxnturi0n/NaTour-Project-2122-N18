package com.cinamidea.natour_2022;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private Animation scale_up, scale_down;
    private Intent intent;
    private EditText usernameET, emailET, passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.fragmentSignup_signup);
        scale_up = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        usernameET = view.findViewById(R.id.fragmentSignup_username);
        emailET = view.findViewById(R.id.fragmentSignup_email);
        passwordET = view.findViewById(R.id.fragmentSignup_password);

        intent = new Intent(getActivity(), PinActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
                buttonAnimation(button);
            }
        });

    }

    private void buttonAnimation(Button button) {

        button.startAnimation(scale_up);
        button.startAnimation(scale_down);
        button.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        }, 200);

    }

    private void handleSignUp() {

        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String username = usernameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String url = "https://eagwqm6kz0.execute-api.eu-central-1.amazonaws.com/dev/user/"+username;
        //BODY PER LA POST
        //Map<String, String>params = new HashMap<>();
        //params.put("username",username);
        //params.put("email",email);
        //params.put("password",password);
        //JSONObject body = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

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