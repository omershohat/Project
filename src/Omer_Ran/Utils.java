package Omer_Ran;

import java.util.ArrayList;

public class Utils {

    public static Nameable findObject(ArrayList<? extends Nameable> arrayList, Nameable item) {
        String itemName = item.getName();
        for (Nameable na : arrayList) {
            if (na.getName().equals(itemName)) {
                return na;
            }
        }
        return null;
    }
}
