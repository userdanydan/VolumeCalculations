package volume;


import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Venue {
    private String name;
    private final List<PartyGoer> partyGoers = new ArrayList<>();
    private BigDecimal totalMoneyDue = BigDecimal.ZERO;

    public Venue(Path path) {
        fromCSV(path);
    }

    private void fromCSV(final Path path) {
        this.name = getNameFromFileTitle(path);
        try (var lines = Files.lines(path)) {
            lines.forEach(l -> {
                String[] infos = l.split(",");
                String id = infos[0];
                var temp = infos[2].split(" ");
                LocalTime arrivalHour;
                if (temp.length == 1)
                    arrivalHour = LocalTime.parse(infos[2]);
                else if (temp.length == 2)
                    arrivalHour = LocalTime.parse(infos[2], DateTimeFormatter.ofPattern("h:mm:ss a"));
                else
                    arrivalHour = LocalTime.of(0, 0, 0);
                LocalDate arrivalDate = LocalDate.parse(infos[3], DateTimeFormatter.ofPattern("dd-MM-yy"));
                PartyGoer partyGoer = new PartyGoer(id);
                boolean isAlreadyThere = isInPartyGoers(partyGoer, partyGoers);
                if (!isAlreadyThere) {
                    partyGoer.addVenue(LocalDateTime.of(arrivalDate, arrivalHour), this);
                    partyGoers.add(partyGoer);
                } else {
                    partyGoers.get(partyGoers.indexOf(partyGoer)).addVenue(LocalDateTime.of(arrivalDate, arrivalHour), this);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isInPartyGoers(PartyGoer partyGoer, List<PartyGoer> partyGoers) {
        return partyGoers.stream()
                .map(PartyGoer::getId)
                .anyMatch(id -> id.equals(partyGoer.getId()));
    }

    private String getNameFromFileTitle(Path path) {
        Path lastPath = path.getFileName();
        return lastPath.toString().substring(0, lastPath.toString().indexOf(".csv"));
    }

    public int getPartyGoersNumber() {
        return this.partyGoers.size();
    }

    public List<PartyGoer> getPartyGoers() {
        return new ArrayList<>(this.partyGoers);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalMoneyDue() {
        return totalMoneyDue;
    }

    public void setTotalMoneyDue(BigDecimal totalMoneyDue) {
        this.totalMoneyDue = totalMoneyDue;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                '}';
    }
}
