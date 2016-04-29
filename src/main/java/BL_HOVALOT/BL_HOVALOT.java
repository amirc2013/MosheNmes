package BL_HOVALOT;

import BackEnd.Driver;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.NotExist;
import HovalotDAL.IDAL_HOVALOT;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Amir on 4/28/2016.
 */
public class BL_HOVALOT implements IBL_HOVALOT {


    private static final Logger log= Logger.getLogger( BL_HOVALOT.class.getName() );
    private IDAL_HOVALOT idal = null;

    public void deleteTruck(Truck t) throws NotExist {
        Truck temp = null;
        if((temp = idal.getTruck(t.getLicense_num()))!=null){
            idal.deleteTruck(t);
        }
        else{
            NotExist n =  new NotExist("Truck");
            log.info(n.getMessage());
            throw n;
        }
    }

    public Driver getDriver(long driverID) {
        return null;
    }

    public void addDriver(Driver add) throws AlreadyExist {
        Driver temp = null;
        if((temp = idal.getDriver(add.getDriverID()))==null){
            idal.addDriver(add);
        }
        else{
            AlreadyExist a =  new AlreadyExist("Driver");
            log.info(a.getMessage());
            throw a;
        }
    }

    public void deleteDriver(Driver delete) throws NotExist {
        Driver temp = null;
        if((temp = idal.getDriver(delete.getDriverID()))!=null){
            idal.deleteDriver(delete);
        }
        else{
            NotExist n =  new NotExist("Truck");
            log.info(n.getMessage());
            throw n;
        }
    }

    public void addTruck(Truck t) throws AlreadyExist {
        Truck temp = null;
        if((temp = idal.getTruck(t.getLicense_num()))==null){
            idal.addTruck(t);
        }
        else{
            AlreadyExist a =  new AlreadyExist("Truck");
            log.info(a.getMessage());
            throw a;
        }
    }

    public Truck getTruck(long license_num) {
        return idal.getTruck(license_num);
    }

    //singleton
    private BL_HOVALOT(IDAL_HOVALOT idal){
        this.idal = idal;
        FileHandler fh;
        try {
            log.setUseParentHandlers(false);
            // This block configure the logger with handler and formatter
            fh = new FileHandler("Log_BL.log");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BL_HOVALOT instance = null;

    public static BL_HOVALOT getInstance(IDAL_HOVALOT idal){
        if(instance == null)
            instance = container.getContent(idal);
        return instance;
    }

    private static class container{
        private static  BL_HOVALOT getContent(IDAL_HOVALOT idal){
            return new BL_HOVALOT(idal);
        }
    }



}
