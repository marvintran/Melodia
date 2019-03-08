package comp3350.melodia.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.PlaylistPersistence;

public class PlaylistPersistenceHSQLDB implements PlaylistPersistence{

    private final String dbPath;

    public PlaylistPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Playlist fromResultSet(final ResultSet rs) throws SQLException {
        final String playlistName = rs.getString("playlistName");
        final int playlistTime = rs.getInt("playlistTime");
        final int numberOfSongs = rs.getInt("numberOfSongs");
        final String songs = rs.getString("songs");
        final String playlistDataPath = rs.getString("playlistData");

        //return new Playlist(playlistName, playlistTime, numberOfSongs, songs, playlistDataPath);
        return null;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        final List<Playlist> playlists = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM playlists");
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
            final PreparedStatement st = c.prepareStatement("INSERT INTO playlists VALUES(?, ?)");
            st.setString(1, currentPlaylist.getPlaylistName());
            st.setInt(2, currentPlaylist.getPlaylistTime());
            st.setInt(3, currentPlaylist.getNumberOfSongs());
            st.setObject(4, currentPlaylist.getSongs());

            st.executeUpdate();

            return currentPlaylist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Playlist updatePlaylist(Playlist currentPlaylist) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE playlists SET name = ? WHERE playlistName = ?");
            st.setString(1, currentPlaylist.getPlaylistName());
            st.setInt(2, currentPlaylist.getPlaylistTime());
            st.setInt(3, currentPlaylist.getNumberOfSongs());
            st.setObject(4, currentPlaylist.getSongs());

            st.executeUpdate();

            return currentPlaylist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deletePlaylist(Playlist currentPlaylist) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM playlist WHERE playlistName = ?");
            sc.setString(1, currentPlaylist.getPlaylistName());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}


