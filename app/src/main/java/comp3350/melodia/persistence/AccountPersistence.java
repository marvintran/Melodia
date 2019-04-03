package comp3350.melodia.persistence;

import comp3350.melodia.objects.Account;

public interface AccountPersistence {

    Account getAccount(String userName);

    Account insertAccount(Account currentAccount);

    Account updateAccount(Account currentAccount);

    void deleteAccount(Account currentAccount);

    void addFavouritePlaylists(String userName, int playlistID, int position);

    void deleteFavouritePlaylist(int playlistID, int position);
}
