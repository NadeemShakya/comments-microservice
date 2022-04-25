package comment.util;

import com.google.gson.JsonObject;


public class BaseUtil {
    /**
     * Gets json object created using the passed key and value
     *
     * @param key   The key of jsonObject
     * @param value The value of jsonObject
     * @return JsonObject
     */
    public static JsonObject getJsonObject(String key, String value) {
        var jsonObject = new JsonObject();

        jsonObject.addProperty(key, value);

        return jsonObject;
    }
}