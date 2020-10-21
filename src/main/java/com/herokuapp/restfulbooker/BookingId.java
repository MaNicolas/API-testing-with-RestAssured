package com.herokuapp.restfulbooker;

public class BookingId {

	//Variables
	private int bookingid;
	private Booking booking;

	//Constructors
	public BookingId(int bookingid, Booking booking) {
		this.bookingid = bookingid;
		this.booking = booking;
	}
	
	public BookingId() {
	}


	@Override
	public String toString() {
		return "Bookingid [bookingid=" + bookingid + ", booking=" + booking + "]";
	}


	//Getters and setters
	public int getBookingid() {
		return bookingid;
	}


	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}


	public Booking getBooking() {
		return booking;
	}


	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}