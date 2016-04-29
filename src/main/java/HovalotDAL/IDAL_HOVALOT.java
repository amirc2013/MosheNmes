package HovalotDAL;

import BackEnd.Driver;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.WrongInfo;

/**
 * Created by Amir on 4/27/2016.
 */
public interface IDAL_HOVALOT {

    boolean connectDatabase(String databaseName);
    boolean disconnectDatabase();


    Truck getTruck(long license_num);
    void addTruck(Truck add) throws AlreadyExist;


    Driver getDriver(long driverID);




}
