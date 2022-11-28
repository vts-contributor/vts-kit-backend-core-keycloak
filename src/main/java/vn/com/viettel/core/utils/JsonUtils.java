package vn.com.viettel.core.utils;

import com.google.gson.Gson;

class JsonUtils {
    private static final Gson gson = new Gson();
    private JsonUtils(){
        // Disable New Instance
    }

    public static String cvtObjToJson(Object object){
        return gson.toJson(object);
    }

}
