package com.example.melodia.Persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.objects.Account;
import comp3350.melodia.persistence.AccountPersistence;

public class AccountPersistenceStub implements AccountPersistence {
    private List<Account> accounts;

    public AccountPersistenceStub() {
        this.accounts = new ArrayList<Account>();

        accounts.add(0, new Account(
                "Full Name",
                "unknownUSERNAME",
                "12345678",
                "EMAIL@email.ca"));
        accounts.add(1, new Account(
                "First Middle Last",
                "someUser",
                "somepassword",
                "unknownEMAIL@myumanitoba.ca"));
    }

    @Override
    public Account getAccount(String userName) {
        int index = 0;

        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getUserName().equalsIgnoreCase(userName)){
                index = i;
            }
        }

        return accounts.get(index);
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

    @Override
    public void addFavouritePlaylists(String userName, int playlistID, int position){

    }
    @Override
    public void deleteFavouritePlaylist(int playlistID, int position){

    }
}

