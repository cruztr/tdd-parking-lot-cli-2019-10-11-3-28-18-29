package com.oocl.cultivation;

public class ParkingBoy {

    private final ParkingLot parkingLot;
    private String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingTicket park(Car car) {
        ParkingTicket parkingTicket = parkingLot.park(car);

        if(parkingTicket == null)
            this.lastErrorMessage = "Not enough position.";

        return parkingTicket;
    }

    public Car fetch(ParkingTicket ticket) {
        Car car = parkingLot.fetch(ticket);

        if(ticket == null)
            this.lastErrorMessage = "Please provide your parking ticket.";
        else if(car == null)
            this.lastErrorMessage = "Unrecognized parking ticket.";

        return car;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
