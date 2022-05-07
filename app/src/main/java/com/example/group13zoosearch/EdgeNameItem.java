package com.example.group13zoosearch;

        import android.content.Context;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.Reader;
        import java.lang.reflect.Type;
        import java.util.Collections;
        import java.util.List;

public class EdgeNameItem {
    public String id;
    public String street;

    EdgeNameItem(String id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public String toString() {
        return "EdgeNameItem{" +
                "id='" + id + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    public static List<EdgeNameItem> loadNodeInfoJSON(Context context, String path){
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<EdgeNameItem>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
