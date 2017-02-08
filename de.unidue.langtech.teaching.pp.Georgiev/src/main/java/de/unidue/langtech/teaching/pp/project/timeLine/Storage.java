package de.unidue.langtech.teaching.pp.project.timeLine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Storage {
	
	private List<LocalDate> locDateList;
	private LocalDate loc;
	private static HashMap<String, List<Integer>> globalEntityCandidatesStorage;
	public Storage(LocalDate loc_){
		this.loc=loc_;
		locDateList.add(loc);
	}

	public List<LocalDate> getLocDateList() {
		return locDateList;
	}
	
	
	
}
