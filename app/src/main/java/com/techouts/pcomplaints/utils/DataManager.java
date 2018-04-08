package com.techouts.pcomplaints.utils;

import android.util.Log;

import com.techouts.pcomplaints.entities.Area;
import com.techouts.pcomplaints.entities.City;
import com.techouts.pcomplaints.entities.State;

import java.util.ArrayList;
import java.util.List;

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

    public static List<State> getStateList(){
        List<State> listStates = new ArrayList<>();
        State state = null;
        try{
            state = new State();
            state.stateName = AppConstents.TELANGANA;
            listStates.add(state);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listStates;
    }

    public static List<City> getCityList(){
        List<City> listCities = new ArrayList<>();
        City city = null;
        try{
            city = new City();
            city.cityName = AppConstents.HYDERABAD;
            listCities.add(city);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listCities;
    }

    public static List<Area> getAreaList(){
        List<Area> listAreas = new ArrayList<>();
        Area area = null;
        try{
            area = new Area();
            area.areaName = "South Banjara Hills";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Madhapur";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Jubilee Hills";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Liberty";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Cyberabad";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Vijay Nagar Colony";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Somajiguda";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Seetharampet";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Ramkoti";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Gandhi Bhavan";
            listAreas.add(area);
            area = new Area();
            area.areaName = "RajBolaram";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Sriranga Colony";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Vidya nagar";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Uppal";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Stn Kachiguda";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Snehapuri Colony";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Santhosh Nagar Colony";
            listAreas.add(area);
            area = new Area();
            area.areaName = "L.B. Nagar";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Jntu";
            listAreas.add(area);
            area = new Area();
            area.areaName = "KukatPalli";
            listAreas.add(area);
            area = new Area();
            area.areaName = "Gachibowlli";
            listAreas.add(area);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listAreas;
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
                    menuList.add(AppConstents.CHAT);
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_CUSTOMER:
                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.MY_ACCOUNT);
                    menuList.add(AppConstents.CHAT);
                    menuList.add(AppConstents.MY_COMPLAINTS);
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
        List<String>  permissionList = new ArrayList<>();
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


    public static  List<String> getInstructionsForCyberCafesPermission(){
        List<String> listPermissionInstructions = new ArrayList<>();
        try{
            listPermissionInstructions.add(PermissionInstruConstents.APPLICATION_ESEVE);
            listPermissionInstructions.add(PermissionInstruConstents.DETAILS);
            listPermissionInstructions.add(PermissionInstruConstents.DOCS);
            listPermissionInstructions.add(PermissionInstruConstents.MUNICIPAL_PERMISSION);
            listPermissionInstructions.add(PermissionInstruConstents.LEASE_AGR);
            listPermissionInstructions.add(PermissionInstruConstents.SITE_PLAN);
            listPermissionInstructions.add(PermissionInstruConstents.RENT_RECEIPTS);
            listPermissionInstructions.add(PermissionInstruConstents.TAX_RECEIPTS);
            listPermissionInstructions.add(PermissionInstruConstents.NOC_FIRE_DEPT);
            listPermissionInstructions.add(PermissionInstruConstents.CER_BSNL_TEL);
            listPermissionInstructions.add(PermissionInstruConstents.NOC_NEIG);
            listPermissionInstructions.add(PermissionInstruConstents.GAMBLING_MACH);
            listPermissionInstructions.add(PermissionInstruConstents.ID_RESIDENTIAL);
            listPermissionInstructions.add(PermissionInstruConstents.SERVER_CLIENT);
            listPermissionInstructions.add(PermissionInstruConstents.PARTNERSHIP_DEED);
            listPermissionInstructions.add(PermissionInstruConstents.MEM_ASSOCI);
            listPermissionInstructions.add(PermissionInstruConstents.ARTICLE_ASSOCI);
            listPermissionInstructions.add(PermissionInstruConstents.BOND);
            listPermissionInstructions.add(PermissionInstruConstents.IT_RETURNS);
            listPermissionInstructions.add(PermissionInstruConstents.FEE_PAYABLE);
            listPermissionInstructions.add(PermissionInstruConstents.SERVICE_CHARGES);
            listPermissionInstructions.add(PermissionInstruConstents.FRESH_LICENCE_CHARGE);
            listPermissionInstructions.add(PermissionInstruConstents.ANNUAL_RENEWAL_CHARGE);
            listPermissionInstructions.add(PermissionInstruConstents.SUBMIT_ESEVA);
            listPermissionInstructions.add(PermissionInstruConstents.LICENCE_ISSUED);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listPermissionInstructions;
    }

    public static List<String> getInstructionForArmsLicense(){
        List<String> listInstruArmsLicense = new ArrayList<>();
        try{
            listInstruArmsLicense.add(PermissionInstruConstents.APPLICATION_ESEVE);
            listInstruArmsLicense.add(PermissionInstruConstents.FORM_A);
            listInstruArmsLicense.add(PermissionInstruConstents.DOCS);
            listInstruArmsLicense.add(PermissionInstruConstents.RESIDENTIAL_PROOF);
            listInstruArmsLicense.add(PermissionInstruConstents.ORIGINAL_ARMS_LICENCE);
            listInstruArmsLicense.add(PermissionInstruConstents.ORIGINAL_CHALLAN);
            listInstruArmsLicense.add(PermissionInstruConstents.NO_OBJECTIONS);
            listInstruArmsLicense.add(PermissionInstruConstents.NOC_FAMILY_MEMBERS);
            listInstruArmsLicense.add(PermissionInstruConstents.PASSPORT_PHOTOS);
            listInstruArmsLicense.add(PermissionInstruConstents.IT_RETURNS);
            listInstruArmsLicense.add(PermissionInstruConstents.FEE_PAYABLE);
            listInstruArmsLicense.add(PermissionInstruConstents.SERVICE_CHARGES);
            listInstruArmsLicense.add(PermissionInstruConstents.FRESH_LICENCE_CHARGE);
            listInstruArmsLicense.add(PermissionInstruConstents.ANNUAL_RENEWAL_CHARGE);
            listInstruArmsLicense.add(PermissionInstruConstents.SUBMIT_ESEVA);
            listInstruArmsLicense.add(PermissionInstruConstents.LICENCE_ISSUED);
            listInstruArmsLicense.add(PermissionInstruConstents.NOTE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listInstruArmsLicense;
    }
}
