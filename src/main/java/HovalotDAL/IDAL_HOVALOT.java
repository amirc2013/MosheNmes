package HovalotDAL;

import BackEnd.*;
import Exceptions.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Amir on 4/27/2016.
 */
public interface IDAL_HOVALOT {

    boolean connectDatabase(String databaseName);
    boolean disconnectDatabase();


    Truck getTruck(long license_num);
    void addTruck(Truck add) throws AlreadyExist;
    void deleteTruck(Truck delete) throws NotExist;


    Driver getDriver(long driverID);
    void addDriver(Driver add) throws AlreadyExist;
    void deleteDriver(Driver delete) throws NotExist;

    Delivery getDelivery(Date date);
    void addDelivery(Delivery add) throws AlreadyExist;
    void deleteDelivery(Delivery delete) throws NotExist;

    Participant getParticipant(String adress);
    void addParticipant(Participant add) throws AlreadyExist;
    void deleteParticipant(Participant delete) throws NotExist;

    Map<Integer, Participant> getPartInDelivery(Date d);

}
