package com.techouts.pcomplaints.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techouts.pcomplaints.entities.PermissionApplication;

import java.util.List;

/**
 * Created by TO-OW109 on 08-02-2018.
 */
@Dao
public interface PermissionApplicationDao {

    @Query("SELECT * FROM PermissionApplication")
    List<PermissionApplication> getAll();

    @Query("Select * From PermissionApplication Where area LIKE :area ")
    List<PermissionApplication> getAllApplicationsByArea(String area);

    @Insert
    void insertAll(List<PermissionApplication> users);

    @Update
    void update(PermissionApplication permissionApplication);

    @Delete
    void delete(PermissionApplication permissionApplication);
}
