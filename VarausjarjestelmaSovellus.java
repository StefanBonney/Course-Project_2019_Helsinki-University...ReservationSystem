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

@SpringBootApplication
public class VarausjarjestelmaSovellus implements CommandLineRunner {
    private static int gKey1;
    
    public static void main(String[] args) {
        SpringApplication.run(VarausjarjestelmaSovellus.class);
    }

    @Autowired
    Tekstikayttoliittyma tekstikayttoliittyma;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    Alustus alustus; 
    
    @Autowired
    Tehtava1 tehtava1;     
    
    @Autowired
    Tehtava2 tehtava2;     

    @Autowired
    Tehtava3 tehtava3; 
    
    @Override
    public void run(String... args) throws Exception {
        Scanner lukija = new Scanner(System.in);
        
        
        System.out.println("OHJELMAN ALUSTUS JA TESTAUS \n\n\n\n\n\n\n");
        
        System.out.println("##############################################################################[ALUSTUS]");
        alustus.aloitaAlustus();
    
        
        System.out.println("#############################################################################[TEST_DAO]");        
        TestDao.testHuone();
        
        System.out.println("#######################################################################[TEST_TEHTÄVÄ[1]");
        System.out.println("\n Lisätään uusi huone - numero: 103, Tyyppi: Moderate, Hinta: 50 \n");
        
        Huone huone = new Huone(103, "Moderate", 50);
        Tehtava1.talennaHuone(huone);
        
        alustus.tulostaTaulujenData();
        
        System.out.println("#######################################################################[TEST_TEHTÄVÄ[2]");
        
        System.out.println("\n Listataan kaikki huoneet \n");
        Tehtava2.listaaHuoneet();
        
        System.out.println("#######################################################################[TEST_TEHTÄVÄ[3]");
        
        System.out.println("---------------------------[ilman tyyppi/hinta]");
        System.out.println("\nHaetaan huoneet aikavälillä 2010-01-01 - 2010-01-28\n");
        LocalDate alku = LocalDate.of(2010,01,01);
        LocalDate loppu = LocalDate.of(2010,02,28);
        Tehtava3.haeHuoneet(alku , loppu);
                
        System.out.println("----------------------------[kaikilla parametereillä(tyyppi/hinta mukana)]");
        
        System.out.println("\nHaetaan huoneet aikavälillä 2010-01-01 - 2010-01-28, Tyyppi: Sviitti, Maksimihinta: 180\n");
        LocalDate alku2 = LocalDate.of(2010,01,01);
        LocalDate loppu2 = LocalDate.of(2010,01,28);

        Tehtava3.haeHuoneet(alku , loppu, "Sviitti", 180);
  

        System.out.println("#######################################################################[TEST_TEHTÄVÄ[4]");        
        //** Käyttöliittymän toiminnallisuus ei vielä toteutettu
        
        
        System.out.println("---------------------------[ilman tyyppi/hinta]");
        System.out.println("\nLuodaan uusi varaus - "
                + "\nalku: 2010-01-01"
                + "\nloppu: 2010-02-28"
                
                + "Varaaja on jo järjestelmässä: id_1,"
                + "Ei lisävarusateita"
                + "  \n");
        

        //Käyttöliittymän kautta tehty asiakas olio joka annetaan eteenpäin varausjärjestelmälle
        //Käyttöliittymän kautta tehty lisävaruste-oliot
        AsiakasDao asiakasDao = new AsiakasDao();
        Asiakas asiakas = asiakasDao.read(1);
        
        LocalDate alku3 = LocalDate.of(2010,01,01);
        LocalDate loppu3 = LocalDate.of(2010,02,28);
        
        Lisavaruste l1 = new Lisavaruste (100, "saippua", 20);
        Lisavaruste l2 = new Lisavaruste (100, "hammasharja", 10);
        
        ArrayList<Lisavaruste> lis = new ArrayList();
        ArrayList oLis = Tehtava4.selvitaLisavarusteId(lis);
        
        Tehtava4.teeVaraus(alku3, loppu3, asiakas, oLis);
        
        System.out.println("----------------------------[kaikilla parametereillä(tyyppi/hinta mukana)]");        
        
        
        System.out.println("#######################################################################[TEST_TEHTÄVÄ[5]");         
        
        listaaVaraukset();
      
        
        System.out.println("########################################################################################");
               
        
        alustus.tulostaTaulujenData();        
        
        System.out.println("\n\n\n OHJELMAN KÄYNNISTYS \n\n\n\n\n\n\n");
        
        tekstikayttoliittyma.kaynnista(lukija);
        
    }
       

    
    //#########################################################################[TEHTÄVÄ][1][HOTELLIHUONEEN TALLENNUS] 
    
    //#######################################################################[TEHTÄVÄ][2][HOTELLIHUONEIDEN LISTAUS] 
    

    
      //#####################################################################[TEHTÄVÄ][3][HOTELLIHUONEIDEN LISTAUS]       
      
      //#####################################################################[TEHTÄVÄ][4][VARAUKSEN TEKEMINEN]        
 
      
      
      

      
      //#####################################################################[TEHTÄVÄ][5][VARAUSTEN LISTAAMINEN]  
        
        
        
       public static void listaaVaraukset(){
           
           
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
          
          
            //java.sql.Date sAlku = java.sql.Date.valueOf(alku);

            //java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely = 
            "SELECT Asiakas.etuNimi, Asiakas.sukuNimi, Asiakas.sahkoposti, Varaus.alku, Varaus.loppu, COUNT(Lisavaruste.id) as lvm , COUNT(HUONE.NUMERO) hnm , Huone.numero, SUM(Huone.paivahinta) as hhint, SUM(Lisavaruste.hinta) as lhint\n" +
            "FROM Asiakas\n" +
            "JOIN Varaus ON Varaus.asiakas_id = Asiakas.id\n" +
            "JOIN HuoneVaraus ON HuoneVaraus.varaus_id = Varaus.id\n" +
            "JOIN Huone ON HuoneVaraus.huone_numero = huone.numero\n" +
            "JOIN LisavarusteVaraus ON LisavarusteVaraus.varaus_id = varaus.id\n" +
            "JOIN Lisavaruste ON LisavarusteVaraus.lisavaruste_id = Lisavaruste.id\n" +
            "group by Asiakas.etuNimi;";
            
           PreparedStatement statement = conn.prepareStatement(kysely);           
           ResultSet resultSet = statement.executeQuery();
           
           while(resultSet.next()){
               String etuNimi = resultSet.getString("etuNimi");
               String sukuNimi = resultSet.getString("sukuNimi");
               String sahkoposti = resultSet.getString("sahkoposti");
               
               java.sql.Date alku = resultSet.getDate("alku");
               LocalDate lAlku = alku.toLocalDate();
               
               
               java.sql.Date loppu = resultSet.getDate("alku");
               LocalDate lLoppu = alku.toLocalDate();
               
               
               int lisavarusteLkm = resultSet.getInt("lvm");
               int huoneLkm = resultSet.getInt("hnm");         
                        
               int huoneidenHinta = resultSet.getInt("hhint");
               
               int lisavarusteidenHinta = resultSet.getInt("lhint");  
               
               int yhteishinta = huoneidenHinta + lisavarusteidenHinta;
               
               System.out.println("|Etunimi: " + etuNimi + sukuNimi + " |Sähköposti: " + sahkoposti + " |Alku: " + lAlku +" |Loppu: " + lLoppu + " + LisavarusteLkm " + lisavarusteLkm + "| HuomeLkm: " + huoneLkm);
               System.out.println("Yhteishinta: " + yhteishinta);               
               
               
           }   
          
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }                
           
           
           
           
           
           
           
           
           
       } 
        
        
        
      
      
      
      
      

    
    
}
