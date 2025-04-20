package Omer_Ran;

import java.util.Scanner;

// Omer 315854091
// Ran  212929269

public class Main {
    private static Scanner s;
    public static void main(String[] args) {
        s = new Scanner(System.in);
        getCollegeName(s).run();
        s.close();
    }
    private static College getCollegeName(Scanner s) {
        System.out.print("Please enter a college name: ");
        College college = new College(s.next());
        return college;
    }
}
