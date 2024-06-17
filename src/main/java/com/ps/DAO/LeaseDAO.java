package com.ps.DAO;

import com.ps.DAO.interfaces.LeaseInt;
import com.ps.models.LeaseContract;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaseDAO implements LeaseInt {

    private BasicDataSource dataSource;

    public LeaseDAO (BasicDataSource basicDataSource) {

        this.dataSource = basicDataSource;

    }

    @Override
    public List<LeaseContract> allLeaseContracts() {

        List<LeaseContract> allLeases = new ArrayList<>();

        try(

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM lease_contracts");
                ResultSet resultSet = preparedStatement.executeQuery();

        ) {
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String leaseName = resultSet.getString("lease_name");
                String vin = resultSet.getString("vin");

                LeaseContract lease = new LeaseContract(leaseName, vin);
                allLeases.add(lease);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return allLeases;
        }
    }

    @Override
    public int createLease(LeaseContract lease) {

        int generatedId = -1;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO lease_contracts(id, lessee_name, vin) VALUES(?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {

            preparedStatement.setInt(1, lease.getId());
            preparedStatement.setString(2, lease.getLeaseName());
            preparedStatement.setString(3,lease.getVin());
            preparedStatement.executeUpdate();

            try (
                    ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return generatedId;
    }

    @Override
    public void deleteLease(int id){

        try(

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM lease_contracts WHERE id = ?"

                );
        ){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}