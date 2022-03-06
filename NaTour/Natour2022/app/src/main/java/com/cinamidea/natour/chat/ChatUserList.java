package com.cinamidea.natour.chat;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.cinamidea.natour.R;

import java.util.Arrays;
import java.util.List;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.api.models.FilterObject;
import io.getstream.chat.android.client.api.models.QueryUsersRequest;
import io.getstream.chat.android.client.models.Filters;
import io.getstream.chat.android.client.models.User;

public class ChatUserList extends AppCompatActivity {

    ChatClient client = ChatClient.instance();
    ListView userListView;
    TextView username_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userListView = findViewById(R.id.list);
        username_list = findViewById(R.id.username_list_users);

        queryAllUsers();

        userListView.setOnItemClickListener((parent, view, position, id) -> {
            String channelType = "messaging";
            User item = (User) parent.getItemAtPosition(position);
            List<String> members = Arrays.asList(client.getCurrentUser().getId(), item.getId());

            client.createChannel(channelType, members).enqueue(result -> {
                if (result.isSuccess()) {
                    Intent intent = new Intent(ChatUserList.this, HomeChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // Handle result.error()
                    Log.e(TAG, "ChatUserList: Errore creazione canale");
                }
            });


        });

        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(ChatUserList.this, HomeChatActivity.class)));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty())
                    cercaUtente(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        return true;
    }

    private void cercaUtente(String query) {

        FilterObject filter = Filters.in("name", query);
        int offset = 0;
        int limit = 100;
        QueryUsersRequest request = new QueryUsersRequest(filter, offset, limit);

        client.queryUsers(request).enqueue(result -> {
            if (result.isSuccess()) {
                List<User> users = result.data();
                ChatUserAdapter adapter = new ChatUserAdapter(this, users);
                userListView.setAdapter(adapter);


            } else {
                // Handle result.error()
                Log.e("Search Activity", "UTENTE NON TROVATO");
            }
        });

    }


    private void queryAllUsers() {
        FilterObject filter = Filters.ne("id", client.getCurrentUser().getId());
        int offset = 0;
        int limit = 100;

        QueryUsersRequest request = new QueryUsersRequest(filter, offset, limit);

        client.queryUsers(request).enqueue(result -> {
            if (result.isSuccess()) {
                List<User> users = result.data();
                ChatUserAdapter adapter = new ChatUserAdapter(this, users);
                userListView.setAdapter(adapter);

            } else {
                // Handle result.error()
                Log.e("Search Activity", "UTENTE NON TROVATO");
            }
        });

    }
}