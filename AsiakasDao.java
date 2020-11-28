
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
public class AsiakasDao implements Dao<Asiakas, Integer> {
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
    public void create(Asiakas asiakas) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO Asiakas (etuNimi, sukuNimi, puhelinnumero, sahkoposti) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
        stmt.setString(1, asiakas.getEtuNimi());
        stmt.setString(2, asiakas.getSukuNimi());        
        stmt.setString(3, asiakas.getPuhelinnumero());
        stmt.setString(4, asiakas.getSahkoposti());        
 
        stmt.executeUpdate();
        
        
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if(generatedKeys.next()) {
            id = generatedKeys.getInt(1);
            System.out.println(id + "@id");
        }
        
        
        setGKey(id);
        

        stmt.close();
        conn.close();
    }

    
    @Override
    public Asiakas read(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM Asiakas WHERE id = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()){
            return null;
        }
        
        Asiakas a = new Asiakas(key, rs.getString("etuNimi"), rs.getString("sukuNimi"), rs.getString("puhelinnumero"), rs.getString("sahkoposti"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return a;
        
    }    

    @Override
    public Asiakas update(Asiakas asiakas) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "UPDATE Asiakas SET etuNimi = ?, sukuNimi = ?, puhelinnumero = ? , sahkoposti = ?   WHERE id = ? ");
        stmt.setString(1, asiakas.getEtuNimi());
        stmt.setString(2, asiakas.getSukuNimi());
        stmt.setString(3, asiakas.getPuhelinnumero());  
        stmt.setString(4, asiakas.getSahkoposti());
        
        
        
        stmt.executeUpdate();
        
        return read(asiakas.getId());    

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
    public List<Asiakas> list() throws SQLException {
        
        List<Asiakas> asiakkaat = new ArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String listSql = "SELECT * FROM Asiakas;";
        
        PreparedStatement stmt = conn.prepareStatement(listSql);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            int id = rs.getInt("id");
            String etuNimi = rs.getString("etuNimi");
            String sukuNimi = rs.getString("sukuNimi");
            String puhelinnumero = rs.getString("puhelinnumero");
            String sahkoposti = rs.getString("sahkoposti");
            
            Asiakas asiakas = new Asiakas(id, etuNimi, sukuNimi, puhelinnumero, sahkoposti);
            asiakkaat.add(asiakas);
        }
        
        
            return asiakkaat;
              
    }

    
    
}
