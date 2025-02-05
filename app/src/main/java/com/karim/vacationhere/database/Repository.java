package com.karim.vacationhere.database;

import android.app.Application;

import com.karim.vacationhere.dao.ExcursionDAO;
import com.karim.vacationhere.dao.VacationDAO;
import com.karim.vacationhere.entities.Excursion;
import com.karim.vacationhere.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;

    private List<Vacation> mALlVacations;
    private List<Excursion> mAllExcursions;


    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public Repository(Application application){
        MyDatabase db = MyDatabase.getDatabase(application);
        mVacationDAO = db.vacationDAO();
        mExcursionDAO = db.excursionDAO();
    }

    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mALlVacations = mVacationDAO.getAllVacations();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mALlVacations;
    }

    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public List<Excursion> getAssociatedExcursions(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }



    public void delete(Vacation vacation){
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
            System.out.println("vacation deleted");
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion){
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
            System.out.println("excursion deleted");
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Vacation vacation){
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Excursion excursion){
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation){
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Excursion excursion){
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
