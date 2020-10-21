package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
	
	//Variables
	protected RequestSpecification spec;
	
	@BeforeMethod
	public void Setup() {
		spec = new RequestSpecBuilder()
			.setBaseUri("https://restful-booker.herokuapp.com")
			.build();
	}

	protected Response createBooking() {

		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "Nico");
		body.put("lastname", "Smith");
		body.put("totalprice", 500);
		body.put("depositpaid", false);

		JSONObject dates = new JSONObject();
		dates.put("checkin", "2020-12-01");
		dates.put("checkout", "2020-12-30");
		body.put("bookingdates", dates);
		
		body.put("additionalneeds", "Some meat please!");

		// Get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON)
				.body(body.toString()).post("/booking");
		response.print();
		return response;
	}

	protected Response updateBooking(int ID, String username, String password) {
		// Variables
		String url = "/booking/" + ID;

		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "Stef");
		body.put("lastname", "SecretName");
		body.put("totalprice", 200);
		body.put("depositpaid", true);

		JSONObject dates = new JSONObject();
		dates.put("checkin", "2020-12-01");
		dates.put("checkout", "2020-12-30");

		body.put("bookingdates", dates);
		body.put("additionalneeds", "Some meat please!");

		// Get response
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).auth().preemptive()
				.basic(username, password).body(body.toString()).put(url);
		response.print();
		return response;
	}
}