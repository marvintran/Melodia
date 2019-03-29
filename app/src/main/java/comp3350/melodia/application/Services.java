package comp3350.melodia.application;

import comp3350.melodia.persistence.AccountPersistence;
import comp3350.melodia.persistence.PlaylistPersistence;
import comp3350.melodia.persistence.SongPersistence;
import comp3350.melodia.persistence.ArtistPersistence;
import comp3350.melodia.persistence.AlbumPersistence;
import comp3350.melodia.persistence.hsqldb.AlbumPersistenceHSQLDB;
import comp3350.melodia.persistence.hsqldb.ArtistPersistenceHSQLDB;
import comp3350.melodia.persistence.hsqldb.AccountPersistenceHSQLDB;
import comp3350.melodia.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import comp3350.melodia.persistence.hsqldb.SongPersistenceHSQLDB;

public class Services
{
    private static AccountPersistence accountPersistence = null;
    private static PlaylistPersistence playlistPersistence = null;
    private static SongPersistence songPersistence = null;
    private static ArtistPersistence artistPersistence = null;
    private static AlbumPersistence albumPersistence = null;

    public static synchronized AccountPersistence getAccountPersistence()
    {
        if (accountPersistence == null)
        {
            accountPersistence = new AccountPersistenceHSQLDB(Main.getDBPathName());
        }

        return accountPersistence;
    }

    public static synchronized PlaylistPersistence getPlaylistPersistence()
    {
        if (playlistPersistence == null)
        {
            playlistPersistence = new PlaylistPersistenceHSQLDB(Main.getDBPathName());
        }

        return playlistPersistence;
    }

    public static synchronized SongPersistence getSongPersistence()
    {
        if (songPersistence == null) {

            songPersistence = new SongPersistenceHSQLDB(Main.getDBPathName());
        }
        return songPersistence;
    }

    public static synchronized ArtistPersistence getArtistPersistence()
    {
        if (artistPersistence == null) {

            artistPersistence = new ArtistPersistenceHSQLDB(Main.getDBPathName());
        }
        return artistPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence()
    {
        if (albumPersistence == null) {

            albumPersistence = new AlbumPersistenceHSQLDB(Main.getDBPathName());
        }
        return albumPersistence;
    }
}

