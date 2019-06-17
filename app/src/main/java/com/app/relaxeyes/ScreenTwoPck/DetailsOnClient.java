package com.app.relaxeyes.ScreenTwoPck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.relaxeyes.R;
import com.app.relaxeyes.ScreenOnePck.WelcomeActivityRegulations;
import com.app.relaxeyes.ScreenThreePck.TimerActivity;

public class DetailsOnClient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner spinnerAge, spinnerSex, spinnerScreen, spinnerRest;
    private Button btn_pass_data;
    private String[] age_arrays = {"גיל:", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
            "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63",
            "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
            "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"};
    private String[] sex_arrays = {"מין:", "גבר", "אישה"};
    private String[] screen_arrays = {"מסך:", "פלאפון", "מחשב", "טלוויזיה"};
    private String[] rest_arrays = {"דקות:", "1", "2", "3", "4", "5"};
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        initUI();
        checkStopScreen();
        checkStopRest();
        initSpinner();
        submitData();
        myDrawerLayout();
    }

    private void initUI() {
        spinnerAge = findViewById(R.id.spinner_age);
        spinnerSex = findViewById(R.id.spinner_sex);
        spinnerScreen = findViewById(R.id.spinner_screen);
        spinnerRest = findViewById(R.id.spinner_rest);

        btn_pass_data = findViewById(R.id.pass_data);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void myDrawerLayout() {
        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else
                    drawer.openDrawer(GravityCompat.END);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.regulations) {
            Intent intentTutorial = new Intent(DetailsOnClient.this, WelcomeActivityRegulations.class);
            startActivity(intentTutorial);
        } else if (id == R.id.shareIntent) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "היי, אתה מוזמן לבקר באפליקצייה שלי: https://play.google.com/store/apps/details?id=com.app.relaxeyes");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    private void checkStopScreen() {
        SharedPreferences prefs = getSharedPreferences("total_stop_screen", MODE_PRIVATE);
        int idNumStopScreen = prefs.getInt("screen", 900000);

        if (idNumStopScreen == 1) {
            Intent intent = new Intent(DetailsOnClient.this, TimerActivity.class);
            startActivity(intent);
        }
    }

    private void checkStopRest() {
        SharedPreferences prefs = getSharedPreferences("total_stop_rest", MODE_PRIVATE);
        int idNumStopRest = prefs.getInt("rest", 900000);

        if (idNumStopRest == 1) {
            Intent intent = new Intent(DetailsOnClient.this, TimerActivity.class);
            startActivity(intent);
        }
    }

    private void initSpinner() {
        // spinnerAge
        ArrayAdapter<String> spinnerArrayAdapterAge = new ArrayAdapter<String>(this, R.layout.spinner_item, age_arrays);
        spinnerArrayAdapterAge.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerAge.setAdapter(spinnerArrayAdapterAge);

        // spinnerSex
        ArrayAdapter<String> spinnerArrayAdapterSex = new ArrayAdapter<String>(this, R.layout.spinner_item, sex_arrays);
        spinnerArrayAdapterSex.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerSex.setAdapter(spinnerArrayAdapterSex);

        // spinnerScreen
        ArrayAdapter<String> spinnerArrayAdapterScreen = new ArrayAdapter<String>(this, R.layout.spinner_item, screen_arrays);
        spinnerArrayAdapterScreen.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerScreen.setAdapter(spinnerArrayAdapterScreen);

        // spinnerRest
        ArrayAdapter<String> spinnerArrayAdapterRest = new ArrayAdapter<String>(this, R.layout.spinner_item, rest_arrays);
        spinnerArrayAdapterRest.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerRest.setAdapter(spinnerArrayAdapterRest);
    }

    public void submitData() {
        btn_pass_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double yourValAge = 0.0;
                if (!(spinnerAge.getSelectedItem().toString().equals("גיל:"))) {
                    int ageMy = Integer.parseInt(String.valueOf(spinnerAge.getSelectedItem()));
                    if (ageMy <= 99 && ageMy >= 65) {
                        yourValAge = 0.9;
                    } else if (ageMy <= 64 && ageMy >= 40) {
                        yourValAge = 0.95;
                    } else if (ageMy <= 39 && ageMy >= 4) {
                        yourValAge = 1.0;
                    } else if (ageMy <= 3 && ageMy >= 0) {
                        yourValAge = 0.93;
                    }
                }

                double yourValSex = 0.0;
                String sexMy = spinnerSex.getSelectedItem().toString();
                if (!sexMy.equals("מין:")) {
                    switch (sexMy) {
                        case "גבר":
                            yourValSex = 1.0;
                            break;
                        case "אישה":
                            yourValSex = 0.95;
                            break;
                    }
                }

                double yourValScreen = 0.0;
                String screenMy = spinnerScreen.getSelectedItem().toString();
                if (!screenMy.equals("מסך:")) {
                    switch (screenMy) {
                        case "טלוויזיה":
                            yourValScreen = 1.0;
                            break;
                        case "מחשב":
                            yourValScreen = 0.95;
                            break;
                        case "פלאפון":
                            yourValScreen = 0.8;
                            break;
                    }
                }

                double yourValTotal = 1000.0 * yourValAge * yourValSex * yourValScreen;

                int yourValMins = 0;
                String MinsMy = spinnerRest.getSelectedItem().toString();
                if (!MinsMy.equals("דקות:")) {
                    switch (MinsMy) {
                        case "1":
                            yourValMins = 1;
                            break;
                        case "2":
                            yourValMins = 2;
                            break;
                        case "3":
                            yourValMins = 3;
                            break;
                        case "4":
                            yourValMins = 4;
                            break;
                        case "5":
                            yourValMins = 5;
                            break;
                    }
                }

                if (spinnerAge.getSelectedItem() == "גיל:" || spinnerSex.getSelectedItem() == "מין:" ||
                        spinnerScreen.getSelectedItem() == "מסך:" || spinnerRest.getSelectedItem() == "דקות:") {
                    Toast toast = Toast.makeText(DetailsOnClient.this, getString(R.string.fill_all_message), Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.colorYellow));
                    toast.show();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("total_val_screen", MODE_PRIVATE).edit();
                    editor.putFloat("total_screen", (float) yourValTotal);
                    editor.apply();

                    SharedPreferences.Editor editorRest = getSharedPreferences("total_val_rest", MODE_PRIVATE).edit();
                    editorRest.putInt("total_rest", yourValMins);
                    editorRest.apply();

                    Intent intent = new Intent(DetailsOnClient.this, TimerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}