package comp3350.melodia.application;

import comp3350.melodia.persistance.AccountPersistence;
import comp3350.melodia.persistance.PlaylistPersistence;
import comp3350.melodia.persistance.SongPersistence;
import comp3350.melodia.persistance.ArtistPersistence;
import comp3350.melodia.persistance.AlbumPersistence;
import comp3350.melodia.persistance.Stubs.AlbumPersistenceStub;
import comp3350.melodia.persistance.Stubs.ArtistPersistenceStub;
import comp3350.melodia.persistance.stubs.AccountPersistenceStub;
import comp3350.melodia.persistence.Stubs.PlaylistPersistenceStub;
import comp3350.melodia.persistence.stubs.SongPersistenceStub;

public class Services
{
    private static SongPersistence songPersistence = null;
    private static ArtistPersistence artistPersistence = null;
    private static AlbumPersistence albumPersistence = null;
    private static AccountPersistence accountPersistence = null;
    private static PlaylistPersistence playlistPersistence = null;

    public static synchronized SongPersistence getSongPersistence()
    {
        if (songPersistence == null)
        {
            songPersistence = new SongPersistenceStub();
        }

        return songPersistence;
    }

    public static synchronized ArtistPersistence getArtistPersistence()
    {
        if (artistPersistence == null)
        {
            artistPersistence = new ArtistPersistenceStub();
        }

        return artistPersistence;
    }

    public static synchronized AlbumPersistence getAlbumPersistence()
    {
        if (albumPersistence == null)
        {
            albumPersistence = new AlbumPersistenceStub();
        }

        return albumPersistence;
    }

    public static synchronized AccountPersistence getAccountPersistence()
    {
        if (accountPersistence == null)
        {
            accountPersistence = new AccountPersistenceStub();
        }
        return accountPersistence;
    }

    public static synchronized PlaylistPersistence getPlaylistPersistence()
    {
        if (playlistPersistence == null)
        {
            playlistPersistence = new PlaylistPersistenceStub();
        }
        return playlistPersistence;
    }
}
