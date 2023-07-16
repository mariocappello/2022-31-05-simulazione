package it.polito.tdp.nyc.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.Graphs;

import it.polito.tdp.nyc.db.NYCDao;

   public class Model {
	
	NYCDao dao;
	Map<String,City> idMap;
	Graph<City,DefaultWeightedEdge> grafo;
	

    
	public Model() {
		dao= new NYCDao();
		idMap= new HashMap<String,City>();
		dao.getAllCity(idMap);
	}
	
	
	public void creaGrafo(Provider p) {
		grafo=new SimpleWeightedGraph<City,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<City> listaVertici = dao.getVerticiGrafo(p, idMap);
		//problema: raddoppiano i vertici
		Graphs.addAllVertices(grafo, listaVertici);
		
		List<Adiacenza> listaArchi = dao.getArchiGrafo(p,idMap);
		for(Adiacenza a : listaArchi) {
			Graphs.addEdgeWithVertices(grafo, a.getC1(), a.getC2(), a.getPeso());
		}
		
		System.out.println("**********listaVertici*************"+"\n"+listaVertici);
		System.out.println("**********lista AllVertices*************"+ "\n"+Graphs.addAllVertices(grafo, listaVertici) );
		System.out.println("VERTICI: " + grafo.vertexSet());
		System.out.println("ARCHI: "+ grafo.edgeSet().size());
		
	}
	
	
	public List<Adiacenza> elencoQuartieriCollegati(City city) {
		
		List<Adiacenza> adiacenza = new ArrayList<Adiacenza>();
		
		//List<City> listaAdiacenti = Graphs.neighborListOf(grafo, city);
		//non serve il neighborOf
		
		Set<DefaultWeightedEdge> archi = grafo.edgesOf(city);
		
			for(DefaultWeightedEdge edge : archi) {
				Adiacenza a=new Adiacenza(grafo.getEdgeSource(edge),grafo.getEdgeTarget(edge),grafo.getEdgeWeight(edge));
				adiacenza.add(a);
			}
			return adiacenza;
	}
	
	
	
	public List<Provider> getAllProvider(){
		return dao.getAllProvider();
	
	}


	public int getNumeroVertici() {
		return grafo.vertexSet().size();
	}


	public int getNumeroArchi() {
		return grafo.edgeSet().size();
	}


	public boolean modelloCreato() {
		if(grafo==null) {
			return false;
		}
		else {
			return true;
		}
	}

	public Set<City> getQuartieriGrafo() {
		return grafo.vertexSet();
	}
	
	
}
