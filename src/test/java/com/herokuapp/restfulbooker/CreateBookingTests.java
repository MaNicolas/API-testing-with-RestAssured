package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTests extends BaseTest {

	// @Test
	public void createBookingTest() {

		// Create booking
		Response response = createBooking();

		// Verifications:
		Assert.assertEquals(response.getStatusCode(), 200,
				"Status code is " + response.getStatusCode() + " and should be 200.");

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(response.jsonPath().getString("booking.firstname"), "Nico");
		softAssert.assertEquals(response.jsonPath().getString("booking.lastname"), "Smith");
		softAssert.assertEquals(response.jsonPath().getInt("booking.totalprice"), 500);
		softAssert.assertEquals(response.jsonPath().getBoolean("booking.depositpaid"), false);
		softAssert.assertEquals(response.jsonPath().getJsonObject("booking.bookingdates.checkin"), "2020-12-01");
		softAssert.assertEquals(response.jsonPath().getJsonObject("booking.bookingdates.checkout"), "2020-12-30");
		softAssert.assertEquals(response.jsonPath().getString("booking.additionalneeds"), "Some meat please!");

		softAssert.assertAll();
	}

	@Test
	public void createBookingWithPOJOTest() {

		// Create body using POJOs
		Bookingdates bookingDatesPojo = new Bookingdates("2020-12-01", "2020-12-30");
		Booking bookingPojo = new Booking("Patricia", "Angele", 500, false, bookingDatesPojo, "Some meat please!");

		// Get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(bookingPojo).post("/booking");
		response.print();
		BookingId bookingId = response.as(BookingId.class);

		// Verifications:
		Assert.assertEquals(response.getStatusCode(), 200);
		
		System.out.println("Request booking: " + bookingPojo.toString());
		System.out.println("Request booking: " + bookingId.getBooking().toString());

		Assert.assertEquals(bookingId.getBooking().toString(), bookingPojo.toString());
	}
}