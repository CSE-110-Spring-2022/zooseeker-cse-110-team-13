//package com.example.group13zoosearch;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//import java.util.Map;
//
//@Dao
//public interface myDao {
//    @Insert
//    long insert(String str);
//
//    @Insert
//    List<Long> insertAll(Map<String, ZooData.VertexInfo> zooItems);
//
//    @Query("SELECT * FROM `zoo_items` WHERE `ID`=:id")
//    String get(long id);
//
//    @Query("SELECT * FROM `zoo_items`")
//    Map<String, ZooData.VertexInfo> getAll();
//
//    @Query("SELECT * FROM `zoo_items`")
//    LiveData<List<String>> getAllLive();
//
//    @Update
//    int update(String todoListItem);
//
//    @Delete
//    int delete(String todoListItem);
//
//}
