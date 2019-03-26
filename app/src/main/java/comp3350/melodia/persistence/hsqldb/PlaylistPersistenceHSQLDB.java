package comp3350.melodia.persistence.hsqldb;

import java.io.File;
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
        final String playlistPath = rs.getString("playlistPath");

        File playlist = new File(playlistPath);

        return new Playlist(playlistID, playlistName, 0, 0, null, playlist);

    }

    @Override
    public List<Playlist> getAllPlaylists() {
        final List<Playlist> playlists = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM PLAYLIST");
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
    public Playlist insertPlaylist(Playlist currentPlaylist) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO PLAYLIST VALUES(?, ?)");
            st.setInt(1, currentPlaylist.getPlaylistID());
            st.setString(2, currentPlaylist.getPlaylistName());

            st.executeUpdate();

            return currentPlaylist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Playlist updatePlaylist(Playlist currentPlaylist) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE PLAYLIST SET name = ? WHERE PLAYLISTID = ?");
            st.setString(1, currentPlaylist.getPlaylistName());
            st.setInt(2, currentPlaylist.getPlaylistID());

            st.executeUpdate();

            return currentPlaylist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deletePlaylist(Playlist currentPlaylist) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM PLAYLIST WHERE PLAYLISTID = ?");
            sc.setInt(1, currentPlaylist.getPlaylistID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

