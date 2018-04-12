package com.techouts.pcomplaints.entities;

import java.io.Serializable;

/**
 * Created by TO-OW109 on 06-02-2018.
 */
public class User implements Serializable{
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String mobileNo;
    public String state;
    public String city;
    public String area;
    public String userImg;
    public String userType;
    public String district = "";
    public String subDivision = "";
    public String circlePolicestation = "";
}
