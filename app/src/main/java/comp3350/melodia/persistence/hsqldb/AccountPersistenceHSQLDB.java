package comp3350.melodia.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.melodia.objects.Account;
import comp3350.melodia.persistence.AccountPersistence;

public class AccountPersistenceHSQLDB implements AccountPersistence {

    private final String dbPath;

    public AccountPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Account fromResultSet(final ResultSet rs) throws SQLException {

        final String fullName = rs.getString("fullName");
        final String userName = rs.getString("userName");
        final String password = rs.getString("password");
        final String email = rs.getString("email");

        return new Account(fullName, userName,password, email);
    }

    @Override
    public Account getAccount(String userName) {
        final Account account;

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ACCOUNT WHERE USERNAME = " + userName);
            rs.next();

            account = fromResultSet(rs);

            rs.close();
            st.close();

            return account;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Account insertAccount(Account currentAccount) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO ACCOUNT VALUES(?, ?, ?, ?)");
            st.setString(1, currentAccount.getFullName());
            st.setString(2, currentAccount.getUserName());
            st.setString(3, currentAccount.getPasswordHash());
            st.setString(4, currentAccount.getEmail());

            st.executeUpdate();

            return currentAccount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Account updateAccount(Account currentAccount) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE ACCOUNT SET name = ? WHERE fullName = ? WHERE email = ? ");
            st.setString(0, currentAccount.getFullName());
            st.setString(1, currentAccount.getUserName());
            st.setString(2, "password");
            st.setString(3, currentAccount.getEmail());

            st.executeUpdate();

            return currentAccount;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteAccount(Account currentAccount) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM ACCOUNT WHERE USERNAME = " + currentAccount.getUserName());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void addFavouritePlaylists(String userName, int playlistID, int position){
        try (final Connection c = connection()) {

            final PreparedStatement st = c.prepareStatement("UPDATE ACCOUNT_FAVOURITEPLAYLIST SET POSITION = POSITION + 1 WHERE USERNAME = ? AND POSITION >= ?");
            st.setString(1, userName);
            st.setInt(2, position);

            st.executeUpdate();

            final PreparedStatement st2 = c.prepareStatement("INSERT INTO ACCOUNT_FAVOURITEPLAYLIST VALUES(?, ?, ?)");
            st2.setString(1, userName);
            st2.setInt(2, playlistID);
            st2.setInt(3, position);
            st2.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteFavouritePlaylist(int playlistID, int position){
        System.out.println("Delete at Position: "+position);
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM ACCOUNT_FAVOURITEPLAYLIST WHERE PLAYLISTID = ? AND POSITION = ?");
            sc.setInt(1, playlistID);
            sc.setInt(2, position );
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
