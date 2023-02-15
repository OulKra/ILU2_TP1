package villagegaulois;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	Marche marche;
	public Village(String nom, int nbVillageoisMaximum, int nbrEtal) {
		this.nom = nom;
		marche = new Marche(nbrEtal);
		villageois = new Gaulois[nbVillageoisMaximum];
	}
	
	public String installerVendeur(Gaulois gaulois, String produit, int quantite)
	{
		int indice = marche.trouverEtalLibre();
		String phrase;
		if(indice == -1)
		{
			phrase = "Il n'y a plus d'étal libre";
		}
		else
		{
			marche.utiliserEtal(indice, gaulois, produit, quantite);
			phrase = "L'étal numéro " + indice + " est maintenant occupé par " + gaulois.getNom()
					+ " et contient " + quantite + " " + produit;
		}
		
		return phrase;
	}
	
	public Etal[] rechercherVendeursProduit(String produit)
	{
		return marche.trouverEtals(produit);
	}
	
	public Etal rechercherEtal(Gaulois gaulois)
	{
		return marche.trouverVendeur(gaulois);
	}
	
	public String partirVendeur(Gaulois gaulois)
	{
		Etal etal =  marche.trouverVendeur(gaulois);
		return etal.libererEtal();
	}
	
	public String afficherMarche()
	{
		return marche.afficherMarche();
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
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	class Marche {
		Etal[] etals;

		Marche(int nbrEtal) {
			etals = new Etal[nbrEtal];
			for (int i = 0; i < nbrEtal; i++) {
				Etal etal = new Etal();
				etals[i] = etal;
			}
			System.out.println("Je crée un marché contenant " + nbrEtal + " étal.s");

		}

		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbrProduit) {
			Etal etal = new Etal();
			etal.occuperEtal(vendeur, produit, nbrProduit);
			etals[indiceEtal] = etal;
		}

		int trouverEtalLibre() {
			int indice = -1;
			for (int i = 0; i < etals.length && indice == -1; i++) {
				if (!etals[i].isEtalOccupe()) {
					indice = i;
				}
			}

			return indice;
		}

		Etal[] trouverEtals(String produit) {
			
			int nbrEtalOccupee = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					nbrEtalOccupee++;
				}
			}
			Etal[] etals_p = new Etal[nbrEtalOccupee];
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etals_p[i] = etals[i];
				}
			}
			return etals_p;
		}

		Etal trouverVendeur(Gaulois gaulois) {
			Etal etal = null;
			for (int i = 0; i < etals.length && etal == null; i++) {
				if (etals[i].getVendeur() == gaulois) {
					etal = etals[i];
				}
			}
			return etal;
		}

		String afficherMarche() {
			String description = "";
			int indice = 0;
			while (etals[indice].isEtalOccupe()) {
				description += etals[indice].afficherEtal();
				indice++;
			}

			int taille = etals.length;
			if (indice != taille) {
				description += "Il reste " + (taille - indice) + " étals non utilisés dans le marché.\n";
			}
			return description;
		}
	}

	/*public static void main(String[] args) {
		Druide druide = new Druide("Panoramix", 2, 5, 10);
		Gaulois obelix = new Gaulois("Obelix", 25);
		Gaulois asterix = new Gaulois("Asterix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);

		Village village = new Village("le village des gaulois", 10);
		Marche marche = village.new Marche(5);
		marche.utiliserEtal(0, druide, "tomate", 10);
		marche.utiliserEtal(1, obelix, "poisson", 15);
		marche.afficherMarche();
		
		Etal[] etal_produit = marche.trouverEtals("tomate");
	}*/
}