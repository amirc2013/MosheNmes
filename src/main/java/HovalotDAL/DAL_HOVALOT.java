package HovalotDAL;

import BackEnd.*;
import BackEnd.Driver;
import Exceptions.*;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
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
                log.info("Truck num "+add.getLicense_num()+" NOT inserted to the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Truck num "+add.getLicense_num()+" NOT inserted to the DATABASE");
            throw new AlreadyExist("license_num");
        }
    }

    public void deleteTruck(Truck delete) throws NotExist {
        try {
            Statement stm = database.createStatement();
            String sql = "DELETE from trucks where license_num="+delete.getLicense_num()+";";

            if(stm.executeUpdate(sql) == 1) {
                log.info("Truck num "+delete.getLicense_num()+" got removed SUCCESSFULLY to the DATABASE");
            } else {
                log.info("Truck num "+delete.getLicense_num()+" NOT removed from the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Truck num "+delete.getLicense_num()+" NOT removed from the DATABASE");
            throw new NotExist("license_num");
        }
    }

    public void addDriver(Driver add) throws AlreadyExist {
        try {
            Statement stm = database.createStatement();
            List<String> l=add.getLicenses();
            for(int i=0; i<l.size(); i++) {
                String sql = "INSERT INTO drivers (driverID,license) " +
                        "VALUES (" + add.getDriverID() + ", '" + l.get(i) + "');";

                if (stm.executeUpdate(sql) != 1) {
                    log.info("Driver " + add.getDriverID() + " NOT inserted to the DATABASE");
                    break;
                }
            }
            log.info("Driver " + add.getDriverID() + " got inserted SUCCESSFULLY to the DATABASE");
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Driver "+add.getDriverID()+" NOT inserted to the DATABASE");
            throw new AlreadyExist("driverID, license");
        }
    }

    public Map<Integer, Participant> getPartInDelivery(Date d) {
        Map<Integer, Participant> ans=new HashMap<Integer, Participant>();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");

            Statement stm = database.createStatement();
            String sql = "SELECT delivery_destinations.adress , area , phone, contact, order_doc " +
            "FROM delivery_destinations join participants on delivery_destinations.adress = participants.adress where delivery_destinations.date = '"+ df.format(d) +"';" ;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                ans.put(rs.getInt("order_doc"),new Participant(rs.getString("adress"), rs.getString("area"), rs.getLong("phone"), rs.getString("contact")));
                log.info("We read here");
            }
            log.info("Participants in Delivery "+ df.format(d) +" are:\n");
            for(Map.Entry<Integer,Participant> ti : ans.entrySet()){
                log.info(ti.toString()+"/n");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (WrongInfo w){ /* Ignore*/  }
        return ans;
    }

    public Delivery getDelivery(Date date) {
        Delivery ans = null;
        try {
            Statement stm = database.createStatement();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
            String sql = "SELECT date , driverID , truck_nun , source " +
                    "FROM deliveries WHERE date = '"+ df.format(date) +"';" ;
            log.info(df.format(date));
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                try {
                    ans = new Delivery(new java.sql.Date(df.parse(rs.getString("date")).getTime()),getDriver(rs.getLong("driverID")),getTruck(rs.getLong("truck_nun")),getParticipant(rs.getString("source")),getPartInDelivery(date));
                } catch (ParseException e) {
                    log.info(e.getMessage());
                }
                log.info("A Delivery details has been read , with the details below :\n"+ans.toString());
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (WrongInfo w){ /* Ignore */ }
        return ans;
    }

    public void clearTable() {
        try {
            Statement stm = database.createStatement();
            String sql = "DELETE from deliveries;";
            stm.executeUpdate(sql);
            sql = "DELETE from delivery_destinations;";
            stm.executeUpdate(sql);
            sql = "DELETE from drivers;";
            stm.executeUpdate(sql);
            sql = "DELETE from participants;";
            stm.executeUpdate(sql);
            sql = "DELETE from trucks;";
            stm.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDelivery(Delivery add) throws AlreadyExist {
        SimpleDateFormat df = null;
        try {
            Statement stm = database.createStatement();
            Map<Integer, Participant> m= add.getDestinations();

           // log.info(add.getDate().toString());
            df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
            String sql = "INSERT INTO deliveries (date,driverID,truck_nun,source) " +
                    "VALUES ('"+ df.format(add.getDate()).toString() + "', '" + add.getDriver().getDriverID() +"' , '"+add.getTruck().getLicense_num()+"' , '"+add.getSource().getAddress()+ "');";
            if (stm.executeUpdate(sql) != 1) {
                df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
                log.info("Delivery " + df.format(add.getDate()).toString() + " NOT inserted to the DATABASE");
            }
            for (Map.Entry<Integer,Participant> entry : m.entrySet())
            {
                df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
                String sql2 = "INSERT INTO delivery_destinations (date,adress,order_doc) " +
                        "VALUES ('" + df.format(add.getDate()).toString() + "', '" + entry.getValue().getAddress() +"' , "+entry.getKey()+ ");";
                if (stm.executeUpdate(sql2) != 1) {
                    log.info("Delivery " + df.format(add.getDate()).toString() + " NOT inserted to the DATABASE");
                    break;
                }
            }
            log.info("Delivery " + df.format(add.getDate()) + " got inserted SUCCESSFULLY to the DATABASE");
        } catch (SQLException e) {
            log.info(e.getMessage());
            df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
            log.info("Delivery "+df.format(add.getDate())+" NOT inserted to the DATABASE");
            throw new AlreadyExist("date and time");
        }
    }

    public void deleteDelivery(Delivery delete) throws NotExist {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");

        try {

            Statement stm = database.createStatement();
            String sql = "DELETE from deliveries where date='"+df.format(delete.getDate())+"';";

            if(stm.executeUpdate(sql) == 1) {
                String sql2= "DELETE from delivery_destinations where date='"+df.format(delete.getDate())+"';";
                if(stm.executeUpdate(sql2)>=1)
                    log.info("Delivery "+df.format(delete.getDate())+" got removed SUCCESSFULLY to the DATABASE");
                else
                    log.info("Delivery "+df.format(delete.getDate())+" NOT removed from the DATABASE");
            } else {
                log.info("Delivery "+df.format(delete.getDate())+" NOT removed from the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Delivery " + df.format(delete.getDate()) + " NOT removed from the DATABASE");
            throw new NotExist("date and hour");
        }
    }

    public Participant getParticipant(String adress) {
        Participant ans = null;
        try {
            Statement stm = database.createStatement();
            String sql = "SELECT adress , area , phone, contact " +
                    "FROM participants WHERE adress = '"+ adress +"';" ;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                ans=new Participant(adress,rs.getString("area"),rs.getLong("phone"),rs.getString("contact"));
                log.info("A Participant's details has been read , with the details below :\n"+ans.toString());
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (WrongInfo w){ /* Ignore*/  }
        return ans;
    }

    public void addParticipant(Participant add) throws AlreadyExist {
        try {
            Statement stm = database.createStatement();
            String sql = "INSERT INTO participants (adress,area,phone,contact) " +
                    "VALUES ('"+add.getAddress()+"', '"+add.getArea()+"', '"+add.getPhone()+"', '"+add.getContact()+"');";

            if(stm.executeUpdate(sql) == 1) {
                log.info("Participant "+add.getAddress()+" got inserted SUCCESSFULLY to the DATABASE");
            } else {
                log.info("Participant "+add.getAddress()+" NOT inserted to the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Participant "+add.getAddress()+" NOT inserted to the DATABASE");
            throw new AlreadyExist("address");
        }
    }

    public void deleteParticipant(Participant delete) throws NotExist {
        try {
            Statement stm = database.createStatement();
            String sql = "DELETE from participants where adress='"+delete.getAddress()+"';";

            if(stm.executeUpdate(sql) == 1) {
                log.info("Participant "+delete.getAddress()+" got removed SUCCESSFULLY to the DATABASE");
            } else {
                log.info("Participant "+delete.getAddress()+" NOT removed from the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Participant " + delete.getAddress() + " NOT removed from the DATABASE");
            throw new NotExist("address");
        }
    }

    public void deleteDriver(Driver delete, String license) throws NotExist {
        try {
            Statement stm = database.createStatement();
            String sql = "DELETE from drivers where driverID="+delete.getDriverID()+" and license= '"+license+"';";

            if(stm.executeUpdate(sql) == 1) {
                log.info("Driver "+delete.getDriverID()+" got removed SUCCESSFULLY to the DATABASE");
            } else {
                log.info("Driver "+delete.getDriverID()+" NOT removed from the DATABASE");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            log.info("Driver " + delete.getDriverID() + " NOT removed from the DATABASE");
            throw new NotExist("driverID");
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
        Driver ans = null;
        List<String> licenses=new ArrayList<String>();
        try {
            Statement stm = database.createStatement();
            String sql = "SELECT driverID , license " +
                    "FROM drivers WHERE driverID = "+ driverID +";" ;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                licenses.add(rs.getString("license"));
            }
            if(licenses.size()!=0) {
                ans = new Driver(driverID, licenses);
                log.info("A Driver's details has been read , with the details below :\n"+ans.toString());
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (WrongInfo w){  log.info(w.getMessage()); }
        return ans;
    }

    public boolean driverHasLicense(long driverId, String license) {
        boolean ans=false;
        try {
            Statement stm = database.createStatement();
            String sql = "SELECT driverID , license " +
                    "FROM drivers WHERE driverID = "+ driverId +" and license= '"+license+"';" ;
            ResultSet rs = stm.executeQuery(sql);
            if(rs.next()) {
                ans = true;
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return ans;
    }

    /**
     * @inv: a valid license
     * @param license_num
     * @param t
     */
    public void editTruck(long license_num, Truck t) {
        try {
            Statement stm = database.createStatement();
            String sql = "UPDATE trucks " +
                         "set model = '"+t.getModel() +"' , color = '"+t.getColor() +"' , maximum_weight = "+t.getMax_weight() +" , clean_weight = "+t.getClean_weight()+" , license_needed = '"+t.getAppro_license()+"' " +
                         "where license_num = "+t.getLicense_num()+" ;";
            int o = stm.executeUpdate(sql);
            if(o==1){
                log.info("Truck "+license_num+" got updated SUCCESSFULLY.");
            } else {
                log.info("Truck "+license_num+" has NOT updated.");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }

    }


    public void editDriver(long driverId, String oldLicense, String newLicense) {
        try {
            Statement stm = database.createStatement();
            String sql = "UPDATE drivers " +
                    "set license = '"+newLicense+"' " +
                    "where driverID = "+driverId+" and license = "+oldLicense+" ;";
            int o = stm.executeUpdate(sql);
            if(o==1){
                log.info("driver and license "+driverId+" "+oldLicense+" got updated SUCCESSFULLY.");
            } else {
                log.info("driver and license "+driverId+" "+oldLicense+" has NOT updated.");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    public void editDelivery(Date date, Delivery d) {
        try {
            Statement stm = database.createStatement();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
            String sql = "UPDATE deliveries " +
                    "set driverID = "+d.getDriver().getDriverID()+", " +
                    "truck_nun = "+d.getTruck().getLicense_num()+", "+
                    "source = '"+d.getSource().getAddress()+"' "+
                    "where date = '"+df.format(d.getDate())+"' ;";
            editPartInDelivery(d);
            int o = stm.executeUpdate(sql);
            if(o==1){
                log.info("delivery "+date+" got updated SUCCESSFULLY.");
            } else {
                log.info("delivery "+date+ " was NOT updated.");
            }
        }
        catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void editPartInDelivery(Delivery d){
        try {
            Map<Integer,Participant> m=d.getDestinations();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm");
            Statement stm = database.createStatement();
            String sql = "DELETE from delivery_destinations where date='"+df.format(d.getDate())+"';";
            stm.executeUpdate(sql);
            for (Map.Entry<Integer, Participant> entry : m.entrySet()) {
                String sql2 = "INSERT INTO delivery_destinations (date,adress,order_doc) " +
                        "VALUES ('" +df.format(d.getDate()) + "', '" + entry.getValue().getAddress() + "' , '" + entry.getKey() + "');";
                stm.executeUpdate(sql2);
                log.info("participant"+entry.getValue().getAddress()+ " edited for "+df.format(d.getDate()));
            }
        }
        catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    public void editParticipant(String adress, Participant p) {
        try {
            Statement stm = database.createStatement();
            String sql = "UPDATE participants " +
                    "set area = '"+p.getArea()+"' , " +
                    "phone = "+p.getPhone()+" , "+
                    "contact = '"+p.getContact()+"' "+
                    "where adress = '"+adress+"' ;";
            int o = stm.executeUpdate(sql);
            if(o==1){
                log.info("participant "+adress+" got updated SUCCESSFULLY.");
            } else {
                log.info("participant "+adress+ " was NOT updated.");
            }
        }
        catch (SQLException e) {
            log.info(e.getMessage());
        }
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
