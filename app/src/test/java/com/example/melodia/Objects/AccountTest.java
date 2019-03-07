package com.example.melodia.Objects;
import org.junit.Test;
import comp3350.melodia.objects.Account;
import static org.junit.Assert.*;

public class AccountTest{
	
	
	@Test
	public void accountTest1(){
		
		Account testAccount = new Account("name","userID",
			"emailAddress", "usrProfile", null
		);
		
		
		assertNotNull(testAccount);
		assertEquals("name",testAccount.getFullName());
		assertEquals("userID", testAccount.getUserName());
		assertEquals("emailAddress", testAccount.getEmail());
		assertEquals("usrProfile",testAccount.getProfile());
		
		System.out.println("Finished AccountTest");
		
	}
	
}