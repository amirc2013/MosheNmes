package BackEnd;

import Exceptions.WrongInfo;

import java.awt.*;

/**
 * Created by Amir on 4/27/2016.
 */
public class Truck {
    private long license_num;
    private String model;
    private String color;
    private int max_weight;
    private int clean_weight;
    private String appro_license;

    public Truck (long license_num, String model, String color, int max_weight, int clean_weight, String appro_license) throws WrongInfo{
        setLicense_num(license_num);
        setModel(model);
        setColor(color);
        setMax_weight(max_weight);
        setClean_weight(clean_weight);
        setAppro_license(appro_license);
    }

    public long getLicense_num() {
        return license_num;
    }

    public void setLicense_num(long license_num) throws WrongInfo {
        if(license_num<0)
            throw new WrongInfo("license_num");
        this.license_num = license_num;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) throws WrongInfo {
        if(model == null || model.equals(""))
            throw new WrongInfo("model");
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) throws WrongInfo{
        this.color = color;
    }

    public int getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(int max_weight)throws WrongInfo {
        if(max_weight<0)
            throw new WrongInfo("max_weight");
        this.max_weight = max_weight;
    }

    public int getClean_weight() {
        return clean_weight;
    }

    public void setClean_weight(int clean_weight)throws WrongInfo {
        if(clean_weight<0)
            throw new WrongInfo("clean_weight");
        this.clean_weight = clean_weight;
    }

    public String getAppro_license() {
        return appro_license;
    }

    public void setAppro_license(String appro_license)throws WrongInfo {
        if(appro_license == null || appro_license.equals(""))
            throw new WrongInfo("appro_license");
        this.appro_license = appro_license;
    }

    public String toString(){
        return  "Truck's license number     "+license_num+"\n" +
                "Max weight :     "+ getMax_weight()+"\n" +
                "Model :        "+getModel()+"\n" +
                "Color :        "+getColor()+"\n" +
                "Clean weight :         "+getClean_weight()+"\n" +
                "Appropriate license :      "+getAppro_license()+"\n" ;
    }

    public boolean equals(Object o){
        if(o instanceof Truck && ((Truck)o).getLicense_num()==license_num)
            return true;
        return false;
    }
}
