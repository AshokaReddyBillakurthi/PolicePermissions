package com.techouts.pcomplaints.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techouts.pcomplaints.entities.ExServiceMan;

import java.util.List;

/**
 * Created by TO-OW109 on 26-02-2018.
 */
@Dao
public interface ExServiceManDao {

    @Query("SELECT * FROM ExServiceMan")
    List<ExServiceMan> getAll();

    @Query("SELECT * FROM User WHERE email LIKE :email")
    ExServiceMan findByEmail(String email);

    @Query("SELECT count(*) FROM ExServiceMan Where email LIKE :email AND password LIKE :password")
    int findExServiceManByEmailAndPassword(String email,String password);

    @Insert
    void insertAll(List<ExServiceMan> exServiceMans);

    @Update
    void update(ExServiceMan exServiceMan);

    @Delete
    void delete(ExServiceMan exServiceMan);
}
