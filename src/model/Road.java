package model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Road {
    // Atributo pista com HashMap aninhado
    private HashMap<String, HashMap<Integer, List<Vehicle>>> pista = new HashMap<>();
    
    /**
     * Retorna a lista de veículos de uma determinada pista e via
     * @param pista Nome da pista
     * @param via Número da via
     * @return Lista de veículos ou null se não existir
     */
    public List<Vehicle> getVehicles(String pista, int via) {
        HashMap<Integer, List<Vehicle>> vias = this.pista.get(pista);
        if (vias != null) {
            return vias.get(via);
        }
        return null;
    }
    
    /**
     * Adiciona um veículo à lista no último índice (comportamento LIFO)
     * @param pista Nome da pista
     * @param via Número da via
     * @param v Veículo a ser adicionado
     */
    public void addVehicle(String pista, int via, Vehicle v) {
        // Obtém ou cria o HashMap para as vias desta pista
        HashMap<Integer, List<Vehicle>> vias = this.pista.get(pista);
        if (vias == null) {
            vias = new HashMap<>();
            this.pista.put(pista, vias);
        }
        
        // Obtém ou cria a lista para esta via específica
        List<Vehicle> veiculos = vias.get(via);
        if (veiculos == null) {
            veiculos = new ArrayList<>();
            vias.put(via, veiculos);
        }
        
        // Adiciona o veículo no final da lista (LIFO)
        veiculos.add(v);
    }
    
    /**
     * Remove o primeiro veículo da lista (comportamento LIFO)
     * @param pista Nome da pista
     * @param via Número da via
     * @return Veículo removido ou null se a lista estiver vazia
     */
    public Vehicle removeVehicle(String pista, int via) {
        HashMap<Integer, List<Vehicle>> vias = this.pista.get(pista);
        if (vias != null) {
            List<Vehicle> veiculos = vias.get(via);
            if (veiculos != null && !veiculos.isEmpty()) {
                // Remove o primeiro elemento (LIFO)
                return veiculos.remove(0);
            }
        }
        return null;
    }
    
    /**
     * Remove todos os veículos de todas as pistas e vias
     */
    public void clearRoad() {
        for (HashMap<Integer, List<Vehicle>> vias : pista.values()) {
            for (List<Vehicle> veiculos : vias.values()) {
                veiculos.clear();
            }
        }
        pista.clear();
    }
    
    /**
     * Método auxiliar para visualização do estado atual
     * @return String com representação das pistas e veículos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String nomePista : pista.keySet()) {
            sb.append("Pista: ").append(nomePista).append("\n");
            HashMap<Integer, List<Vehicle>> vias = pista.get(nomePista);
            for (Integer via : vias.keySet()) {
                sb.append("  Via ").append(via).append(": ");
                List<Vehicle> veiculos = vias.get(via);
                if (veiculos != null) {
                    sb.append(veiculos);
                } else {
                    sb.append("[]");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
