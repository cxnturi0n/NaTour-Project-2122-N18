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
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

                String username = edit_user.getText().toString();
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();
                buttonAnimator(button);
                handleSignUp(username, email, password);
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

    private void handleSignUp(String username, String email, String password) {


        String URL_POST= "https://eagwqm6kz0.execute-api.eu-central-1.amazonaws.com/dev/user";

        OkHttpClient client = new OkHttpClient();

        String json = "{\"username\":"+username+",\"email\":"+email+",\"password\":"+password+"}";

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(URL_POST)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //Se metto Toast per qualche motivo crasha, da risolvere
                Log.e("HTTP_ERROR",e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //Qui va stampato il messaggio di avvenuta registrazione : response.body().string()
                //Se metto Toast per qualche motivo crasha, da risolvere
                Log.e("HTTP_RESPONSE",  response.body().string());
            }
        });


    }
}