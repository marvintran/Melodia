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

import comp3350.melodia.application.Services;
import comp3350.melodia.objects.Playlist;
import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.PlaylistPersistence;
import comp3350.melodia.persistence.SongPersistence;

public class PlaylistPersistenceHSQLDB implements PlaylistPersistence {

    private final String dbPath;
    private SongPersistence songPersistence;

    public PlaylistPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        this.songPersistence = Services.getSongPersistence();
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Playlist fromResultSet(final ResultSet rs) throws SQLException {

        final int playlistID = rs.getInt("playlistID");
        final String playlistName = rs.getString("playlistName");
        ArrayList<Integer> songIDs = getSongsForPlaylist(playlistID);
        List<Song> songs = getSongsFromDB(songIDs);

        int totalTime = 0;

        for(Song song : songs){
            totalTime += song.getSongTime();
        }

        return new Playlist(playlistID, playlistName, totalTime, songs.size(), songs);

    }

    private ArrayList<Integer> getSongsForPlaylist(int playlistID){
        ArrayList<Integer> songs = new ArrayList<>();
        String query = "SELECT " + playlistID + " FROM PLAYLIST_SONGS";

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                final int songID = rs.getInt(1);
                songs.add(songID);
            }
            rs.close();
            st.close();

            return songs;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }

    private ArrayList<Song> getSongsFromDB(ArrayList<Integer> songIDs){
        ArrayList<Song> songs = new ArrayList<>();

        for(Integer songID : songIDs){
            songs.add(songPersistence.getSong(songID));
        }
        return songs;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        final List<Playlist> playlists = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();

            final ResultSet rs = st.executeQuery("SELECT * FROM PLAYLIST_DATA");

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
            final PreparedStatement st = c.prepareStatement("INSERT INTO PLAYLIST_DATA VALUES(?, ?)");
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

            // insert song into playlist
            final PreparedStatement st = c.prepareStatement("INSERT INTO PLAYLIST_SONGS VALUES(?, ?)");
            st.setInt(1, playlistID);
            st.setInt(2, songID);
            st.executeUpdate();

            // update the number of songs in this playlist
            final Statement st2 = c.createStatement();
            final ResultSet rs = st2.executeQuery("SELECT * FROM PLAYLIST WHERE PLAYLISTID = " + playlistID);
            rs.next();

            int numSongsPlusOne = rs.getInt("NUMSONGS") + 1;
            final PreparedStatement st3 = c.prepareStatement("UPDATE PLAYLIST SET NUMSONGS = "+numSongsPlusOne+" WHERE PLAYLISTID = "+playlistID);
            st3.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public void deletePlaylist(Playlist currentPlaylist) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM PLAYLIST_DATA WHERE PLAYLISTID = ?");
            sc.setInt(1, currentPlaylist.getPlaylistID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

