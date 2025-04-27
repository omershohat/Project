package Omer_Ran;

import java.util.Arrays;

public class Utils {
    public static Object[] resizeArr(Object[] arr) {
        return Arrays.copyOf(arr, arr.length == 0 ? 2 : arr.length * 2);
    }

    public static Object findObject(Object[] arr, int numOfItems, Object item) {
        String itemName = getGeneralName(item);
        for (int i = 0; i < numOfItems; i++) {
            String currentName = getGeneralName(arr[i]);
            if (currentName.equals(itemName)) {
                return arr[i];
            }
        }
        return null;
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

    static boolean removeObject(Object obj, Object[] objects, int numOfObjects) {
        for (int i = 0; i < numOfObjects; i++) {                            // checking for existing object
            if (objects[i] == (obj)) {
                for (int j = i; j < numOfObjects - 1; j++) {                // if exist - from that object index, shift all objects left
                    if (objects[j] == null) {
                        break;
                    }
                    objects[j] = objects[j + 1];
                }
                objects[numOfObjects - 1] = null;                           // remove last doubled object
                return true;
            }
        }
        return false;                                                       // if not exists - ERROR
    }

}
