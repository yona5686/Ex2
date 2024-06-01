package com.example.ex2.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Users")
    List<UsersTables> getAll();

    @Query("SELECT * FROM Users WHERE uid = :id")
    UsersTables findUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UsersTables usersTables);}
