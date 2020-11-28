
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
public class TestDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;  
    
    public static void testHuone(){
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "")) { 
        HuoneDao huoneDao = new HuoneDao();       
        
        Huone h1 = new Huone(503, "Sviitti", 140);
        huoneDao.create(h1);
        
        int hNum = huoneDao.getHNum();
        
        //System.out.println(gKey + "@gKey" );
        
        Huone h2 = huoneDao.read(hNum);
        System.out.println("Luetun huoneen numero: " + h2.getNumero());
        
        Huone h3 = new Huone(503, "Sviitti", 180);
        huoneDao.update(h3);
        
        Huone h4 = new Huone(504, "Sviitti", 2000);
        huoneDao.create(h4);
        int hNum2 = huoneDao.getHNum();
        
        huoneDao.delete(504);
        
        List<Huone> huoneet2 = huoneDao.list();
        
        for(Huone h : huoneet2){
            System.out.print("Numero: " + h.getNumero() + "    ");
            System.out.print("Tyyppi: " + h.getTyyppi() + "    ");
            System.out.print("Paivahinta: " + h.getPaivahinta());
            System.out.println();
        }
        
             } catch (SQLException ex) {
            Logger.getLogger(VarausjarjestelmaSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }              
    }
    
}
