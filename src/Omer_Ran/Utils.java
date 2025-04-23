package Omer_Ran;

import java.util.Arrays;

public class Utils {

    public static Object[] resizeArr(Object[] arr){
        return Arrays.copyOf(arr, arr.length == 0 ? 2 : arr.length * 2);
    }

    public static boolean isExist(Object[] arr, int numOfItems, Object item) {
        String itemName = getGeneralName(item);
        for (int i = 0; i < numOfItems ; i++) {
            String currentName = getGeneralName(arr[i]);
            if (currentName.equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    private static String getGeneralName(Object obj) {
        if (obj instanceof Lecturer) {
            return ((Lecturer) obj).getName();
        } else if (obj instanceof Department) {
            return ((Department) obj).getName();
        } else if (obj instanceof Committee) {
            return ((Committee) obj).getName();
        }
        return null;
    }

}
