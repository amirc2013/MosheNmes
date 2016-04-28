import BackEnd.Truck;
import Exceptions.WrongInfo;
import HovalotDAL.DAL_HOVALOT;
import HovalotDAL.IDAL_HOVALOT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Amir on 4/27/2016.
 */
public class mainRun {

    public static void main(String[] args){

        IDAL_HOVALOT dal = DAL_HOVALOT.getInstance();

        dal.connectDatabase("AD1.db");

        Truck t = null;
        try {
            t = new Truck(123412341234L,"MAZDA","Red",400,20,"A2");
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }

        dal.addTruck(t);

        Truck temp = dal.getTruck(123412341234L);

        System.out.println(temp.getColor());

        dal.disconnectDatabase();


    }


}
