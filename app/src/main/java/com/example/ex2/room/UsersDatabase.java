package com.example.ex2.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UsersTables.class}, version = 3)
public abstract class UsersDatabase extends RoomDatabase {
    private static UsersDatabase instance;

    public static synchronized UsersDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UsersDatabase.class, "UserDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
}
