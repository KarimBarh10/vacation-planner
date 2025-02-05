package com.karim.vacationhere.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.karim.vacationhere.dao.ExcursionDAO;
import com.karim.vacationhere.dao.VacationDAO;
import com.karim.vacationhere.entities.Excursion;
import com.karim.vacationhere.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    private static volatile MyDatabase INSTANCE;

    static MyDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (MyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "MyDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
