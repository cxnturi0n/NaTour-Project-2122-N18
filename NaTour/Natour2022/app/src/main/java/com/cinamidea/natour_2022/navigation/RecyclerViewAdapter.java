package com.cinamidea.natour_2022.navigation;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
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
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.chat.ChatUserList;
import com.cinamidea.natour_2022.chat.HomeChatActivity;
import com.cinamidea.natour_2022.map.CreatePathActivity;
import com.cinamidea.natour_2022.map.DetailedMap;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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
        ImageButton favourite;
        ViewGroup options_container;
        ImageButton options;
        ImageButton chat;
        TextView favourites_number;
        ImageView isreported;
        ImageButton open_map;

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
            favourite = itemView.findViewById(R.id.post_favourite);
            options = itemView.findViewById(R.id.post_options);
            isreported = itemView.findViewById(R.id.post_isreported);
            favourites_number = itemView.findViewById(R.id.post_numberoffavourites);
            options_container = itemView.findViewById(R.id.post_options_container);
            chat = itemView.findViewById(R.id.post_chat);
            open_map = itemView.findViewById(R.id.post_map);


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

        SpannableStringBuilder sb = new SpannableStringBuilder(route.getCreator_username() + " " + route.getDescription());
        sb.setSpan(new StyleSpan(Typeface.BOLD), 0, route.getCreator_username().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        holder.username.setText(route.getCreator_username());
        holder.title.setText(route.getName());
        holder.description.setText(sb);
        holder.duration.setText(getFormattedTime(route.getDuration()));
        holder.length.setText(getKm(route.getLength()) + "km");
        holder.difficulty.setText(route.getLevel());
        byte[] image_array = Base64.getDecoder().decode(route.getImage_base64());
        Glide.with(context).load(image_array).into(holder.image);
        holder.favourites_number.setText(route.getLikes() + " likes");


        if (route.isDisability_access())
            holder.handicap.setVisibility(View.VISIBLE);

        if (route.getReport_count() >= 3)
            holder.isreported.setVisibility(View.VISIBLE);

        holder.chat.setOnClickListener(view -> {

            if (SigninFragment.current_username.equals(holder.username.getText())) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

            } else {
                ArrayList<String> members = new ArrayList<>();
                members.add(SigninFragment.current_username);
                members.add(holder.username.getText().toString());
                Log.e("Members",members.toString());
                Intent chat_intent = new Intent(context, HomeChatActivity.class);
                chat_intent.putStringArrayListExtra("members", members);
                context.startActivity(chat_intent);

            }

        });

        holder.open_map.setOnClickListener(view -> {


            Intent map_detail = new Intent(context, DetailedMap.class);
            map_detail.putParcelableArrayListExtra("route",(ArrayList<? extends Parcelable>) route.getCoordinates());
            context.startActivity(map_detail);


        });

        holder.options.setOnClickListener(view -> openMenu(holder.options_container));

        holder.favourite.setOnClickListener(view -> {
            Toast.makeText(context, holder.username.getText().toString(), Toast.LENGTH_LONG).show();

            SharedPreferences sharedPreferences;
            sharedPreferences = context.getSharedPreferences("natour_tokens", MODE_PRIVATE);
            String id_token = sharedPreferences.getString("id_token", null);
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
                            holder.favourite.setBackgroundColor(Color.RED);
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

        return String.valueOf(meters / 1000);

    }

    private String getFormattedTime(int time) {

        int hours = time / 60;
        int minutes = time % 60;

        String formattedTime = hours + "h" + minutes + "m";

        return formattedTime;

    }

    private void openMenu(ViewGroup options_container) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(context).inflate(
                R.layout.post_options, options_container);
        bottomSheetView.findViewById(R.id.post_addtovisit).setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(R.id.post_reportinaccuracy).setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });
        bottomSheetView.findViewById(R.id.post_reportoutofdate).setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

}
