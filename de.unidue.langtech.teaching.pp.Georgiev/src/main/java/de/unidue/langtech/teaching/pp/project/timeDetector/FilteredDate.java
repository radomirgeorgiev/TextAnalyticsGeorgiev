package de.unidue.langtech.teaching.pp.project.timeDetector;

/**
 * Klasse FilteredDate.
 */
public class FilteredDate {

	private int day, month, year;

	/**
	 * Erstellt Instanz von FilteredDate
	 * 
	 * @param day_
	 *            the day
	 * @param month_
	 *            the month
	 * @param year_
	 *            the year
	 */
	public FilteredDate(int day_, int month_, int year_) {
		this.day = day_;
		this.month = month_;
		this.year = year_;
	}

	/**
	 * Holt day
	 *
	 * @return gibt day zurück
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Setzt neu day
	 *
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Holt month
	 *
	 * @return gibt month zurück
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Setzt neue month.
	 *
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Holt year.
	 *
	 * @return gibt year zurück
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Setzt neue year.
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Bestimmt wie die Kette retString representiert werden muss, falls es
	 * unbestimmte Date-Format vorkommt
	 *
	 * @return gibt retString zurück
	 */

	public String toString() {
		String retString = "";
		String tempDay = "";
		String tempMonth = "";
		String tempYear = "";
		if (day == 0) {
			tempDay = "XX";
		} else {
			tempDay = (day < 10 ? "0" : "") + day;
		}
		if (month == 0) {
			tempMonth = "XX";
		} else {
			tempMonth = (month < 10 ? "0" : "") + month;
		}
		if (year == 0) {
			tempYear = "XXXX";
		} else {
			tempYear = Integer.toString(year);
		}

		retString = tempYear + "-" + tempMonth + "-" + tempDay;
		return retString;
	}

}
