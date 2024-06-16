package com.ps;

public class SalesContract extends Contract {

    private double salesTaxAmount = 0.05;
    private double recordingFee = 100;
    private double processingFeeUnder10000 = 295;
    private double processingFeeOver10000 = 495;
    private double interestRateOver10000 = 0.0425;
    private double interestRateUnder10000 = 0.0525;
    private int termOfMonthsOver10000 = 48;
    private int termOfMonthsUnder10000 = 24;

    private boolean financeOption;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean financeOption) {

        super(date, customerName, customerEmail, vehicleSold);
        this.financeOption = financeOption;

    }

    @Override
    public double getTotalPrice() {

        double vehiclePrice = getVehicleSold().getPrice();
        double salesTax = vehiclePrice * salesTaxAmount;
        double processingFee = vehiclePrice < 10000 ? processingFeeUnder10000 : processingFeeOver10000; // check if vehiclePrice is less than 10k, then if its over 10k, will use over 10k processing fee and so on
        return vehiclePrice + salesTax + recordingFee + processingFee;

    }

    @Override
    public double getMonthlyPayment() {

        if (!financeOption) {
            return 0;
        }

        double vehiclePrice = getVehicleSold().getPrice();
        double loanAmount = getTotalPrice();
        double interestRate = vehiclePrice >= 10000 ? interestRateOver10000 : interestRateUnder10000;
        int termInMonths = vehiclePrice >= 10000 ? termOfMonthsOver10000 : termOfMonthsUnder10000;

        double monthlyInterestRate = interestRate / 12;

        return loanAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termInMonths)) /
                (Math.pow(1 + monthlyInterestRate, termInMonths) - 1);
    }

    public boolean isFinanceOption() {
        return financeOption;
    }

    public void setFinanceOption(boolean financeOption) {
        this.financeOption = financeOption;
    }
}