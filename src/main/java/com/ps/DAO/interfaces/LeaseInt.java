package com.ps.DAO.interfaces;

import com.ps.models.LeaseContract;

import java.util.List;

public interface LeaseInt {

    List<LeaseContract> allLeaseContracts();

    int createLease(LeaseContract lease);

    void deleteLease(int id);
}