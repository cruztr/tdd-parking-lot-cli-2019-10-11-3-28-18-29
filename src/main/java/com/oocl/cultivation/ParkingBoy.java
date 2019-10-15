package com.oocl.cultivation;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public class ParkingBoy {

    List<ParkingLot> parkingLotList;
    String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLotList = new ArrayList<>();
        this.parkingLotList.add(parkingLot);
    }

    public void addParkingLot(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public List<ParkingLot> getParkingLotList(){
        return this.parkingLotList;
    }

    public ParkingLot getParkingLotAtIndex(int index) {
        return this.parkingLotList.get(index);
    }

    public ParkingTicket park(Car car) {
        if(parkingLotList.isEmpty())
            return null;

        ParkingLot parkingLot = getFirstAvailableParkingLot();
        if(parkingLot == null) {
            this.lastErrorMessage = "Not enough position.";
            return null;
        }

        ParkingTicket parkingTicket = parkingLot.park(car);
        if(parkingTicket == null)
            this.lastErrorMessage = "Not enough position.";

        return parkingTicket;
    }

    public Car fetch(ParkingTicket ticket) {
        if(parkingLotList.isEmpty())
            return null;

        if(ticket == null){
            this.lastErrorMessage = "Please provide your parking ticket.";
            return null;
        }

        Car car = parkingLotList.stream()
                .map(parkingLotInsideList -> parkingLotInsideList.fetch(ticket))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if(!isNull(car)){
            return car;
        }

        this.lastErrorMessage = "Unrecognized parking ticket.";
        return null;
    }

    private ParkingLot getFirstAvailableParkingLot() {
        return parkingLotList.stream()
                .filter(parkingLot -> parkingLot.getAvailableParkingPosition() != 0)
                .findFirst()
                .orElse(null);
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
