package com.cinamidea.natour_2022.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.routes_util.Route;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Route route = routes.get(position);
        holder.username.setText(route.getCreator_username());
        holder.title.setText(route.getName());
        holder.description.setText(route.getDescription());
        holder.difficulty.setText(route.getLevel());

        if(route.isDisability_access())
            holder.handicap.setVisibility(View.VISIBLE);

        holder.like.setOnClickListener(view -> Toast.makeText(context, holder.username.getText().toString(), Toast.LENGTH_LONG).show());


    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

}
