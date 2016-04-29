package BL_HOVALOT;

import BackEnd.Truck;
import Exceptions.AlreadyExist;

/**
 * Created by Amir on 4/28/2016.
 */
public interface IBL_HOVALOT {

    void addTruck(Truck t) throws AlreadyExist;
    Truck getTruck(long license_num);


}
