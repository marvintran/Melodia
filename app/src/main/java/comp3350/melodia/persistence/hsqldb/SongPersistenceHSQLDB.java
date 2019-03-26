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

import comp3350.melodia.objects.Album;
import comp3350.melodia.objects.Artist;
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
        final String artistStr = rs.getString("artistName");
        final String albumStr = rs.getString("albumName");
        final int trackNumber = rs.getInt("trackNumber");
        final String songDataPath = rs.getString("songPath");

        File song = new File(songDataPath);
        //Artist artist = new Artist(artistStr);
        //Album album = new Album(albumStr);

        return new Song(songID, songName, songTime, null, null, trackNumber, song);
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


    @Override
    public Song insertSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO SONG VALUES(?, ?, ?, ?, ?, ?)");
            st.setInt(1, currentSong.getSongID());
            st.setString(2, currentSong.getSongName());
            st.setInt(3, currentSong.getSongTime());
            st.setObject(4, currentSong.getArtist());
            st.setObject(5, currentSong.getAlbum());
            st.setInt(6, currentSong.getTrackNumber());

            st.executeUpdate();

            return currentSong;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Song updateSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE SONG SET name = ? WHERE SONGID = ?");
            st.setString(1, currentSong.getSongName());
            st.setInt(2, currentSong.getSongID());


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

