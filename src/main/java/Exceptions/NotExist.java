package Exceptions;

/**
 * Created by משה on 29/04/2016.
 */
public class NotExist extends Exception {
    public NotExist(String filed) {
        super("The "+filed+" does not exist in the database.");
    }
}
