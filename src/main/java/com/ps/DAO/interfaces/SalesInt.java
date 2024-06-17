package com.ps.DAO.interfaces;

import com.ps.models.SalesContract;

import java.util.List;

public interface SalesInt {


    List<SalesContract> getAllSales();

    int createSale(SalesContract sale);

    void deleteSale(int id);
}