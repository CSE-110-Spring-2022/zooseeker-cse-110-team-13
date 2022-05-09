package com.example.group13zoosearch;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converters {
    // Store as JSON
    @TypeConverter
    public static List<String> fromJsonToList(String jsonStr) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(jsonStr, listType);
    }

    @TypeConverter
    public static String fromListToJson(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
//
//    // Store as comma separated values
//    @TypeConverter
//    public static List<String> fromCsvToList(String csvStr) {
//        return Arrays.asList(csvStr.split(",", 0));
//    }
//
//    @TypeConverter
//    public static String fromListToCsv(List<String> list) {
//        return String.join(",", list);
//    }
}