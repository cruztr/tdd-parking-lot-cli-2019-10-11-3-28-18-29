package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
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

    @Test
    void should_successfully_add_to_parkingLotList() {
        parkingBoy.addParkingLot(new ParkingLot());
        parkingBoy.park(new Car());

        assertSame(2, parkingBoy.getParkingLotList().size());
    }

    @Test
    void should_add_cars_in_otherParkingLot_when_firstParkingLot_is_full() {
        ParkingLot otherParkingLot = new ParkingLot();
        parkingBoy.addParkingLot(otherParkingLot);

        ParkingTicket parkingTicket = null;
        for(int i=0; i<10; i++){
            parkingTicket = parkingBoy.park(new Car());
        }

        ParkingTicket otherParkingTicket = parkingBoy.park(new Car());

        int parkingLotAvail = parkingBoy.getParkingLotAtIndex(0).getAvailableParkingPosition();
        int otherParkingLotAvail = parkingBoy.getParkingLotAtIndex(1).getAvailableParkingPosition();

        assertSame(parkingLotAvail, 0);
        assertSame(otherParkingLotAvail, 9);
    }

    @Test
    void should_assert_SmartParkingBoy_parks_in_lot_with_more_available_position() {
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLot);
        ParkingLot otherParkingLot = new ParkingLot();
        smartParkingBoy.addParkingLot(otherParkingLot);

        ParkingTicket parkingTicket = null;
        for(int i=0; i<10; i++){
            parkingTicket = smartParkingBoy.park(new Car());
        }

        ParkingTicket otherParkingTicket = smartParkingBoy.park(new Car());

        int parkingLotAvail = smartParkingBoy.getParkingLotAtIndex(0).getAvailableParkingPosition();
        int otherParkingLotAvail = smartParkingBoy.getParkingLotAtIndex(1).getAvailableParkingPosition();

        assertSame(parkingLotAvail, 4);
        assertSame(otherParkingLotAvail, 5);
    }

    @Test
    void should_assert_SuperSmartParkingBoy_parks_in_lot_with_more_available_position_rate() {
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLot);
        ParkingLot otherParkingLot = new ParkingLot();
        superSmartParkingBoy.addParkingLot(otherParkingLot);

        ParkingTicket parkingTicket = null;
        for(int i=0; i<10; i++){
            parkingTicket = superSmartParkingBoy.park(new Car());
        }

        ParkingTicket otherParkingTicket = superSmartParkingBoy.park(new Car());

        int parkingLotAvail = superSmartParkingBoy.getParkingLotAtIndex(0).getAvailableParkingPosition();
        int otherParkingLotAvail = superSmartParkingBoy.getParkingLotAtIndex(1).getAvailableParkingPosition();

        assertSame(parkingLotAvail, 4);
        assertSame(otherParkingLotAvail, 5);
    }
}
