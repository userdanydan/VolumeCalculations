package volume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import volume.Venue;

import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class VenueTest {
    private Venue venue;

    @BeforeEach
    void setUp(){
        venue = new Venue(Path.of("src/test/resources/molen.csv"));
    }

    @Test
    void testConstructor(){
        int expected = 4;
        int actual = venue.getPartyGoersNumber();
        assertEquals(expected, actual);
    }

    @Test
    void testPartyGoersList(){
        var partyGoers  =venue.getPartyGoers();
        System.err.println(Arrays.toString(partyGoers.toArray(new PartyGoer[0])));
    }


}