//package com.example.group13zoosearch;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executors;
//
//@Database(entities = {ZooData.class}, version = 1)
//public abstract class ZooDataBase extends RoomDatabase {
//    private static ZooDataBase singleton = null;
//
//    public abstract myDao myDao();
//
//    public synchronized static ZooDataBase getSingleton(Context context) {
//        if (singleton == null) {
//            singleton = ZooDataBase.makeDatabase(context);
//        }
//        return singleton;
//    }
//
//    private static ZooDataBase makeDatabase(Context context) {
//        return Room.databaseBuilder(context, ZooDataBase.class, "todo_app.db")
//                .allowMainThreadQueries()
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
//                            Map<String, ZooData.VertexInfo> zoos =
//                                    ZooData.loadVertexInfoJSON(context, "demo_todos.json");
//
//                            getSingleton(context).myDao().insertAll(zoos);
//                        });
//                    }
//                })
//                .build();
//    }
//}
