package com.example.melodia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.example.melodia.business.AccessPlaylistIT;
import com.example.melodia.business.AccessSongIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessPlaylistIT.class,
        AccessSongIT.class
})
public class IntegrationTests {
}

