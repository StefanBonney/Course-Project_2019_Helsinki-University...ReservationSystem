
package varausjarjestelma;

import java.util.*;



public class Lisavaruste {
    private int id;
    private String nimi;
    private int hinta;
    
    public static HashMap<String, Integer> lisavarusteLista = new HashMap(); 
    
    public Lisavaruste(int id, String nimi, int hinta){
        this.id = id;
        this.nimi = nimi;
        this.hinta = hinta;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
    
    public void setHinta(int hinta){
        this.hinta = hinta;
    }

    public int getId(){
        return this.id;
    }

    public String getNimi(){
        return this.nimi;
    }
    
    public int getHinta(){
        return this.hinta;
    }    
    
}
