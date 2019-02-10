package com.example.melodia.Objects;
import org.junit.Test;
import comp3350.melodia.objects.Account;
import static org.junit.Assert.*;

public class AccountTest{
	
	
	@Test
	public void accountTest1(){
		
		Account testAccount = new Account("mane","userID",
			"emailAddress", "usrProfile", null
		);
		
		
		assertNotNull(testAccount);
		assertEquals("name",getFullName());
		assertEquals("userID", getUserName());
		assertEquals("emailAddress", getEmail());
		assertEquals("usrProfile",getProfile());
		
		System.out.println("Finished AccountTest");
		
	}
	
}