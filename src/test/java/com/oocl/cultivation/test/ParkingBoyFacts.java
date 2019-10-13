package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {

    private ParkingLot parkingLot;
    private ParkingBoy parkingBoy;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot();
        parkingBoy = new ParkingBoy(parkingLot);
    }

    @Test
    void should_return_parkingTicket_when_parkingBoy_park_car() {
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);

        assertNotNull(parkingTicket);
    }

    @Test
    void should_return_car_when_parkingBoy_fetch_using_parkingTicket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(parkingTicket);

        assertNotNull(car);
    }

    @Test
    void should_return_different_parkingTicket_when_parkingBoy_park_multiple_cars() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());
        ParkingTicket parkingTicket1 = parkingBoy.park(new Car());

        assertNotNull(parkingTicket);
        assertNotNull(parkingTicket1);
        assertNotEquals(parkingTicket, parkingTicket1);
    }

    @Test
    void should_return_different_cars_when_fetch_using_different_ticket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());
        ParkingTicket parkingTicket1 = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(parkingTicket);
        Car otherCar = parkingBoy.fetch(parkingTicket1);

        assertNotNull(car);
        assertNotNull(otherCar);
        assertNotEquals(car, otherCar);
    }

    @Test
    void should_return_null_when_fetch_with_no_ticket() {
        Car car = parkingBoy.fetch(null);

        assertNull(car);
    }

    @Test
    void should_return_null_when_fetch_using_wrong_ticket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(new ParkingTicket());

        assertNull(car);
        assertNotNull(parkingTicket);
    }


    @Test
    void should_return_null_when_fetch_using_used_ticket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(parkingTicket);
        Car otherCar = parkingBoy.fetch(parkingTicket);

        assertNotNull(car);
        assertNull(otherCar);
    }

    @Test
    void should_return_null_when_park_car_given_capacity_is_reached() {
        ParkingTicket parkingTicket = null;
        for(int i=0; i<10; i++){
            parkingTicket = parkingBoy.park(new Car());
        }

        ParkingTicket overloadParkingTicket = parkingBoy.park(new Car());

        assertNotNull(parkingTicket);
        assertNull(overloadParkingTicket);
    }

    @Test
    void should_return_null_when_park_car_given_already_parked_car() {
        Car car = new Car();

        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingTicket ticketForSameCar = parkingBoy.park(car);

        assertNotNull(parkingTicket);
        assertNull(ticketForSameCar);
    }

    @Test
    void should_return_error_message_when_customer_gives_wrong_ticket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(new ParkingTicket());

        assertNotNull(parkingTicket);
        assertNull(car);
        assertSame(parkingBoy.getLastErrorMessage(), "Unrecognized parking ticket.");
    }

    @Test
    void should_return_error_message_when_customer_gives_used_ticket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(parkingTicket);
        Car otherCar = parkingBoy.fetch(parkingTicket);

        assertNotNull(parkingTicket);
        assertNotNull(car);
        assertNull(otherCar);
        assertSame(parkingBoy.getLastErrorMessage(), "Unrecognized parking ticket.");
    }

    @Test
    void should_return_error_message_when_customer_provides_no_ticket() {
        ParkingTicket parkingTicket = parkingBoy.park(new Car());

        Car car = parkingBoy.fetch(null);

        assertNotNull(parkingTicket);
        assertNull(car);
        assertSame(parkingBoy.getLastErrorMessage(), "Please provide your parking ticket.");
    }

    @Test
    void should_return_error_message_when_parking_car_in_full_parkingLot() {
        ParkingTicket parkingTicket = null;
        for(int i=0; i<10; i++){
            parkingTicket = parkingBoy.park(new Car());
        }

        ParkingTicket overloadParkingTicket = parkingBoy.park(new Car());

        assertNotNull(parkingTicket);
        assertNull(overloadParkingTicket);
        assertSame(parkingBoy.getLastErrorMessage(), "Not enough position.");
    }
}
