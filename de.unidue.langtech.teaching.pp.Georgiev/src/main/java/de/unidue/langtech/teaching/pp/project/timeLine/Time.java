package de.unidue.langtech.teaching.pp.project.timeLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Time {

	private List<DateTimeFormatter> timeFormatter;
	private Locale loc;
	private LocalDate locDate;
	
	public Time(String time, String language){
		
		
		loc = new Locale(language);
	
		timeFormatter = new ArrayList<DateTimeFormatter>();
		
		timeFormatter.add(DateTimeFormatter.ofPattern("d. MMMM yyyy", loc ));
		timeFormatter.add(DateTimeFormatter.ofPattern("dd.MM.yyyy", loc ));
		timeFormatter.add(DateTimeFormatter.ofPattern("MMM dd, yyyy", loc));
		locDate = myDate(time);
		
	}
	
	
	public LocalDate myDate(String s){
		for(DateTimeFormatter sl : timeFormatter){
			try{
				return LocalDate.parse(s, sl);
			}catch (Exception pe){
				
			}
		}
		return null;
	}
	
	public LocalDate getCurrentDate(){
		return locDate;
	}
}
