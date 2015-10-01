package fightingpit.foodtracker;

/**
 * Created by AG on 01-Oct-15.
 */
public class CommonUtils {

    public CommonUtils() {
        // Required empty public constructor
    }

    public static CommonUtils newInstance() {
        CommonUtils utils = new CommonUtils();
        return utils;
    }

    public boolean islengthValid(String iString){
        iString = iString.trim();
        boolean result = false;
        if(iString.length()>0){
            result = true;
        }
        return result;
    }

    public String makeProperFormat(String iString){
        iString= iString.trim();
        iString = iString.toLowerCase();
        iString = iString.substring(0,1).toUpperCase() + iString.substring(1);
        return iString;
    }
}
