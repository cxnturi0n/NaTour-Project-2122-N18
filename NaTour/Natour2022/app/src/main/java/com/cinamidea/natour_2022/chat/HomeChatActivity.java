package com.cinamidea.natour_2022.chat;

import static android.content.ContentValues.TAG;
import static java.util.Collections.singletonList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.databinding.ActivityHomeChatBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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
        user.setId(SigninFragment.current_username);
        user.setName(SigninFragment.current_username);

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

        final Button cerca = (Button) findViewById(R.id.cerca_utente);

        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeChatActivity.this, ChatUserList.class));
            }
        });


        Intent chat_intent = getIntent();
        if (chat_intent.getStringArrayListExtra("members") != null) {
            ArrayList<String> members = chat_intent.getStringArrayListExtra("members");
            client.createChannel("messaging", members);
        }


    }



}