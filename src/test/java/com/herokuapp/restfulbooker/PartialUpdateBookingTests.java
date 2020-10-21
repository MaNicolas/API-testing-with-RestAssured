package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBookingTests extends BaseTest {

	@Test
	public void partialUpdateBookingTest() {

		// Create booking
		Response response = createBooking();

		// Get booking ID
		int bookingID = response.jsonPath().getInt("bookingid");

		// Update only firstname and checkin of the booking ID we just created
		String username = "admin";
		String password = "password123";
		
		JSONObject body = new JSONObject();
		body.put("firstname", "Lory");
		body.put("bookingdates.checkin", "2020-12-05");	
		
		Response partiallyUpdatedResponse = RestAssured.given(spec).contentType(ContentType.JSON).auth().preemptive()
				.basic(username, password).body(body.toString()).patch("/booking/" + bookingID);
		partiallyUpdatedResponse.print();
		
		// Verifications:
		Assert.assertEquals(partiallyUpdatedResponse.getStatusCode(), 200,
				"Status code is " + response.getStatusCode() + " and should be 200.");

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getString("firstname"), "Lory");
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getString("lastname"), "Smith");
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getInt("totalprice"), 500);
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getBoolean("depositpaid"), false);
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getJsonObject("bookingdates.checkin"), "2020-12-05");
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getJsonObject("bookingdates.checkout"), "2020-12-30");
		softAssert.assertEquals(partiallyUpdatedResponse.jsonPath().getString("additionalneeds"), "Some meat please!");

		softAssert.assertAll();		
	}
}