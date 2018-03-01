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
public class PermissionApplication implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="registrationNo")
    public String applicationNo;
    @ColumnInfo(name="fullName")
    public String fullName;
    @ColumnInfo(name="occupation")
    public String occupation;
    @ColumnInfo(name="parentage")
    public String parentage;
    @ColumnInfo(name="nationality")
    public String nationality;
    @ColumnInfo(name="owneremail")
    public String owneremail;
    @ColumnInfo(name="telephoneNo")
    public String telephoneNo;
    @ColumnInfo(name="cyberCafeEmail")
    public String cyberCafeEmail;
    @ColumnInfo(name="noOfBranchs")
    public String noOfBranchs;
    @ColumnInfo(name="areaOfPremise")
    public String areaOfPremise;
    @ColumnInfo(name="noOfTerminals")
    public String noOfTerminals;
    @ColumnInfo(name="premise")
    public String premise;
    @ColumnInfo(name="applicantImg")
    public String applicantImg;
    @ColumnInfo(name="applicationType")
    public String applicationType;
    @ColumnInfo(name="area")
    public String area;
    @ColumnInfo(name="socialStatus")
    public String socialStatus;
    @ColumnInfo(name="headForLicense")
    public String headForLicense;
    @ColumnInfo(name="qtyAmmuition")
    public String qtyAmmuition;
    @ColumnInfo(name="oldLicenseNo")
    public String oldLicenseNo;
    @ColumnInfo(name="sex")
    public String sex;
    @ColumnInfo(name="placeOfCrime")
    public String placeOfCrime;
    @ColumnInfo(name="condOfProp")
    public String condOfProp;
    @ColumnInfo(name="address")
    public String address;

}
