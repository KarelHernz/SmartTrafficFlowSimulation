
package model;

import java.util.*;

public class Road {
  
    private HashMap<String, HashMap<Integer, List<Vehicle>>> pista = new HashMap<>();
    
    public List<Vehicle> getVehicles(String nomePista, int via) {
        if (!pista.containsKey(nomePista)) {
            return null; 
        }
        
        HashMap<Integer, List<Vehicle>> vias = pista.get(nomePista);
        
        
        if (!vias.containsKey(via)) {
            return null; 
        }
        
        return vias.get(via);
    }
    

    public void addVehicle(String nomePista, int via, Vehicle carro) {
       
        if (!pista.containsKey(nomePista)) {
            pista.put(nomePista, new HashMap<>());
        }
        
        HashMap<Integer, List<Vehicle>> vias = pista.get(nomePista);
      
        if (!vias.containsKey(via)) {
            vias.put(via, new ArrayList<>());
        }
        
        List<Vehicle> carros = vias.get(via);
        carros.add(carro); 
    }
    public Vehicle removeVehicle(String nomePista, int via) {
       
        List<Vehicle> carros = getVehicles(nomePista, via);
      
        if (carros != null && !carros.isEmpty()) {
            return carros.remove(0);
        }
        
        return null; 
    }
    public void clearRoad() {
        pista = new HashMap<>();
    }
    
    public void mostrarEstrada() {
        if (pista.isEmpty()) {
            System.out.println("Estrada vazia!");
            return;
        }
        
        for (String nomePista : pista.keySet()) {
            System.out.println("Pista: " + nomePista);
            
            HashMap<Integer, List<Vehicle>> vias = pista.get(nomePista);
            
            for (int via : vias.keySet()) {
                List<Vehicle> carros = vias.get(via);
                System.out.println("  Via " + via + ": " + carros.size() + " carros");
                
                for (int i = 0; i < carros.size(); i++) {
                    System.out.println("    [" + i + "] " + carros.get(i));
                }
            }
        }
    }
}
