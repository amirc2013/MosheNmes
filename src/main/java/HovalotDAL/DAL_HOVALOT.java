package HovalotDAL;

import BackEnd.Driver;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.WrongInfo;

import java.io.IOException;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Amir on 4/27/2016.
 */
public class DAL_HOVALOT implements IDAL_HOVALOT {

    private static final Logger log= Logger.getLogger( DAL_HOVALOT.class.getName() );

    private Connection database = null;




    public boolean connectDatabase(String databaseName) {
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            database = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
            log.info("Opened database successfully");

        } catch ( Exception e ) {
            log.info( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
        return true;
    }

    public boolean disconnectDatabase(){
        try {
            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void addTruck(Truck add) throws AlreadyExist {
        try {
            Statement stm = database.createStatement();
            String sql = "INSERT INTO trucks (license_num,model,color,clean_weight,maximum_weight,license_needed) " +
                         "VALUES ("+add.getLicense_num()+", '"+add.getModel()+"', '"+add.getColor()+"', "+add.getClean_weight()+","+add.getMax_weight()+",'"+add.getAppro_license()+"');";

            if(stm.executeUpdate(sql) == 1) {
                log.info("Truck num "+add.getLicense_num()+" got inserted SUCCESSFULLY to the DATABASE");
            } else {
                log.info("Truck num "+add.getLicense_num()+" HAS NOT inserted to the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Truck num "+add.getLicense_num()+" HAS NOT inserted to the DATABASE");
            throw new AlreadyExist("license_num");
        }
    }

    /**
     * get Truck from DB, return null if is not exist or if an EXCEPTION occured
     * @param license_num
     * @return
     */
    public Truck getTruck(long license_num) {
        Truck ans = null;
        try {
            Statement stm = database.createStatement();
            String sql = "SELECT license_num , model , color , clean_weight , maximum_weight, license_needed " +
                         "FROM trucks WHERE license_num = "+ license_num +";" ;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                ans = new Truck(rs.getLong("license_num"),rs.getString("model"),rs.getString("color"),rs.getInt("maximum_weight"),rs.getInt("clean_weight"),rs.getString("license_needed"));
                log.info("A Truck details has been read , with the details below :\n"+ans.toString());
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (WrongInfo w){ /* Ignore */ }
        return ans;
    }

    public Driver getDriver(long driverID) {
        return null;
    }



    //singleton
    private DAL_HOVALOT(){
        FileHandler fh;

        try {
            log.setUseParentHandlers(false);
            // This block configure the logger with handler and formatter
            fh = new FileHandler("Log.log");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DAL_HOVALOT instance = null;

    public static DAL_HOVALOT getInstance(){
        if(instance == null)
            instance = container.getContent();
        return instance;
    }

    private static class container{
        private static  DAL_HOVALOT getContent(){
            return new DAL_HOVALOT();
        }
    }


}
