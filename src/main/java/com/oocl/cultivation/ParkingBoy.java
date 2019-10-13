package com.oocl.cultivation;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {

    private List<ParkingLot> parkingLotList;
    private String lastErrorMessage;

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
        if(parkingLot == null)
            return null;

        ParkingTicket parkingTicket = parkingLot.park(car);
        if(parkingTicket == null)
            this.lastErrorMessage = "Not enough position.";

        return parkingTicket;
    }

    public Car fetch(ParkingTicket ticket) {
        if(parkingLotList.isEmpty())
            return null;

        ParkingLot parkingLot = getFirstAvailableParkingLot();

        if(parkingLot == null)
            this.lastErrorMessage = "Not enough position.";

        Car car = parkingLotList.get(0).fetch(ticket);

        if(ticket == null)
            this.lastErrorMessage = "Please provide your parking ticket.";
        else if(car == null)
            this.lastErrorMessage = "Unrecognized parking ticket.";

        return car;
    }

    private ParkingLot getFirstAvailableParkingLot() {
        int index = 0;
        do{
            if(parkingLotList.get(index).getAvailableParkingPosition() > 0)
                return parkingLotList.get(index);
            index++;
        } while (index < parkingLotList.size());
        return null;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
