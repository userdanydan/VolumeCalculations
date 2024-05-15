package volume;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Volume {

    private static final BigDecimal TICKET_PRICE =  BigDecimal.valueOf(30.0);
    private final List<Venue> venues = new ArrayList<>();
    private final List<PartyGoer> partyGoers = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    public Volume(final Path venueFilesDir) {
        readAllFiles(venueFilesDir);
        generatePartyGoers();
        calculateVenuesDueMoney();
        calculateTotal();
        System.err.println(total);
    }

    private void calculateTotal() {
        for (Venue v : venues) {
            total = total.add(v.getTotalMoneyDue());
        }
    }

    private void readAllFiles(final Path venueFilesDir) {
        if (Files.isDirectory(venueFilesDir)) {
            try (var dir = Files.walk(venueFilesDir)) {
                dir.filter(p -> p.toString().endsWith(".csv"))
                        .map(Venue::new)
                        .peek(v -> partyGoers.addAll(v.getPartyGoers()))
                        .forEach(this::addVenue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void generatePartyGoers() {
        for (int i = 0; i < partyGoers.size() - 1; i++) {
            PartyGoer pg1 = partyGoers.get(i);
            for (int j = i + 1; j < partyGoers.size() - 1; j++) {
                PartyGoer pg2 = partyGoers.get(j);
                if (pg1.getId().equals(pg2.getId())) {
                    Long key = pg2.getVenuesVisited().keySet().iterator().next();
                    pg1.addVenue(LocalDateTime.ofInstant(Instant.ofEpochSecond(key),
                            ZoneId.systemDefault()), pg2.getVenuesVisited().get(key));
                    PartyGoer remove = partyGoers.remove(j);
                    if (remove == null) {
                        System.err.println("remove is null");
                    }
                }
            }
        }
    }

    public void addVenue(Venue venue) {
        this.venues.add(venue);
    }

    public int getTotalVenuesNumber() {
        return venues.size();
    }

    public List<String> toVenuesNames() {
        return venues.stream()
                .map(Venue::getName)
                .collect(Collectors.toList());
    }

    public List<Venue> getAllVenues() {
        return new ArrayList<>(this.venues);
    }

    public List<PartyGoer> getPartyGoers() {
        return new ArrayList<>(this.partyGoers);
    }

    public List<PartyGoer> getPartyGoersSortedById() {
        return new ArrayList<>(this.partyGoers)
                .stream()
                .sorted((pg1, pg2) -> compareInt(pg1.getId(), pg2.getId()))
                .toList();
    }

    public static int compareInt(String id1, String id2) {
        return Integer.parseInt(id1) - Integer.parseInt(id2);
    }

    private void calculateVenuesDueMoney() {
        getPartyGoers().forEach(pg -> {
            int nbVenuesVisited = pg.getVenuesVisited().values().size();
            for (Venue v : pg.getVenuesVisited().values()) {
                v.setTotalMoneyDue(v.getTotalMoneyDue().add(TICKET_PRICE.divide(BigDecimal.valueOf(nbVenuesVisited), 2, RoundingMode.HALF_UP)));
            }
        });
    }
    public BigDecimal getTotal() {
        return total;
    }
}
