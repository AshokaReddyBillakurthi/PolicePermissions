package com.techouts.pcomplaints.datahandler;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.techouts.pcomplaints.daos.ApplicationDao;
import com.techouts.pcomplaints.daos.ExServiceManDao;
import com.techouts.pcomplaints.daos.PermissionApplicationDao;
import com.techouts.pcomplaints.daos.UserDao;
import com.techouts.pcomplaints.entities.Application;
import com.techouts.pcomplaints.entities.ExServiceMan;
import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.entities.User;

/**
 * Created by TO-OW109 on 05-02-2018.
 */

@Database(entities = {User.class, PermissionApplication.class,ExServiceMan.class, Application.class},
        version = 1, exportSchema = false)
public abstract class DatabaseHandler extends RoomDatabase {
    private static DatabaseHandler database;
    private static final String DATABASE_NAME = "PoliceServicesDB";

    public static DatabaseHandler getInstance(Context context) {
        if (database == null)
            database = Room.databaseBuilder(context, DatabaseHandler.class, DATABASE_NAME).build();

        return database;
    }

    public abstract UserDao userDao();
    public abstract ExServiceManDao exServiceManDao();
    public abstract PermissionApplicationDao permissionApplicationDao();
    public abstract ApplicationDao applicationDao();
}