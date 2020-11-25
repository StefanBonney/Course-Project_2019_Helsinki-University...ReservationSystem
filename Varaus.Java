
package varausjarjestelma;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;


public class Varaus {
    
    private int id;
    private LocalDate alku;
    private LocalDate loppu;
    
    private Asiakas asiakas;
 
      public Varaus(LocalDate alku, LocalDate loppu, Asiakas asiakas){
        this.alku = alku;
        this.loppu = loppu;
        
        this.asiakas = asiakas;
    }
      
    public Varaus(int id, LocalDate alku, LocalDate loppu, Asiakas asiakas){
        this.id = id;
        this.alku = alku;
        this.loppu = loppu;
        
        this.asiakas = asiakas;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setAlku(LocalDate alku){
        this.alku = alku;
    }    
    
    public void setLoppu(LocalDate loppu){
        this.loppu = loppu;
    }       
    
    public void setAsiakas(Asiakas asiakas){
        this.asiakas = asiakas;
    }        
    
    public int getId(){
        return this.id;
    }
    
    public LocalDate getAlku(){
        return this.alku;
    }    

    public LocalDate getLoppu(){
        return this.loppu;
    } 

    public Asiakas getAsiakas(){
        return this.asiakas;
    } 
    
    
    public String toString(){
        return "|Id: " + this.id + " |Alku: " + this.alku + " |Loppu: " + this.loppu;
    }
    
    
}
