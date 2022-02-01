package com.cinamidea.natour_2022.navigation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cinamidea.natour_2022.MainActivity;
import com.cinamidea.natour_2022.R;
import com.cinamidea.natour_2022.auth.SigninFragment;
import com.cinamidea.natour_2022.auth_util.GoogleAuthentication;
import com.cinamidea.natour_2022.chat.HomeChatActivity;
import com.cinamidea.natour_2022.map.AllPathsFragment;
import com.cinamidea.natour_2022.map.MapActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;
import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;
import io.getstream.chat.android.client.models.User;
import io.getstream.chat.android.livedata.ChatDomain;

public class HomeActivity extends AppCompatActivity {

    public static CircleImageView imgbutton_avatar;
    private TextView textview_username;
    private ImageButton button_home, button_profile;
    private Fragment fragment_home, fragment_profile;
    private ImageButton button_openmap, button_search, button_chat_or_menu;
    private FragmentManager fragmentManager;
    private byte check_chat_or_menu = 0;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupViewComponents();
        setListeners();

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityHome_fragmentcontainer, fragment_home);
        fragmentTransaction.add(R.id.activityHome_fragmentcontainer, fragment_profile);
        fragmentTransaction.hide(fragment_profile);
        fragmentTransaction.commit();

        button_home.performClick();

        //Richiesta permessi
        getLocationPermission();




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

        button_openmap.setOnClickListener(view ->
                startActivity(new Intent(this, MapActivity.class)));

    }

    private void changeFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(fragment instanceof HomeFragment) {

            fragmentTransaction.hide(fragment_profile);
            fragmentTransaction.show(fragment_home);

        }
        else if(fragment instanceof ProfileFragment) {

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
                //TODO:Per salvare nello stream l'immagine
                Glide.with(this).load(image_as_byte_array).circleCrop().into(imgbutton_avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    //Check permessi
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            AllPathsFragment.locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    AllPathsFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
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

}