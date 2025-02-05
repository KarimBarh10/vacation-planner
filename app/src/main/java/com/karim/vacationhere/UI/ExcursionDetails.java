package com.karim.vacationhere.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.karim.vacationhere.R;
import com.karim.vacationhere.database.Repository;
import com.karim.vacationhere.entities.Excursion;
import com.karim.vacationhere.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    EditText excursionTitleText;
    EditText excursionDateText;

    int excursionID;
    int vacationID;
    String title;
    String date;

    String vacStartDate;
    String vacEndDate;

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener excursionDateCalendar;

    Repository eRepository;

    int numExc;
    Excursion currentExc;
    Vacation currentVac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        eRepository = new Repository(getApplication());
        excursionTitleText = findViewById(R.id.excursionTitle);
        excursionDateText = findViewById(R.id.excursionDate);

        vacationID = getIntent().getIntExtra("vacationID", -1);
        excursionID = getIntent().getIntExtra("excursionID", -1);
        title = getIntent().getStringExtra("excursionTitle");
        date = getIntent().getStringExtra("excursionStartDate");

        // Fetch vacation details and handle null values
        List<Vacation> myVacations = eRepository.getAllVacations();
        for (Vacation v : myVacations) {
            if (v.getVacationID() == vacationID) {
                vacStartDate = v.getStartDate();
                vacEndDate = v.getEndDate();
                break;
            }
        }

        // Set the excursion details if title and date are available
        if (title != null) {
            excursionTitleText.setText(title);
        }
        if (date != null) {
            excursionDateText.setText(date);
        }

        excursionDateCalendar = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateLabelDate();
            }
        };

        excursionDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ExcursionDetails.this, excursionDateCalendar, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // SAVING EXCURSION
        FloatingActionButton saveExcursionButton = findViewById(R.id.addExcursion);
        saveExcursionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = excursionDateText.getText().toString();

                if (dateCheckExcursion() && dateValidation(date)) {
                    String title = excursionTitleText.getText().toString();
                    Excursion excursion = new Excursion(title, date, vacationID);
                    eRepository.insert(excursion);

                    Toast.makeText(ExcursionDetails.this, "Excursion Added", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ExcursionDetails.this, "Can't add excursion, check dates", Toast.LENGTH_LONG).show();
                }
            }
        });

        // DELETING EXCURSION
        FloatingActionButton deleteExcursionButton = findViewById(R.id.deleteExcursion);
        deleteExcursionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Excursion exc : eRepository.getAllExcursions()) {
                    if (exc.getExcursionID() == excursionID) currentExc = exc;
                }
                eRepository.delete(currentExc);
                Toast.makeText(ExcursionDetails.this, currentExc.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        // ALERT EXCURSION
        FloatingActionButton alertExcursionButton = findViewById(R.id.alertExcursion);
        alertExcursionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = excursionTitleText.getText().toString();
                String dateFromScreen = excursionDateText.getText().toString();
                String myFormat = "MM/dd/yy"; // Date format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;

                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(ExcursionDetails.this, Receiver.class);
                    intent.putExtra("key", title);
                    PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    private void updateLabelDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        excursionDateText.setText(sdf.format(myCalendar.getTime()));
    }

    // DATE VALIDATION
    public boolean dateCheckExcursion() {
        if (date == null || vacStartDate == null || vacEndDate == null) {
            return false; // Return false if any date is null
        }

        Date excursionStartDate = new Date();
        try {
            excursionStartDate = new SimpleDateFormat("MM/dd/yy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date startDateVac = new Date();
        try {
            startDateVac = new SimpleDateFormat("MM/dd/yy").parse(vacStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date endDateVac = new Date();
        try {
            endDateVac = new SimpleDateFormat("MM/dd/yy").parse(vacEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return !(startDateVac.after(excursionStartDate) || endDateVac.before(excursionStartDate));
    }

    public boolean dateValidation(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false; // Invalid date if null or empty
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(false);
        try {
            sdf.parse(date); // Try to parse the date
            return true; // If no exception is thrown, date is valid
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(ExcursionDetails.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
            return false; // Invalid date
        }
    }
}
