package comp3350.melodia.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Account;
import comp3350.melodia.persistence.AccountPersistence;

public class AccessAccount{
	private AccountPersistence accountPersistence;
	private List<Account> accountList;
	private Account account;
	
	public AccessAccount(){
		accountPersistence = Services.getAccountPersistence();
		accountList = null;
		account = null;
	}
	
	public List<Account> getAccount(){
		
		accountList = accountPersistence.getAllAccounts();
		
		return Collections.unmodifiableList(accountList);
		
	}
	
	public Account insertAccount(Account newAccount){
		return accountPersistence.insertAccount(newAccount);
	}
}
