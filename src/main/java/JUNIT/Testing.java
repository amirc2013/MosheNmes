package JUNIT;
import static org.junit.Assert.assertEquals;

import BL_HOVALOT.BL_HOVALOT;
import BL_HOVALOT.IBL_HOVALOT;
import BackEnd.Delivery;
import BackEnd.Driver;
import BackEnd.Participant;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.NotExist;
import Exceptions.WrongInfo;
import HovalotDAL.DAL_HOVALOT;
import HovalotDAL.IDAL_HOVALOT;
import PL_HOVALOT_TEMP.IPL_HOVALOT;
import PL_HOVALOT_TEMP.PL_HOVALOT;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.util.*;

/**
 * Created by Amir on 5/1/2016.
 */
public class Testing {

    IDAL_HOVALOT idal ;
    IBL_HOVALOT ibl;
    IPL_HOVALOT ipl;


    @Test
    public void Checking_adding_Atruck() {

        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Truck original = null;
        idal.clearTable();

        try {
            idal.addTruck(original = new Truck(123123123,"Mazda","Red",220,10,"A2"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }

        Assert.assertEquals(original, ibl.getTruck(123123123));
        idal.disconnectDatabase();
    }


    @Test
    public void Checking_remove_Atruck() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Truck original = null;
        idal.clearTable();

        try {
            idal.addTruck(original = new Truck(123123123,"Mazda","Red",220,10,"A2"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }

        Assert.assertEquals(original, ibl.getTruck(123123123));

        try {
            ibl.deleteTruck(original);
        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(ibl.getTruck(123123123) == null);
        idal.disconnectDatabase();

    }




    @Test
    public void Checking_adding_Adriver_andRemove() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Driver original = null;
        idal.clearTable();

        List<String> s = new LinkedList<String>();
        s.add("a2");
        s.add("a6");
        try {
            ibl.addDriver(original = new Driver(232323,s));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getDriver(232323));

        try {
            ibl.deleteDriver(original,"a2");
        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(!ibl.getDriver(232323).getLicenses().contains("a2"));

        idal.disconnectDatabase();
    }


    @Test
    public void TestaddParticipate() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Participant original = null;
        idal.clearTable();


        try {
            ibl.addParticipant(original = new Participant("shevet levi","HASGG",0202002,"fofo"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getParticipant("shevet levi"));

        try {
            ibl.deleteParticipant(original);
        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(ibl.getParticipant("shevet levi")==null);

        idal.disconnectDatabase();
    }

    @Test
    public void DeliveryTest() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Participant original = null;
        idal.clearTable();


        try {
            ibl.addParticipant(original = new Participant("shevet levi","HASGG",0202002,"fofo"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getParticipant("shevet levi"));

        try {
            original.setArea("SHARONNNNN");
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }
        try {
            ibl.editParticipant("shevet levi",original);

        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(ibl.getParticipant("shevet levi").getArea().equals("SHARONNNNN"));

        idal.disconnectDatabase();
    }


    @Test
    public void addAfterDeleteTruck() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Truck original = null;
        idal.clearTable();
        try {
            idal.addTruck(original = new Truck(5125,"SHEV","Red",220,10,"A2"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }

        Assert.assertEquals(original, ibl.getTruck(5125));

        try {
            ibl.deleteTruck(original);
        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(ibl.getTruck(5125) == null);
        try {
            idal.addTruck(original = new Truck(5125,"SHEV","Red",220,10,"A2"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }

        Assert.assertEquals(original, ibl.getTruck(5125));
        idal.disconnectDatabase();
    }

    @Test
    public void TestaddAfterDeleteParticipate() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Participant original = null;
        idal.clearTable();


        try {
            ibl.addParticipant(original = new Participant("shevet levi","HASGG",0202002,"fofo"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getParticipant("shevet levi"));

        try {
            ibl.deleteParticipant(original);
        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(ibl.getParticipant("shevet levi")==null);

        try {
            ibl.addParticipant(original = new Participant("shevet levi","HASGG",0202002,"fofo"));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getParticipant("shevet levi"));

        idal.disconnectDatabase();
    }
    @Test
    public void addingAfterRemoveDriver() {
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        Driver original = null;
        idal.clearTable();

        List<String> s = new LinkedList<String>();
        s.add("a2");
        s.add("a6");
        try {
            ibl.addDriver(original = new Driver(232323,s));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getDriver(232323));

        try {
            ibl.deleteDriver(original,"a2");
        } catch (NotExist notExist) {
            notExist.printStackTrace();
        }

        Assert.assertTrue(!ibl.getDriver(232323).getLicenses().contains("a2"));
        try {
            ibl.addDriver(original = new Driver(232323,s));
        } catch (AlreadyExist alreadyExist) {
            alreadyExist.printStackTrace();
        } catch (WrongInfo wrongInfo) {
            wrongInfo.printStackTrace();
        }


        Assert.assertEquals(original, ibl.getDriver(232323));

        idal.disconnectDatabase();
    }

    @Test
    public void Checking_Searching_AtruckThatnotExist() {

        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        idal.clearTable();



        Assert.assertTrue(ibl.getTruck(123123123)==null );
        idal.disconnectDatabase();
    }

    @Test
    public void Checking_Searching_ADriverThatnotExist() {

        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
        idal.clearTable();



        Assert.assertTrue(ibl.getDriver(2222)==null );
        idal.disconnectDatabase();
    }

}
