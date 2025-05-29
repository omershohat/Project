package Omer_Ran;

import java.util.Comparator;

public class CompareComBySumArticles implements Comparator<Committee> {
    @Override
    public int compare(Committee o1, Committee o2) {
        return Integer.compare(o1.getSumOfArticles(), o2.getSumOfArticles());
    }
}
