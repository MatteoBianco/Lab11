package it.polito.tdp.rivers.model;

import java.util.Comparator;

public class ComparatorFlowsForDate implements Comparator<Flow>{

	@Override
	public int compare(Flow o1, Flow o2) {
		return o1.getDay().compareTo(o2.getDay());
	}

}
