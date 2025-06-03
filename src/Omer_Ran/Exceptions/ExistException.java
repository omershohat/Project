package Omer_Ran.Exceptions;

import Omer_Ran.Nameable;

public class ExistException extends InvalidInputException {
    public ExistException(Nameable na) {
        super(na.getClass().getSimpleName() + " '" + na.getName() + "' already exists...");
    }

    public ExistException(Nameable na, Nameable inNa) {
        super(na.getClass().getSimpleName() + " '" + na.getName() + "' already exists in " + inNa.getName() + inNa.getClass().getSimpleName().toLowerCase() + "...");
    }

}
