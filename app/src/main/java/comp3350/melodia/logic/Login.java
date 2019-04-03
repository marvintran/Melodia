package comp3350.melodia.logic;

import comp3350.melodia.application.Services;
import comp3350.melodia.persistence.AccountPersistence;
import comp3350.melodia.objects.Account;

public class Login{
    private AccountPersistence accountPersistence;

    public Login(){
        accountPersistence = Services.getAccountPersistence();
    }

    public boolean checkCredentials(String userName, String password) {
        Account account = this.accountPersistence.getAccount(userName);
        return account.comparePassword(password);
    }
}
