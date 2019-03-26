package comp3350.melodia.persistence;

import java.util.List;
import comp3350.melodia.objects.Account;

public interface AccountPersistence {

    List<Account> getAllAccounts();

    Account insertAccount(Account currentAccount);

    Account updateAccount(Account currentAccount);

    void deleteAccount(Account currentAccount);

}
