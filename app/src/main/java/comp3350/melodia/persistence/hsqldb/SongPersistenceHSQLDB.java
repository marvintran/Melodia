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
        final String songName = rs.getString("songName");
        final int songTime = rs.getInt("songTime");
        final String artistStr = rs.getString("artist");
        final String albumStr = rs.getString("album");
        final int trackNumber = rs.getInt("trackNumber");
        final String songDataPath = rs.getString("songData");

//        return new Song(songName, songTime, artist, album, trackNumber, songData);
        return null;
    }

    @Override
    public List<Song> getAllSongs() {
        final List<Song> songs = new ArrayList<>();

        try (final Connection c = connection()) {

            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM song");
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
            final PreparedStatement st = c.prepareStatement("INSERT INTO songs VALUES(?, ?)");
            st.setString(1, currentSong.getSongName());
            st.setInt(2, currentSong.getSongTime());
            //st.setObject(2, currentSong.getArtist());
            //st.setObject(2, currentSong.getAlbum());
            st.setInt(2, currentSong.getTrackNumber());

            st.executeUpdate();

            return currentSong;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Song updateSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE songs SET name = ? WHERE songName = ?");
            st.setString(1, currentSong.getSongName());
            st.setInt(2, currentSong.getSongTime());
            st.setObject(2, currentSong.getArtist());
            st.setObject(2, currentSong.getAlbum());
            st.setInt(2, currentSong.getTrackNumber());

            st.executeUpdate();

            return currentSong;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteSong(Song currentSong) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM songs WHERE songName = ?");
            sc.setString(1, currentSong.getSongName());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

