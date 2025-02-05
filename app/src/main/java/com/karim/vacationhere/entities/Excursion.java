package com.karim.vacationhere.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;

    private String excursionTitle;

    private String excursionDate;

    private int vacationID;

    // Constructors

    @Override
    public String toString() {
        return "Excursion{" +
                "excursionId=" + excursionID +
                ", vacationId=" + vacationID +
                ", excursionTitle='" + excursionTitle + '\'' +
                ", excursionStartDate='" + excursionDate + '\'' +
                '}';
    }
    public Excursion(String excursionTitle, String excursionDate, int vacationID) {
        this.excursionTitle = excursionTitle;
        this.excursionDate = excursionDate;
        this.vacationID = vacationID;
    }

    // Getters and setters
    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

}
