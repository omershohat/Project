package Omer_Ran;

import java.util.Comparator;

public class CompareComByMemAmount implements Comparator<Committee> {
    @Override
    public int compare(Committee o1, Committee o2) {
        return Integer.compare(o1.getNumOfLecturers(), o2.getNumOfLecturers());
    }
}
