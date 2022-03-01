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

import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.entities.RoutesCompilation;
import com.cinamidea.natour_2022.navigation.compilation.contracts.CompilationRecyclerContract;
import com.cinamidea.natour_2022.navigation.compilation.models.CompilationRecyclerModel;
import com.cinamidea.natour_2022.navigation.compilation.presenters.CompilationRecyclerPresenter;
import com.cinamidea.natour_2022.navigation.compilation.views.CompilationRoutesActivity;
import com.cinamidea.natour_2022.utilities.UserType;
import java.util.ArrayList;

public class CompilationRecyclerAdapter extends RecyclerView.Adapter<CompilationRecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<RoutesCompilation> routecompilations;
    String extra;
    boolean is_insert = false;

    public CompilationRecyclerAdapter(Context context, ArrayList<RoutesCompilation> routecompilations, String extra) {
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

        holder.presenter = new CompilationRecyclerPresenter(holder, new CompilationRecyclerModel());

        RoutesCompilation routecompilation;

        routecompilation = routecompilations.get(position);

        holder.title.setText(routecompilation.getTitle());
        holder.description.setText(routecompilation.getDescription());



        holder.button_open.setOnClickListener(view -> {


            if(is_insert) {
                UserType user_type = new UserType(context);
                String id_token = user_type.getUserType() + user_type.getIdToken();

                holder.presenter.insertIntoCompilationButtonClicked(user_type.getUsername(), extra, routecompilation.getId(), id_token);


            }else {

                Intent intent = new Intent(context, CompilationRoutesActivity.class);
                intent.putExtra("id", routecompilation.getId());
                context.startActivity(intent);

            }

        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return routecompilations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements CompilationRecyclerContract.View {

        TextView title, description;
        Button button_open;
        CompilationRecyclerContract.Presenter presenter;

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

        @Override
        public void addedToCompilation() {
            ((Activity)context).runOnUiThread(() -> ((Activity)context).finish());
        }

        @Override
        public void displayError(String message) {
            //TODO TOAST
        }

        @Override
        public void logOutUnauthorizedUser() {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}

