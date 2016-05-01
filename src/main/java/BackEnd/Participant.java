package BackEnd;

import Exceptions.WrongInfo;

/**
 * Created by Amir on 4/27/2016.
 */
public class Participant {

    private String address;
    private String area;
    private  long phone;
    private String contact;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address)throws WrongInfo {
        if (address == null || address.equals(""))
            throw new WrongInfo("address");
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) throws WrongInfo{
        if (area == null || area.equals(""))
            throw new WrongInfo("area");
        this.area = area;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) throws WrongInfo{
        if (phone <0 )
            throw new WrongInfo("address");
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) throws WrongInfo{
        if (contact == null || contact.equals(""))
            throw new WrongInfo("contact");
        this.contact = contact;
    }

    public Participant(String address, String area, long phone, String contact) throws WrongInfo{

        setAddress(address);
        setArea(area);
        setPhone(phone);
        setContact(contact);
    }

    @Override
    public boolean equals(Object o){
        boolean ans = false;
        if(o instanceof  Participant)
            if(((Participant)o).address.equals(address))
                ans = true;

        return ans;
    }

    @Override
    public String toString(){
        return ""+address;
    }
}
