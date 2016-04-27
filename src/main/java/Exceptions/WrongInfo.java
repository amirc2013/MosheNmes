package Exceptions;

/**
 * Created by Amir on 4/27/2016.
 */
public class WrongInfo extends Exception {
    private String field;
    public WrongInfo(String field){
        super("Wrong given information");
        this.field = field;
    }

}
