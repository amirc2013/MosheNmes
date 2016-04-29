package HovalotDAL;

import BackEnd.*;
import Exceptions.*;

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



}
