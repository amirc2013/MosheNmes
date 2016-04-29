import BL_HOVALOT.BL_HOVALOT;
import BL_HOVALOT.IBL_HOVALOT;
import BackEnd.Truck;
import Exceptions.WrongInfo;
import HovalotDAL.DAL_HOVALOT;
import HovalotDAL.IDAL_HOVALOT;
import PL_HOVALOT_TEMP.IPL_HOVALOT;
import PL_HOVALOT_TEMP.PL_HOVALOT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Amir on 4/27/2016.
 */
public class mainRun {
    private static IDAL_HOVALOT idal = null;
    private static IBL_HOVALOT ibl = null;
    private static IPL_HOVALOT ipl = null;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        initializeSystem();

        /* Write your code loop here */
        boolean isOn = true;

        while(isOn){
            ipl.showMainMenu();
            try {
                isOn = ipl.mainMenuPick(Integer.parseInt(sc.next()));
            } catch (NumberFormatException e) {
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                isOn = true;
            }
        }
        shutdownSystem();
    }

    private static void initializeSystem(){
        idal = DAL_HOVALOT.getInstance();
        idal.connectDatabase("AD1.db");
        ibl = BL_HOVALOT.getInstance(idal);
        ipl = PL_HOVALOT.getInstance(ibl);
    }

    private static void shutdownSystem(){
        idal.disconnectDatabase();
    }

    private static void print(String msg){
        System.out.println(msg);
    }

}
