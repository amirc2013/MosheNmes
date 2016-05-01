package BL_HOVALOT;

import BackEnd.Delivery;
import BackEnd.Driver;
import BackEnd.Participant;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.NotExist;

import java.util.Date;
import java.util.Map;

/**
 * Created by Amir on 4/28/2016.
 */
public interface IBL_HOVALOT {

    void addTruck(Truck t) throws AlreadyExist;
    void deleteTruck(Truck t) throws NotExist;
    Truck getTruck(long license_num);
    void editTruck(long license_num,Truck t)throws NotExist;

    Driver getDriver(long driverID);
    void addDriver(Driver add) throws AlreadyExist;
    void deleteDriver(Driver delete, String license) throws NotExist;
    void editDriver(long driverId, String oldLicense, String newLicense) throws NotExist;


    Delivery getDelivery(Date date);
    void addDelivery(Delivery add) throws AlreadyExist;
    void deleteDelivery(Delivery delete) throws NotExist;
    void editDelivery(Date date,Delivery d)throws NotExist;

    Participant getParticipant(String adress);
    void addParticipant(Participant add) throws AlreadyExist;
    void deleteParticipant(Participant delete) throws NotExist;
    void editParticipant(String adress, Participant p)throws NotExist;

    Map<Integer, Participant> getPartInDelivery(Date d);

}
