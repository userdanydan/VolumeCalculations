package volume;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VolumeExpertTest {
    private Volume volumeExpert;

    @Test
    void testReadAllFiles() {
        volumeExpert = new Volume(Path.of("src/test/resources"));
        int expected = 10;
        int actual = volumeExpert.getTotalVenuesNumber();
        assertEquals(expected, actual);
    }

    @Test
    void testConstructor() {
        volumeExpert = new Volume(Path.of("src/test/resources"));
        System.err.println(volumeExpert.getPartyGoersSortedById());
    }

    @Test
    void testCalculateVenuesDueMoney(){
        volumeExpert = new Volume(Path.of("src/test/resources"));
        for (Venue v : volumeExpert.getAllVenues()) {
            System.err.println("***********");
            System.err.println(v.getName().toUpperCase()+ " ");
            System.err.println(v.getPartyGoersNumber() + " visitors ");
            int[] visited = new int[4];
            v.getPartyGoers().forEach(pg->{
                if(pg.getVenuesVisited().containsValue(v)){
                    int nbVenuesVisited = pg.getVenuesVisited().size();
                    System.err.println("Visitor  with id " + pg.getId() + " visited " + nbVenuesVisited + " venue(s)");
                    pg.getVenuesVisited().forEach((k,v1)->{
                        System.err.print("   " + v1.getName());
                        System.err.println(" at "+ LocalDateTime.ofInstant(Instant.ofEpochSecond(k), ZoneOffset.UTC));
                    });
                    switch(nbVenuesVisited){
                        case 1-> visited[0]++;
                        case 2-> visited[1]++;
                        case 3-> visited[2]++;
                        case 4-> visited[3]++;
                        default -> {
                            break;
                        }
                    };
                }
            });
            for (int i = 0; i < 4; i++)
                printResults(visited, i);
            System.err.println("Total for this venue: " + v.getTotalMoneyDue());
        }
        System.err.println(volumeExpert.getTotal());
        assertEquals(3180.0
                , volumeExpert.getTotal().doubleValue());
    }

    private static void printResults(int[] visited, int pos) {
        BigDecimal val = BigDecimal.valueOf(30).divide(BigDecimal.valueOf(pos+1), 2, RoundingMode.HALF_UP);
        System.err.println(" "+ "nb with "+(pos+1)+" venue : " + visited[pos] + " -> Ticket price ratio = " + val);
        System.err.println(visited[pos]+" * "+val + " = " + val.multiply(BigDecimal.valueOf(visited[pos])));
    }

    private int compareInt(String id1, String id2) {
        return Integer.parseInt(id1) - Integer.parseInt(id2);
    }

}