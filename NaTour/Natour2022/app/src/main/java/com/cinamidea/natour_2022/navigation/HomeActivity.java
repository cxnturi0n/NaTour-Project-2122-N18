package com.cinamidea.natour_2022.navigation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.auth_util.GoogleAuthentication;
import com.cinamidea.natour_2022.auth_util.UserType;
import com.cinamidea.natour_2022.chat.HomeChatActivity;
import com.cinamidea.natour_2022.map.AllPathsFragment;
import com.cinamidea.natour_2022.map.MapActivity;
import com.cinamidea.natour_2022.user_callbacks.GetProfileImageCallback;
import com.cinamidea.natour_2022.user_callbacks.PutProfileImageCallback;
import com.cinamidea.natour_2022.users_util.UsersHTTP;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;
import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.livedata.ChatDomain;
import okhttp3.OkHttpClient;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeActivity extends AppCompatActivity {

    public static CircleImageView imgbutton_avatar;
    private TextView textview_username;
    private ImageButton button_home, button_profile;
    private Fragment fragment_home, fragment_profile;
    private ImageButton button_openmap, button_search, button_chat_or_menu;
    private FragmentManager fragmentManager;
    private byte check_chat_or_menu = 0;
    public static boolean is_updated = false;
    public static boolean[] counter_updated = {true, true, true};
    private static final int PERMISSION_REQUEST_CODE = 1;

    //TODO:CHAT
    ChatClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupViewComponents();
        //TODO:CHAT
        setUpChatUser();
        setListeners();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityHome_fragmentcontainer, fragment_home);
        fragmentTransaction.add(R.id.activityHome_fragmentcontainer, fragment_profile);
        fragmentTransaction.hide(fragment_profile);
        fragmentTransaction.commit();

        button_home.performClick();

        getPermissions();







    }


    private void setupViewComponents() {

        fragment_profile = new ProfileFragment();
        fragment_home = new HomeFragment();

        imgbutton_avatar = findViewById(R.id.activityHome_avatar);
        textview_username = findViewById(R.id.activityHome_usernameText);
        button_home = findViewById(R.id.activityHome_home);
        button_profile = findViewById(R.id.activityHome_profile);
        button_chat_or_menu = findViewById(R.id.activityHome_chat_or_menu);
        button_search = findViewById(R.id.activityHome_search);
        button_openmap = findViewById(R.id.activityHome_openMap);

        textview_username.setText(SigninFragment.current_username);

        //TODO:Check bucket per l'immagine
//        UserType userType = new UserType(this);
//        UsersHTTP.getProfileImage(userType.getUser_type(), SigninFragment.current_username, userType.getId_token(), new GetProfileImageCallback(this, imgbutton_avatar));
          Glide.with(this).load("https://natour-android.s3.eu-central-1.amazonaws.com/Users/ProfilePics/Umberto").into(imgbutton_avatar);
    }

    private void setListeners() {

        button_home.setOnClickListener(view -> {

            button_home.setClickable(false);
            button_home.setImageResource(R.drawable.ic_home_active);
            button_profile.setImageResource(R.drawable.ic_profile_inactive);
            button_chat_or_menu.setImageResource(R.drawable.ic_chat);
            check_chat_or_menu=0;

            changeFragment(fragment_home);

            button_profile.setClickable(true);
            imgbutton_avatar.setClickable(true);

        });

        button_profile.setOnClickListener(view -> {

            button_profile.setClickable(false);
            imgbutton_avatar.setClickable(false);
            button_profile.setImageResource(R.drawable.ic_profile_active);
            button_home.setImageResource(R.drawable.ic_home_inactive);
            button_chat_or_menu.setImageResource(R.drawable.ic_menu);
            check_chat_or_menu=1;

            changeFragment(fragment_profile);


            button_home.setClickable(true);

        });

        imgbutton_avatar.setOnClickListener(view -> {

            button_profile.performClick();

        });

        button_chat_or_menu.setOnClickListener(view -> {

            if(check_chat_or_menu==0)
                startActivity(new Intent(this, HomeChatActivity.class));
            else
                openMenu();

        });

        button_openmap.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED   ) {
                startActivity(new Intent(this, MapActivity.class));
            } else {
                showPermissionDialog();
            }
        });
    }

    private void changeFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(fragment instanceof HomeFragment) {

            if(is_updated) {
                fragmentTransaction.remove(fragment_home);
                fragment_home = new HomeFragment();
                fragmentTransaction.add(R.id.activityHome_fragmentcontainer, fragment_home);
                is_updated = false;
            }
            fragmentTransaction.show(fragment_home);
            fragmentTransaction.hide(fragment_profile);

        }
        else if(fragment instanceof ProfileFragment) {

            if(is_updated) {
                fragmentTransaction.remove(fragment_profile);
                fragment_profile = new ProfileFragment();
                fragmentTransaction.add(R.id.activityHome_fragmentcontainer, fragment_profile);
                is_updated = false;
            }
            fragmentTransaction.show(fragment_profile);
            fragmentTransaction.hide(fragment_home);

        }
        fragmentTransaction.commit();

    }

    private void openMenu() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.menu_bottom_layout,
                findViewById(R.id.menuLayout_container)
        );
        bottomSheetView.findViewById(R.id.menuLayout_logout).setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
            logout();
        });
        UserType user_type = new UserType(this);
        if(!user_type.getUser_type().equals("Cognito"))
            bottomSheetView.findViewById(R.id.menuLayout_changePassword).setVisibility(View.GONE);
        bottomSheetView.findViewById(R.id.menuLayout_changePassword).setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            startActivity(new Intent(this, ChangePasswordActivity.class));
        });

        bottomSheetView.findViewById((R.id.menuLayout_editPhoto)).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            openImage();


        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    private void openImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 4);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            InputStream iStream = null;
            try {
                iStream = getContentResolver().openInputStream(uri);
                byte[] image_as_byte_array = getBytes(iStream);
                String image_base64 = Base64.getEncoder().encodeToString(image_as_byte_array);
                UserType userType = new UserType(this);
                UsersHTTP.putProfileImage(userType.getUser_type(), image_base64, SigninFragment.current_username, userType.getId_token(), new PutProfileImageCallback(this, imgbutton_avatar, image_as_byte_array));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == PERMISSION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null)
                return;

            AllPathsFragment.locationPermissionGranted = true;
        }



    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (fineLocationGranted && coarseLocationGranted) {
                            AllPathsFragment.locationPermissionGranted = true;
                        } else {
                            AllPathsFragment.locationPermissionGranted = false;
                        }
                    }
            );


    public void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Permissions");
        builder.setMessage("You have to enable permissions in order to use the map");
        Intent permissions_intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        builder.setPositiveButton("Enable permissions", (dialog, which) ->
                startActivityForResult(permissions_intent, PERMISSION_REQUEST_CODE)).setNegativeButton("No, Just Exit", (dialog, which) -> {
        });
        AlertDialog mGPSDialog = builder.create();
        mGPSDialog.show();
    }

    private boolean getPermissions() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            AllPathsFragment.locationPermissionGranted = true;
        } else {
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }

        return AllPathsFragment.locationPermissionGranted;
    }

    private void logout() {

        SharedPreferences natour_shared_pref;

        natour_shared_pref = getSharedPreferences("natour_tokens", MODE_PRIVATE);
        String id_token = natour_shared_pref.getString("id_token", null);

        if(id_token!=null) natour_shared_pref.edit().clear().commit();
        else {

            GoogleAuthentication googleAuthentication = new GoogleAuthentication(this);
            googleAuthentication.signOut();

        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

    //TODO:CHAT
    private void setUpChatUser(){
        client = new ChatClient.Builder(getString(R.string.chat_api_key), getApplicationContext())
                .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                .build();
        new ChatDomain.Builder(client, getApplicationContext()).build();

        User user = new User();
        user.setId(SigninFragment.current_username);
        user.setName(SigninFragment.current_username);
        user.setImage("https://natour-android.s3.eu-central-1.amazonaws.com/Users/ProfilePics/Umberto");

        String token = client.devToken(user.getId());
        client.connectUser(
                user,
                token
        ).enqueue();
    }

}