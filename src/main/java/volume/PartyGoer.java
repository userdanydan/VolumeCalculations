package volume;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class PartyGoer {

    private final String id;
    private final Map<Long, Venue> venuesVisited = new TreeMap<>();

    public PartyGoer(String id) {
        this.id = id;
    }

    public Map<Long, Venue> getVenuesVisited() {
        return new HashMap<>(venuesVisited);
    }

    public void addVenue(final LocalDateTime arrivedDateTime, final Venue venue) {
        this.venuesVisited.put(arrivedDateTime.toEpochSecond(ZoneOffset.UTC), venue);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyGoer partyGoer = (PartyGoer) o;
        return Objects.equals(id, partyGoer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PartyGoer{" +
                "id='" + id + '\'' +
                ", venuesVisited=" + venuesVisited +
                '}';
    }
}
