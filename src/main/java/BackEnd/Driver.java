package BackEnd;


import Exceptions.WrongInfo;

import java.util.List;

/**
 * Created by Amir on 4/27/2016.
 */
public class Driver {

    private long driverID;
    private List<String> licenses;

    public Driver(long driverID, List<String> licenses) throws WrongInfo {
        setDriverID(driverID);
        setLicenses(licenses);
    }

    public long getDriverID() {
        return driverID;
    }

    public void setDriverID(long driverID) throws WrongInfo {
        if(driverID<0)
            throw new WrongInfo("driverID");
            this.driverID = driverID;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<String> licenses) throws WrongInfo {
        if (licenses == null)
            throw new WrongInfo("licenses");
        this.licenses = licenses;
    }

    @Override
    public String toString(){
        return ""+driverID;
    }
}
