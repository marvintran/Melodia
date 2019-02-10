package com.example.melodia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.example.melodia.Objects.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlbumTest.class,
        ArtistTest.class,
        GenreTest.class,
        PlaylistTest.class,
        SongTest.class
})
public class AllTests {

}