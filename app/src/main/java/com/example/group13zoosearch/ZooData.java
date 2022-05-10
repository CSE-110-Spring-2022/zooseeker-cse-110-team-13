package com.example.group13zoosearch;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity(tableName = "zoo_items")
public class ZooData {

    @PrimaryKey(autoGenerate = true)
    public long mid = 0;

    @NonNull
        public String id;
        public String kind;
        public String name;
        public List<String> tags;

//    public static class VertexInfo {
//        public static enum Kind {
//            // The SerializedName annotation tells GSON how to convert
//            // from the strings in our JSON to this Enum.
//            @SerializedName("gate") GATE,
//            @SerializedName("exhibit") EXHIBIT,
//            @SerializedName("intersection") INTERSECTION
//        }
//
//
//        @NonNull
//        public String id;
//        public Kind kind;
//        public String name;
//        public List<String> tags;
//    }

//    public static Map<String, ZooData.VertexInfo> loadVertexInfoJSON(Context context, String path){
//        try {
//            InputStream inputStream = context.getAssets().open(path);
//            Reader reader = new InputStreamReader(inputStream);
//
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<ZooData.VertexInfo>>() {
//            }.getType();
//            List<ZooData.VertexInfo> zooData = gson.fromJson(reader, type);
//
//            // This code is equivalent to:
//            //
//            // Map<String, ZooData.VertexInfo> indexedZooData = new HashMap();
//            // for (ZooData.VertexInfo datum : zooData) {
//            //   indexedZooData[datum.id] = datum;
//            // }
//            //
//            Map<String, ZooData.VertexInfo> indexedZooData = zooData
//                    .stream()
//                    .collect(Collectors.toMap(v -> v.id, datum -> datum));
//
//            return indexedZooData;
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return Collections.emptyMap();
//        }
//    }

    public static List<ZooData> loadVertexInfoJSON(Context context, String path){
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<ZooData>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
