
package varausjarjestelma;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import java.util.*;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class VarausDao implements Dao<Varaus, Integer> {
        public int gKey;
        
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    
    public void setGKey(int gKey){
        this.gKey = gKey;
    }
    
    public int getGKey(){
        return this.gKey;
    }
    

    @Override
    public void create(Varaus varaus) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        

        java.sql.Date sDateAlku = java.sql.Date.valueOf(varaus.getAlku());
        java.sql.Date sDateLoppu = java.sql.Date.valueOf(varaus.getLoppu());
        
        PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO Varaus (alku, loppu, asiakas_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);//, new String[]{"USER_ID"});//Statement.RETURN_GENERATED_KEYS); 
        stmt.setDate(1, sDateAlku);
        stmt.setDate(2, sDateLoppu);        
        stmt.setInt(3, varaus.getAsiakas().getId());       
 
        stmt.executeUpdate();
        
        /*
        PreparedStatement stmt2 = conn.prepareStatement(
        "SELECT COUNT(*) AS key FROM Varaus;");
        ResultSet rs = stmt2.executeQuery();
        /*
        if(!rs.next()){
            System.out.println("ongelmia");
            return;
        }
        */

        /*
        int key = rs.getInt("key");
  
                System.out.println("Key " + key);
        */        
                
        
        ArrayList<Integer> genkeysArr = new ArrayList();
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if(generatedKeys.next()) {
            id = generatedKeys.getInt(1);
            System.out.println(id + "@@@@@@@@@@@@@@@@id");
            genkeysArr.add(id);
        }
        
        for(Integer key : genkeysArr){
            System.out.println(key);
        }
        
        
        setGKey(id);
        

        stmt.close();
        conn.close();
    }    
    

    @Override
    public Varaus read(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM Varaus WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()){
            return null;
        }
        
        AsiakasDao asiakasDao = new AsiakasDao();
        
        java.sql.Date alku = rs.getDate("alku");
        LocalDate lAlku = alku.toLocalDate();
        
        java.sql.Date loppu = rs.getDate("loppu");
        LocalDate lLoppu = loppu.toLocalDate();        
        
        Asiakas asiakas = asiakasDao.read(rs.getInt("asiakas_id"));
        
        Varaus v = new Varaus(key, lAlku, lLoppu, asiakas);
        
        stmt.close();
        rs.close();
        conn.close();
        
        return v;
        
    }      
    
    @Override
    public Varaus update(Varaus varaus) throws SQLException {
        
        java.sql.Date sDateAlku = java.sql.Date.valueOf(varaus.getAlku());
        java.sql.Date sDateLoppu = java.sql.Date.valueOf(varaus.getLoppu());
        
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "UPDATE Varaus SET alku = ?, loppu = ?, asiakas_id = ?  WHERE id = ? ");
        stmt.setDate(1, sDateAlku);
        stmt.setDate(2, sDateLoppu);
        stmt.setInt(3, varaus.getAsiakas().getId());  
        stmt.setInt(4, varaus.getId());
        
        
        
        stmt.executeUpdate();
        
        return read(varaus.getId());    

    }  
    
    
    
    @Override
    public void delete(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String deleteSQL = "DELETE FROM Asiakas WHERE id = ?"; 
        PreparedStatement stmt = conn.prepareStatement(deleteSQL);
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        
    }   


    @Override
    public List<Varaus> list() throws SQLException {
        
        List<Varaus> varaukset = new ArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String listSql = "SELECT * FROM Varaus;";
        
        PreparedStatement stmt = conn.prepareStatement(listSql);
        
        ResultSet rs = stmt.executeQuery();
        
        AsiakasDao asiakasDao = new AsiakasDao();
        
        while(rs.next()){
            int id = rs.getInt("id");
            
            
            java.sql.Date alku = rs.getDate("alku");
            LocalDate lAlku = alku.toLocalDate();
        
            java.sql.Date loppu = rs.getDate("loppu");
            LocalDate lLoppu = loppu.toLocalDate();  
            
            int asiakasId = rs.getInt("asikas_id");
            Asiakas asiakas = asiakasDao.read(asiakasId); 
            
            Varaus varaus = new Varaus(id, lAlku, lLoppu, asiakas);
            varaukset.add(varaus);
        }
        
        
            return varaukset;
              
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
