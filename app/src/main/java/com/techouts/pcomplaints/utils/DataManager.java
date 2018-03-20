package com.techouts.pcomplaints.utils;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by TO-OW109 on 05-03-2018.
 */

public class DataManager {

    public static List<String> getList(String type){
        List<String> listNames = new ArrayList<>();
        try{
            switch (type){
                case AppConstents.TYPE_STATE :
                    listNames.add(AppConstents.TELANGANA);
                    break;
                case AppConstents.TYPE_CITY :
                    listNames.add(AppConstents.HYDERABAD);
                    break;
                case AppConstents.TYPE_AREA :
                    listNames.add("South Banjara Hills");
                    listNames.add("Madhapur");
                    listNames.add("Jubilee Hills");
                    listNames.add("Liberty");
                    listNames.add("Cyberabad");
                    listNames.add("Vijay Nagar Colony");
                    listNames.add("Somajiguda");
                    listNames.add("Seetharampet");
                    listNames.add("Ramkoti");
                    listNames.add("Gandhi Bhavan");
                    listNames.add("RajBolaram");
                    listNames.add("Sriranga Colony");
                    listNames.add("Vidya nagar");
                    listNames.add("Uppal");
                    listNames.add("Stn Kachiguda");
                    listNames.add("Snehapuri Colony");
                    listNames.add("Santhosh Nagar Colony");
                    listNames.add("L.B. Nagar");
                    listNames.add("Jntu");
                    listNames.add("KukatPalli");
                    listNames.add("Gachibowlli");
                    break;
                default:
                    Log.i("Data Manager","No List");
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return listNames;
    }

    public static List<String> getMenuList(String loginType){
        List<String> menuList = new ArrayList<>();
        try{
            switch (loginType){
                case AppConstents.LOGIN_ADMIN:
                    menuList.add(AppConstents.CREATE_SERVICE);
                    menuList.add(AppConstents.SERVICE_MAN_LIST);
                    menuList.add(AppConstents.CUSTOMER_LIST);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_SERVICE_MAN:
                    menuList.add(AppConstents.APPLICATION_LIST);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_CUSTOMER:
                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.MY_ACCOUNT);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_TYPE_NONE:
                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.SERVICE_MAN_LIST);
                    menuList.add(AppConstents.LOGIN);
                    break;
                default:
                    Log.i("Data Manager","No List");
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return menuList;
    }

    public static List<String> getServices(String serviceType){
        List<String>  permissionList = new ArrayList<>();;
        try{
            switch (serviceType){
                case AppConstents.POLICE_PERMISSIONS:
                    permissionList.add(AppConstents.GUN_LICENCES);
                    permissionList.add(AppConstents.INTERNET_CAFES);
                    permissionList.add(AppConstents.SNOOKERS_PARLOURS);
                    permissionList.add(AppConstents.PARKING_PLACES);
                    permissionList.add(AppConstents.EVENTS_FUNCTIONS_MIKES);
                    permissionList.add(AppConstents.LODGES_HOTELS);
                    permissionList.add(AppConstents.FILM_TV_SHOOTINGS);
                    permissionList.add(AppConstents.POLICE_BB_FOR_PVT_FUNCTIONS);
                    break;
                case AppConstents.MATRIMONIAL_VERIFICATIONS:
                    permissionList.add(AppConstents.MARTIMONIAL_VERIFICATION);
                    break;
                case AppConstents.DRAFTING_COMPLAINTS:
                    permissionList.add(AppConstents.CRIME_REPORT);
                    permissionList.add(AppConstents.NOCS);
                    permissionList.add(AppConstents.LICENCES_RENEWALS);
                    permissionList.add(AppConstents.CERTIFIED_COPIES);
                    permissionList.add(AppConstents.RTI_AND_APPEALS_TO_HIGHER_UPS);
                    break;
                case AppConstents.POLICE_IDENTITY_ADDRESS_TRACE:
                    permissionList.add(AppConstents.PHONE_ADDRESSES);
                    permissionList.add(AppConstents.ADDHAR_ID_PROOFS);
                    permissionList.add(AppConstents.DECLARED_AND_STATED_ADDRESS);
                    break;
                default:
                    Log.d("Services", "No Services available for this type");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return permissionList;
    }


    public static List<String> getAllServices(){
        List<String> servicesList = new ArrayList<>();
        try{
            servicesList.add(AppConstents.GUN_LICENCES);
            servicesList.add(AppConstents.INTERNET_CAFES);
            servicesList.add(AppConstents.SNOOKERS_PARLOURS);
            servicesList.add(AppConstents.PARKING_PLACES);
            servicesList.add(AppConstents.EVENTS_FUNCTIONS_MIKES);
            servicesList.add(AppConstents.LODGES_HOTELS);
            servicesList.add(AppConstents.FILM_TV_SHOOTINGS);
            servicesList.add(AppConstents.POLICE_BB_FOR_PVT_FUNCTIONS);
            servicesList.add(AppConstents.MARTIMONIAL_VERIFICATION);
            servicesList.add(AppConstents.CRIME_REPORT);
            servicesList.add(AppConstents.NOCS);
            servicesList.add(AppConstents.LICENCES_RENEWALS);
            servicesList.add(AppConstents.CERTIFIED_COPIES);
            servicesList.add(AppConstents.RTI_AND_APPEALS_TO_HIGHER_UPS);
            servicesList.add(AppConstents.PHONE_ADDRESSES);
            servicesList.add(AppConstents.ADDHAR_ID_PROOFS);
            servicesList.add(AppConstents.DECLARED_AND_STATED_ADDRESS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return servicesList;
    }


    public static  List<String> getUserLoginTypes(){
        List<String> userLoginType = new ArrayList<>();
        try{
            userLoginType.add(AppConstents.LOGIN_ADMIN);
            userLoginType.add(AppConstents.LOGIN_SERVICE_MAN);
            userLoginType.add(AppConstents.LOGIN_CUSTOMER);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return userLoginType;
    }
}
