package comp3350.melodia.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.melodia.objects.Album;
import comp3350.melodia.persistence.AlbumPersistence;

public class AlbumPersistenceHSQLDB implements AlbumPersistence {

    private final String dbPath;

    public AlbumPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Album fromResultSet(final ResultSet rs) throws SQLException {

        final int albumID = rs.getInt("albumID");
        final String albumNameStr = rs.getString("albumNameStr");
        final String songs = rs.getString("songs");
        final String genreNameStr = rs.getString("genreName");

        //return new Album(albumNameStr, List<Song> songs, genreNameStr);

        return null;
    }

    @Override
    public List<Album> getAllAlbums() {
        final List<Album> albums = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ALBUM");
            while (rs.next())
            {
                final Album album = fromResultSet(rs);
                albums.add(album);
            }
            rs.close();
            st.close();

            return albums;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }


    @Override
    public Album insertAlbum(Album currentAlbum) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO ALBUM VALUES(?, ?, ?, ?)");
            st.setInt(1, currentAlbum.getAlbumID());
            st.setString(2, currentAlbum.getAlbumName());
            st.setObject(3, currentAlbum.getSongs());
            st.setObject(4, currentAlbum.getAlbumGenre());

            st.executeUpdate();

            return currentAlbum;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Album updateAlbum(Album currentAlbum) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE ALBUM SET name = ? WHERE ALBUMID = ?");
            st.setString(1, currentAlbum.getAlbumName());
            st.setInt(2, currentAlbum.getAlbumID());

            st.executeUpdate();

            return currentAlbum;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteAlbum(Album currentAlbum) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM ALBUM WHERE ALBUMID = ?");
            sc.setInt(1, currentAlbum.getAlbumID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}