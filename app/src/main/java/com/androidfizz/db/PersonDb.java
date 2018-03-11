package com.androidfizz.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.androidfizz.dao.PersonDao;
import com.androidfizz.model.ModelPerson;

/**
 * Created by Aklesh on 3/9/2018.
 */
@Database(entities = {ModelPerson.class,}, version = 1)
public abstract class PersonDb extends RoomDatabase {

    private static final String DB_NAME = "personDB";
    private static volatile PersonDb instance;


    public static PersonDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, PersonDb.class, DB_NAME)
                    .allowMainThreadQueries()//TO ALLOW DATABASE OPERATION ON MAIN THREAD
                    .build();
        }
        return instance;
    }

    public abstract PersonDao getPersonDao();
}
