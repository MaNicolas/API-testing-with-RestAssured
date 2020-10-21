package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HealthCheckTest extends BaseTest{

	//A simple health check endpoint to confirm whether the API is up and running.
	
	public void healthCheckTest() {
		
		given().
			spec(spec).
		when().
			get("/ping").
		then().
			assertThat().
			statusCode(201);
	}
	
	@Test
	public void headerAndCookiesTest() {
		//Create a header and add it to the request specification
		Header someHeader = new Header("someHeaderName", "someHeaderValue");
		spec.header(someHeader);
		
		//Create a cookie and add it to the request specification
		Cookie someCookie = new Cookie.Builder("someCookieName", "someCookieValue")
				.build();
		spec.cookie(someCookie);
		
		
		Response response = RestAssured.given(spec)
				.cookie("Test cookie name", "Test cookie value")
				.header("Test header name", "Test header value")
				.log().all().
				get("/ping");
		
		// Get headers
		Headers headers = response.getHeaders();
		System.out.println("Headers: " + headers);
		
		Header serverHeader1 = headers.get("Server");
		System.out.println( serverHeader1.getName() + ": " + serverHeader1.getValue());
		
		String serverHeader2 = response.getHeader("Server");
		System.out.println("Server2: " + serverHeader2);				
		
		//Get cookies
		Cookies cookies = response.getDetailedCookies();
		System.out.println("Cookies: " + cookies);
	}
}

