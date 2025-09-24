package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		new Marche(nbEtal);
	}
	
	public class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			 this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		 }
		 
		public int trouverEtalLibre(){
			 int etalLibre = -1;
			 for(int i = 0; i < etals.length && etalLibre == -1;i++) {
				 if (etals[i].isEtalOccupe()) {
					 etalLibre = i;
				 }
			 }
			 return etalLibre;
		 }
		 
		public Etal[] trouverEtals(String produit) {
			 int nbEtalTrouver = 0;
			 for(int i = 0; i < etals.length;i++) {
				 if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					 nbEtalTrouver++;
				 }
			 }
			 Etal[] etalsAvecProduit;
			 etalsAvecProduit = new Etal[nbEtalTrouver];
			 int indiceEtalProduit = 0;
			 for(int j = 0; j < etals.length;j++) {
				 if (etals[j].isEtalOccupe() && etals[j].contientProduit(produit)) {
					 etalsAvecProduit[indiceEtalProduit] = etals[j];
					 indiceEtalProduit++;
				 }
			 }
			 return etalsAvecProduit;
		 }
		 
		public Etal trouverVendeur(Gaulois gaulois){
			 for(int i = 0; i < etals.length;i++) {
				 if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					 return etals[i];
				 }
			 }
			 return null;
		 }
		 
		 public String afficherMarche(){
			 int nbEtalVide = 0;
			 for(int i = 0; i < etals.length;i++) {
				 if (etals[i].isEtalOccupe()) {
					 etals[i].afficherEtal();
				 }
				 else {
					 etals[i].afficherEtal();
					 nbEtalVide++;
				 }
			 }
			 if (nbEtalVide == 0) {
				 return null;
			 }
			 else {
				 return "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			 }
		 }
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit){
		StringBuilder chaine = new StringBuilder();
		// A FAIRE
		return chaine.toString();
	}
}