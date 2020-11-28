
package varausjarjestelma;

import java.util.*;

public class Huone {
    private int numero;
    private String tyyppi;
    private int paivahinta;
    
    public Huone(int numero, String tyyppi, int paivahinta){
        this.numero = numero;
        this.tyyppi = tyyppi;
        this.paivahinta = paivahinta;
    }
    
    public void setNumero(int numero){
        this.numero= numero;
    }
    
   public void setTyyppi(String tyyppi){
        this.tyyppi = tyyppi;
    }
    
    public void setPaivahinta(int paivahinta){
        this.paivahinta = paivahinta;
    }
    
    public int getNumero(){
        return this.numero;
    }
    
    public String getTyyppi(){
        return this.tyyppi;
    }    
    
    public int getPaivahinta(){
        return this.paivahinta;
    }
    
    public String toString(){
        return "|Numero: " + this.numero + " |Tyyppi: " + this.tyyppi + " |Päivähinta: " + this.paivahinta;
    }
    
}
