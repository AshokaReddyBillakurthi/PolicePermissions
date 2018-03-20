package com.techouts.pcomplaints.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.techouts.pcomplaints.entities.User;

import java.util.List;

/**
 * Created by TO-OW109 on 06-02-2018.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM User Where userType Like :userType")
    List<User> getAll(String userType);

    @Query("SELECT * FROM User WHERE email LIKE :email LIMIT 1")
    User getUserDetailsByEmailId(String email);

    @Query("SELECT count(*) FROM User Where email LIKE :email AND password LIKE :password")
    int findUserByEmailAndPassword(String email,String password);
    @Insert
    void insertAll(List<User> users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
