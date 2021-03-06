package com.techouts.pcomplaints.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by TO-OW109 on 06-02-2018.
 */

@Entity
public class User implements Serializable{
    @ColumnInfo(name = "firstName")
    public String firstName;
    @ColumnInfo(name = "lastName")
    public String lastName;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="email")
    public String email;
    @ColumnInfo(name="password")
    public String password;
    @ColumnInfo(name = "mobileNo")
    public String mobileNo;
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "area")
    public String area;
    @ColumnInfo(name = "location")
    public String location;
    @ColumnInfo(name = "userImg")
    public String userImg;
    @ColumnInfo(name = "userType")
    public String userType;
}
