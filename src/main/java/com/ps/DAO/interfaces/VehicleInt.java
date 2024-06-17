package com.ps.DAO.interfaces;

import com.ps.models.Vehicle;
import java.util.List;

public interface VehicleInt {

    List<Vehicle> getAllVehicles();

    List<Vehicle> getVehiclesByMakeModel(String make, String model);

    List<Vehicle> getVehiclesByYearRange(int startYear, int endYear);

    List<Vehicle> getVehiclesByColor(String color);

    int createVehicle(Vehicle vehicle);

    void deleteVehicle(String vin);
}