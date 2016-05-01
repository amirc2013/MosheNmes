package BL_HOVALOT;

import BackEnd.Delivery;
import BackEnd.Driver;
import BackEnd.Participant;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.NotExist;
import HovalotDAL.IDAL_HOVALOT;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Amir on 4/28/2016.
 */
public class BL_HOVALOT implements IBL_HOVALOT {


    private static final Logger log= Logger.getLogger( BL_HOVALOT.class.getName() );
    private IDAL_HOVALOT idal = null;

    public Delivery getDelivery(Date date) {
        return idal.getDelivery(date);
    }

    public void addDelivery(Delivery add) throws AlreadyExist {
        Delivery temp = null;
        if((temp = getDelivery(add.getDate()))==null){
            idal.addDelivery(add);
        }
        else{
            AlreadyExist a =  new AlreadyExist("Delivery");
            log.info(a.getMessage());
            throw a;
        }
    }

    public void deleteDelivery(Delivery delete) throws NotExist {
        Delivery temp = null;
        if((temp = getDelivery(delete.getDate()))!=null){
            idal.deleteDelivery(delete);
        }
        else{
            NotExist n =  new NotExist("Delivery");
            log.info(n.getMessage());
            throw n;
        }
    }

    public Participant getParticipant(String adress) {
        return idal.getParticipant(adress);
    }

    public void addParticipant(Participant add) throws AlreadyExist {
        Participant temp = null;
        if((temp = idal.getParticipant(add.getAddress()))==null){
            idal.addParticipant(add);
        }
        else{
            AlreadyExist a =  new AlreadyExist("Participant");
            log.info(a.getMessage());
            throw a;
        }
    }

    public void deleteParticipant(Participant delete) throws NotExist {
        Participant temp = null;
        if((temp = idal.getParticipant(delete.getAddress()))!=null){
            idal.deleteParticipant(delete);
        }
        else{
            NotExist n =  new NotExist("Participant");
            log.info(n.getMessage());
            throw n;
        }
    }

    public Map<Integer, Participant> getPartInDelivery(Date d) {
        return idal.getPartInDelivery(d);
    }

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
        return idal.getDriver(driverID);
    }

    public void addDriver(Driver add) throws AlreadyExist {
        idal.addDriver(add);
    }

    public void deleteDriver(Driver delete, String license) throws NotExist {
        Driver temp = null;
        if((temp = idal.getDriver(delete.getDriverID()))!=null){
            idal.deleteDriver(delete,license);
        }
        else{
            NotExist n =  new NotExist("Driver");
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

    public void editTruck(long license_num, Truck t) throws NotExist {
        if(idal.getTruck(license_num)==null)
            throw new NotExist("Truck");
        else
            idal.editTruck(license_num,t);
    }

    public void editDriver(long driverId, String oldLicense, String newLicense) throws NotExist {
        if(idal.driverHasLicense(driverId,oldLicense))
            idal.editDriver(driverId,oldLicense,newLicense);
        else
            throw new NotExist("Driver, License");
    }

    public void editDelivery(Date date, Delivery d) throws NotExist {
        if(idal.getDelivery(date)==null)
            throw new NotExist("Delivery");
        else
            idal.editDelivery(date,d);
    }

    public void editParticipant(String adress, Participant p) throws NotExist {
        if(idal.getParticipant(adress)==null)
            throw new NotExist(adress);
        else
            idal.editParticipant(adress,p);
    }
}
