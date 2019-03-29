package comp3350.melodia.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

        final int artistID = rs.getInt("artistID");
        final String artistName = rs.getString("artistName");
        final String albums = rs.getString("albums");

        return new Artist(artistID, artistName, null);
    }

    @Override
    public List<Artist> getAllArtists() {
        final List<Artist> artists = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM ARTIST");
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
            final PreparedStatement st = c.prepareStatement("INSERT INTO ARTIST VALUES(?, ?)");
            st.setInt(1, currentArtist.getArtistID());
            st.setString(2, currentArtist.getArtistName());

            st.executeUpdate();

            return currentArtist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Artist updateArtist(Artist currentArtist) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE ARTIST SET name = ? WHERE ARTISTID = ?");
            st.setString(1, currentArtist.getArtistName());
            st.setInt(2, currentArtist.getArtistID());

            st.executeUpdate();

            return currentArtist;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteArtist(Artist currentArtist) {

        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM ARTIST WHERE ARTISTID = ?");
            sc.setInt(1, currentArtist.getArtistID());
            sc.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
