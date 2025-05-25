package Omer_Ran;

public class ExistException extends InvalidInputException {
    public ExistException(Nameable na) {
        super(na.getClass().getSimpleName() + " '" + na.getName() + "' already exists...");
    }

    public ExistException(Nameable na, Nameable inNa) {
        super(na.getClass().getSimpleName() + " '" + na.getName() + "' already exists in " + inNa.getName() + inNa.getClass().getSimpleName().toLowerCase() + "...");
    }

}
