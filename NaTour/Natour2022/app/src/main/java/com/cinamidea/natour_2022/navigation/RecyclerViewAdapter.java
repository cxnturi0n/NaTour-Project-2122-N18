package com.cinamidea.natour_2022.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Base64;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Route> routes;

    public /*static */ class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView title, description;
        TextView difficulty;
        ImageView image;
        TextView duration, length;
        ImageView handicap;
        ImageButton like, favourite, tovisit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.post_username);
            title = itemView.findViewById(R.id.post_title);
            description = itemView.findViewById(R.id.post_description);
            difficulty = itemView.findViewById(R.id.post_difficulty);
            image = itemView.findViewById(R.id.post_image);
            duration = itemView.findViewById(R.id.post_duration);
            length = itemView.findViewById(R.id.post_length);
            handicap = itemView.findViewById(R.id.post_handicap);
            like = itemView.findViewById(R.id.post_like);
            favourite = itemView.findViewById(R.id.post_favourite);
            tovisit = itemView.findViewById(R.id.post_tovisit);


        }

    }

    public RecyclerViewAdapter(Context context, ArrayList<Route> routes) {
        this.context = context;
        this.routes = routes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.post, parent, false);
        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Route route = routes.get(position);
        holder.username.setText(route.getCreator_username());
        holder.title.setText(route.getName());
        holder.description.setText(route.getDescription());
        holder.duration.setText(getFormattedTime(route.getDuration()));
        holder.length.setText(getKm(route.getLength()));
        holder.difficulty.setText(route.getLevel());
        byte[] image_array = Base64.getDecoder().decode(route.getImage_base64());
        Glide.with(context).load(image_array).into(holder.image);

        if (route.isDisability_access())
            holder.handicap.setVisibility(View.VISIBLE);


        holder.like.setOnClickListener(view -> {
            Toast.makeText(context, holder.username.getText().toString(), Toast.LENGTH_LONG).show();

            SharedPreferences sharedPreferences;
            sharedPreferences = context.getSharedPreferences("natour_tokens", MODE_PRIVATE);
            String id_token=sharedPreferences.getString("id_token", null);
            String user_type;
            if (id_token != null)
                user_type = "Cognito";
            else {
                id_token = context.getSharedPreferences("google_tokens", MODE_PRIVATE).getString("id_token", null);
                user_type = "Google";
            }
            RoutesHTTP.addLike(user_type, route, id_token, new RoutesCallback() {
                @Override
                public void handleStatus200(String response) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.like.setBackgroundColor(Color.RED);
                        }
                    });

                }

                @Override
                public void handleStatus400(String response) {

                }

                @Override
                public void handleStatus401(String response) {

                }

                @Override
                public void handleStatus500(String response) {

                }

                @Override
                public void handleRequestException(String message) {

                }
            });

        });




    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    private String getKm(float meters) {

        return String.valueOf(meters/1000);

    }

    private String getFormattedTime(int time) {

        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;

        String formattedTime = hours + "h" + minutes + "m";

        return formattedTime;

    }

}
