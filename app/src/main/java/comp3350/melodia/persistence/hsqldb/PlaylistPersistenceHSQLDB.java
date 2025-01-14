package comp3350.melodia.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.objects.Playlist;
import comp3350.melodia.persistence.PlaylistPersistence;

public class PlaylistPersistenceHSQLDB implements PlaylistPersistence {

    private final String dbPath;

    public PlaylistPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Playlist fromResultSet(final ResultSet rs) throws SQLException {

        final int playlistID = rs.getInt("playlistID");
        final String playlistName = rs.getString("playlistName");
        final int numberOfSongs = rs.getInt("numSongs");

        return new Playlist(playlistID, playlistName, numberOfSongs);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        final List<Playlist> playlists = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM PLAYLIST ORDER BY PLAYLISTNAME ASC");
            while (rs.next())
            {
                final Playlist playlist = fromResultSet(rs);
                playlists.add(playlist);
            }
            rs.close();
            st.close();

            return playlists;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Playlist getSpecificPlaylist(int playlistID) {

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM PLAYLIST WHERE PLAYLISTID = "+playlistID);
            rs.next();
            final Playlist playlist = fromResultSet(rs);

            rs.close();
            st.close();

            return playlist;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void insertPlaylist(String playlistName) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement(
                    "INSERT INTO PLAYLIST (PLAYLISTNAME, NUMSONGS) VALUES(?, ?)");
            st.setString(1, playlistName);
            st.setInt(2, 0);
            st.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deletePlaylist(int playlistID) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement(
                    "DELETE FROM PLAYLIST WHERE PLAYLISTID = ?");
            sc.setInt(1, playlistID);
            sc.executeUpdate();

            final PreparedStatement sc2 = c.prepareStatement(
                    "DELETE FROM PLAYLIST_SONGS WHERE PLAYLISTID = ?");
            sc2.setInt(1, playlistID);
            sc2.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

