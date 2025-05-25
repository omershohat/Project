package Omer_Ran;

public class InvalidInputException extends CollegeExceptions {
    public InvalidInputException() {
        super("Invalid input.");
    }
    public InvalidInputException(String message) {
        super(message);
    }
}
