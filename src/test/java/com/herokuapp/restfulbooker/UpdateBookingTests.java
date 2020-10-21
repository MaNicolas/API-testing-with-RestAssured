package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class UpdateBookingTests extends BaseTest {

	@Test
	public void updateBookingTest() {

		// Create booking
		Response response = createBooking();

		// Get booking ID
		int bookingID = response.jsonPath().getInt("bookingid");

		// Update booking ID we just created
		String username = "admin";
		String password = "password123";
		Response updatedResponse = updateBooking(bookingID, username, password);
		
		// Verifications:
		Assert.assertEquals(updatedResponse.getStatusCode(), 200,"Status code is " + response.getStatusCode() + " and should be 200.");

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(updatedResponse.jsonPath().getString("firstname"), "Stef");
		softAssert.assertEquals(updatedResponse.jsonPath().getString("lastname"), "SecretName");
		softAssert.assertEquals(updatedResponse.jsonPath().getInt("totalprice"), 200);
		softAssert.assertEquals(updatedResponse.jsonPath().getBoolean("depositpaid"), true);
		softAssert.assertEquals(updatedResponse.jsonPath().getJsonObject("bookingdates.checkin"), "2020-12-01");
		softAssert.assertEquals(updatedResponse.jsonPath().getJsonObject("bookingdates.checkout"),"2020-12-30");
		softAssert.assertEquals(updatedResponse.jsonPath().getString("additionalneeds"), "Some meat please!");

		softAssert.assertAll();
	}
}