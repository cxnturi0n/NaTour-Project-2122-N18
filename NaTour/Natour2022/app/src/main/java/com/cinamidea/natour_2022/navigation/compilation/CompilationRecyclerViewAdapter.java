package com.cinamidea.natour_2022.navigation.compilation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.signin.SigninFragment;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.utilities.UserType;
import com.cinamidea.natour_2022.utilities.auth.UserSharedPreferences;
import com.cinamidea.natour_2022.utilities.http.RoutesHTTP;
import com.cinamidea.natour_2022.utilities.http.callbacks.routes.InsertRouteInCompilationCallback;

import java.util.ArrayList;

public class CompilationRecyclerViewAdapter extends RecyclerView.Adapter<CompilationRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<RoutesCompilation> routecompilations;
    String extra;
    boolean is_insert = false;

    public CompilationRecyclerViewAdapter(Context context, ArrayList<RoutesCompilation> routecompilations, String extra) {
        this.context = context;
        this.routecompilations = routecompilations;
        this.extra = extra;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.compilation_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RoutesCompilation routecompilation;

        routecompilation = routecompilations.get(position);

        holder.title.setText(routecompilation.getTitle());
        holder.description.setText(routecompilation.getDescription());



        holder.button_open.setOnClickListener(view -> {


            if(is_insert) {
                UserSharedPreferences userSharedPreferences = new UserSharedPreferences(context);
                String id_token = userSharedPreferences.getUser_type() + userSharedPreferences.getId_token();
                new RoutesHTTP().insertRouteIntoCompilation(new UserType(context).getUsername(), extra, routecompilation.getId(), id_token, new InsertRouteInCompilationCallback((Activity) context));

            }else {

                Intent intent = new Intent(context, RoutesInCompilationActivity.class);
                intent.putExtra("id", routecompilation.getId());
                context.startActivity(intent);

            }

        });

    }


    @Override
    public int getItemCount() {
        return routecompilations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        Button button_open;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.compilation_title);
            description = itemView.findViewById(R.id.compilation_description);
            button_open = itemView.findViewById(R.id.compilation_open);

            if(extra!=null) {

                button_open.setText(R.string.select);
                is_insert = true;

            }



        }

    }

}

