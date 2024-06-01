package com.example.ex2.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users", indices = {@Index(value = {"uid"}, unique = true)})
public class UsersTables {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "count")
    public int count;
    @ColumnInfo(name = "uid")
    public String uid;
    @ColumnInfo(name = "firstname")
    public String firstname;
    @ColumnInfo(name = "lastname")
    public String lastname;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "age")
    public double age;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "picture")
    public String picture;


}
