package BL_HOVALOT;

import BackEnd.Driver;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.NotExist;

/**
 * Created by Amir on 4/28/2016.
 */
public interface IBL_HOVALOT {

    void addTruck(Truck t) throws AlreadyExist;
    void deleteTruck(Truck t) throws NotExist;
    Truck getTruck(long license_num);

    Driver getDriver(long driverID);
    void addDriver(Driver add) throws AlreadyExist;
    void deleteDriver(Driver delete) throws NotExist;

}
