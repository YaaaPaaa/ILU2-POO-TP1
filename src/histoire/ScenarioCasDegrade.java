package histoire;

import villagegaulois.Etal;
import personnages.Gaulois;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {
    public static void main(String[] args) {
        Etal etal = new Etal();
        
        //Gérer Exception 1)
        etal.libererEtal();
        System.out.println("Fin du test 1).\n");
        
        //Gérer Excetption 2)
        //a)
        Gaulois asterix = new Gaulois("asterix", 5); 	//Ecrit aussi pour ne pas que ça interfère avec c)
        etal.occuperEtal(asterix, "Fleurs", 10);		//Bien que plus utile pout b)
        
        etal.acheterProduit(5, null);
        System.out.println("Fin du test 2)a).");
        
        //b)
        try {
        	etal.acheterProduit(-5, asterix);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception capturée : " + e.getMessage());
        }
        System.out.println("Fin du test 2)b).");
        
        //c)
        Etal etalVide = new Etal();
        try {
        	etalVide.acheterProduit(5, asterix);
        } catch (IllegalStateException e) {
            System.out.println("Exception capturée : " + e.getMessage());
        }
        System.out.println("Fin du test 2)c).");
        
        //Test Classe Village
        Village village = new Village("TOULOUSE",2, 1);
        try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			System.out.println("Exception capturée : " + e.getMessage());
		}
    }
}