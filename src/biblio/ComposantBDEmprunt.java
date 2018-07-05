package biblio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Composant logiciel assurant la gestion des emprunts d'exemplaires
 * de livre par les abonnés.
 */
public class ComposantBDEmprunt {

  /**
   * Retourne le nombre total d'emprunts en cours référencés dans la base.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours() throws SQLException {
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select count (*) from emprunt where dateDeRetour is NULL"; // il faut ajouter la date
    ResultSet rset = stmt.executeQuery(sql);
    int i=0;
    while (rset.next()) {
      i=i+1;
    return i;
    rset.close();
    stmt.close();
  }

  /**
   * Retourne le nombre d'emprunts en cours pour un abonné donné connu
   * par son identifiant.
   * 
   * @return le nombre d'emprunts.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static int nbEmpruntsEnCours(int idAbonne) throws SQLException {
    Statement stmt = Connexion.getConnection().createStatement();
    String sql = "select count (*) from emprunt where idAbonne='"+idAbonne+"' and dateDeRetour is NULL"; //if faut ajouter la date
    ResultSet rset = stmt.executeQuery(sql);
    int i=0;
    while (rset.next()) {
      i=i+1;
    return i;
    rset.close();
    stmt.close();
  }

  
  /**
   * Récupération de la liste complète des emprunts en cours.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours.<br/>
   * Il doit contenir 8 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours() throws SQLException {
      ArrayList<String[]> emprunts = new ArrayList<String[]>();
      Statement stmt = Connexion.getConnection().createStatement();
      String sql="select idExemplaire, E.id, titre, auteur, E.idAbonne, nom, prenom, dateEmprunt from emprunt E join abonne A on E.idAbonne=A.idAbonne  join  livre L on E.id=L.id where dateRetour is null ";
      ResultSet rset = stmt.executeQuery(sql);
      while(rset.next()){
        String[] emprunt= new String[8];
        emprunt[0]=Integer.toString(rset.getInt("idExemplaire"));
        emprunt[1]=Integer.toString(rset.getInt("id"));
        emprunt[2]=rset.getString("titre");
        emprunt[3]=rset.getString("auteur");
        emprunt[4]=Integer.toString(rset.getInt("idAbonne"));
        emprunt[5]=rset.getString("nom");
        emprunt[6]=rset.getString("prenom");
        emprunt[7]=rset.getString("dateEmprunt");
        emprunts.add(emprunt);
      }
      rset.close();
      stmt.close();
      
      return emprunts;
  }

  /**
   * Récupération de la liste des emprunts en cours pour un abonné donné.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt en cours pour l'abonné.<br/>
   * Il doit contenir 5 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : la date de l'emprunt</li>
   * </ul>
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsEnCours(int idAbonne) throws SQLException {
    ArrayList<String[]> emprunts = new ArrayList<String[]>();
    Statement stmt = Connexion.getConnection().createStatement();
    String sql="select idExemplaire, E.id, titre, auteur, dateEmprunt from emprunt E join abonne A on E.idAbonne=A.idAbonne  join  livre L on E.id=L.id where A.idAbonne='"+idAbonne+"' and dateRetour is null";
    ResultSet rset = stmt.executeQuery(sql);
    while(rset.next()){
      String[] iEmprunt= new String[5];
      iEmprunt[0]=Integer.toString(rset.getInt("idExemplaire"));
      iEmprunt[1]=Integer.toString(rset.getInt("id"));
      iEmprunt[2]=rset.getString("titre");
      iEmprunt[3]=rset.getString("auteur");
      iEmprunt[4]=rset.getString("dateEmprunt");
      emprunts.add(iEmprunt);
    }
    rset.close();
    stmt.close();
    return emprunts;
  }

  /**
   * Récupération de la liste complète des emprunts passés.
   * 
   * @return un <code>ArrayList<String[]></code>. Chaque tableau de chaînes
   * de caractères contenu correspond à un emprunt passé.<br/>
   * Il doit contenir 9 éléments (dans cet ordre) :
   * <ul>
   *   <li>0 : id de l'exemplaire</li>
   *   <li>1 : id du livre correspondant</li>
   *   <li>2 : titre du livre</li>
   *   <li>3 : son auteur</li>
   *   <li>4 : id de l'abonné</li>
   *   <li>5 : nom de l'abonné</li>
   *   <li>6 : son prénom</li>
   *   <li>7 : la date de l'emprunt</li>
   *   <li>8 : la date de retour</li>
   * </ul>
   * @return un <code>ArrayList</code> contenant autant de tableaux de String (5 chaînes de caractères) que d'emprunts dans la base.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static ArrayList<String[]> listeEmpruntsHistorique() throws SQLException {
      ArrayList<String[]> emprunts = new ArrayList<String[]>();
      Statement stmt = Connexion.getConnection().createStatement();
      String sql="select idExemplaire, E.id, titre, auteur, E.idAb, nom, prenom, dateEmprunt, dateRetour from emprunt E join abonne A on E.idAbonne=A.idAbonne  join  livre L on E.id=L.id where dateRetour is not null ";
      ResultSet rset = stmt.executeQuery(sql);
      while(rset.next()){
        String[] iEmprunt= new String[9];
        iEmprunt[0]=Integer.toString(rset.getInt("idExemplaire"));
        iEmprunt[1]=Integer.toString(rset.getInt("id"));
        iEmprunt[2]=rset.getString("titre");
        iEmprunt[3]=rset.getString("auteur");
        iEmprunt[4]=Integer.toString(rset.getInt("idAbonne"));
        iEmprunt[5]=rset.getString("nom");
        iEmprunt[6]=rset.getString("prenom");
        iEmprunt[7]=rset.getString("dateEmprunt");
        iEmprunt[8]=rset.getString("dateRetour");
        emprunts.add(iEmprunt);
      }
      rset.close();
      stmt.close();
      
      return emprunts;
  }
  }

  /**
   * Emprunter un exemplaire à partir de l'identifiant de l'abonné et de
   * l'identifiant de l'exemplaire.
   * 
   * @param idAbonne : id de l'abonné emprunteur.
   * @param idExemplaire id de l'exemplaire emprunté.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void emprunter(int idAbonne, int idExemplaire) throws SQLException {
    Statement stmt = Connexion.getConnection().createStatement();
    String queryDate="select current_date";
    String query = "select * from exemplaire where idExemplaire="+idExemplaire;
    ResultSet x=stmt.executeQuery(query);
    x.next();
    int id = x.getInt("id");
    ResultSet rset2=stmt.executeQuery(queryDate);
    rset2.next();
    String dateEmp= rset2.getString("date");
    String sql = "insert into emprunt(idAb,dateEmprunt,id,idExemplaire) values('"+idAbonne+"','"+dateEmp+"','"+id+"','"+idExemplaire+"')";
    boolean rset = stmt.execute(sql);
    x.close();
    rset2.close();
    stmt.close();
    }

  /**
   * Retourner un exemplaire à partir de son identifiant.
   * 
   * @param idExemplaire id de l'exemplaire à rendre.
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static void rendre(int idExemplaire) throws SQLException {
    Statement stmt = Connexion.getConnection().createStatement();
    String queryDate="select current_date";
    ResultSet rset=stmt.executeQuery(queryDate);
    rset.next();
    String dateRet= rset.getString("date");
    String sql = "update emprunt SET dateRetour='"+dateRet+"' where idExemplaire="+idExemplaire;
    boolean rset1 = stmt.execute(sql);
    rset.close();
    stmt.close();
  }
  
  /**
   * Détermine si un exemplaire sonné connu par son identifiant est
   * actuellement emprunté.
   * 
   * @param idExemplaire
   * @return <code>true</code> si l'exemplaire est emprunté, <code>false</code> sinon
   * @throws SQLException en cas d'erreur de connexion à la base.
   */
  public static boolean estEmprunte(int idExemplaire) throws SQLException {
    boolean estEmprunte = false;
    Statement stmt = Connexion.getConnection().createStatement();
    String query="select count(*) from emprunt where idExemplaire="+idExemplaire+" and dateRetour is null";
    ResultSet rset=stmt.executeQuery(query);
    rset.next();
    if(rset.getInt(1)!=0){estEmprunte=true;}
    return estEmprunte;
  }

  /**
   * Récupération des statistiques sur les emprunts (nombre d'emprunts et de
   * retours par jour).
   * 
   * @return un <code>HashMap<String, int[]></code>. Chaque enregistrement de la
   * structure de données est identifiée par la date (la clé) exprimée sous la forme
   * d'une chaîne de caractères. La valeur est un tableau de 2 entiers qui représentent :
   * <ul>
   *   <li>0 : le nombre d'emprunts</li>
   *   <li>1 : le nombre de retours</li>
   * </ul>
   * Exemple :
   * <pre>
   * +-------------------------+
   * | "2017-04-01" --> [3, 1] |
   * | "2017-04-02" --> [0, 1] |
   * | "2017-04-07" --> [5, 9] |
   * +-------------------------+
   * </pre>
   *   
   * @throws SQLException
   */
  public static HashMap<String, int[]> statsEmprunts() throws SQLException
  {
    {
    HashMap<String, int[]> stats = new HashMap<String, int[]>();
    Statement stmt = Connexion.getConnection().createStatement();
    String query="SELECT dateEmprunt, totalEmp, totalRet from (select dateEmprunt,count(*) as totalEmp from emprunt group by dateEmprunt) emp join (select dateRetour,count(*) as totalRet from emprunt group by dateRetour) ret on emp.dateEmprunt=ret.dateRetour";
    ResultSet rset=stmt.executeQuery(query);
    while(rset.next()){
      int[] occurences=new int[2];
      occurences[0]=rset.getInt("totalEmp");
      occurences[1]=rset.getInt("totalRet");
      stats.put(rset.getString("dateEmprunt"), occurences);
    }
    rset.close();
    stmt.close();
    return stats;
  }
}
