package com.karim.vacationhere.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    EditText vacationIDText;
    EditText vacationTitleText;
    EditText vacationHotelText;
    EditText vacationStartText;
    EditText vacationEndText;

    int vacationID;
    String title;
    String hotel;
    String start_date;
    String end_date;

    Vacation currentVac;
    int numExc;
    int excursionID;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    DatePickerDialog.OnDateSetListener alertDate;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        repository = new Repository(getApplication());
        vacationTitleText = findViewById(R.id.vacationTitle);
        vacationHotelText = findViewById(R.id.vacationHotel);
        vacationStartText = findViewById(R.id.startDate);
        vacationEndText = findViewById(R.id.endDate);

        vacationID = getIntent().getIntExtra("vacationID", -1);
        title = getIntent().getStringExtra("vacationTitle");
        hotel = getIntent().getStringExtra("vacationHotel");
        start_date = getIntent().getStringExtra("vacationStartDate");
        end_date = getIntent().getStringExtra("vacationEndDate");

        if (title != null) {
            vacationID = getIntent().getIntExtra("vacationID", -1);
            String vacStringStart = getIntent().getStringExtra("vacStartDate");
            String vacStringEnd = getIntent().getStringExtra("vacEndDate");
            vacationTitleText.setText(title);
            vacationHotelText.setText(hotel);
            vacationStartText.setText(start_date);
            vacationEndText.setText(end_date);
        }

        vacationStartText = findViewById(R.id.startDate);
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelStart();
            }

        };
        vacationStartText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        vacationEndText = findViewById(R.id.endDate);
        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelEnd();
            }

        };

        vacationEndText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.enterExcursionDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
            }

        };

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);


        //SAVE VACATION
        FloatingActionButton saveVacationButton = findViewById(R.id.saveVacation);
        saveVacationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_date = vacationStartText.getText().toString();
                end_date = vacationEndText.getText().toString();

                if (dateCheck() && dateValidationStart(start_date) && dateValidationEnd(end_date)) {

                    // Get the values from your EditText fields and convert them to strings
                    String title = vacationTitleText.getText().toString();
                    String hotel = vacationHotelText.getText().toString();
                    String startDate = vacationStartText.getText().toString();
                    String endDate = vacationEndText.getText().toString();

                    Vacation vacation = new Vacation(title, hotel, startDate, endDate);

                    repository.insert(vacation);
                    Toast.makeText(VacationDetails.this, "Vacation added", Toast.LENGTH_LONG).show();

                    finish();
                }
                else{
                    Toast.makeText(VacationDetails.this, "Can't add vacation, check dates", Toast.LENGTH_LONG).show();
                }
            }
        });

        //DELETE VACATION
        FloatingActionButton deleteVacationButton = findViewById(R.id.deleteVacation);
        deleteVacationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                for (Vacation vac : repository.getAllVacations()) {
                    if (vac.getVacationID() == Integer.parseInt(String.valueOf(vacationID)))
                        currentVac = vac;
                }
                numExc = 0;
                for (Excursion exc : repository.getAllExcursions()) {
                    if (exc.getVacationID() == Integer.parseInt(String.valueOf(vacationID)))
                        ++numExc;
                }

                if (numExc == 0) {
                    repository.delete(currentVac);
                    Toast.makeText(VacationDetails.this, currentVac.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        //SHARE VACATION
        FloatingActionButton shareVacationButton = findViewById(R.id.shareVacation);
        shareVacationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, vacationTitleText.getText().toString() + " " + vacationHotelText.getText().toString() + " " +
                        vacationStartText.getText().toString() + " " + vacationEndText.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "This destination looks amazing!");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                finish();
            }

        });

        //ALERT VACATION
        FloatingActionButton alertVacationButton = findViewById(R.id.alertVacation);
        alertVacationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFromScreen = vacationStartText.getText().toString();
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try{
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(VacationDetails.this, Receiver.class);
                    intent.putExtra("key", title + " is starting");
                    PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);}
                catch (Exception e){

                }
                finish();
            }

        });

    }


    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        vacationStartText.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        vacationEndText.setText(sdf.format(myCalendarEnd.getTime()));
    }

    //DATE VALIDATION
    public boolean dateValidationStart(String start_date){

        if(start_date.trim().equals("")){
            return true;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            sdf.setLenient(false);
            try{
                Date validDate = sdf.parse(start_date);
                //date is valid

            }catch (ParseException e){
                e.printStackTrace();
                Toast.makeText(VacationDetails.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;

        }

    }

    public boolean dateValidationEnd(String end_date){

        if(end_date.trim().equals("")){
            return true;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            sdf.setLenient(false);
            try{
                Date validDate = sdf.parse(end_date);
                //date is valid

            }catch (ParseException e){
                e.printStackTrace();
                Toast.makeText(VacationDetails.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
                return false;
            }

            return true;

        }

    }

    private boolean dateCheck() {

        Date startDateVac = new Date();
        try {
            startDateVac = new SimpleDateFormat("MM/dd/yy").parse(start_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date endDateVac = new Date();
        try {
            endDateVac = new SimpleDateFormat("MM/dd/yy").parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (startDateVac.before(endDateVac)){
            return true;
        }else{
            return false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if(e.getVacationID() == vacationID) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
        //Toast.makeText(VacationDetails.this,"refresh details",Toast.LENGTH_LONG).show();
    }



}