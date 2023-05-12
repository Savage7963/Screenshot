package com.example.firstapplication.timepickerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView btDate,btTime,tvCurrent,tvSelectedDate,tvSelectedTime,tvResult;
    int cYear,cMonth,cDay,sYear,sMonth,sDay;
    int cHour,cMinute,sHour,sMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btDate=findViewById(R.id.bt_date);
        btTime=findViewById(R.id.bt_time);
        tvCurrent=findViewById(R.id.tv_current);
        tvSelectedDate=findViewById(R.id.tv_selected_date);
        tvSelectedTime=findViewById(R.id.tv_selected_time);
        tvResult=findViewById(R.id.tv_result);

//Initialize calander
        Calendar calendar= Calendar.getInstance();

        //get current year
        cYear=calendar.get(Calendar.YEAR);
        cMonth=calendar.get(Calendar.MONTH);
        sDay=calendar.get(Calendar.DAY_OF_MONTH);

        cHour=calendar.get(Calendar.HOUR_OF_DAY);
        cMinute=calendar.get(Calendar.MINUTE);
        String date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String time=new  SimpleDateFormat("hh:mm aa",Locale.getDefault()).format(new Date());

        tvCurrent.setText(String.format("%s\n%s",date,time));

        tvSelectedDate.setText(date);
        tvSelectedTime.setText(time);

        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize date picker dialog

                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//initialize year month
                        sYear=year;
                        sMonth=month;
                        sDay=dayOfMonth;

                        String sDate= sDay +"-"+ (sMonth)+"-"+sYear;

                        tvSelectedDate.setText(sDate);
                    }
                },cYear,cMonth,cDay
                );
                //
                datePickerDialog.updateDate(sYear,sMonth,sDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
//
                datePickerDialog.show();


            }
        });
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        sHour=hourOfDay;
                        sMinute=minute;

                        Calendar calendar=Calendar.getInstance();

                        String sDate=tvSelectedDate.getText().toString().trim();

                        String[] strings=sDate.split("-");

                        int sDay=Integer.parseInt(strings[0]);
                        //
                        calendar.set(Calendar.DAY_OF_MONTH,sDay);

                        calendar.set(Calendar.HOUR_OF_DAY,sHour);

                        calendar.set(Calendar.MINUTE,sMinute);

                        tvSelectedTime.setText(DateFormat.format("hh:mm:aa",calendar));

                        if(calendar.getTimeInMillis()==Calendar.getInstance().getTimeInMillis())
                        {
                            tvResult.setText("Current time");


                            tvResult.setTextColor(Color.CYAN);


                        } else if (calendar.getTimeInMillis()>Calendar.getInstance().getTimeInMillis())
                        {
                            tvResult.setText("Future time selected");

                            tvResult.setTextColor(Color.GREEN);
                        }else{
                            tvResult.setText("past Time selected");
                        }


                    }
                },cHour,cMinute,false
                );

                timePickerDialog.show();
            }
        });

    }
}