package de.unidue.langtech.teaching.pp.project.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

import org.junit.Test;

import de.unidue.langtech.teaching.pp.project.timeDetector.FilteredDate;
import de.unidue.langtech.teaching.pp.project.timeDetector.Time;

public class TimeDetectorTest {

	// Dies sind Beispiele von Daten, die im Text vorkommen koennten. Das letzte
	// "Datum" wird absichtlich leer gewaehlt
	// um es zu zeigen, dass wenn es im Text keine erkenbare Merkmale vom Datum
	// gibt, ein undefiniertes Datum "XXXX-XX-XX" zurueckgibt
	private String[] exampleDate = { "March 2000", "February 17, 2005", "2001", "22-02-2017", "" };
	// Das soll unser Model als Ergebnis liefern
	private String[] expectedDate = { "2000-03-XX", "2005-02-17", "2001-XX-XX", "2017-02-22", "XXXX-XX-XX" };
	private Time t;

	@Test
	public void testTimeDetection() {
		// Initialisiere alle Beispiel Text-Daten
		for (int i = 0; i < exampleDate.length; i++) {
			// Hier kommt unseres Language Detector Modul zunutze. Obwohl in
			// unserer Arbeit nur englische Texte
			// vorhanden sind, koennten auch franzoesische oder deutsche Texte
			// auch auf Datum ueberprueft werden.
			t = new Time(exampleDate[i], "EN");
			// Pruefe ob die Text-Daten mit den nach Vorgabe umgewandelten Daten
			// uebereinstimmen
			// (Bitte SemEval 2015, Task 4 vergleichen)
			assertEquals(getDate(), expectedDate[i]);
		}
	}

	private String getDate() {
		if (t.getLocDate() != null) {
			LocalDate ld = t.getLocDate();
			return new FilteredDate(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear()).toString();
		} else if (t.getYearMonth() != null) {
			YearMonth ym = t.getYearMonth();
			return new FilteredDate(0, ym.getMonthValue(), ym.getYear()).toString();
		} else if (t.getYear() != null) {
			Year year = t.getYear();
			return new FilteredDate(0, 0, year.getValue()).toString();
		} else {
			return new FilteredDate(0, 0, 0).toString();
		}
	}
}
