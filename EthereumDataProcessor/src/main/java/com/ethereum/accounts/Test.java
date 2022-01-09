package com.ethereum.accounts;

import java.time.Duration;
import java.time.Instant;

public class Test {

	public static void main(String[] args) {
		long unixTime = 1594218320;
		Instant instant = Instant.ofEpochSecond(unixTime);
		
		long unixTime2 = 1594275779;
		Instant instant2 = Instant.ofEpochSecond(unixTime2);
		
		System.out.println(Duration.between(instant, instant2).toSeconds());
		
		
		
	}

}
