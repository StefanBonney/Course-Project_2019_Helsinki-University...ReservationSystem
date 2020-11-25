
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
public class Tehtava3 {
   
    @Autowired
    JdbcTemplate jdbcTemplate;   
    
    
    
    public static void haeHuoneet(LocalDate alku, LocalDate loppu, String tyyppi, int paivahinta){
          try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
          
          
            java.sql.Date sAlku = java.sql.Date.valueOf(alku);

            java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely =
            "select * from Huone where Huone.tyyppi = ? and Huone.paivahinta < ? and not exists( select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku < ? and Varaus.loppu > ?);";
          
           PreparedStatement statement = conn.prepareStatement(kysely);
           statement.setString(1, tyyppi);
           statement.setInt(2, paivahinta);
           statement.setDate(3, sLoppu);
           statement.setDate(4, sAlku);
 
           
           ResultSet resultSet = statement.executeQuery();
           
           while(resultSet.next()){
               int haettuNumero = resultSet.getInt("numero");
               String haettuTyyppi = resultSet.getString("tyyppi");
               int haettuPaivahinta = resultSet.getInt("paivahinta");            
               
               System.out.println("|Numero: " + haettuNumero + " |Tyyppi: " + haettuTyyppi + " |Päivähinta: " + haettuPaivahinta);
           }   
          
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
    public static void haeHuoneet(LocalDate alku, LocalDate loppu){
          
         try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {         
          
          
            java.sql.Date sAlku = java.sql.Date.valueOf(alku);

            java.sql.Date sLoppu = java.sql.Date.valueOf(loppu);      
            
            String kysely = 
            "select * from Huone where not exists (select * from HuoneVaraus join Varaus ON HuoneVaraus.varaus_id = Varaus.id where HuoneVaraus.huone_numero = Huone.numero and Varaus.alku < ? and Varaus.loppu > ?);";
            
           PreparedStatement statement = conn.prepareStatement(kysely);
           statement.setDate(1, sLoppu);
           statement.setDate(2, sAlku);
 
           
           ResultSet resultSet = statement.executeQuery();
           
           while(resultSet.next()){
               int numero = resultSet.getInt("numero");
               String tyyppi = resultSet.getString("tyyppi");
               int paivahinta = resultSet.getInt("paivahinta");            
               
               System.out.println("|Numero: " + numero + " |Tyyppi: " + tyyppi + " |Päivähinta: " + paivahinta);
           }   
          
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
               
           
      }      
      
    
    
    
}
