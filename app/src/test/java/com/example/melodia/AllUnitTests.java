package com.example.melodia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.example.melodia.Objects.*;

import com.example.melodia.business.AccessPlaylistTest;
import com.example.melodia.business.AccessSongTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PlaylistTest.class,
        SongTest.class,
        AccountTest.class,
        AccessPlaylistTest.class,
        AccessSongTest.class
})
public class AllUnitTests {

}