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
public class HuoneDao implements Dao<Huone, Integer>{
    private int gKey;
    private int hNum;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public void setHNum(int hNum){
        this.hNum = hNum;
    }
    
    public int getHNum(){
        return this.hNum;
    }   
    
    
    
    public void setGKey(int gKey){
        this.gKey = gKey;
    }
    
    public int getGKey(){
        return this.gKey;
    }
    
    @Override
    public void create(Huone huone) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO Huone (numero, tyyppi, paivahinta) VALUES (?, ?, ?)"); //,Statement.RETURN_GENERATED_KEYS
        stmt.setInt(1, huone.getNumero());
        stmt.setString(2, huone.getTyyppi());        
        stmt.setInt(3, huone.getPaivahinta());
        
 
        stmt.executeUpdate();
        
        /*
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if(generatedKeys.next()) {
            id = generatedKeys.getInt(1);
            System.out.println(id + "@@@@@@@@@@@@@@@@id");
        }
        
        
        setGKey(id);
        */
        
        setHNum(huone.getNumero());

        stmt.close();
        conn.close();
    }
    
    @Override
    public Huone read(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM Huone WHERE numero = ?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()){
            return null;
        }
        
        Huone h = new Huone(rs.getInt("numero"), rs.getString("tyyppi"), rs.getInt("paivahinta"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return h;
        
    }
    
    @Override
    public Huone update(Huone huone) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");        
        PreparedStatement stmt = conn.prepareStatement(
        "UPDATE Huone SET numero = ?, tyyppi = ?, paivahinta = ?  WHERE numero = ? ");
        stmt.setInt(1, huone.getNumero());
        stmt.setString(2, huone.getTyyppi());
        stmt.setInt(3, huone.getPaivahinta());  
        stmt.setInt(4, huone.getNumero());
        
        
        
        stmt.executeUpdate();
        
        return read(huone.getNumero());
        
        /*
        System.out.println("numero: " + huone.getNumero());
        
        int key = huone.getNumero();
        
        String query = "UPDATE Huone SET tyyppi = ?, paivahinta = ? WHERE numero = ?";
        
        jdbcTemplate.update(query,
        huone.getTyyppi(), huone.getPaivahinta(), key
        );
        
        return read(key);
        */
        
    }    
    
    @Override
    public void delete(Integer key) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String deleteSQL = "DELETE FROM Huone WHERE numero = ?"; 
        PreparedStatement stmt = conn.prepareStatement(deleteSQL);
        stmt.setInt(1, key);
        
        
        stmt.executeUpdate();
        
        
        
        /*
        jdbcTemplate.update("DELETE FROM Huone WHERE numero = ?", key);
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@33333" );
        */
    }
    
    @Override
    public List<Huone> list() throws SQLException {
        
        List<Huone> huoneet = new ArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:h2:./hotelliketju", "sa", "");
        
        String listSql = "SELECT * FROM Huone;";
        
        PreparedStatement stmt = conn.prepareStatement(listSql);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            int numero = rs.getInt("numero");
            String tyyppi = rs.getString("tyyppi");
            int paivahinta = rs.getInt("paivahinta");
            
            Huone huone = new Huone(numero, tyyppi, paivahinta);
            huoneet.add(huone);
        }
        
        
            
            return huoneet;
        /*
        List<Huone> huoneet = jdbcTemplate.query(
                "SELECT * FROM Huone",
                (rs, rowNum) -> new Huone(rs.getInt("numero"), rs.getString("tyyppi"), rs.getInt("paivahinta"))
        );
        */
        
        
        
        /*
        List<Asiakas> asiakkaat = jdbcTemplate.query(
                "SELECT * FROM Asiakas",
                (rs, rowNum) -> new Asiakas(rs.getInt("id"), rs.getString("nimi"), rs.getString("puhelinnumero"), 
                rs.getString("katuosoite"), rs.getInt("postinumero"), rs.getString("postitoimipaikka"))
        );
        */        

        //if(huoneet.isEmpty()){return null;}
        
        //return huoneet;        
        
    }
}
