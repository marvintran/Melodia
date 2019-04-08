package comp3350.melodia.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.objects.Song;
import comp3350.melodia.persistence.SongPersistence;

public class SongPersistenceHSQLDB implements SongPersistence {

    private final String dbPath;

    public SongPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Song fromResultSet(final ResultSet rs) throws SQLException {
        return new Song.Builder()
                .withSongID(rs.getInt("songID"))
                .withSongName(rs.getString("songName"))
                .withSongTime(rs.getInt("songTime"))
                .withAlbumID(rs.getInt("albumID"))
                .withAlbumName(rs.getString("albumName"))
                .withArtistID(rs.getInt("artistID"))
                .withArtistName(rs.getString("artistName"))
                .withTrackNumber(rs.getInt("trackNumber"))
                .withSongData(rs.getString("songPath"))
                .build();
    }

    @Override
    public List<Song> getSongsSortedTrackName() {
        final List<Song> songs = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM SONG ORDER BY SONGNAME ASC");
            while (rs.next())
            {
                final Song song = fromResultSet(rs);
                songs.add(song);
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

    @Override
    public List<Song> getSongsSortedArtist() {
        final List<Song> songs = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM SONG ORDER BY ARTISTNAME, SONGNAME ASC");
            while (rs.next())
            {
                final Song song = fromResultSet(rs);
                songs.add(song);
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

    @Override
    public List<Song> getPlaylistSongs(int playlistID) {
        final List<Song> songs = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(
                    "SELECT * FROM PLAYLIST_SONGS WHERE PLAYLISTID = " +
                            playlistID +" ORDER BY POSITION ASC");
            while (rs.next())
            {
                final Statement st2 = c.createStatement();
                final ResultSet songIDs = st2.executeQuery(
                        "SELECT * FROM SONG WHERE SONGID = "
                                + rs.getInt("songID"));
                songIDs.next();
                final Song song = fromResultSet(songIDs);
                songs.add(song);
            }
            rs.close();
            st.close();
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }

        return songs;
    }

    @Override
    public void insertPlaylistSong(int playlistID, int songID, int position) {
        try (final Connection c = connection()) {

            final PreparedStatement st = c.prepareStatement(
                    "UPDATE PLAYLIST_SONGS SET POSITION = POSITION + 1 " +
                            "WHERE PLAYLISTID = ? AND POSITION >= ?");
            st.setInt(1, playlistID);
            st.setInt(2, position);

            st.executeUpdate();

            final PreparedStatement st2 = c.prepareStatement(
                    "INSERT INTO PLAYLIST_SONGS VALUES(?, ?, ?)");
            st2.setInt(1, playlistID);
            st2.setInt(2, songID);
            st2.setInt(3, position);
            st2.executeUpdate();

            // Update the number of songs in this playlist.
            final Statement st3 = c.createStatement();
            final ResultSet rs = st3.executeQuery(
                    "SELECT * FROM PLAYLIST WHERE PLAYLISTID = " + playlistID);
            rs.next();

            int numSongsPlusOne = rs.getInt("NUMSONGS") + 1;
            final PreparedStatement st4 = c.prepareStatement(
                    "UPDATE PLAYLIST SET NUMSONGS = " + numSongsPlusOne +
                            " WHERE PLAYLISTID = "+playlistID);
            st4.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deletePlaylistSong(int playlistID, int position) {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement(
                    "DELETE FROM PLAYLIST_SONGS " +
                            "WHERE PLAYLISTID = ? AND POSITION = ?");
            sc.setInt(1, playlistID);
            sc.setInt(2, position);
            sc.executeUpdate();

            final PreparedStatement sc2 = c.prepareStatement(
                    "UPDATE PLAYLIST_SONGS SET POSITION = POSITION - 1 " +
                            "WHERE PLAYLISTID = ? AND POSITION > ?");
            sc2.setInt(1, playlistID);
            sc2.setInt(2, position);
            sc2.executeUpdate();

            // Update the number of songs in this playlist.
            final Statement st2 = c.createStatement();
            final ResultSet rs = st2.executeQuery(
                    "SELECT * FROM PLAYLIST WHERE PLAYLISTID = " + playlistID);
            rs.next();

            int numSongsMinusOne = rs.getInt("NUMSONGS") - 1;
            final PreparedStatement st3 = c.prepareStatement(
                    "UPDATE PLAYLIST SET NUMSONGS = " + numSongsMinusOne +
                            " WHERE PLAYLISTID = "+playlistID);
            st3.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

