package Omer_Ran.Exceptions;

public class CollegeExceptions extends RuntimeException{
    private static final String PRE_MESSAGE = "Error: ";
    public CollegeExceptions(String message) {
        super(PRE_MESSAGE + message);
    }
}
