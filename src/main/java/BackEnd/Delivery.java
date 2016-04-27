package BackEnd;

import Exceptions.WrongInfo;

import java.util.Date;
import java.util.Map;

/**
 * Created by Amir on 4/27/2016.
 */
public class Delivery {

    private Date date;
    private Driver driver;
    private Truck truck;
    private Participant source;
    private Map<Integer,Participant> destinations;

    public Delivery(Date date, Driver driver, Truck truck, Participant source, Map<Integer, Participant> destinations) throws WrongInfo {
        this.date = date;
        this.driver = driver;
        this.truck = truck;
        this.source = source;
        this.destinations = destinations;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) throws WrongInfo{
        if(date == null)
            throw new WrongInfo("date");
        this.date = date;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) throws WrongInfo{
        if(driver == null)
            throw new WrongInfo("driver");
        this.driver = driver;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck)throws WrongInfo {
        if(truck == null)
            throw new WrongInfo("truck");
        this.truck = truck;
    }

    public Participant getSource() {
        return source;
    }

    public void setSource(Participant source)throws WrongInfo {
        if(source == null)
            throw new WrongInfo("source");
        this.source = source;
    }

    public Map<Integer, Participant> getDestinations() {
        return destinations;
    }

    public void setDestinations(Map<Integer, Participant> destinations) throws WrongInfo{
        if(destinations == null || destinations.size() == 0)
            throw new WrongInfo("destinations");
        this.destinations = destinations;
    }
}
