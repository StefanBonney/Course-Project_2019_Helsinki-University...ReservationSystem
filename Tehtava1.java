
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
public class Tehtava1 {


@Autowired
JdbcTemplate jdbcTemplate;    
    

    public static void talennaHuone(Huone huone){
          
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) {
           
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)");
            stmt.setInt(1, huone.getNumero());
            stmt.setString(2, huone.getTyyppi());            
            stmt.setInt(3, huone.getPaivahinta());
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            
    
        } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
      
      }
        
}
