<?php
/*
 * Script permettant de calculer avec une très grande précision tout plein de trucs.
 */
	header( 'content-type: text/html; charset=utf-8' );
	setlocale(LC_ALL, "fr_FR");	
	echo "<!-- Latest compiled and minified CSS -->
		<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">";
	echo "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">
			<link rel=\"stylesheet\" href=\"https://code.getmdl.io/1.1.1/material.indigo-pink.min.css\">";
	echo "<script defer src=\"https://code.getmdl.io/1.1.1/material.min.js\"></script>";
	
	echo '<body style="background-color:honeydew;"> ';
	echo '<div class="container-fluid">';

	define('PRIX_BILLET', 30.0);
	
	$clubs = array();
	
	$bloody = file("bloody.csv");
	$fuse = file("fuse.csv");
	$groove = file("groove.csv");
	$mirano = file("mirano.csv");
	$molen =file("molen.csv");
	$sounds =file("sounds.csv");
	$wood = file("wood.csv");
	$opus = file("opus.csv");
	$moustache = file("moustache.csv");
	$vilaine = file("vilaine.csv");
	$you = file("you.csv");

	
	$clubs['bloody'] = splitIntoClubs($bloody);
	$clubs['fuse'] = splitIntoClubs($fuse);
	$clubs['groove'] = splitIntoClubs($groove);
	$clubs['mirano'] = splitIntoClubs($mirano);
	$clubs['molen'] =splitIntoClubs($molen);
	$clubs['sounds'] =splitIntoClubs($sounds);
	$clubs['wood'] = splitIntoClubs($wood);
	$clubs['opus'] = splitIntoClubs($opus);
	$clubs['moustache'] = splitIntoClubs($moustache);
	$clubs['vilaine'] = splitIntoClubs($vilaine);
	$clubs['you'] = splitIntoClubs($you);
	
	
	echo '<h2>Total entrances : '. (count($bloody) + count($fuse) +
								     count($groove) + count($mirano) +
									  count($molen) + count($sounds) + 
										count($wood)+ count($opus)+ 
										 count($moustache)+ count($vilaine) + count($you)).'</h2>';

// 	echo '<p>nb d\'entrées : </p>';
// 	echo 'bloody : '.count($bloody).'</br>' ;
// 	echo 'fuse : '.count($fuse).'</br>' ;
// 	echo 'groove : '.count($groove).'</br>';
// 	echo 'mirano : '.count($mirano).'</br>';
// 	echo 'molen  : '.count($molen).'</br>';
// 	echo 'sound : '.count($sounds).'</br>';
// 	echo 'wood : '.count($wood).'</br>';
// 	echo 'opus : '.count($opus).'</br>';
// 	echo 'moustache  : '.count($moustache).'</br>';
// 	echo 'vilaine : '.count($vilaine).'</br>';
	
	
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
	$nomsclubs['fuse'] =0.0;
	$nomsclubs['groove'] =0.0;
	$nomsclubs['mirano'] =0.0;
	$nomsclubs['molen'] =0.0;
	$nomsclubs['sounds'] =0.0;
	$nomsclubs['wood'] =0.0;
	$nomsclubs['opus'] =0.0;
	$nomsclubs['moustache'] =0.0;
	$nomsclubs['vilaine']=0.0;
	$nomsclubs['you']=0.0;	
	
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
	echo '</table>';
	echo "<!-- jQuery library -->
		<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>";
		
 	echo	"<!-- Latest compiled JavaScript -->
 		<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script>";
	
