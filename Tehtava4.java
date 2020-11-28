
package varausjarjestelma;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import java.util.*;
import java.io.*;
import java.text.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Tehtava4 {
   
    @Autowired
    JdbcTemplate jdbcTemplate;   
    
    //================================================================================================================[LISÄVARUSTEEN ID:N SELVITTÄMINEN]
    public static ArrayList<Lisavaruste> selvitaLisavarusteId(List<Lisavaruste> objLisavarusteet){
        
        ArrayList ObjOikeaIdLisavaruste = new ArrayList();        
        
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
                     
            String kysely = 
            "select count * from Lisavaruste;";
            
           PreparedStatement statement = conn.prepareStatement(kysely);           
           ResultSet resultSet = statement.executeQuery();
           

            while(resultSet.next()){
               int id = resultSet.getInt("id");
               String nimi = resultSet.getString("nimi");
               for(Lisavaruste l : objLisavarusteet){
                   if(l.getNimi().equals(nimi)){l.setId(id);}
                   ObjOikeaIdLisavaruste.add(l);
               }
           }   
           
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return ObjOikeaIdLisavaruste;        
    }//======================================================================================================================================================
    
    //=======================================================================================================================[VAPAIDEN HUONEIDEN TARKISTUS]   
    public static int tarkistaOnkoHuoneita(LocalDate alku, LocalDate loppu){
     
    int huoneita = 0;    
    try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
          
          
            java.sql.Date sAlku = java.sql.Date.valueOf(alku);

            java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely = 
            "select count(*) AS huoneita from Huone where not exists (select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku > ? and Varaus.loppu < ?);";
            
           PreparedStatement statement = conn.prepareStatement(kysely);
           statement.setDate(1, sLoppu);
           statement.setDate(2, sAlku);
 
           
           ResultSet resultSet = statement.executeQuery();
           

               while(resultSet.next()){
               huoneita = resultSet.getInt("huoneita");          
               
           }   
           
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return huoneita;
     }//========================================================================================================================================
      
      
    
    //=======================================================================================================================[VARAUS VAIN AJALLA}
    public static void teeVaraus(LocalDate alku, LocalDate loppu, Asiakas asiakas, List<Lisavaruste> lisavarusteet){      
          
        //----------------------------------------------------------------------[A] VAPAIDEN HUONEIDEN MÄÄRÄN LISTAUS HAKUEHTOIHIN PERUSTUEN
         try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
          
            java.sql.Date sAlku = java.sql.Date.valueOf(alku);
            java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely = 
            "select count(*) AS huoneita from Huone where not exists (select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku > ? and Varaus.loppu < ?);";
            
           PreparedStatement statement = conn.prepareStatement(kysely);
           statement.setDate(1, sLoppu);
           statement.setDate(2, sAlku);
 
           
           ResultSet resultSet = statement.executeQuery();
           
           int huoneita = 0;
           

               while(resultSet.next()){
               huoneita = resultSet.getInt("huoneita");          
               
               System.out.println("|Vapaita Huoneita: " + huoneita);
           }   
          
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }          
          
         
        //----------------------------------------------------------------------[B] KÄYTTÄJÄN LISÄÄMINEN / PäIVITTÄMINEN
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {                  
        AsiakasDao asiakasDao = new AsiakasDao();
         
        if(asiakasDao.read(asiakas.getId()) == null){asiakasDao.create(asiakas);}
        else{asiakasDao.update(asiakas);}
         
         
        //----------------------------------------------------------------------[C] VARAUKSEN LISÄÄMINEN
        Varaus varaus = new Varaus(alku, loppu, asiakas);
        VarausDao varausDao = new VarausDao();
        varausDao.create(varaus);
        int gKey = varausDao.getGKey();
        //System.out.println("gkey" + gKey);        
        
        
        //----------------------------------------------------------------------[D] HUONEEN LIITTÄMINEN VARAUKSEEN 
       
        //[1] kalleimman huoneen selvittäminen
 
        java.sql.Date sAlku = java.sql.Date.valueOf(alku);
        java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
        String kysely = 
        "select max(Huone.paivahinta), huone.numero from Huone where not exists (select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku > ? and Varaus.loppu < ?) group by Huone.numero order by Huone.paivahinta desc;";
        PreparedStatement statement = conn.prepareStatement(kysely);
        statement.setDate(1, sLoppu);
        statement.setDate(2, sAlku);
 
        ResultSet resultSet = statement.executeQuery();
         
        int numero = 0;
        ArrayList<Integer> numerot = new ArrayList();
        while(resultSet.next()){
            numero = resultSet.getInt("numero"); 
            numerot.add(numero);
        }            
           
        numero = numerot.get(0);
        System.out.println("numero: " + numero);
        System.out.println(gKey);
        
        //[2] Huoneen ja varauksen ykilöivien avainten liittäminen HuoneVaraus tauluun  
           
        String kysely2 = 
        "INSERT INTO HuoneVaraus (huone_numero, varaus_id) VALUES(?, ?);";
        PreparedStatement statement2 = conn.prepareStatement(kysely2);
        System.out.println("Lisätty huoneen numero: " + numero);
        System.out.println("Lisätty varauksen id: " + gKey);
        statement2.setInt(1, numero);
        statement2.setInt(2, gKey);
 
        statement2.executeUpdate();
           
        
        //----------------------------------------------------------------------[E] LISÄVARUSTEIDEN LIITTÄMINEN VARAUKSEEN
        
        for(Lisavaruste lisavarust : lisavarusteet){
            
            String kysely3 = 
            "INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES(?, ?);";
            PreparedStatement statement3 = conn.prepareStatement(kysely3);
            System.out.println("Lisätty lisavarusteen id: " + lisavarust.getId());
            System.out.println("Lisätty varauksen id: " + gKey);
            statement3.setInt(1, lisavarust.getId());
            statement3.setInt(2, gKey);
            statement3.executeUpdate();
        }
        
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }                               
      }//===============================================================================================================
      
    
    

      //==============================================================================================================[VARAUS AJALA, HUONETYYPILLÄ JA MAKSIMIHINNALLA]
      public static void teeVaraus(LocalDate alku, LocalDate loppu, String tyyppi, int korkeinHinta, Asiakas asiakas, List<Lisavaruste> lisavarusteet){
          
          
         
         //--------------------------------------------------------------[A] VAPAIDEN HUONEIDEN MÄÄRÄN LISTAUS HAKUEHTOIHIN PERUSTUEN
          
         try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
          
            java.sql.Date sAlku = java.sql.Date.valueOf(alku);
            java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely = 
            "select count(*) AS huoneita from Huone where not exists (select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku > ? and Varaus.loppu < ?);";
            
           PreparedStatement statement = conn.prepareStatement(kysely);
           statement.setDate(1, sLoppu);
           statement.setDate(2, sAlku);
 
           
           ResultSet resultSet = statement.executeQuery();
           
           int huoneita = 0;
           
            while(resultSet.next()){
               huoneita = resultSet.getInt("huoneita");          
               
               System.out.println("|Vapaita Huoneita: " + huoneita);
            }   
          
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }          
                
        //-------------------------------------------------------------[B] KÄYTTÄJÄN LISÄÄMINEN / PäIVITTÄMINEN
         
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {                  
        AsiakasDao asiakasDao = new AsiakasDao();
         
        if(asiakasDao.read(asiakas.getId()) == null){asiakasDao.create(asiakas);}
        else{asiakasDao.create(asiakas);}
         

        //-------------------------------------------------------------[C] VARAUKSEN LISÄÄMINEN
        
        Varaus varaus = new Varaus(alku, loppu,asiakas);
        VarausDao varausDao = new VarausDao();
        varausDao.create(varaus);
        int gKey = varausDao.getGKey();
        System.out.println("gkey" + gKey);        
          
        //--------------------------------------------------------------[D] HUONEEN LIITTÄMINEN VARAUKSEEN 
        
          // kalleimman huoneen selvittäminen
                
            java.sql.Date sAlku = java.sql.Date.valueOf(alku);

            java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely =
            "select max(Huone.paivahinta), huone.numero from Huone where not exists (select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku > ? and Varaus.loppu < ?) group by Huone.numero order by Huone.paivahinta desc;";
           PreparedStatement statement = conn.prepareStatement(kysely);
           statement.setDate(1, sLoppu);
           statement.setDate(2, sAlku);
 
           ResultSet resultSet = statement.executeQuery();
         
           int numero = 0;
           ArrayList<Integer> numerot = new ArrayList();
           while(resultSet.next()){
               numero = resultSet.getInt("numero"); 
               numerot.add(numero);
           }            
           
           numero = numerot.get(0);
           System.out.println("numero: " + numero);
           System.out.println(gKey);
        
         // Huoneen ja varauksen ykilöivien avainten liittäminen HuoneVaraus tauluun  
           
           String kysely2 = 
           "INSERT INTO HuoneVaraus (huone_numero, varaus_id) VALUES(?, ?);";
            
           PreparedStatement statement2 = conn.prepareStatement(kysely2);
           System.out.println("numero: " + numero);
           System.out.println("gKey: " + gKey);
           statement2.setInt(1, numero);
           statement2.setInt(2, gKey);
 
           statement2.executeUpdate();
           
           
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }                
         
                 
     }//==========================================================================================================================  

    
    
    
}



 
