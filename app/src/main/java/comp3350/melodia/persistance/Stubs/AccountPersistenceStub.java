package comp3350.melodia.persistance.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.melodia.objects.Song;
import comp3350.melodia.objects.Account;
import comp3350.melodia.persistance.AccountPersistence;

public class AccountPersistenceStub implements AccountPersistence {
    private List<Account> accounts;

    public AccountPersistenceStub() {
        this.accounts = new ArrayList<Account>();

        accounts.add(0, new Account(
            "Full Name",
            "unknownUSERNAME",
            "EMAIL",
            "profile",
            new ArrayList<Song>()));
        accounts.add(1, new Account(
            "First Middle Last",
            "someUser",
            "unknownEMAIL@myumanitoba.ca",
            "blahprofile",
            new ArrayList<Song>()));
        accounts.add(2, new Account(
                "Some Unknown User",
                "nameofsomeUser",
                "blahdsb@gmail.com",
                "profileTest",
                new ArrayList<Song>()
                ));
    }

    @Override
    public List<Account> getAllAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    @Override
    public List<Account> getAccountRandom(Account currentAccount) {
        List<Account> newAccounts;
        int index;

        newAccounts = new ArrayList<>();
        index = accounts.indexOf(currentAccount);
        if (index >= 0)
        {
            newAccounts.add(accounts.get(index));
        }
        return newAccounts;
    }

    @Override
    public Account insertAccount(Account currentAccount) {
        accounts.add(currentAccount);
        return currentAccount;
    }

    @Override
    public Account updateAccount(Account currentAccount) {
        int index;

        index = accounts.indexOf(currentAccount);
        if (index >= 0)
        {
            accounts.set(index, currentAccount);
        }
        return currentAccount;
    }

    @Override
    public void deleteAccount(Account currentAccount) {
        int index;

        index = accounts.indexOf(currentAccount);
        if (index >= 0)
        {
            accounts.remove(index);
        }
    }
}


