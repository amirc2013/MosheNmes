package PL_HOVALOT_TEMP;

import BL_HOVALOT.IBL_HOVALOT;
import BackEnd.Driver;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.NotExist;
import Exceptions.WrongInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Amir on 4/28/2016.
 */
public class PL_HOVALOT implements IPL_HOVALOT{

    private static Scanner sc = new Scanner(System.in);

    private static final Logger log= Logger.getLogger( PL_HOVALOT.class.getName() );
    private IBL_HOVALOT ibl = null;


    public boolean mainMenuPick(int pick) {
        switch (pick){
            case 1:
                trucksActions();
                break;
            case 2:
                driversActions();
                break;
            case 3:
                stationsActions();
                break;
            case 4:
                deliveriesActions();
                break;
            case 5:
                print("");
                print("Thank you for using our system.\nGOOD BYE.");
                print("");
                return false;
            default:
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                break;
        }
        return true;
    }

    public void showMainMenu() {
        print("");
        print("Welcome to Hovalot menu, please pick a Section :");
        print("1. Trucks");
        print("2. Drivers");
        print("3. Stations");
        print("4. Deliveries");
        print("5. EXIT");
        print("");
    }

    private void trucksActions(){
        boolean isOn = true;

        while(isOn){
            showTrucksMenu();
            try {
                isOn = trucksPickHandler(Integer.parseInt(sc.next()));
            } catch (NumberFormatException e) {
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                isOn = true;
            }
        }


    }

    private void driversActions(){
        boolean isOn = true;

        while(isOn){
            showDriversMenu();
            try {
                isOn = driversPickHandler(Integer.parseInt(sc.next()));
            } catch (NumberFormatException e) {
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                isOn = true;
            }
        }


    }

    private void stationsActions(){
        boolean isOn = true;

        while(isOn){
            showStationsMenu();
            try {
                isOn = trucksPickHandler(Integer.parseInt(sc.next()));
            } catch (NumberFormatException e) {
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                isOn = true;
            }
        }


    }

    private void deliveriesActions(){
        boolean isOn = true;

        while(isOn){
            showDeliveriesMenu();
            try {
                isOn = trucksPickHandler(Integer.parseInt(sc.next()));
            } catch (NumberFormatException e) {
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                isOn = true;
            }
        }


    }
    private boolean driversPickHandler(int pick){
        switch (pick){
            case 1:
                try {
                    showDriverDetail();
                } catch (Exception e) {
                    log.info(e.getMessage());
                    print("");
                    print("You wrote a wrong input, please choose a valid choise.");
                    print("");
                }
                break;
            case 2:
                addDriverLiecense();
                break;
            case 3:
                editDriver();
                break;
            case 4:
                deleteTruck();
                break;
            case 5:
                return false;
            default:
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                break;
        }
        return true;
    }

    private boolean trucksPickHandler(int pick){
        switch (pick){
            case 1:
                try {
                    showTruckDetail();
                } catch (Exception e) {
                    log.info(e.getMessage());
                    print("");
                    print("You wrote a wrong input, please choose a valid choise.");
                    print("");
                }
                break;
            case 2:
                addTrucks();
                break;
            case 3:
                editTruck();
                break;
            case 4:
                deleteTruck();
                break;
            case 5:
                return false;
            default:
                print("");
                print("You wrote a wrong input, please choose a valid choise.");
                print("");
                break;
        }
        return true;
    }
    private void editDriver(){
        boolean more = true;
        long license_num = 0;
        String color=null;
        String model =null;
        String appro = null;
        int max = -1;
        int clean = -1;
        Truck toEdit = null;
        while (more){
            boolean correct = false;

            while (!correct) {
                print("");
                printl("Please provide a Truck's license number you want to edit : ");
                try {
                    sc = new Scanner(System.in);
                    license_num = sc.nextLong();
                    if (license_num < -1)
                        throw new Exception();
                    if ((toEdit=ibl.getTruck(license_num)) == null) {
                        if (license_num == -1)
                            return;
                        print("");
                        print("There is not such as Truck in our database. Try again.\nOr write -1 to cancel.");

                        correct = false;
                    } else {
                        correct = true;
                    }
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }


            correct = false;
            while (!correct) {
                print("");
                print("Your current model : "+toEdit.getModel()+"\n");
                printl("Please provide an alternative model or write -2 to continue  : ");
                try {
                    sc = new Scanner(System.in);
                    model = sc.next();
                    if (model.equals("") || model == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if (model.equals("-1"))
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current color : "+toEdit.getColor()+"\n");
                printl("Please provide an alternative color or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    color = sc.next();
                    if (color.equals("") || color == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(color.equals("-1"))
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current maximum weight : "+toEdit.getMax_weight()+"\n");
                printl("Please provide an alternative maximum weight or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    max = sc.nextInt();
                    if (max < 1 && max != -2 && max != -1)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(max == -1)
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current clean weight : "+toEdit.getClean_weight()+"\n");
                printl("Please provide an alternative clean weight or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    clean = sc.nextInt();
                    if (clean < 1 && clean != -1 &&  clean != -2)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(clean == -1)
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current appropriate license : "+toEdit.getAppro_license()+"\n");
                printl("Please provide an alternative appropriate license or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    appro = sc.next();
                    if (appro.equals("") || appro == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(appro.equals("-1"))
                return;



            try {
                if(!color.equals("-2"))
                    toEdit.setColor(color);
                if(max != -2)
                    toEdit.setMax_weight(max);
                if(clean!=-2)
                    toEdit.setClean_weight(clean);
                if(!model.equals("-2"))
                    toEdit.setModel(model);
                if(!appro.equals("-2"))
                    toEdit.setAppro_license(appro);
                ibl.editTruck(license_num,toEdit);
            } catch (NotExist notExist) {
                log.info(notExist.getMessage());
                print("");
                print("If you get THIS message please contact us. something went wrong.");
                return;
            } catch (WrongInfo wrongInfo) {
                log.info(wrongInfo.getMessage());
                print("");
                print("If you get THIS message please contact us. something went wrong.");
                return;
            }
            print("");
            print("A Truck with license number "+license_num+" has been added Successfully");


            correct = false;
            while (!correct) {
                print("");
                printl("Do you want to add an other one ? write Y/N :");
                String ans = sc.next();
                if (ans.equals("N") || ans.equals("n")) {
                    correct = true;
                    more = false;
                } else if (!ans.equals("Y") && !ans.equals("y")) {
                    print("");
                    print("Wrong input. ");

                } else {
                    correct = true;
                }
            }
        }





    }


    private void editTruck(){
        boolean more = true;
        long license_num = 0;
        String color=null;
        String model =null;
        String appro = null;
        int max = -1;
        int clean = -1;
        Truck toEdit = null;
        while (more){
            boolean correct = false;

            while (!correct) {
                print("");
                printl("Please provide a Truck's license number you want to edit : ");
                try {
                    sc = new Scanner(System.in);
                    license_num = sc.nextLong();
                    if (license_num < -1)
                        throw new Exception();
                    if ((toEdit=ibl.getTruck(license_num)) == null) {
                        if (license_num == -1)
                            return;
                        print("");
                        print("There is not such as Truck in our database. Try again.\nOr write -1 to cancel.");

                        correct = false;
                    } else {
                        correct = true;
                    }
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }


            correct = false;
            while (!correct) {
                print("");
                print("Your current model : "+toEdit.getModel()+"\n");
                printl("Please provide an alternative model or write -2 to continue  : ");
                try {
                    sc = new Scanner(System.in);
                    model = sc.next();
                    if (model.equals("") || model == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if (model.equals("-1"))
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current color : "+toEdit.getColor()+"\n");
                printl("Please provide an alternative color or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    color = sc.next();
                    if (color.equals("") || color == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(color.equals("-1"))
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current maximum weight : "+toEdit.getMax_weight()+"\n");
                printl("Please provide an alternative maximum weight or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    max = sc.nextInt();
                    if (max < 1 && max != -2 && max != -1)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(max == -1)
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current clean weight : "+toEdit.getClean_weight()+"\n");
                printl("Please provide an alternative clean weight or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    clean = sc.nextInt();
                    if (clean < 1 && clean != -1 &&  clean != -2)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(clean == -1)
                return;

            correct = false;
            while (!correct) {
                print("");
                print("Your current appropriate license : "+toEdit.getAppro_license()+"\n");
                printl("Please provide an alternative appropriate license or write -2 to continue : ");
                try {
                    sc = new Scanner(System.in);
                    appro = sc.next();
                    if (appro.equals("") || appro == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            if(appro.equals("-1"))
                return;



            try {
                if(!color.equals("-2"))
                    toEdit.setColor(color);
                if(max != -2)
                    toEdit.setMax_weight(max);
                if(clean!=-2)
                    toEdit.setClean_weight(clean);
                if(!model.equals("-2"))
                    toEdit.setModel(model);
                if(!appro.equals("-2"))
                    toEdit.setAppro_license(appro);
                ibl.editTruck(license_num,toEdit);
            } catch (NotExist notExist) {
                log.info(notExist.getMessage());
                print("");
                print("If you get THIS message please contact us. something went wrong.");
                return;
            } catch (WrongInfo wrongInfo) {
                log.info(wrongInfo.getMessage());
                print("");
                print("If you get THIS message please contact us. something went wrong.");
                return;
            }
            print("");
            print("A Truck with license number "+license_num+" has been added Successfully");


            correct = false;
            while (!correct) {
                print("");
                printl("Do you want to add an other one ? write Y/N :");
                String ans = sc.next();
                if (ans.equals("N") || ans.equals("n")) {
                    correct = true;
                    more = false;
                } else if (!ans.equals("Y") && !ans.equals("y")) {
                    print("");
                    print("Wrong input. ");

                } else {
                    correct = true;
                }
            }
        }





    }

    private  void deleteTruck(){
        boolean more = true;
        long license_num = 0;
        Truck toDelete = null;
        while (more){
            boolean correct = false;

            while (!correct) {
                print("");
                printl("Please provide license number : ");
                try {
                    sc = new Scanner(System.in);
                    license_num = sc.nextLong();
                    if (license_num < -1)
                        throw new Exception();
                    if ((toDelete = ibl.getTruck(license_num)) == null) {

                        if (license_num == -1)
                            return;
                        print("");
                        print("There is not such as Truck in our database. Try again.\nOr write -1 to cancel.");

                        correct = false;
                    } else {
                        correct = true;
                    }
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }

            try {
                ibl.deleteTruck(toDelete);
            } catch (NotExist notExist) {
                log.info(notExist.getMessage());

                print("");
                print("If you get THIS message please contact us. something went wrong.");
                print("");
                return;


            }

            print("");
            print("A Truck with license number "+license_num+" has been removed Successfully");


            correct = false;
            while (!correct) {
                print("");
                printl("Do you want to remove an other one ? write Y/N :");
                String ans = sc.next();
                if (ans.equals("N") || ans.equals("n")) {
                    correct = true;
                    more = false;
                } else if (!ans.equals("Y") && !ans.equals("y")) {
                    print("");
                    print("Wrong input. ");

                } else {
                    correct = true;
                }
            }
        }


    }


    private void addDriverLiecense(){
        boolean more = true;
        long driverID = -1 ;
        while (more){
            Driver d = null;
            List<String> licenses=new ArrayList<String>();
            boolean correct = false;

            while (!correct) {
                print("");
                printl("Please provide a Driver ID : ");
                try {
                    sc = new Scanner(System.in);
                    driverID = sc.nextLong();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }

            if (driverID == -1)
                return;
            d = ibl.getDriver(driverID);
            String license = null;
            correct = false;
            while (!correct) {
                print("");
                printl("Please provide a license  : ");
                try {
                    sc = new Scanner(System.in);
                    license = sc.next();

                    if (license.equals("") || license == null)
                        throw new Exception();
                    if (license.equals("-1"))
                        return;
                    if(d != null && !d.getLicenses().contains(license))
                        licenses.add(license);
                    else if (d==null)
                        licenses.add(license);
                    else{
                        print("");
                        print("Already exist in our database, its ok.");
                    }
                    print("");
                    printl("Do you want to add another license ? write Y/N :");
                    String ans = sc.next();

                    if (ans.equals("N") || ans.equals("n")) {
                        correct = true;
                    } else if (!ans.equals("Y") && !ans.equals("y")) {
                        print("");
                        print("Wrong input. ");

                    } else {
                        correct = false;
                    }
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
            correct = false;
            Driver t = null;
            try {
                t= new Driver(driverID,licenses);
            } catch (WrongInfo wrongInfo) {
                log.info(wrongInfo.getMessage());
                print("");
                print("If you get THIS message please contact us. something went wrong.");
                return;
            }
            try {
                ibl.addDriver(t);
            } catch (AlreadyExist alreadyExist) {
                log.info(alreadyExist.getMessage());
                print("");
                print("If you get THIS message please contact us. something went wrong.");
                return;
            }
            print("");
            print("A Driver with ID -  "+driverID+" has been added Successfully with his licenses");


            correct = false;
            while (!correct) {
                print("");
                printl("Do you want to add an other driver's licenses ? write Y/N :");
                String ans = sc.next();
                if (ans.equals("N") || ans.equals("n")) {
                    correct = true;
                    more = false;
                } else if (!ans.equals("Y") && !ans.equals("y")) {
                    print("");
                    print("Wrong input. ");

                } else {
                    correct = true;
                }
            }
        }


    }
    private void addTrucks(){
        boolean more = true;
        long license_num = 0;
        String color=null;
        String model =null;
        String appro = null;
        int max = -1;
        int clean = -1;
        while (more){
            boolean correct = false;

            while (!correct) {
                print("");
                printl("Please provide license number : ");
                try {
                    sc = new Scanner(System.in);
                    license_num = sc.nextLong();
                    if (license_num < -1)
                        throw new Exception();
                    if (ibl.getTruck(license_num) != null) {
                        print("");
                        print("License number already exist. Please check that out.");

                        correct = false;
                    } else {
                        correct = true;
                    }
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }

                if (license_num == -1)
                    return;

            correct = false;
            while (!correct) {
                print("");
                printl("Please provide model  : ");
                try {
                    sc = new Scanner(System.in);
                    model = sc.next();
                    if (model.equals("") || model == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
                if (model.equals("-1"))
                    return;

            correct = false;
            while (!correct) {
                print("");
                printl("Please provide color  : ");
                try {
                    sc = new Scanner(System.in);
                    color = sc.next();
                    if (color.equals("") || color == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
                if(color.equals("-1"))
                    return;

            correct = false;
            while (!correct) {
                print("");
                printl("Please provide maximum weight  : ");
                try {
                    sc = new Scanner(System.in);
                    max = sc.nextInt();
                    if (max < 1 && max != -1)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
                if(max == -1)
                    return;

            correct = false;
            while (!correct) {
                print("");
                printl("Please provide clean weight  : ");
                try {
                    sc = new Scanner(System.in);
                    clean = sc.nextInt();
                    if (clean < 1 && clean != -1)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
                if(clean == -1)
                    return;

            correct = false;
            while (!correct) {
                print("");
                printl("Please provide appropriate license  : ");
                try {
                    sc = new Scanner(System.in);
                    appro = sc.next();
                    if (appro.equals("") || appro == null)
                        throw new Exception();
                    correct = true;
                } catch (Exception e) {
                    print("");
                    print("Wrong input. write -1 if you want to cancel.");
                }
            }
                if(appro.equals("-1"))
                    return;

                Truck temp = null;
                try {
                    temp = new Truck(license_num,model,color,max,clean,appro);
                } catch (WrongInfo wrongInfo) {
                    log.info(wrongInfo.getMessage());
                    print("");
                    print("If you get THIS message please contact us. something went wrong.");
                    return;
                }
                try {
                    ibl.addTruck(temp);
                } catch (AlreadyExist alreadyExist) {
                    log.info(alreadyExist.getMessage());
                    print("");
                    print("If you get THIS message please contact us. something went wrong.");
                    return;
                }
                print("");
                print("A Truck with license number "+license_num+" has been added Successfully");


            correct = false;
            while (!correct) {
                print("");
                printl("Do you want to add an other one ? write Y/N :");
                String ans = sc.next();
                if (ans.equals("N") || ans.equals("n")) {
                    correct = true;
                    more = false;
                } else if (!ans.equals("Y") && !ans.equals("y")) {
                    print("");
                    print("Wrong input. ");

                } else {
                    correct = true;
                }
            }
        }


    }
    public void showDriverDetail() {
        print("");
        printl("Please provide a Driver's ID : ");
        long driverID = sc.nextLong();
        Driver t = ibl.getDriver(driverID);
        if(t != null){
            print("");
            print("The details for the Driver's ID number  "+driverID+" : ");
            print("Licenses : \n");
            for(String s : t.getLicenses()){
                print(s+"\n");
            }
            pressENTERKeyToContinue();
        } else {
            print("");
            print("There is not such as Driver's ID number in our database.");
            print("");
        }
    }


    public void showTruckDetail() {
        print("");
        printl("Please provide a Truck's license number : ");
        long license_num = sc.nextLong();
        Truck t = ibl.getTruck(license_num);
        if(t != null){
            print("");
            print("The details for the Truck's license number "+license_num+" : ");
            print("Max weight "+ t.getMax_weight());
            print("Model : "+t.getModel());
            print("Color : "+t.getColor());
            print("Clean weight : "+t.getClean_weight());
            print("Appropriate license : "+t.getAppro_license());
            pressENTERKeyToContinue();
        } else {
            print("");
            print("There is not such as license number in our database.");
            print("");
        }
    }


    private void pressENTERKeyToContinue()
    {
        print("");
        System.out.println("Press the ENTER key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }

    //singleton
    private PL_HOVALOT(IBL_HOVALOT ibl){
        this.ibl = ibl;
        FileHandler fh;
        try {
            log.setUseParentHandlers(false);
            // This block configure the logger with handler and formatter
            fh = new FileHandler("Log_PL.log");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PL_HOVALOT instance = null;

    public static PL_HOVALOT getInstance(IBL_HOVALOT ibl){
        if(instance == null)
            instance = container.getContent(ibl);
        return instance;
    }

    private static class container{
        private static  PL_HOVALOT getContent(IBL_HOVALOT ibl){
            return new PL_HOVALOT(ibl);
        }
    }

    private void showTrucksMenu(){
        print("");
        print("Welcome to Trucks menu, please pick a Section :");
        print("1. View Truck's details.");
        print("2. Add a Truck to your database.");
        print("3. Edit a Truck's details.");
        print("4. Delete a Truck from you database. ");
        print("5. Return back to main menu.");
        print("");
    }

    private void showDriversMenu(){
        print("");
        print("Welcome to Drivers menu, please pick a Section :");
        print("1. View Driver's details.");
        print("2. Add a Driver to your database.");
        print("3. Edit a Driver's details.");
        print("4. Delete a Driver from your database. ");
        print("5. Return back to main menu.");
        print("");
    }

    private void showStationsMenu(){
        print("");
        print("Welcome to Stations menu, please pick a Section :");
        print("1. View Station's details.");
        print("2. Add a Station to your database.");
        print("3. Edit a Station's details.");
        print("4. Delete a Station from your database. ");
        print("5. Return back to main menu.");
        print("");
    }

    private void showDeliveriesMenu(){
        print("");
        print("Welcome to Deliveries menu, please pick a Section :");
        print("1. View Delivery's details.");
        print("2. Add a Delivery to your database.");
        print("3. Edit a Delivery's details.");
        print("4. Delete a Delivery from your database. ");
        print("5. Return back to main menu.");
        print("");
    }

    private static void print(String msg){
        System.out.println(msg);
    }
    private static void printl(String msg){
        System.out.print(msg);
    }

}
