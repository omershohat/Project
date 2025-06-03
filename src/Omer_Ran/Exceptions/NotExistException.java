package Omer_Ran.Exceptions;

import Omer_Ran.Nameable;

public class NotExistException extends InvalidInputException {
    public NotExistException(Nameable na) {
        super(na.getClass().getSimpleName() + " '" + na.getName() + "' does not exist...");
    }
    public NotExistException(Nameable na, Nameable inNa) {
        super(na.getClass().getSimpleName() + " '" + na.getName() + "' does not exist in " + inNa.getName() + " " + inNa.getClass().getSimpleName().toLowerCase() + "...");
    }
    public NotExistException(String message) {
        super(message);
    }
}
