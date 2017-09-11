package com.sairam.data.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.sairam.data.model.NewsArticleModel;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 * Room Database helper
 */

@Database(entities = {NewsArticleModel.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "InshortsDB";
    public static AppDataBase dataBase;

    public static AppDataBase getDatabase(Context context){
        if(dataBase == null){
            dataBase = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return dataBase;
    }

    public abstract NewsDao getNewsDao();
}
