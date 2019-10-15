package com.oocl.cultivation;

import java.util.Collections;
import java.util.Comparator;

public class SuperSmartParkingBoy extends ParkingBoy{

    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
    }

    @Override
    public ParkingTicket park(Car car) {
        if(parkingLotList.isEmpty())
            return null;

        ParkingLot parkingLot = getParkingLotWithGreaterEmptyPositionRate();
        if(parkingLot == null) {
            this.lastErrorMessage = "Not enough position.";
            return null;
        }

        ParkingTicket parkingTicket = parkingLot.park(car);
        if(parkingTicket == null)
            this.lastErrorMessage = "Not enough position.";

        return parkingTicket;
    }

    private ParkingLot getParkingLotWithGreaterEmptyPositionRate() {
        return Collections.max(parkingLotList, Comparator.comparing(this::getEmptyPositionRate));
    }

    private double getEmptyPositionRate(ParkingLot lot) {
        return (double) lot.getAvailableParkingPosition()/ (double) lot.getCapacity();
    }
}
