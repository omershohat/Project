package Omer_Ran.Exceptions;

public class InvalidInputException extends CollegeExceptions {
    public InvalidInputException() {
        super("Invalid input.");
    }
    public InvalidInputException(String message) {
        super(message);
    }
}
