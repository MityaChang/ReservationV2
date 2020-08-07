package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etSize, etDate, etTime, etTelephone, etName;
    CheckBox checkBox;
    TextView tvDate, tvTime;
    Button btReserve, btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);
        tvDate = findViewById(R.id.textViewDate);
        tvTime = findViewById(R.id.textViewTime);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }
                Calendar.getInstance();

                String message = "New Reservation " + "\n" +
                        "Name: " + etName.getText().toString() + "\n" +
                        "Smoking: " + isSmoke + "\n" +
                        "Size: " + etSize.getText().toString() + "\n" +
                        "Date: " + etDate.getText() + "\n" +
                        "Time: " + etTime.getText();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(message);
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("CONFIRM", null);
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String isSmoke = "";
                        if (checkBox.isChecked()) {
                            isSmoke = "smoking";
                        } else {
                            isSmoke = "non-smoking";
                        }
                        String msg = "New Reservation \n Name: " + etName.getText().toString() + " \n Smoking: " + isSmoke + " \n Size: " + etSize.getText().toString() + " \n Date: " + etDate.getText().toString() + "\n Time: " + etTime.getText().toString();
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);
                myDialog.show();

//                Toast.makeText(MainActivity.this,
//                        "Hi, " + etName.getText().toString() + ", you have booked a "
//                                + etSize.getText().toString() + " person(s) "
//                                + isSmoke + " table on "
//                                + datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + " at "
//                                + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ". Your phone number is "
//                                + etTelephone.getText().toString() + ".",
//                        Toast.LENGTH_LONG).show();
            }
        });


        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                etDate.setText(null);
                etTime.setText(null);
                checkBox.setChecked(false);
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the Listener to set the date
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };

                // Create the Date Picker Dialog
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, 2014, 11, 31);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };
                // Create the Time Picker Dialog
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, 20, 0, true);
                myTimeDialog.show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", etName.getText().toString());
        edit.putString("tel", etTelephone.getText().toString());
        edit.putString("size", etSize.getText().toString());
        edit.putBoolean("smoking", checkBox.isChecked());
        edit.putString("day", etDate.getText().toString());
        edit.putString("time", etTime.getText().toString());
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(prefs.getString("name", ""));
        etTelephone.setText(prefs.getString("tel", ""));
        etSize.setText(prefs.getString("size", ""));
        checkBox.setChecked(prefs.getBoolean("smoking", false));
        etDate.setText(prefs.getString("day", ""));
        etTime.setText(prefs.getString("time", ""));
    }
}