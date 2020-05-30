package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private Map<Integer, River> idMapRivers;
	private RiversDAO dao;
	private Simulator s;
	
	public Model() {
		dao = new RiversDAO();
		idMapRivers = new HashMap<>();
		dao.getAllRivers(idMapRivers);
		s = new Simulator();
	}

	public Map<Integer, River> getAllRivers() {
		dao.getAllRivers(idMapRivers);
		return idMapRivers;
	}
	
	public List<Flow> getAllFlows() {
		return dao.getAllFlow(idMapRivers);
	}
	
	public void simulate(River river, Double k) {
		this.s.init(river, k);
		this.s.run();
	}
	
	public Integer getNotAvailable() {
		return s.getNOT_AVAILABLE();
	}
	
	public double getAverageCapacity() {
		return s.getAVG_CAPACITY();
	}
	
}
