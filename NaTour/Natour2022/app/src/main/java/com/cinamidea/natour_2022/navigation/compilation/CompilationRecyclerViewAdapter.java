package com.cinamidea.natour_2022.navigation.compilation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.Route;

import java.util.ArrayList;

public class CompilationRecyclerViewAdapter extends RecyclerView.Adapter<CompilationRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Route> routes;
    ArrayList<Route> favourite_routes;


    public CompilationRecyclerViewAdapter(Context context, ArrayList<Route> routes) {
        this.context = context;
        this.routes = routes;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.compilation_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        holder.title.setText();
//        holder.description.setText();

        holder.button_open.setOnClickListener(view -> {



        });

    }


    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        Button button_open;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.compilation_title);
            description = itemView.findViewById(R.id.compilation_description);
            button_open = itemView.findViewById(R.id.compilation_open);


        }

    }

}

