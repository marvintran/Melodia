package comp3350.melodia.application;

import comp3350.melodia.persistence.AccountPersistence;
import comp3350.melodia.persistence.PlaylistPersistence;
import comp3350.melodia.persistence.SongPersistence;
import comp3350.melodia.persistence.hsqldb.AccountPersistenceHSQLDB;
import comp3350.melodia.persistence.hsqldb.PlaylistPersistenceHSQLDB;
import comp3350.melodia.persistence.hsqldb.SongPersistenceHSQLDB;

public class Services
{
    private static AccountPersistence accountPersistence = null;
    private static PlaylistPersistence playlistPersistence = null;
    private static SongPersistence songPersistence = null;

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
}

