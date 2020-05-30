package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class River {
	private int id;
	private String name;
	private double flowAvg;
	private List<Flow> flows;
	
	public River(int id) {
		this.id = id;
	}

	public River(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public List<Flow> getFlows() {
		if (flows == null)
			flows = new ArrayList<Flow>();
		return flows;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		River other = (River) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public Flow getFirstFlow() {
		Collections.sort(this.flows, new ComparatorFlowsForDate());
		return this.flows.get(0);
	}
	
	public Flow getLastFlow() {
		Collections.sort(this.flows, new ComparatorFlowsForDate());
		return this.flows.get(this.flows.size() - 1);
	}
	
	public Double getAvg() {
		Double media = 0.0;
		for(Flow f : this.flows) {
			media += f.getFlow();
		}
		return media/this.flows.size();
	}
}
