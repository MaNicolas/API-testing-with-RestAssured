package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class getBookingTest {

	@Test
	public void getBooking() {
		// Get response based on booking id
		Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/9");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200 and it should be!");

		// Verify first and last name
		SoftAssert softAssert = new SoftAssert();
		
		String firstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(firstName, "Jim", "First name is incorrect!");
		
		String lastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(lastName, "Jones", "Last name is incorrect!");
		
		int price = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 501, "Price is incorrect!");
		
		boolean deposit = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertEquals(deposit, true, "Deposit is incorrect!");
		
		String checkin = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(checkin, "2019-01-11", "Checkin date is incorrect!");
		
		String checkout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(checkout, "2020-05-17", "Checkout date is incorrect!");
		
		String needs = response.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(needs, "Breakfast", "Additional need is incorrect!");
		
		softAssert.assertAll();
	}
}