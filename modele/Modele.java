package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import controleur.Produit;


public class Modele 
{
	public static LinkedList<Produit> selectAll()
	{
		LinkedList<Produit> uneListe = new LinkedList<Produit>();
		BDD uneBDD = new BDD("localhost", "stock", "root", "");
		uneBDD.chargerPilote();
		uneBDD.seConnecter();
		String requete = "select * from produit;";
		try 
		{
			Statement unStat = uneBDD.getMaConnexion().createStatement();
			ResultSet unRes = unStat.executeQuery(requete);
			while (unRes.next())
			{
				String reference = unRes.getString("reference");
				String designation = unRes.getString("designation");
				String categorie = unRes.getString("categorie");
				float prix = unRes.getFloat("prix");
				int qte = unRes.getInt("qte");
				Produit unProd = new Produit(reference, designation, prix, qte, categorie);
				uneListe.add(unProd);
			}
			unStat.close();
			unRes.close();
		} 
		catch (SQLException exp) 
		{
			System.out.println("Erreur d'exécution : " + requete);
		}
		uneBDD.seDeconnecter();
		return uneListe;
	}
	
	public static void insertProduit(Produit unProd)
	//Permet d'insérer un produit dans la table Produit
	{
		String requete="insert into produit(reference, designation, prix, qte, categorie)"
				+ "values('" + unProd.getReference() + "','" + unProd.getDesignation()
				+ "'," + unProd.getPrix() + "," + unProd.getQte() +",'" + unProd.getCategorie()
				+ "');";
		BDD uneBDD = new BDD("localhost", "stock", "root", "");
		uneBDD.chargerPilote();
		uneBDD.seConnecter();
		try
		{
			Statement unStat = uneBDD.getMaConnexion().createStatement();
			unStat.execute(requete);
			unStat.close();
		}
		catch (SQLException exp)
		{
			System.out.println("Erreur d'exécution : " + requete);
		}
		uneBDD.seDeconnecter();
	}
	
	public static LinkedList<Produit> selectWhere(String cle)
	{
		LinkedList<Produit> mesProduits = new LinkedList<Produit>();
		BDD uneBDD = new BDD("localhost", "stock", "root", "");
		uneBDD.chargerPilote();
		uneBDD.seConnecter();
		String requete = "select * from produit where reference like '%" + cle
				+ "%' OR designation like '%" + cle + "%' OR categorie like '%" + cle + "%';";
		try 
		{
			Statement unStat = uneBDD.getMaConnexion().createStatement();
			ResultSet unRes = unStat.executeQuery(requete);
			while (unRes.next())
			{
				String reference = unRes.getString("reference");
				String designation = unRes.getString("designation");
				String categorie = unRes.getString("categorie");
				float prix = unRes.getFloat("prix");
				int qte = unRes.getInt("qte");
				Produit unProd = new Produit(reference, designation, prix, qte, categorie);
				mesProduits.add(unProd);
			}
			unStat.close();
			unRes.close();
		} 
		catch (SQLException exp)
		{
			System.out.println("Erreur d'exécution : " + requete);
		}
		uneBDD.seDeconnecter();
		
		return mesProduits;
	}
	
	public static int deleteProduits(String cle)
	{
		int nb=0;
		if(!cle.equals(""))
		{
			BDD uneBDD = new BDD("localhost", "stock", "root", "");
			uneBDD.chargerPilote();
			uneBDD.seConnecter();

			String requete1 = "select count(*) from produit where reference like '%" + cle
					+ "%' OR designation like '%" + cle + "%' OR categorie like '%" + cle + "%';";
			String requete2 = "delete from produit where reference like '%" + cle
					+ "%' OR designation like '%" + cle + "%' OR categorie like '%" + cle + "%';";

			try 
			{
				Statement unStat = uneBDD.getMaConnexion().createStatement();
				ResultSet unRes = unStat.executeQuery(requete1);
				unRes.next();
				nb = unRes.getInt(1);
				if(nb>0)
				{
					unStat.execute(requete2);
				}
				unStat.close();
				unRes.close();
			}
			
			catch (SQLException exp)
			{
				System.out.println("Erreur d'exécution : " + requete1 + " ou " + requete2);
			}
			
			uneBDD.seDeconnecter();
		}
		return nb;
	}
	
	public static void updateProduit(Produit unProd)
	{
		String requete = "update Produit set designation='" + unProd.getDesignation()
				+ "', prix=" + unProd.getPrix() + ", qte=" + unProd.getQte()
				+ ", categorie='" + unProd.getCategorie() + "' where reference='"
				+ unProd.getReference() + "';";
		
		BDD uneBDD = new BDD("localhost", "stock", "root", "");
		uneBDD.chargerPilote();
		uneBDD.seConnecter();
		try
		{
			Statement unStat = uneBDD.getMaConnexion().createStatement();
			unStat.execute(requete);
			unStat.close();
		}
		catch (SQLException exp)
		{
			System.out.println("Erreur d'exécution : " + requete);
		}
		uneBDD.seDeconnecter();
		
	}
	
	//Modele :
	//	selectWhere(String cle)
	//		cle=ref or cle=design or cle=cat
	//		return LinkedList
	//Vue :
	//	public String saisirCle()
	//Contrôler Produit:
	//	case 3 :
	//		afficherLesResultats()
}