package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal(); // Initialisation de chaque �tal
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {
			int etalLibre = -1;
			for (int i = 0; i < etals.length && etalLibre == -1; i++) {
				if (!etals[i].isEtalOccupe()) {
					etalLibre = i;
				}
			}
			return etalLibre;
		}

		public Etal[] trouverEtals(String produit) {
			int nbEtalTrouver = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalTrouver++;
				}
			}
			Etal[] etalsAvecProduit;
			etalsAvecProduit = new Etal[nbEtalTrouver];
			int indiceEtalProduit = 0;
			for (int j = 0; j < etals.length; j++) {
				if (etals[j].isEtalOccupe() && etals[j].contientProduit(produit)) {
					etalsAvecProduit[indiceEtalProduit] = etals[j];
					indiceEtalProduit++;
				}
			}
			return etalsAvecProduit;
		}

		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				} else {
					etals[i].afficherEtal();
					nbEtalVide++;
				}
			}
			if (nbEtalVide == 0) {
				return null;
			} else {
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
		if (nomGaulois != null && nomGaulois.equals(chef.getNom())) {
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

	public String afficherVillageois() throws VillageSansChefException {
		// Mofification pour lever Exception
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef !");
		}

		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int etalLibretrouver = marche.trouverEtalLibre();
		if (etalLibretrouver == -1) {
			chaine.append("Le vendeur " + vendeur.getNom()
					+ " n'a pas pu être installé car il n'y a plus d'étals de libre.");
		} else {
			marche.utiliserEtal(etalLibretrouver, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°"
					+ (etalLibretrouver + 1) + ".");
			// Ici on a (etalLibretrouver+1) car "l'étal n°0 est libre" ça n'est pas
			// très cohérant, donc on mais +1 pour initialisé à 1.
		}

		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalAvecProduit = marche.trouverEtals(produit);
		if (etalAvecProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeurs qui propose des " + produit + " au marché.");
		} else if (etalAvecProduit.length == 1) {
			chaine.append("Seuls le vendeur " + etalAvecProduit[0].getVendeur().getNom() + " propose des " + produit
					+ " au marché.");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :");
			for (int i = 0; i < etalAvecProduit.length; i++) {
				chaine.append("\n- " + etalAvecProduit[i].getVendeur().getNom());
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeurPartant = rechercherEtal(vendeur);
		return etalVendeurPartant.libererEtal();
		// On ne peux accéder aux attributs quantiteDebutMarche et quantite que part la
		// méthode
		// libererEtal, donc l'affichage rend bizarre pourtant on ne doit pas modifier
		// Etal.java...
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i].isEtalOccupe()) {
				chaine.append(marche.etals[i].afficherEtal());
				// Pareil que pour "partirVendeur", on ne peut pas modifier Etal.java donc...
			}
		}
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}

}