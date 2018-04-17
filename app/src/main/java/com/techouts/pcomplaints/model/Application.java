package com.techouts.pcomplaints.model;

import java.io.Serializable;

/**
 * Created by TO-OW109 on 08-02-2018.
 */

public class Application implements Serializable{
    public String applicationNo;
    public String firstName;
    public String lastName;
    public String email;
    public String mobileNo;
    public String state;
    public String city;
    public String area;
    public String userImg;
    public String applicationType;
    public int status = 0;
    public int isAccepted = 0;
    public String xServiceManEmail = "";
    public double payableAmount = 0;
    public String district = "";
    public String subDivision = "";
    public String circlePolicestation = "";
}
