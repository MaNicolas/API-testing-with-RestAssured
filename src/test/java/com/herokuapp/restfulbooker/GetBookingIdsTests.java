package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIdsTests extends BaseTest {

	@Test
	public void getBookingIdsWithoutFilterTest() {
		// Get response with booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not!");

		// Verify at least 1 booking id in response
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");

		// Verify our list is not empty
		Assert.assertFalse(bookingIds.isEmpty(), "List of booking Ids is empty, but it should not!");
	}
	
	@Test
	public void getBookingIdsWithFilterTest() {
		// Create query parameter
		spec.queryParam("firstname", "Nico");
		spec.queryParam("lastname", "Smith");
		
		// Get response with booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not!");

		// Verify at least 1 booking id in response
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");

		// Verify our list is not empty
		Assert.assertFalse(bookingIds.isEmpty(), "List of booking Ids is empty, but it should not!");
	}
}