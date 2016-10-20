package com.angelsoft.angelo_romel.randomsix;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    SharedPreferences pref;
    private int minValue = 0;
    private int maxValue = 0;
    private EditText minValueEdit;
    private EditText maxValueEdit;
    private Switch noRepeatSwitch;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        minValueEdit = (EditText)findViewById(R.id.minvalue_edittext);
        maxValueEdit = (EditText)findViewById(R.id.maxvalue_edittext);
        noRepeatSwitch = (Switch)findViewById(R.id.no_repeat_switch);
        saveButton = (Button)findViewById(R.id.save_settings_button);

        //Retrieve values from preferences.
        minValueEdit.setText(String.valueOf(pref.getInt("minValue", 1)));
        maxValueEdit.setText(String.valueOf(pref.getInt("maxValue", 50)));
        if(pref.getBoolean("noRepeat", true)) {
            noRepeatSwitch.setChecked(true);
        }
        else {
            noRepeatSwitch.setChecked(false);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minValue = Integer.parseInt(minValueEdit.getText().toString());
                maxValue = Integer.parseInt(maxValueEdit.getText().toString());

                if(isInputComplete()) {
                    if(isMinAndMaxValid()) {
                        //The number 6 is hardcoded to be the default value but can be specified
                        //to a different value if required.
                        if(isNumberOfSetsOK(pref.getInt("numberSet", 6))) {
                            //Store new value for the settings/preferences.
                            //prefs.edit().putBoolean("scrollToItem", false).apply();
                            pref.edit().putInt("minValue", minValue).apply();
                            pref.edit().putInt("maxValue", maxValue).apply();
                            if(noRepeatSwitch.isChecked()) {
                                pref.edit().putBoolean("noRepeat", true).apply();
                            }
                            else {
                                pref.edit().putBoolean("noRepeat", false).apply();
                            }
                            Toast.makeText(SettingsActivity.this, "Your preference have been saved.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(SettingsActivity.this, "The number of sets within the starting and ending value" +
                                            " must be a set of 6 numbers.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SettingsActivity.this, "The starting value must be less " +
                                "than the ending value.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SettingsActivity.this, "A value is required for both the " +
                            "starting and ending value.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Displays an alert dialog box.
     * @param message = the message to be displayed.
     */
    public void displayMessage(String message) {
        AlertDialog.Builder alertDiag = new AlertDialog.Builder(this);
        alertDiag.setMessage(message);
        alertDiag.setTitle("Random Six");
        alertDiag.setPositiveButton("OK", null);
        alertDiag.setCancelable(true);
        alertDiag.create().show();
    }

    /**
     * Checks if the set of numbers are acceptable.
     * @param requiredSet = The number of sets that the random number generator will generate.
     * @return boolean = true if the number of sets is equal to the number of required set.
     */
    public boolean isNumberOfSetsOK(int requiredSet) {
        try {
            if((maxValue - minValue) > requiredSet) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception ex) {
            Log.e(LOG_TAG, ex.toString(), ex);
            return false;
        }
    }

    /**
     * Checks whether the minimum value is less than the maximum value.
     * @return boolean = true if the minimum value is less than the maximum value. False otherwise.
     */
    public boolean isMinAndMaxValid() {
        try {
            if(minValue < maxValue) {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception ex) {
            Log.e(LOG_TAG, ex.toString(), ex);
            return false;
        }
    }

    /**
     * Checks if an entry is provided for all the required input fields.
     * @return boolean = true if all the required input fields have been completed. False otherwise.
     */
    public boolean isInputComplete() {
        try {
            if(minValueEdit.getText().toString() != "" && maxValueEdit.getText().toString() != "") {
                return true;
            }
            else {
                return false;
            }
        }
        catch(Exception ex) {
            Log.e(LOG_TAG, ex.toString(), ex);
            return false;
        }
    }
}
