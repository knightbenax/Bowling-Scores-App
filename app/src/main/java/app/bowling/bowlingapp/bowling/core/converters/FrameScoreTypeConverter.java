package app.bowling.bowlingapp.bowling.core.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;
import app.bowling.bowlingapp.bowling.core.models.FrameScore;

public class FrameScoreTypeConverter {

    /**
     * This class bascially helps Room take care of converting the List of scores to
     * Strings for easy saving.
     * **/
    
    private static Gson gson = new Gson();
    
    @TypeConverter
    public static List<FrameScore> stringToObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<FrameScore>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<FrameScore> someObjects) {
        return gson.toJson(someObjects);
    }
}