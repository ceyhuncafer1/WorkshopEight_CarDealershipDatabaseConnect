package com.ps.DAO;

import com.ps.DAO.interfaces.VehicleInt;
import com.ps.models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements VehicleInt {

    private BasicDataSource dataSource;

    public VehicleDAO(BasicDataSource basicDataSource) {
        this.dataSource = basicDataSource;
    }

    @Override
    public List<Vehicle> getAllVehicles() {

        List<Vehicle> vehicles = new ArrayList<>();

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement
                        ("SELECT * FROM vehicles");
                ResultSet resultSet = preparedStatement.executeQuery();

        ) {

            while (resultSet.next()) {

                String vin = resultSet.getString("vin");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                String color = resultSet.getString("color");

                Vehicle vehicle = new Vehicle(vin, make, model, year, color);
                vehicles.add(vehicle);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return vehicles;
        }
    }


    @Override
    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {

        List<Vehicle> vehicles = new ArrayList<>();

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM vehicles WHERE make = ? AND model = ?")

        ) {

            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    Vehicle vehicle = new Vehicle(
                            resultSet.getString("vin"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getString("color")
                    );
                    vehicles.add(vehicle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByYearRange(int startYear, int endYear) {

        List<Vehicle> vehicles = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM vehicles WHERE year BETWEEN ? AND ?");

        ) {

            preparedStatement.setInt(1, startYear);
            preparedStatement.setInt(2, endYear);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {

                    Vehicle vehicle = new Vehicle(
                            resultSet.getString("vin"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getString("color")
                    );
                    vehicles.add(vehicle);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getVehiclesByColor(String color) {

        List<Vehicle> vehicles = new ArrayList<>();

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM vehicles WHERE color = ?"
                );

        ) {

            preparedStatement.setString(1, color);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {
                    Vehicle vehicle = new Vehicle(
                            resultSet.getString("vin"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getString("color")
                    );

                    vehicles.add(vehicle);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicles;
    }


    @Override
    public int createVehicle(Vehicle vehicle) {

        int generatedId = -1;

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO vehicles(vin, make, model, year, color) VALUES(?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS

                );

        ) {

            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setString(5, vehicle.getColor());

            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void deleteVehicle(String vin) {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM vehicles WHERE vin = ?"
                );
        ) {
            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}