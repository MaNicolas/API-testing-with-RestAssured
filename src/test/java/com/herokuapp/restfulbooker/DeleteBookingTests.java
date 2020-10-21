package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteBookingTests extends BaseTest {

	@Test
	public void deleteBookingTest() {

		// Create booking
		Response response = createBooking();

		// Get booking ID
		int bookingID = response.jsonPath().getInt("bookingid");

		// Delete the booking we just created
		String username = "admin";
		String password = "password123";

		Response deletedResponse = RestAssured.given(spec).contentType(ContentType.JSON).auth().preemptive()
				.basic(username, password).delete("/booking/" + bookingID);
		deletedResponse.print();

		// Verifications:
		Assert.assertEquals(deletedResponse.getStatusCode(), 201,
				"Status code is " + response.getStatusCode() + " and should be 201.");

		Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingID);
		Assert.assertEquals(responseGet.getBody().asString(), "Not Found","Booking has not been deleted!");
	}
}