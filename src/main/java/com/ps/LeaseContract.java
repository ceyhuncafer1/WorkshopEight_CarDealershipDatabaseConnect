package com.ps;

public class LeaseContract extends Contract {

    private static final double leaseFeeRate = 0.07;
    private static final double interestRate = 0.04;
    private static final int termOfMonths = 36;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {

        super(date, customerName, customerEmail, vehicleSold);

    }

    @Override
    public double getTotalPrice() {

        double vehiclePrice = getVehicleSold().getPrice();
        double leaseFee = vehiclePrice * leaseFeeRate;
        return vehiclePrice * 0.50 + leaseFee;

    }

    @Override
    public double getMonthlyPayment() {

        double vehiclePrice = getVehicleSold().getPrice();
        double leaseFee = vehiclePrice * leaseFeeRate;
        double loanAmount = vehiclePrice * 0.50 + leaseFee;
        double monthlyInterestRate = interestRate / 12;
        return loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termOfMonths)) /
                (Math.pow(1 + monthlyInterestRate, termOfMonths) - 1);

    }
}