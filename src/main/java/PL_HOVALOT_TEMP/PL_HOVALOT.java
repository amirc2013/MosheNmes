package PL_HOVALOT_TEMP;

import BL_HOVALOT.IBL_HOVALOT;
import BackEnd.Truck;
import Exceptions.AlreadyExist;
import Exceptions.WrongInfo;

import java.io.IOException;
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
                isOn = trucksPickHandler(Integer.parseInt(sc.next()));
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
//                stationsActions();
                break;
            case 4:
//                deliveriesActions();
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
                    if (max < 1)
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
                    if (clean < 1)
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

    public void showTruckDetail() {
        print("");
        printl("Please provide Truck's license number : ");
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
