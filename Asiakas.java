
package varausjarjestelma;


import java.util.*;

public class Asiakas {

    private int id;
    private String etuNimi;
    private String sukuNimi;
    private String puhelinnumero;
    private String sahkoposti;
    
    private ArrayList<Varaus> varaukset;
    
    public Asiakas(int id, String etuNimi, String sukuNimi, String puhelinnumero, String sahkoposti){
        this.id = id;
        this.etuNimi = etuNimi;
        this.sukuNimi = sukuNimi;
        this.puhelinnumero = puhelinnumero;
        this.sahkoposti = sahkoposti;
        
        this. varaukset = new ArrayList();
    }
    
 
    public void setId(int id){
        this.id = id;
    }    
    
    public void setEtuNimi(String EtuNimi){
        this.etuNimi = etuNimi;
    }
    
    public void setSukuNimi(String sukuNimi){
        this.sukuNimi = sukuNimi;
    }
    
    public void setPuhelinnumero(String puhelinnumero){
        this.puhelinnumero = puhelinnumero;
    }   
    
    public void setSahkoposti(String sahkoposti){
        this.sahkoposti = sahkoposti;
    }       
    
    
    
    
    public int getId(){
        return this.id;
    }    
    
    public String getEtuNimi(){
        return this.etuNimi;
    }
        
    public String getSukuNimi(){
        return this.sukuNimi;
    }    
    
    public String getPuhelinnumero(){
        return this.puhelinnumero;
    }        
    
    public String getSahkoposti(){
        return this.sahkoposti;
    }       
    
    
    
    
    public void lisaaVaraus(Varaus varaus){
        this.varaukset.add(varaus);
    }
    
    public ArrayList<Varaus> getVaraukset(){
        return this.varaukset;
    }    
    
    public String toString(){
        return "|Id: " + this.id + " |Nimi: " + this.etuNimi + " " + this.sukuNimi + " |Puhelinnumero: " + this.puhelinnumero + " |Sähköposti: " + this.sahkoposti;
    }
    
    
}
