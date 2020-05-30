package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow {
	private LocalDate day;
	private Double flow;
	private River river;

	public Flow(LocalDate day, Double flow, River river) {
		this.day = day;
		this.flow = flow;
		this.river = river;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public Double getFlow() {
		return flow;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}

	@Override
	public String toString() {
		return "Flow [day=" + day + ", flow=" + flow + ", river=" + river + "]";
	}
	
	public Double getFlowInDay() {
		return this.flow*3600*24;
	}
	
}
