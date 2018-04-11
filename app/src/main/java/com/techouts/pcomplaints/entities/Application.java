package com.techouts.pcomplaints.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by TO-OW109 on 08-02-2018.
 */

@Entity
public class Application implements Serializable{

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="registrationNo")
    public String applicationNo;
    @ColumnInfo(name = "firstName")
    public String firstName;
    @ColumnInfo(name = "lastName")
    public String lastName;
    @ColumnInfo(name="email")
    public String email;
    @ColumnInfo(name = "mobileNo")
    public String mobileNo;
    @ColumnInfo(name = "state")
    public String state;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "area")
    public String area;
    @ColumnInfo(name = "userImg")
    public String userImg;
    @ColumnInfo(name = "applicationType")
    public String applicationType;
    @ColumnInfo(name = "status")
    public int status = 0;
    @ColumnInfo(name = "isAccepted")
    public int isAccepted = 0;
    @ColumnInfo(name="xServiceManEmail")
    public String xServiceManEmail = "";
    @ColumnInfo(name="payableAmount")
    public double payableAmount = 0;
    @ColumnInfo(name="divisionPoliceStation")
    public String divisionPoliceStation = "";
}
