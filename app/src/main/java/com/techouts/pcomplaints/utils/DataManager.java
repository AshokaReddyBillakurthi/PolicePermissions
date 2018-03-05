package com.techouts.pcomplaints.utils;

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
                    menuList.add(AppConstents.LOGOUT);
                    break;
                case AppConstents.LOGIN_TYPE_NONE:
                    menuList.add(AppConstents.SERVICES);
                    menuList.add(AppConstents.SERVICE_MAN_LIST);
                    menuList.add(AppConstents.LOGIN);
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return menuList;
    }
}
