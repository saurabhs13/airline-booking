package com.airlinebooking.main;


import com.airlinebooking.data.prepare.CustomerDBDataPrepUtil;
import com.airlinebooking.seat.allocation.SeatAllocationRunnable;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        CustomerDBDataPrepUtil.initializeCustomerDB();
           int numberOfThreads = 120;
            for (int i = 1; i <= numberOfThreads; i++) {
                SeatAllocationRunnable seatAllocationRunnable = new SeatAllocationRunnable(false,i);
                Thread seatAllocationThread = new Thread(seatAllocationRunnable);
                seatAllocationThread.start();
            }
    }

}