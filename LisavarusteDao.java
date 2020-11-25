
package varausjarjestelma;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class LisavarusteDao implements Dao<Lisavaruste, Integer> {
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
    public void create(Lisavaruste lisavaruste) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO Lisavaruste (nimi, hinta) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS); 
        stmt.setString(1, lisavaruste.getNimi());
        stmt.setInt(2, lisavaruste.getHinta());        
       
 
        stmt.executeUpdate();
        
        
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if(generatedKeys.next()) {
            id = generatedKeys.getInt(1);
            System.out.println(id + "@@@@@@@@@@@@@@@@id");
        }
        
        
        setGKey(id);
        

        stmt.close();
        conn.close();
    }    
    
    
    @Override
    public Lisavaruste read(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM Lisavaruste WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()){
            return null;
        }
        
        Lisavaruste l = new Lisavaruste(key, rs.getString("nimi"), rs.getInt("hinta"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return l;
        
    }       
    
    @Override
    public Lisavaruste update(Lisavaruste lisavaruste) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "UPDATE Lisavaruste SET nimi = ?, hinta = ? WHERE id = ? ");
        stmt.setString(1, lisavaruste.getNimi());
        stmt.setInt(2, lisavaruste.getHinta());
        stmt.setInt(3, lisavaruste.getId());  

        stmt.executeUpdate();
        
        return read(lisavaruste.getId());    

    }
    
    

    @Override
    public void delete(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String deleteSQL = "DELETE FROM Lisavaruste WHERE id = ?"; 
        PreparedStatement stmt = conn.prepareStatement(deleteSQL);
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
                
    }        
    
    
    @Override
    public List<Lisavaruste> list() throws SQLException {
        
        List<Lisavaruste> lisavarusteet = new ArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String listSql = "SELECT * FROM Lisavaruste;";
        
        PreparedStatement stmt = conn.prepareStatement(listSql);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            int hinta = rs.getInt("hinta");
            
            Lisavaruste lisavaruste = new Lisavaruste(id, nimi, hinta);
            lisavarusteet.add(lisavaruste);
        }
        
        
            return lisavarusteet;
              
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
