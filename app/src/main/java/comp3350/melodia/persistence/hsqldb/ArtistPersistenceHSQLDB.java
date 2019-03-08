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
import comp3350.melodia.objects.Artist;
import comp3350.melodia.persistence.ArtistPersistence;

public class ArtistPersistenceHSQLDB implements ArtistPersistence{

    private final String dbPath;

    public ArtistPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Artist fromResultSet(final ResultSet rs) throws SQLException {
        final String artistName = rs.getString("artistName");
        final String albums = rs.getString("albums");

        //return new Artist(artistName, albums);

        return null;

    }

    @Override
    public List<Artist> getAllArtists() {
        final List<Artist> artists = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM artists");
            while (rs.next())
            {
                final Artist artist = fromResultSet(rs);
                artists.add(artist);
            }
            rs.close();
            st.close();

            return artists;
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }


    @Override
    public Artist insertArtist(Artist currentArtist) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO artists VALUES(?, ?)");
            st.setString(1, currentArtist.getArtistName());
            //st.setString(2, currentArtist.getAlbums());

            st.executeUpdate();

            return currentArtist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Artist updateArtist(Artist currentArtist) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE artists SET name = ? WHERE artistName = ?");
            st.setString(1, currentArtist.getArtistName());
            st.setObject(2, currentArtist.getAlbums());

            st.executeUpdate();

            return currentArtist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteArtist(Artist currentArtist) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM artists WHERE artistName = ?");
            sc.setString(1, currentArtist.getArtistName());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
