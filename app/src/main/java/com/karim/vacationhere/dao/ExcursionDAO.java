package com.karim.vacationhere.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.karim.vacationhere.entities.Excursion;
import com.karim.vacationhere.entities.Vacation;

import java.util.List;


@Dao
public interface ExcursionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacationID = :vac ORDER BY excursionID ASC ")
    List<Excursion> getAssociatedExcursions(int vac);
}
