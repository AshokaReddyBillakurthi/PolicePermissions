package com.techouts.pcomplaints.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techouts.pcomplaints.entities.Application;

import java.util.List;

/**
 * Created by TO-OW109 on 08-02-2018.
 */

@Dao
public interface ApplicationDao {

    @Query("SELECT * FROM Application")
    List<Application> getAllApplications();

    @Query("SELECT * FROM Application WHERE email LIKE :email")
    List<Application> getApplicationDetailsByEmailId(String email);

    @Query("Select * From Application Where area LIKE :area ")
    List<Application> getAllApplicationsByArea(String area);

    @Query("Select * From Application Where applicationType LIKE :applicationType")
    List<Application> getAllApplicationsByApplicationType(String applicationType);

    @Insert
    void insertApplication(Application application);

    @Insert
    void insertAll(List<Application> applications);

    @Update
    void update(Application application);

    @Delete
    void delete(Application application);
}
