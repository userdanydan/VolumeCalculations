package volume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PartyGoerTest {

    private PartyGoer partyGoer;

    @BeforeEach
    void setUp() {
        partyGoer = new PartyGoer("1");
    }
    @Test
    void testPartyGoer(){
        Venue venue = new Venue(Path.of("src/test/resources/bloody.csv"));
        partyGoer.addVenue(LocalDateTime.of(2000, 1, 1, 1, 1, 1), venue);
        System.err.println(partyGoer);
    }
}