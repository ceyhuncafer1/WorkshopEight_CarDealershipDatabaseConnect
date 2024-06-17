package com.ps.models;

public class LeaseContract {

    private int id;
    private String leaseName;
    private String vin;

    public LeaseContract(int id, String vin, String leaseName) {
        this.vin = vin;
        this.leaseName = leaseName;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public String getLeaseName() {
        return leaseName;
    }

    public void setLeaseName(String leaseName) {
        this.leaseName = leaseName;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Lease{" +
                "id=" + id +
                ", lesseeName='" + leaseName + '\'' +
                ", vin='" + vin + '\'' +
                '}';
    }
}
