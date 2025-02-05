package com.karim.vacationhere.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.time.LocalDate;
import java.util.List;

@Entity (tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;

    private String vacationTitle;

    private String vacationHotel;

    private String startDate;

    private String endDate;


    // Constructors
    public Vacation(String vacationTitle, String vacationHotel, String startDate, String endDate) {
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public String getVacationHotel() {
        return vacationHotel;
    }

    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
