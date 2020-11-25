
package varausjarjestelma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Alustus {
 
    @Autowired
    Tekstikayttoliittyma tekstikayttoliittyma;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    
        public void aloitaAlustus(){
        alustaTietokanta();
        
        lisaaDataAsiakas();
        lisaaDataLisavaruste();
        lisaaDataHuone();
        lisaaDataVaraus();
        lisaaDataHuoneVaraus();
        lisaaDataLisavarusteVaraus();
        
        tulostaTaulujenData();
        
        Lisavaruste.lisavarusteLista.put("saippua", 20);
        Lisavaruste.lisavarusteLista.put("hammasharja", 10);        
        
        }
    
    
        //#################################################################################################[ALUSTUS]
        private static void alustaTietokanta() {
        // mikäli poistat vahingossa tietokannan voit ajaa tämän metodin jolloin 
        // tietokantataulu luodaan uudestaan

        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
            
            //=============================================================================[taulujen pudottaminen]

            
            conn.prepareStatement("DROP TABLE LisavarusteVaraus IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE HuoneVaraus IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE Varaus IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE Lisavaruste IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE Huone IF EXISTS;").executeUpdate();
            conn.prepareStatement("DROP TABLE Asiakas IF EXISTS;").executeUpdate();
            
            //===================================================================================[taulujen luominen]

            
            conn.prepareStatement("CREATE TABLE Asiakas("
                                  + "id INTEGER AUTO_INCREMENT PRIMARY KEY, "
                                  + "etuNimi VARCHAR(200), "
                                  + "sukuNimi VARCHAR(200), "
                                  + "puhelinnumero VARCHAR(100),"
                                  + "sahkoposti VARCHAR(100));").executeUpdate();
            
            
            conn.prepareStatement("CREATE TABLE Lisavaruste("
                                  + "id INTEGER AUTO_INCREMENT PRIMARY KEY, "
                                  + "nimi VARCHAR(200), "
                                  + "hinta INTEGER);").executeUpdate();  
         
            conn.prepareStatement("CREATE TABLE Huone("
                                  + "numero INTEGER PRIMARY KEY, "
                                  + "tyyppi VARCHAR(200), "
                                  + "paivahinta INTEGER);").executeUpdate();       
            
            
            conn.prepareStatement("CREATE TABLE Varaus("
                                  + "id INTEGER AUTO_INCREMENT PRIMARY KEY, "
                                  + "alku DATE, "
                                  + "loppu DATE, "
                                  + "asiakas_id INTEGER, "                    
                                  + "FOREIGN KEY (asiakas_id) REFERENCES Asiakas(id));").executeUpdate();   
            
            conn.prepareStatement("CREATE TABLE HuoneVaraus("
                                  + "huone_numero INTEGER, "
                                  + "varaus_id INTEGER, "
                                  + "FOREIGN KEY (huone_numero) REFERENCES Huone(numero), "                    
                                  + "FOREIGN KEY (varaus_id) REFERENCES Varaus(id));").executeUpdate();            
            
            conn.prepareStatement("CREATE TABLE LisavarusteVaraus("
                                  + "lisavaruste_id INTEGER, "
                                  + "varaus_id INTEGER, "
                                  + "FOREIGN KEY (lisavaruste_id) REFERENCES Lisavaruste(id), "                    
                                  + "FOREIGN KEY (varaus_id) REFERENCES Varaus(id));").executeUpdate();              
            
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      
      
      
      
      
      
      
      
      
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------  
      
      
    private static void lisaaDataAsiakas(){//-------------------------------------------------------[asiakas data][1]
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
           
           PreparedStatement statement = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement.setString(1, "Jari");
           statement.setString(2, "B");
           statement.setString(3, "040-5667");
           statement.setString(4, "jari.b@gmail.com");
           
           PreparedStatement statement2 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement2.setString(1, "Matti");
           statement2.setString(2, "C");
           statement2.setString(3, "040-8878");
           statement2.setString(4, "matti.c@gmail.com");      
           
           PreparedStatement statement3 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement3.setString(1, "Taneli");
           statement3.setString(2, "E");
           statement3.setString(3, "050-9767");
           statement3.setString(4, "taneli.e@gmail.com");            
           
           PreparedStatement statement4 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement4.setString(1, "Pekka");
           statement4.setString(2, "H");
           statement4.setString(3, "040-7890");
           statement4.setString(4, "pekka.h@gmail.com");          
           
           
           PreparedStatement statement5 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement5.setString(1, "Risto");
           statement5.setString(2, "M");
           statement5.setString(3, "040-3343");
           statement5.setString(4, "risto.m@gmail.com");    
           
           PreparedStatement statement6 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement6.setString(1, "Ari");
           statement6.setString(2, "T");
           statement6.setString(3, "040-1234");
           statement6.setString(4, "ari.t@gmail.com");            
           
           
           PreparedStatement statement7 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement7.setString(1, "Milla");
           statement7.setString(2, "B");
           statement7.setString(3, "040-2365");
           statement7.setString(4, "milla.b@gmail.com");           
           
           PreparedStatement statement8 = conn.prepareStatement("INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)");
           statement8.setString(1, "Nina");
           statement8.setString(2, "S");
           statement8.setString(3, "040-1879");
           statement8.setString(4, "nina.s@gmail.com'");              
           
           
           
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();
            statement5.executeUpdate();
            statement6.executeUpdate();
            statement7.executeUpdate(); 
            statement8.executeUpdate();            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    
    private static void lisaaDataLisavaruste(){//-------------------------------------------------------[lisavaruste data][2]
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
           
           PreparedStatement statement = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement.setString(1, "Pyyhe");
           statement.setInt(2, 10);
           
           PreparedStatement statement2 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement2.setString(1, "Hiustenkuivaaja");
           statement2.setInt(2, 30);           
           
           PreparedStatement statement3 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement3.setString(1, "Shampoo");
           statement3.setInt(2, 10);              
           
           PreparedStatement statement4 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement4.setString(1, "Hammasharja");
           statement4.setInt(2, 30);            
           
           PreparedStatement statement5 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement5.setString(1, "wifi");
           statement5.setInt(2, 10);         
           
           
           PreparedStatement statement6 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement6.setString(1, "Hieronta");
           statement6.setInt(2, 70);           
           
           
           PreparedStatement statement7 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement7.setString(1, "Aamiainen");
           statement7.setInt(2, 20);    
           
           PreparedStatement statement8 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement8.setString(1, "Lounas");
           statement8.setInt(2, 20);              
           
           PreparedStatement statement9 = conn.prepareStatement("INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)");
           statement9.setString(1, "Illallinen");
           statement9.setInt(2, 20);           
           
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();
            statement5.executeUpdate();
            statement6.executeUpdate();
            statement7.executeUpdate();
            statement8.executeUpdate();
            statement9.executeUpdate();
          

            
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    
    
    
    
       private static void lisaaDataHuone(){//-------------------------------------------------------[huone data][3]
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
           
           PreparedStatement statement = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement.setInt(1, 101);
           statement.setString(2, "Moderate");
           statement.setInt(3, 50);  
           
           PreparedStatement statement2 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement2.setInt(1, 102);
           statement2.setString(2, "Moderate");
           statement2.setInt(3, 50);         
           
           PreparedStatement statement3 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement3.setInt(1, 201);
           statement3.setString(2, "Standard");
           statement3.setInt(3, 80);             
           
           PreparedStatement statement4 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement4.setInt(1, 202);
           statement4.setString(2, "Standard");
           statement4.setInt(3, 80);              
           
           PreparedStatement statement5 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement5.setInt(1, 301);
           statement5.setString(2, "Superior");
           statement5.setInt(3, 100);              
           
           PreparedStatement statement6 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement6.setInt(1, 302);
           statement6.setString(2, "Superior");
           statement6.setInt(3, 100); 

           
           PreparedStatement statement7 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement7.setInt(1, 401);
           statement7.setString(2, "Deluxe");
           statement7.setInt(3, 120); 

           PreparedStatement statement8 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement8.setInt(1, 402);
           statement8.setString(2, "Deluxe");
           statement8.setInt(3, 120);            
           
           PreparedStatement statement9 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement9.setInt(1, 501);
           statement9.setString(2, "Sviitti");
           statement9.setInt(3, 140);           
           
           PreparedStatement statement10 = conn.prepareStatement("INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
           statement10.setInt(1, 502);
           statement10.setString(2, "Sviitti");
           statement10.setInt(3, 140);            
           
           
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();
            statement5.executeUpdate();
            statement6.executeUpdate();
            statement7.executeUpdate();
            statement8.executeUpdate();
            statement9.executeUpdate();
            statement10.executeUpdate();
          
            
               
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }      
       
       
       
       
       
       
       
       
       
       private static void lisaaDataVaraus(){//-------------------------------------------------------[varaus data][4]
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
           /*
           String muunnettava = "2010-01-01";
           Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(muunnettava);
           java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
           */
           
           /*
            String muunnettava = "2010-01-01";
            LocalDate lDate = LocalDate.of(2010,01,01);
                       java.sql.Date sqlDate = new java.sql.Date(Long.parseInteger(lDate));
           */
           /*
            Timestamp ts = Timestamp.valueOf(ldt);
            
            Localdate localdate = new LocalDate.of(2010, 01,01);
            */
            
            //Timestamp timestamp = Timestamp.valueOf(lDate);
                        /*
            Date date = Date.valueOf(lDate);
            
            Java.Date date = Date.valueOf(); 
            */
            
                        
            //ensimmäinen            
            LocalDate lDate1 = LocalDate.of(2010,01,01);
            java.sql.Date sDate1 = java.sql.Date.valueOf(lDate1);
            LocalDate lDate2 = LocalDate.of(2010,01,10);
            java.sql.Date sDate2 = java.sql.Date.valueOf(lDate2);      
            
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Varaus (alku, loppu, asiakas_id) VALUES (?, ?, ?)");
            statement.setDate(1, sDate1);
            statement.setDate(2, sDate2);
            statement.setInt(3, 1);  
           
            //toinen
            LocalDate lDate3 = LocalDate.of(2010,01,20);
            java.sql.Date sDate3 = java.sql.Date.valueOf(lDate3);
            LocalDate lDate4 = LocalDate.of(2010,01,22);
            java.sql.Date sDate4 = java.sql.Date.valueOf(lDate4);      
            
           PreparedStatement statement2 = conn.prepareStatement("INSERT INTO Varaus (alku, loppu, asiakas_id) VALUES (?, ?, ?)");
           statement2.setDate(1, sDate3);
           statement2.setDate(2, sDate4);
           statement2.setInt(3, 2);        
           
           //kolmas
           LocalDate lDate5 = LocalDate.of(2010,02,01);
           java.sql.Date sDate5 = java.sql.Date.valueOf(lDate5);
           LocalDate lDate6 = LocalDate.of(2010,02,10);
           java.sql.Date sDate6 = java.sql.Date.valueOf(lDate6);      
            
           PreparedStatement statement3 = conn.prepareStatement("INSERT INTO Varaus (alku, loppu, asiakas_id) VALUES (?, ?, ?)");
           statement3.setDate(1, sDate5);
           statement3.setDate(2, sDate6);
           statement3.setInt(3, 3);       
           
           //neljäs
           LocalDate lDate7 = LocalDate.of(2010,02,20);
           java.sql.Date sDate7 = java.sql.Date.valueOf(lDate7);
           LocalDate lDate8 = LocalDate.of(2010,02,22);
           java.sql.Date sDate8 = java.sql.Date.valueOf(lDate8);      
            
           PreparedStatement statement4 = conn.prepareStatement("INSERT INTO Varaus (alku, loppu, asiakas_id) VALUES (?, ?, ?)");
           statement4.setDate(1, sDate7);
           statement4.setDate(2, sDate8);
           statement4.setInt(3, 4);       
                      
           

            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();            

            
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }           
       
       
       
       
       
       
       
       
       
       private static void lisaaDataHuoneVaraus(){//-------------------------------------------------------[HuoneVaraus data][5]
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
           
           PreparedStatement statement = conn.prepareStatement("INSERT INTO HuoneVaraus (huone_numero, varaus_id) VALUES (?, ?)");
           statement.setInt(1, 101);
           statement.setInt(2, 1);
           
           PreparedStatement statement2 = conn.prepareStatement("INSERT INTO HuoneVaraus (huone_numero, varaus_id) VALUES (?, ?)");
           statement2.setInt(1, 102);
           statement2.setInt(2, 2);      
           
           
           PreparedStatement statement3 = conn.prepareStatement("INSERT INTO HuoneVaraus (huone_numero, varaus_id) VALUES (?, ?)");
           statement3.setInt(1, 201);
           statement3.setInt(2, 3);              
           
           PreparedStatement statement4 = conn.prepareStatement("INSERT INTO HuoneVaraus (huone_numero, varaus_id) VALUES (?, ?)");
           statement4.setInt(1, 202);
           statement4.setInt(2, 4);              
           
           
           
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }         
       
       
       
       
       
       private static void lisaaDataLisavarusteVaraus(){//-------------------------------------------------------[LisavarusteVaraus data][6]
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
           
           PreparedStatement statement = conn.prepareStatement("INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES (?, ?)");
           statement.setInt(1, 1);
           statement.setInt(2, 1);
           PreparedStatement statement2 = conn.prepareStatement("INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES (?, ?)");
           statement2.setInt(1, 2);
           statement2.setInt(2, 1);  
           
           
           PreparedStatement statement3 = conn.prepareStatement("INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES (?, ?)");
           statement3.setInt(1, 5);
           statement3.setInt(2, 3);            
           PreparedStatement statement4 = conn.prepareStatement("INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES (?, ?)");
           statement4.setInt(1, 6);
           statement4.setInt(2, 3);            
           
           PreparedStatement statement5 = conn.prepareStatement("INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES (?, ?)");
           statement5.setInt(1, 1);
           statement5.setInt(2, 4);            
           PreparedStatement statement6 = conn.prepareStatement("INSERT INTO LisavarusteVaraus (lisavaruste_id, varaus_id) VALUES (?, ?)");
           statement6.setInt(1, 7);
           statement6.setInt(2, 4);            
           
           
            statement.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();            
            statement5.executeUpdate();
            statement6.executeUpdate();           
            
            
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }           
       
       //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
       
       
       
       
       
    public static void tulostaTaulujenData(){
       try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
                      
           System.out.println("================================================[ASIAKKAIDEN TIEDOT]");
           
           PreparedStatement statement = conn.prepareStatement("SELECT * FROM Asiakas;");
           ResultSet resultSet = statement.executeQuery();
           

           
           while(resultSet.next()){
               int id = resultSet.getInt("id");
               String etuNimi = resultSet.getString("etuNimi");
               String sukuNimi = resultSet.getString("sukuNimi");       
               String puhelinnumero = resultSet.getString("puhelinnumero");
               String sahkoposti = resultSet.getString("sahkoposti");      
               
               System.out.println("|Id: " + id + " |Nimi: " + etuNimi + " " + sukuNimi + " |Puhelinnumero: " + puhelinnumero + " |Sähköposti: " + sahkoposti);
           }
            
           System.out.println("================================================[LISÄVARUSTEIDEN TIEDOT]");
           
           PreparedStatement statement2 = conn.prepareStatement("SELECT * FROM Lisavaruste;");
           ResultSet resultSet2 = statement2.executeQuery();

           
           while(resultSet2.next()){
               int id = resultSet2.getInt("id");
               String nimi = resultSet2.getString("nimi");
               int hinta = resultSet2.getInt("hinta");            
               
               System.out.println("|Id: " + id + " |Nimi: " + nimi + " |Hinta: " + hinta);
           }
           
           
           System.out.println("================================================[HUONEIDEN TIEDOT]");
           
           PreparedStatement statement3 = conn.prepareStatement("SELECT * FROM Huone;");
           ResultSet resultSet3 = statement3.executeQuery();

           
           while(resultSet3.next()){
               int numero = resultSet3.getInt("numero");
               String tyyppi = resultSet3.getString("tyyppi");
               int paivahinta = resultSet3.getInt("paivahinta");            
               
               System.out.println("|Numero: " + numero + " |Tyyppi: " + tyyppi + " |Päivähinta: " + paivahinta);
           }
                      
           
           System.out.println("================================================[VARAUSTEN TIEDOT]");
           
           PreparedStatement statement4 = conn.prepareStatement("SELECT * FROM Varaus;");
           ResultSet resultSet4 = statement4.executeQuery();

           
           while(resultSet4.next()){
               int id = resultSet4.getInt("id");
               java.sql.Date alku = resultSet4.getDate("alku");
               java.sql.Date loppu = resultSet4.getDate("loppu");
               int asiakas_id = resultSet4.getInt("asiakas_id");          
               
               System.out.println("|Id: " + id + " |Alku: " + alku + " |Loppu: " + loppu + " |Asiakas_id: " + asiakas_id);
           }    
           
           
            System.out.println("================================================[HUONE_VARAUS TAULUN TIEDOT]");
           
           PreparedStatement statement5 = conn.prepareStatement("SELECT * FROM HuoneVaraus;");
           ResultSet resultSet5 = statement5.executeQuery();

           
           while(resultSet5.next()){
               int huone_numero = resultSet5.getInt("huone_numero");
               int varaus_id = resultSet5.getInt("varaus_id");
               
               System.out.println("|Huone_numero: " + huone_numero + " |Varaus_id: " + varaus_id);
           }              
       
           
           
           System.out.println("================================================[LISAVARUSTE_VARAUS TAULUN TIEDOT]");
           
           PreparedStatement statement6 = conn.prepareStatement("SELECT * FROM LisavarusteVaraus;");
           ResultSet resultSet6 = statement6.executeQuery();

           
           while(resultSet6.next()){
               int lisavaruste_id = resultSet6.getInt("lisavaruste_id");
               int varaus_id = resultSet6.getInt("varaus_id");
               
               System.out.println("|Lisävaruste_id: " + lisavaruste_id + " |Varaus_id: " + varaus_id);
           }              
           

           
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
