package com.iot.monitoring.service;

import java.net.InetAddress;
import java.util.List;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iot.monitoring.model.De;

/*L'annotation @Transactional permet de délimiter une transaction (entre le début et la fin de la méthode) et de définir le comportement transactionnel d'une méthode.

    propagation : précise le mode de propagation de la transaction grâce à une énumération de type Propagation. La valeur par défaut est Propagation.REQUIRED
    readonly : booléen qui précise de façon informative au système de gestion des transactions sous-jacent si la transaction est en lecture seule (true) ou si elle effectue des mises à jour (false)
    isolation : précise le niveau d'isolation de la transaction grâce à une énumération de type Isolation. La valeur par défaut est Isolation.DEFAULT
    timeout : entier qui précise le timeout de la transaction

*/
@Transactional
@Service //l'annotation @Service, cela permet d'utiliser l'auto détection. 
public class IotService {
	
	//variables d'instance	
	@Autowired
	JTransfo jTransfo;
	

	public String checkResultat(List<De> des, int player){
		
		/* On calcule la somme des lancers pour chaque dé */
		int scoreFinalDe1 = des.get(0).getNumber1() + des.get(0).getNumber2();
		int scoreFinalDe2 = des.get(1).getNumber1() + des.get(1).getNumber2();
		
		/* Le nombre de répétitions est égal à la différence de la somme des dés pour chaque joueur */
		int nbSerie = getNbRepetitions(des) ;
		int idGagnant = -1;
		int idPerdant = -1;
		String resultat = "";
		String gage ="";
		
		/* Si les 2 joueurs ont joué */
		if(des.get(0).isaJouer() && des.get(1).isaJouer()){
			
			/* Si le score1 est plus grand alors gagnant = joueur 1 */
			if(scoreFinalDe1 < scoreFinalDe2){
				idGagnant = des.get(0).getId();
				idPerdant = des.get(1).getId();
				gage = getGage(des.get(1));
			} else if(scoreFinalDe2 <scoreFinalDe1) {
				idGagnant = des.get(1).getId();
				idPerdant = des.get(0).getId();
				gage = getGage(des.get(0));
			} else {
				idGagnant = -1;
			}
			
			/* Si l'id gagnant est celui du lanceur */
			if(idGagnant == -1) {
				resultat = "0,0,rien";
				//resultat = "Egalité";
			} else {
				if(idGagnant == player) {
					resultat = "1,"+nbSerie+","+gage;
					
					//resultat = " Vous avez perdu, faites " +nbSerie+ " " +(nbSerie== 1 ? gage : gage+"s" )+ " fois! ";
				} else if(idPerdant == player){
					resultat = "2,"+nbSerie+","+gage;
					//resultat = " Vous avez gagné, l'adversaire fait " +nbSerie+ " " +(nbSerie== 1 ? gage : gage+"s" )+ " fois! ";
				}
				else{
					resultat= "-1,0,rien";
					//resultat= "Le joueur n'existe pas";
				}
			}	
		} else if( !des.get(0).isaJouer() || !des.get(1).isaJouer()) {
			resultat = "3,0,rien";
		}
		
		// On passe resultat recup à true pour reset la partie
		
//		if(des.get(0).isaJouer() && des.get(1).isaJouer()){
//		
//		}
//		
//		if(des.get(0).isaJouer()) {
//			des.get(0).setResultatRecup(true);
//		}
//		if(des.get(1).isaJouer()) {
//			des.get(1).setResultatRecup(true);
//		}
		return resultat;
	}
		
		
			
			// Si fouble 			
			
			/*if(scoreFinalDe1 > scoreFinalDe2){
				//si double
				if(des.get(0).getNumber1() == des.get(0).getNumber2()){
					scoreFinalDe1*=2;
				}
				if(des.get(1).getNumber1() == des.get(1).getNumber2()){
					scoreFinalDe1*=2;
				}
				
				nbSerie = scoreFinalDe1 - scoreFinalDe2;
				idGagnant = des.get(0).getId();
				resultat = "Gagner";
				
			}else if(scoreFinalDe2 > scoreFinalDe1){
				
				//si double
				if(des.get(0).getNumber1() == des.get(0).getNumber2()){
					scoreFinalDe1*=2;
				}
				if(des.get(1).getNumber1() == des.get(1).getNumber2()){
					scoreFinalDe1*=2;
				
				nbSerie = scoreFinalDe2 - scoreFinalDe1;
				idGagnant = des.get(1).getId();
				
				resultat = "Perdu !";
				
			}else{
				resultat = "Egalite";
			}
			
			return resultat;
			
		}*/
	
	
		public int getNbRepetitions(List<De> des){
			
			int scoreDe1 = des.get(0).getNumber1() + des.get(0).getNumber2();
			int scoreDe2 = des.get(1).getNumber1() + des.get(1).getNumber2();
			int repetitions;
			
			if(scoreDe1 > scoreDe2) {
				repetitions = multiplierSeries(des.get(0)) * (scoreDe1 - scoreDe2);
				
			} else if( scoreDe1 < scoreDe2) {
				repetitions = multiplierSeries(des.get(1)) * (scoreDe2 - scoreDe1);
			} else {
				repetitions = 1;
			}
			
			return repetitions;
		}
		
		
		public int multiplierSeries(De de) {
			
			int multiplicateur =1;
			if(de.getNumber1() == de.getNumber2() ) {
				multiplicateur = 2;
			} else {
				multiplicateur =1;
			}
			return multiplicateur;
		}
		
		
		public String getGage(De de) {
			String gage ="";
			
			// Si double
			if(de.getNumber1() == de.getNumber2()) {
				gage = "abdo";
			// Si la somme des lancers est paire	
			} else if( (de.getNumber1() + de.getNumber2()) %2 == 0 ){
				gage = "pompe";
			// Si la somme des lancers est impaire	
			} else if( (de.getNumber1() + de.getNumber2()) %2 != 0) {
				gage = "squat";
			} 
			
		return gage;
		}
		
		
}
