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

        final int songID = rs.getInt("songID");
        final String songName = rs.getString("songName");
        final int songTime = rs.getInt("songTime");
        final int albumID = rs.getInt("albumID");
        final String albumName = rs.getString("albumName");
        final int artistID = rs.getInt("artistID");
        final String artistName = rs.getString("artistName");
        final int trackNumber = rs.getInt("trackNumber");
        final String songDataPath = rs.getString("songPath");

        File song = new File(songDataPath);

        return new Song(songID, songName, songTime, albumID, albumName, artistID, artistName, trackNumber, song);
    }

    @Override
    public List<Song> getAllSongs() {
        final List<Song> songs = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM SONG");
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

    public Song getSong(int songID){
        String query = "SELECT " + songID + " FROM SONG";

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery(query);

            if(!rs.next()){
                throw new RuntimeException("No songs found with ID " + songID);
            }

            String songName = rs.getString(1);
            int songTime = rs.getInt(2);
            int albumID = rs.getInt(3);
            String albumName = rs.getString(4);
            int artistID = rs.getInt(5);
            String artistName = rs.getString(6);
            int trackNumber = rs.getInt(7);
            String filePath = rs.getString(8);

            rs.close();
            st.close();

            return new Song(songID, songName, songTime, albumID, albumName, artistID, artistName, trackNumber, new File(filePath));
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }

    }

    @Override
    public Song insertSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO SONG VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(0, currentSong.getSongID());
            st.setString(1, currentSong.getSongName());
            st.setInt(2, currentSong.getSongTime());
            st.setInt(3, currentSong.getAlbumID());
            st.setString(4, currentSong.getAlbumName());
            st.setInt(5, currentSong.getArtistID());
            st.setString(6, currentSong.getArtistName());
            st.setInt(7, currentSong.getTrackNumber());
            st.setString(8, currentSong.getSongData().getPath());

            st.executeUpdate();

            return currentSong;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Song updateSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO SONG VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(0, currentSong.getSongID());
            st.setString(1, currentSong.getSongName());
            st.setInt(2, currentSong.getSongTime());
            st.setInt(3, currentSong.getAlbumID());
            st.setString(4, currentSong.getAlbumName());
            st.setInt(5, currentSong.getArtistID());
            st.setString(6, currentSong.getArtistName());
            st.setInt(7, currentSong.getTrackNumber());
            st.setString(8, currentSong.getSongData().getPath());

            st.executeUpdate();

            return currentSong;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM SONG WHERE SONGID = ?");
            sc.setInt(1, currentSong.getSongID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

