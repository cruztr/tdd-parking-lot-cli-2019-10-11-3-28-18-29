package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableParkingPosition() {
        return capacity - cars.size();
    }

    public ParkingTicket park(Car car) {
        if(cars.size() == capacity)
            return null;

        if(cars.containsValue(car))
            return null;

        ParkingTicket parkingTicket = new ParkingTicket();
        cars.put(parkingTicket, car);

        return parkingTicket;
    }

    public Car fetch(ParkingTicket ticket) {
        return cars.remove(ticket);
    }
}
