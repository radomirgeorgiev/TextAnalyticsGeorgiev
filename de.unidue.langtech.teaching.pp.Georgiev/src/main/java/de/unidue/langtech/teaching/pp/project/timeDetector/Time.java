package de.unidue.langtech.teaching.pp.project.timeDetector;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Klasse Time.
 */
public class Time {

	private List<DateTimeFormatter> timeFormatter;

	private Locale loc;

	private LocalDate locDate;

	private YearMonth yearMonth;

	private Year year;

	/**
	 * Erstellt Instanz von Time.
	 *
	 * @param time
	 * @param language
	 */
	public Time(String time, String language) {
		
		loc = new Locale(language);

		timeFormatter = new ArrayList<DateTimeFormatter>();

		timeFormatter.add(DateTimeFormatter.ofPattern("d. MMMM yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("dd.MM.yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("MMM dd, yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("MMMM dd, yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("yyyy-MM-dd", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("dd-MM-yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("MMMM dd yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("MMMM yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("MMM yyyy", loc));
		timeFormatter.add(DateTimeFormatter.ofPattern("yyyy", loc));
		locDate = myDateLD(time);
		yearMonth = myDateMY(time);
		year = myDateY(time);
	}

	/**
	 * myDateY
	 *
	 * @param s
	 * @return gibt entweder das gefundene Year zurück oder null wenn es kein
	 *         passenden Muster gab
	 */
	public Year myDateY(String s) {
		for (DateTimeFormatter sl : timeFormatter) {
			try {
				return timeYear(s, sl);
			} catch (Exception pe) {

			}
		}
		return null;
	}

	/**
	 * myDateLD
	 * 
	 * @param s
	 * @return gibt entweder das gefundene LocalDate zurück oder null wenn es
	 *         kein passenden Muster gab
	 */
	public LocalDate myDateLD(String s) {
		for (DateTimeFormatter sl : timeFormatter) {
			try {
				return timeLD(s, sl);
			} catch (Exception pe) {

			}
		}
		return null;
	}

	/**
	 * myDateMY
	 * 
	 * @param s
	 * @return gibt entweder das gefundene YearMonth zurück oder null wenn es
	 *         kein passenden Muster gab
	 */
	public YearMonth myDateMY(String s) {
		for (DateTimeFormatter sl : timeFormatter) {
			try {
				return timeMY(s, sl);
			} catch (Exception pe) {

			}
		}
		return null;
	}

	/**
	 * Versuchen LocalDate zu parsen nach bestimmten Mustern
	 *
	 * @param time
	 * @param dtf
	 * @return gibt localDate zurück
	 */
	private LocalDate timeLD(String time, DateTimeFormatter dtf) {
		return LocalDate.parse(time, dtf);
	}

	/**
	 * Versuchen YearMoth zu parsen nach bestimmten Mustern
	 *
	 * @param time
	 * @param dtf
	 * @return gibt YearMonth zurück
	 */
	private YearMonth timeMY(String time, DateTimeFormatter dtf) {
		return YearMonth.parse(time, dtf);
	}

	/**
	 * Versuchen Year zu parsen nach bestimmten Mustern
	 *
	 * @param time
	 * @param dtf
	 * @return gibt year zurück
	 */
	private Year timeYear(String time, DateTimeFormatter dtf) {
		return Year.parse(time, dtf);
	}

	/**
	 * Holt locDate.
	 *
	 * @return gibt locDate zurück
	 */
	public LocalDate getLocDate() {
		return locDate;
	}

	/**
	 * Setzt neue locDate.
	 *
	 * @param locDate
	 */
	public void setLocDate(LocalDate locDate) {
		this.locDate = locDate;
	}

	/**
	 * Holt yearMonth
	 *
	 * @return gibt yearMonth zurück
	 */
	public YearMonth getYearMonth() {
		return yearMonth;
	}

	/**
	 * setzt neue yearMonth
	 *
	 * @param yearMonth
	 */
	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * Holt year
	 *
	 * @return gibt year zurück
	 */
	public Year getYear() {
		return year;
	}

	/**
	 * Setzt neue year.
	 *
	 * @param year
	 */
	public void setYear(Year year) {
		this.year = year;
	}

}
