For an events organiser,  I was asked several years ago to write a script to calculate a quote in a total sale of tickets for venues according to the number of people who visited the places. It is not obvious how we could calculate it. 

I decided to provide him with a quick and dirty solution in PHP that he could run in his web server easily.

The files are .csv that was generated by a scanner at the venues.

The algorithm is not obvious at all. It requires to think more than ten minutes.

'''
         define('PRIX_BILLET', 30.0);
	
	...

	
	$clubs['bloody'] = splitIntoClubs($bloody);
	$clubs['fuse'] = splitIntoClubs($fuse);
	...
	
	
	echo '<h2>Total entrances : '. (count($bloody) + count($fuse) +
								     count($groove) + count($mirano) +
									  count($molen) + count($sounds) + 
										count($wood)+ count($opus)+ 
										 count($moustache)+ count($vilaine) + count($you)).'</h2>';
$cpt=0;
	$lignesClubs=array();
	echo '<div class="row">';
	foreach ($clubs as $nomClub => $club){
		echo '<table class="table table-bordered" style="width:50%; margin-left: auto; margin-right: auto;">';
		echo '<caption>';
		echo '<h4 style="text-align:center;">'.ucfirst($nomClub).' : '.count($club).' </h4>';
		echo '</caption>';
		echo '<thead>';
		echo '<tr>';
		echo '<th>ID</th>';
		echo '<th>Arrival time</th>';
		echo '<th>Count</th>';
		echo '</tr>';
		echo '</thead>';
		$count=0;
		foreach ($club as $ligne){
			$lignesClubs[$cpt][0] = $ligne[0];
			$lignesClubs[$cpt][1] = $nomClub;
			$cpt++;
			echo '<tbody>';
			echo '<tr>';
			echo '<td>'.$ligne[0].'</td>'; //id
			echo '<td>'.$ligne[2].'</td>'; //arrivée
			echo '<td>'.++$count.'</td>'; //comptage
			echo '</tr>';
			echo '</tbody>';
		}
		echo '</table>';
	}
	echo '</div>';
	$cpt=0;

	$nomsclubs= array();
	
	$nomsclubs['bloody']=0.0;
	...
	echo '<table class="table table-bordered" style="width:50%; margin-left: auto; margin-right: auto;">';
	echo '<caption><h3 style="text-align:center;">All clubs</h3></caption>';
	echo '<thead>';
	echo '<tr>';
	echo '<th>N°</th>';
	echo '<th>ID</th>';
	echo '<th>Club</th>';
	echo '</tr>';
	echo '</thead>';
	$idPrec=0;
	sort($lignesClubs);
	$checkFinalTotal=array();
	$accuPognon=0.0;
	
	foreach ($lignesClubs as $club){
		$checkFinalTotal[$cpt]=$club[0];
		$cpt++;
		echo '<tbody>';
		echo '<tr>';
		echo '<td>'.$cpt.'</td>'; 
		echo '<td>'.$club[0].'</td>'; //id
		echo '<td>'.$club[1].'</td>'; //nom du club
		echo '</tr>';
		echo '</tbody>';
		$nbClubVisite=0;
		$clubsVisites = array();
		
		if($idPrec!==$club[0]){
				
			foreach($lignesClubs as $club2){
				
				$id = $club2[0];
				$nom = $club2[1];
				
				if($id===$club[0]){
					++$nbClubVisite;
					array_push($clubsVisites, $nom);
				}
				
			}

			foreach($nomsclubs as $nom => &$pognon){
				foreach($clubsVisites as $nomDuClub){
					if($nom===$nomDuClub){
						$pognon+=(PRIX_BILLET/$nbClubVisite);
						$accuPognon+=(PRIX_BILLET/$nbClubVisite);
					}
				}
				unset($pognon);
			}
				
		}
		$idPrec=$club[0];
	}
	echo '</table>';
	
	
	echo '</div>';
	
	$newUnique = array_unique($checkFinalTotal);
	echo '<table class="table table-bordered" style="width:50%; margin-left: auto; margin-right: auto;">';
	echo '<caption><h3 style="text-align:center;">ID Unique</h3></caption>';
	echo '<thead>';
	echo '<tr>';
	echo '<th>Club</th>';
	echo '<th>count</th>';
	echo '</tr>';
	echo '</thead>';
	$totalPognon=0.0;
	$cnt=0;
	foreach($newUnique as $id ){
		$totalPognon+=PRIX_BILLET;
		echo '<tbody>';
		echo '<tr>';
		echo '<td>'.$id.'</td>';
		echo '<td>'.++$cnt.'</td>';
		echo '</tr>';
		echo '</tbody>';
	}
	echo '<p>Total entrances for all id before pro rata calculation (must be the same value as the one in \'pro rata\' table row "All Clubs"): '.$totalPognon.'</p>';
	echo '</tbody>';
	echo '</table>';
	echo '</div>';	
	echo '<table class="table table-bordered table-hover" border="1" style="width:50%; margin-left: auto; margin-right: auto;">';
	echo '<caption><h3 style="text-align:center;">Pro rata</h3></caption>';
	echo '<thead>';
	echo '<tr>';
	echo '<th>Club</th>';
	echo '<th>Total</th>';
	echo '</tr>';
	echo '</thead>';
	$totalPognon=0.0;
	function nbEntreeSansTests($club){
		$nbEntrees=0;
		for($i=0; $i<count($club); $i++){
			if((int)$club[$i][0]<ID_BRACELET_NON_TEST){
				$nbEntrees++;
			}
		}
		return $nbEntrees;
	}
	function splitIntoClubs($club){
		$lignes=array();
		for($i=0; $i<count($club); $i++){
			array_push($lignes, str_replace("\"", "", split(",", $club[$i])));
		}
		return $lignes;
	}
	foreach($nomsclubs as $nom=>$pognon ){
		$totalPognon+=$pognon;
		echo '<tbody>';
		echo '<tr>';
		echo '<td>'.ucfirst($nom).'</td>';
		echo '<td>'.round($pognon,  2).' EUR</td>';
		echo '</tr>';
		echo '</tbody>';
	}
	echo '<tfoot>';
	echo '<tr>';
	echo '<td>All clubs</td>';
	echo '<td>'.$totalPognon .' EUR</td>';
	echo '</tr>';
	echo '</tfoot>';
	echo '</table>'; '''
So, when I wanted to look at it to see if I could reuse it for the new version of the app for volume.brussel, I was totally unable to understand it immediately and reuse it in another language.

Here is where the power of Object-Oriented Programming is coming.

In Java, I just created three classes:

I created a test class to leverage Test Driven Development and then I wrote the classes Venue, PartyGoer and Volume, an 'expert' in object oriented vocabulary. 

I implemented the classes like this: 

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
public class Volume {

    private static final BigDecimal TICKET_PRICE = BigDecimal.valueOf(30.0);
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
And it works perfectly well. 

"Yes but you have ten times more lines of code". 

Yes, indeed, it is far more code, but now I understand it immediately, I know it works, I can run it with the tests, and it is easy to change and maintain. I dare you to work out the php files and change a bit of the algorithm without loosing your mental health !

The project is here : 
