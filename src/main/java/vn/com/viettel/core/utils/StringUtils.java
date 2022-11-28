package vn.com.viettel.core.utils;

public class StringUtils {

    private StringUtils(){
        // Disable New Instance
    }

    public static boolean isNullOrEmpty(String inputStr){
        return inputStr == null || inputStr.length() == 0;
    }

    /**
     * Return Empty String If Input Is Null
     * @param inputStr
     * @return
     */
    public static String safeString(String inputStr){
        if(isNullOrEmpty(inputStr)){
            return "";
        }
        return inputStr;
    }

    /**
     * Trim string if input is not null or empty
     * @param inputStr
     * @return
     */
    public static String trimString(String inputStr){
        if(!isNullOrEmpty(inputStr)){
            return inputStr.trim();
        }
        return inputStr;
    }
}
