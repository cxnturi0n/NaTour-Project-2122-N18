package com.cinamidea.natour_2022.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.cinamidea.natour_2022.chat.HomeChatActivity;
import com.cinamidea.natour_2022.map.DetailedMap;
import com.cinamidea.natour_2022.routes_callbacks.RoutesCallback;
import com.cinamidea.natour_2022.routes_util.Route;
import com.cinamidea.natour_2022.routes_util.RoutesHTTP;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Base64;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Route> routes;
    ArrayList<Route> favourite_routes;
    private boolean is_favourite_fragment = false;
    private boolean is_tovisit_fragment = false;

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


    public RecyclerViewAdapter(Context context, ArrayList<Route> routes, boolean is_favourite_fragment) {
        this.context = context;
        this.routes = routes;
        this.is_favourite_fragment = is_favourite_fragment;
    }

    public RecyclerViewAdapter(Context context, ArrayList<Route> routes, ArrayList<Route> favourite_routes, boolean is_tovisit_fragment) {
        this.context = context;
        this.routes = routes;
        this.favourite_routes = favourite_routes;
        this.is_tovisit_fragment = is_tovisit_fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.post, parent, false);
        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupHolder(MyViewHolder holder, Route route) {

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

        if (holder.username.getText().toString().equals(SigninFragment.current_username))
            holder.chat.setVisibility(View.GONE);

        if (route.isDisability_access())
            holder.handicap.setVisibility(View.VISIBLE);

        if (route.getReport_count() >= 3)
            holder.isreported.setVisibility(View.VISIBLE);

        if(is_favourite_fragment==true) {

            holder.favourite.setImageResource(R.drawable.ic_liked);
            holder.favourite.setTag(1);

        }

        if(favourite_routes!=null) {

            for (Route r : favourite_routes) {

                if (route.getName().equals(r.getName())) {
                    holder.favourite.setImageResource(R.drawable.ic_liked);
                    holder.favourite.setTag(1);
                }

            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Route route = routes.get(position);
        setupHolder(holder, route);

        holder.chat.setOnClickListener(view -> {

            ArrayList<String> members = new ArrayList<>();
            members.add(SigninFragment.current_username);
            members.add(holder.username.getText().toString());
            Intent chat_intent = new Intent(context, HomeChatActivity.class);
            chat_intent.putStringArrayListExtra("members", members);
            context.startActivity(chat_intent);

        });

        holder.open_map.setOnClickListener(view -> {

            Intent map_detail = new Intent(context, DetailedMap.class);
            map_detail.putParcelableArrayListExtra("route", (ArrayList<? extends Parcelable>) route.getCoordinates());
            context.startActivity(map_detail);

        });

        holder.options.setOnClickListener(view -> {

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
            openMenu(holder.options_container, holder, user_type, route, id_token, position);

        });

        holder.favourite.setOnClickListener(view -> {

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

            if (holder.favourite.getTag().equals(1)) {
                deleteFavourite(holder, user_type, route, id_token, position);
                Log.e("Tag1", "1");
            }
            else {
                insertFavourite(holder, user_type, route, id_token);
                Log.e("Tag2", "2");
            }


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

    private void openMenu(ViewGroup options_container, MyViewHolder holder, String user_type, Route route, String id_token, int position) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(context).inflate(
                R.layout.post_options, options_container);
        if(is_tovisit_fragment)
        ((Button)bottomSheetView.findViewById(R.id.post_addtovisit)).setText("Prova");

        bottomSheetView.findViewById(R.id.post_addtovisit).setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
            if(is_tovisit_fragment)
                removeToVisit(holder, user_type, route, id_token, position);
            else
                insertToVisit(holder, user_type, route, id_token);

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

    private void insertFavourite(MyViewHolder holder, String user_type, Route route, String id_token) {

        holder.favourite.setImageResource(R.drawable.ic_liked);
        RoutesHTTP.insertFavouriteRoute(user_type, route.getName(), SigninFragment.current_username, id_token, new RoutesCallback() {

            @Override
            public void handleStatus200(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setTag(1);
                });

            }

            @Override
            public void handleStatus400(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_like);
                });
            }

            @Override
            public void handleStatus401(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_like);
                });
            }

            @Override
            public void handleStatus500(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_like);
                });
            }

            @Override
            public void handleRequestException(String message) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_like);
                });
            }
        });

    }

    private void deleteFavourite(MyViewHolder holder, String user_type, Route route, String id_token, int position) {

        holder.favourite.setImageResource(R.drawable.ic_like);
        RoutesHTTP.deleteFavouriteRoute(user_type, SigninFragment.current_username, id_token, route.getName(), new RoutesCallback() {
            @Override
            public void handleStatus200(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setTag(0);
                    if(is_favourite_fragment) {
                        routes.remove(route);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, routes.size());
                        holder.itemView.setVisibility(View.GONE);

                    }
                });
            }

            @Override
            public void handleStatus400(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_liked);
                });
            }

            @Override
            public void handleStatus401(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_liked);
                });
            }

            @Override
            public void handleStatus500(String response) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_liked);
                });
            }

            @Override
            public void handleRequestException(String message) {
                ((Activity) context).runOnUiThread(() -> {
                    holder.favourite.setImageResource(R.drawable.ic_liked);
                });
            }
        });

    }

    private void insertToVisit(MyViewHolder holder, String user_type, Route route, String id_token) {

        RoutesHTTP.insertToVisitRoute(user_type, route.getName(), SigninFragment.current_username, id_token, new RoutesCallback() {
            @Override
            public void handleStatus200(String response) {

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

    }

    private void removeToVisit(MyViewHolder holder, String user_type, Route route, String id_token, int position) {

        RoutesHTTP.deleteToVisitRoute(user_type, SigninFragment.current_username, id_token, route.getName(), new RoutesCallback() {
            @Override
            public void handleStatus200(String response) {

                ((Activity)context).runOnUiThread(() -> {

                    routes.remove(route);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, routes.size());
                    holder.itemView.setVisibility(View.GONE);

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

    }

}
