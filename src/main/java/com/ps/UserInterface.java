package com.ps;

import com.ps.DAO.LeaseDAO;
import com.ps.DAO.SalesDAO;
import com.ps.DAO.VehicleDAO;
import com.ps.models.LeaseContract;
import com.ps.models.SalesContract;
import com.ps.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private static VehicleDAO vehicleDAO;
    private static SalesDAO salesDAO;
    private static LeaseDAO leaseDAO;
    private static Scanner scanner = new Scanner(System.in);

    public static void init(String[] args) {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/cardealershipdatabase");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(args[0]);
        basicDataSource.setPassword(args[1]);
        vehicleDAO = new VehicleDAO(basicDataSource);
        leaseDAO = new LeaseDAO(basicDataSource);
        salesDAO = new SalesDAO(basicDataSource);

    }

    public static void display(String[] args) {

        init(args);

        int command;

        do {

            System.out.println("What would you like to do?");
            System.out.println("1) Search Vehicles");
            System.out.println("2) Manage Vehicles");
            System.out.println("3) Sales and Leases");
            System.out.println("0) Exit");

            command = scanner.nextInt();

            scanner.nextLine();

            switch (command) {
                case 1:
                    searchVehicles();
                    break;
                case 2:
                    manageVehicles();
                    break;
                case 3:
                    manageSalesAndLeases();
                    break;
                case 0:
                    System.out.println("Exit Dealership");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (command != 0);
    }

    private static void searchVehicles() {

        int choice;

        do {

            System.out.println("Search Vehicles:");
            System.out.println("1) Get all Vehicles");
            System.out.println("2) Make/Model");
            System.out.println("3) Year Range");
            System.out.println("4) Color");

            System.out.println("0. Go Back");

            choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice) {

                case 1:

                    List<Vehicle> allVehicles = vehicleDAO.getAllVehicles();

                    for (Vehicle vehicle: allVehicles) {
                        System.out.println(vehicle);
                    }

                    break;

                case 2:

                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();

                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();

                    List<Vehicle> vehiclesByMakeModel = vehicleDAO.getVehiclesByMakeModel(make, model);

                    vehiclesByMakeModel.forEach(System.out::println);

                    break;

                case 3:

                    System.out.print("Enter start year: ");
                    int startYear = scanner.nextInt();

                    System.out.print("Enter end year: ");
                    int endYear = scanner.nextInt();

                    List<Vehicle> vehiclesByYearRange = vehicleDAO.getVehiclesByYearRange(startYear, endYear);

                    vehiclesByYearRange.forEach(System.out::println);

                    break;

                case 4:

                    System.out.print("Enter color: ");
                    String color = scanner.nextLine();

                    List<Vehicle> vehiclesByColor = vehicleDAO.getVehiclesByColor(color);
                    vehiclesByColor.forEach(System.out::println);

                    break;

                case 0:

                    System.out.println("Going back to last screen");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    private static void manageVehicles() {

        int choice;

        do {

            System.out.println("What would you like to do?:");
            System.out.println("1) Add Vehicle");
            System.out.println("2) Remove Vehicle");
            System.out.println("0) Go Back");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter VIN: ");
                    String vin = scanner.nextLine();

                    System.out.print("Enter make: ");
                    String make = scanner.nextLine();

                    System.out.print("Enter model: ");
                    String model = scanner.nextLine();

                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Enter color: ");
                    String color = scanner.nextLine();

                    Vehicle vehicle = new Vehicle(vin, make, model, year, color);
                    vehicleDAO.createVehicle(vehicle);

                    System.out.println("Vehicle added successfully.");
                    break;

                case 2:

                    System.out.print("Enter VIN to delete: ");
                    String deleteVin = scanner.nextLine();

                    vehicleDAO.deleteVehicle(deleteVin);
                    System.out.println("Vehicle removed successfully.");
                    break;

                case 0:

                    System.out.println("Going back to last screen");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 0);
    }

    private static void manageSalesAndLeases() {

        int choice;

        do {

            System.out.println("Manage Sales and Leases:");
            System.out.println("1) Add Sale");
            System.out.println("2) Delete Sale");
            System.out.println("3) View all Sales");
            System.out.println("4) Add Lease");
            System.out.println("5) Delete Lease");
            System.out.println("6) View all Leases");
            System.out.println("0) Go Back");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter the buyer's name: ");
                    String buyerName = scanner.nextLine();

                    System.out.print("Enter vehicle VIN: ");
                    String saleVin = scanner.nextLine();

                    System.out.print("Enter sale date (YYYY-MM-DD): ");
                    String saleDate = scanner.nextLine();

                    System.out.println("Sale added successfully.");
                    break;

                case 2:

                    System.out.print("Enter Sale ID to delete: ");
                    int saleId = scanner.nextInt();

                    scanner.nextLine();

                    salesDAO.deleteSale(saleId);
                    System.out.println("Sale removed successfully.");
                    break;

                case 3:

                    List<SalesContract> allSales = salesDAO.getAllSales();

                    for (SalesContract sales : allSales) {
                        System.out.println(sales);
                    }

                    break;

                case 4:

                    System.out.print("Enter lease name: ");
                    String leaseName = scanner.nextLine();

                    System.out.print("Enter vehicle VIN: ");
                    String leaseVin = scanner.nextLine();

                    LeaseContract lease = new LeaseContract(leaseName, leaseVin);
                    int id = leaseDAO.createLease(lease);

                    System.out.println("Lease added successfully.");
                    break;

                case 5:

                    System.out.print("Enter Lease ID to delete: ");
                    int leaseId = scanner.nextInt();

                    scanner.nextLine();
                    leaseDAO.deleteLease(leaseId);

                    System.out.println("Lease removed successfully.");
                    break;

                case 6:

                    List<LeaseContract> leases = leaseDAO.allLeaseContracts();
                    leases.forEach(System.out::println);

                    System.out.println(leases);

                    break;

                case 0:

                    System.out.println("Returning to main menu...");

                    return;

                default:
                    System.out.println("Invalid choice, please try again.");

            }
        } while (choice != 0);
    }
}