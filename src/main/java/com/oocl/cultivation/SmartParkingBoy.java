package com.oocl.cultivation;

import java.util.Collections;
import java.util.Comparator;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
    }

    @Override
    public ParkingTicket park(Car car) {
        if(parkingLotList.isEmpty())
            return null;

        ParkingLot parkingLot = getParkingLotWithMoreEmptyPositions();
        if(parkingLot == null) {
            this.lastErrorMessage = "Not enough position.";
            return null;
        }

        ParkingTicket parkingTicket = parkingLot.park(car);
        if(parkingTicket == null)
            this.lastErrorMessage = "Not enough position.";

        return parkingTicket;
    }

    private ParkingLot getParkingLotWithMoreEmptyPositions() {
        return Collections.max(parkingLotList, Comparator.comparing(ParkingLot::getAvailableParkingPosition));
    }
}
