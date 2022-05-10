package com.example.group13zoosearch;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

@Dao
public interface myDao {
    @Insert
    long insert(ZooData vertexInfo);

    @Insert
    List<Long> insertAll(List<ZooData> zooItems);

    @Query("SELECT * FROM `zoo_items` WHERE `mid`=:id")
    ZooData get(long id);

    @Query("SELECT * FROM `zoo_items`")
    List<ZooData> getAll();

//    @Query("SELECT * FROM `zoo_items`")
//    LiveData<List<String>> getAllLive();

    @Update
    int update(ZooData vertexInfo);

    @Delete
    int delete(ZooData vertexInfo);

}
