package Exceptions;

/**
 * Created by Amir on 4/28/2016.
 */
public class AlreadyExist extends Exception{
    public AlreadyExist(String filed){
        super("The "+filed+" is already exist in the database.");
    }
}
