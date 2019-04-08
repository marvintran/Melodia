package com.example.melodia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //LibraryManagementTest.class,
        PlaylistManagementTest.class,
        PlaySongsTest.class,
        ViewMusicInfoTest.class
})
public class AllAcceptanceTests {
}