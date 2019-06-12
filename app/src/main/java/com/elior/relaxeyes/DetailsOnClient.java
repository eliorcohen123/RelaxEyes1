package com.elior.relaxeyes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsOnClient extends AppCompatActivity {

    private Spinner spinnerAge, spinnerSex, spinnerScreen;
    private Button btn_pass_data;
    private String[] age_arrays = {"גיל:", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
            "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63",
            "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
            "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"};
    private String[] sex_arrays = {"מין:", "גבר", "אישה"};
    private String[] screen_arrays = {"מסך:", "פלאפון", "מחשב", "טלוויזיה"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        initUI();
        initSpinner();
        submitData();
    }

    private void initUI() {
        spinnerAge = findViewById(R.id.spinner_age);
        spinnerSex = findViewById(R.id.spinner_sex);
        spinnerScreen = findViewById(R.id.spinner_screen);

        btn_pass_data = findViewById(R.id.pass_data);
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

                if (spinnerAge.getSelectedItem() == "גיל:" || spinnerSex.getSelectedItem() == "מין:" || spinnerScreen.getSelectedItem() == "מסך:") {
                    Toast toast = Toast.makeText(DetailsOnClient.this, getString(R.string.fill_all_message), Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                    TextView text = view.findViewById(android.R.id.message);
                    text.setTextColor(getResources().getColor(R.color.colorYellow));
                    toast.show();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("total_val", MODE_PRIVATE).edit();
                    editor.putFloat("total", (float) yourValTotal);
                    editor.apply();

                    Intent intent = new Intent(DetailsOnClient.this, TimerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
