package com.ps.DAO;

import com.ps.DAO.interfaces.SalesInt;
import com.ps.models.SalesContract;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO implements SalesInt {
    private BasicDataSource dataSource;

    public SalesDAO(BasicDataSource basicDataSource) {
        this.dataSource = basicDataSource;
    }

    @Override
    public List<SalesContract> getAllSales() {

        List<SalesContract> sales = new ArrayList<>();

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement
                        ("SELECT * FROM sales_contracts");
                ResultSet resultSet = preparedStatement.executeQuery();

        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String buyerName = resultSet.getString("buyer_name");
                String vin = resultSet.getString("vin");
                String date = resultSet.getString("contract_date");

                SalesContract salesContract = new SalesContract (id, buyerName, vin, date);
                sales.add(salesContract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sales;

    }

    @Override
    public int createSale(SalesContract sale) {

        int generatedId = -1;

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO sales_contracts(id, buyer_name, contract_date, vin) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );

        ) {

            preparedStatement.setInt(1, sale.getId());
            preparedStatement.setString(2, sale.getBuyerName());
            preparedStatement.setString(3, sale.getVin());
            preparedStatement.setString(4, sale.getContractDate());
            preparedStatement.executeUpdate();

            try (
                    ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    generatedId = keys.getInt(1);
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return generatedId;
    }

    @Override
    public void deleteSale(int id) {

        try (

                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM sales_contracts WHERE id = ?"
                );

        ) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}