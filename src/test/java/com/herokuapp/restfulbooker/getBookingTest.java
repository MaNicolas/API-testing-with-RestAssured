package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class getBookingTest extends BaseTest {

	//@Test
	public void getBooking() {
		// Create booking
		Response responseCreate = createBooking();
		
		// Set path parameter
		spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

		// Get response based on booking id
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200 and it should be!");

		// Verify first and last name
		SoftAssert softAssert = new SoftAssert();

		String firstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(firstName, "Nico", "First name is incorrect!");

		String lastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(lastName, "Smith", "Last name is incorrect!");

		int price = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(price, 500, "Price is incorrect!");

		boolean deposit = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertEquals(deposit, false, "Deposit is incorrect!");

		String checkin = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(checkin, "2020-12-01", "Checkin date is incorrect!");

		String checkout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(checkout, "2020-12-30", "Checkout date is incorrect!");

		String needs = response.jsonPath().getString("additionalneeds");
		softAssert.assertEquals(needs, "Some meat please!", "Additional need is incorrect!");

		softAssert.assertAll();
	}
	
	@Test
	public void getBookingXml() {
		// Create booking
		Response responseCreate = createBooking();
		
		// Set path parameter
		spec.pathParam("bookingId", responseCreate.jsonPath().getInt("bookingid"));

		// Get response based on booking id
		Header xml = new Header("Accept", "application/xml");
		spec.header(xml);
		Response response = RestAssured.given(spec).get("/booking/{bookingId}");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200 and it should be!");

		// Verify first and last name
		SoftAssert softAssert = new SoftAssert();

		String firstName = response.xmlPath().getString("booking.firstname");
		softAssert.assertEquals(firstName, "Nico", "First name is incorrect!");

		String lastName = response.xmlPath().getString("booking.lastname");
		softAssert.assertEquals(lastName, "Smith", "Last name is incorrect!");

		int price = response.xmlPath().getInt("booking.totalprice");
		softAssert.assertEquals(price, 500, "Price is incorrect!");

		boolean deposit = response.xmlPath().getBoolean("booking.depositpaid");
		softAssert.assertEquals(deposit, false, "Deposit is incorrect!");

		String checkin = response.xmlPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(checkin, "2020-12-01", "Checkin date is incorrect!");

		String checkout = response.xmlPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(checkout, "2020-12-30", "Checkout date is incorrect!");

		String needs = response.xmlPath().getString("booking.additionalneeds");
		softAssert.assertEquals(needs, "Some meat please!", "Additional need is incorrect!");

		softAssert.assertAll();
	}
}