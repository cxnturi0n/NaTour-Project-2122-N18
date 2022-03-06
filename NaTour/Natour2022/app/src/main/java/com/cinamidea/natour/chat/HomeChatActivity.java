package com.cinamidea.natour.chat;

import static android.content.ContentValues.TAG;
import static java.util.Collections.singletonList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cinamidea.natour.R;
import com.cinamidea.natour.databinding.ActivityHomeChatBinding;
import com.cinamidea.natour.utilities.UserType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.api.models.FilterObject;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.client.models.Filters;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.livedata.ChatDomain;
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel;
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModelBinding;
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory;


public final class HomeChatActivity extends AppCompatActivity {

    private ChatClient client;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chat);
        ActivityHomeChatBinding binding = ActivityHomeChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Step 1 - Set up the client for API calls and the domain for offline storage
        client = new ChatClient.Builder(getString(R.string.chat_api_key), getApplicationContext())
                .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                .build();
        new ChatDomain.Builder(client, getApplicationContext()).build();

        User user = new User();
        String username = new UserType(this).getUsername();
        user.setId(username);
        user.setName(username);

        String token = client.devToken(user.getId());
        client.connectUser(
                user,
                token
        ).enqueue();


        // Step 3 - Set the channel list filter and order
        // This can be read as requiring only channels whose "type" is "messaging" AND
        // whose "members" include our "user.id"
        FilterObject filter = Filters.and(
                Filters.eq("type", "messaging"),
                Filters.in("members", singletonList(user.getId()))
        );

        ChannelListViewModelFactory factory = new ChannelListViewModelFactory(
                filter,
                ChannelListViewModel.DEFAULT_SORT
        );

        ChannelListViewModel channelsViewModel =
                new ViewModelProvider(this, factory).get(ChannelListViewModel.class);

        // Step 4 - Connect the ChannelListViewModel to the ChannelListView, loose
        //          coupling makes it easy to customize
        ChannelListViewModelBinding.bind(channelsViewModel, binding.channelListView, this);
        binding.channelListView.setChannelItemClickListener(
                channel -> startActivity(ChatActivity.newIntent(this, channel))
        );

        final FloatingActionButton cerca = findViewById(R.id.search_button);

        cerca.setOnClickListener(v -> startActivity(new Intent(HomeChatActivity.this, ChatUserList.class)));

        Intent chat_intent = getIntent();
        if (chat_intent.getStringArrayListExtra("members") != null) {
            ArrayList<String> members = chat_intent.getStringArrayListExtra("members");
            Log.e("Members", members.toString());
            createChannel(members, client);
        }


    }

    private void createChannel(ArrayList<String> members, ChatClient client) {
        client.createChannel("messaging", members).enqueue(result -> {
            if (result.isSuccess()) {
                Toast.makeText(this, "Nuova chat creata", Toast.LENGTH_SHORT).show();
            } else {
                // Handle result.error()
                Log.e(TAG, "HomeChat: Errore creazione canale");
            }
        });
    }


}