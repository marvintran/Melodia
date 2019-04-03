package com.example.melodia.Objects;
import org.junit.Test;
import comp3350.melodia.objects.Account;
import static org.junit.Assert.*;

public class AccountTest{

	@Test
	public void accountTest1(){

		Account testAccount = new Account("fullname",
				"userName", "12458", "emailAddress"
		);

		assertNotNull(testAccount);
		assertEquals("fullname",testAccount.getFullName());
		assertEquals("userName", testAccount.getUserName());
		assertEquals("emailAddress", testAccount.getEmail());

		System.out.println("Finished AccountTest");

	}

// test used to get  hash and salt of the password for this account to input into database
// @Test
//	public void accountTest2() {
//		Account account = new Account(
//				"Someone Name",
//				"some_username",
//				"password",
//				"someone@example.com"
//		);
//		System.out.println("======== CREATING USER ==========");
//		System.out.println(account.getUserID());
//		System.out.println(account.getAccountSalt());
//		System.out.println(account.getPasswordHash());
//		System.out.println("=================================");
//	}

}
