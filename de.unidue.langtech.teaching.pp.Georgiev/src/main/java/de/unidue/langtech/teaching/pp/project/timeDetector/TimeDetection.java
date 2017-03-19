package de.unidue.langtech.teaching.pp.project.timeDetector;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.project.storage.SimpleToken;
import joptsimple.internal.Strings;

/**
 * Klasse TimeDetection Hier wird es versucht von dem Text ein Datum zu
 * extrahieren
 */
public class TimeDetection {

	private FilteredDate filDate;

	private int partOfDate, partOfDates;

	private Sentence sentence;

	private String currentWord;

	private Time docCreationDateTimeFormat;

	private Time dateToReturn;

	private List<String> currentExpression;

	private List<Token> linkedWordsOfSentence;

	private int currentWordIndex;

	private int timeWordsIndex;

	private int daysOfWeek, yestom;

	private int monthOfYear;

	private int counterOrd;

	private JCas jcas;

	private Token currentToken;

	private String language;

	private LinkedList<SimpleToken> events;

	private boolean eventPresentTime;

	private boolean sinceAt;

	private enum yestom {
		YESTERDAY, TOMMOROW, CURRENTLY, NOW
	}

	private enum daysOfWeek {
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
	}

	private enum monthOfYear {
		JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OKTOBER, NOVEMBER, DECEMBER
	}

	private enum countAsWort {
		FIRST, SECOND, THIRTH, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINETH, TENTH, ELEVENTH, TWELVETH, THIRTEENTH, FOURTEENTH, FIFTEENTH, SIXTEENTH, SEVENTEENTH, EIGHTEENTH, NINETEENTH, TWENTIETH, THIRTHIETH
	}

	private enum counterWorts {
		ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN, SEVENTEEN, EIGHTTEEN, NINETEEN, TWENTY, THIRTY
	}

	private enum timeWords {
		LAST, NEXT, AGO, BEFORE, SAME, END, AROUND, BEGIN, AFTER, SINCE
	}

	private enum prepWords {
		IN, ON, OF, AT
	}

	private enum nameTime {
		DAY, WEEK, MONTH, YEAR, TIME
	}

	private enum nameTimes {
		DAYS, WEEKS, MONTHS, YEARS
	}

	private enum theWord {
		THE, THIS
	}

	/**
	 * Erstellt Intanz von TimeDetection
	 *
	 * @param jcas_
	 * @param sentence_
	 * @param docCreationDate_
	 * @param language_
	 * @param events_
	 */
	public TimeDetection(JCas jcas_, Sentence sentence_, String docCreationDate_, String language_,
			LinkedList<SimpleToken> events_) {
		this.events = events_;
		this.language = language_;
		this.jcas = jcas_;
		this.sentence = sentence_;
		this.docCreationDateTimeFormat = setCurrentTime(docCreationDate_, jcas);
	}

	/**
	 * Setzt Dokumenterstellungsdatum als currentTime.
	 *
	 * @param docCreationDate
	 * @param jcas
	 * @return docCreationDateTimeFormat
	 */
	private Time setCurrentTime(String docCreationDate, JCas jcas) {
		return docCreationDateTimeFormat = new Time(docCreationDate, language);
	}

	/**
	 * Diese Methode gibt ein Datum zurück abhängig von der Zeit des Eventes.
	 * Wenn die Zeit Präsens ist dann Dokumenterstellungsdatum, sonst 0
	 *
	 * @return gibt filDate zurück in dem entsprechenden Representationformat
	 */
	public String getTime() {
		process();
		if (filDate == null) {
			if (eventPresentTime) {
				filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
						docCreationDateTimeFormat.getLocDate().getMonthValue(),
						docCreationDateTimeFormat.getLocDate().getYear());
			} else {
				filDate = new FilteredDate(0, 0, 0);
			}

		}
		return filDate.toString();

	}

	/**
	 * Process.
	 */
	public void process() {

		eventPresentTime = false;
		int sentenceBegin = sentence.getBegin();
		int sentenceEnd = sentence.getEnd();
		linkedWordsOfSentence = new LinkedList<Token>();

		for (Token to : JCasUtil.select(jcas, Token.class)) {
			if (to.getBegin() >= sentenceBegin && to.getEnd() <= sentenceEnd) {
				verbCheck(events, to);
				linkedWordsOfSentence.add(to);

			}
		}

		filDate = null;
		sinceAt = false;
		dateToReturn = null;
		yestom = -5;
		partOfDate = 0;
		partOfDates = 0;
		currentWord = "";
		currentWordIndex = 0;
		daysOfWeek = 0;
		monthOfYear = 0;
		timeWordsIndex = 0;
		currentExpression = new LinkedList<String>();
		filter();

	}

	/**
	 * Die Methode untersucht jeden Token im Text nach bestimmten enume und
	 * speichert die gefundene Token in einer LinkedList.
	 */
	public void filter() {

		currentToken = getCurrentWord(currentWordIndex);
		currentWord = currentToken.getCoveredText();

		if (currentToken.getPos().getPosValue().equals("CD") && sinceAt) {
			if (isInteger(currentWord)) {
				if (Integer.parseInt(currentWord) > 1000 && Integer.parseInt(currentWord) < 2050) {
					currentExpression.add(currentWord);
				}
			}
			currentWordIndex++;
			filter();
		} else if (currentToken.getCoveredText().equals(",")) {
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, theWord.class)) {
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, prepWords.class)) {
			sinceAt = true;
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, yestom.class)) {
			switch (currentWord.toUpperCase()) {
			case "YESTERDAY":
				yestom = -1;
				break;
			case "TOMMOROW":
				yestom = 1;
				break;
			case "CURRENTLY":
				yestom = 0;
				break;
			case "NOW":
				yestom = 0;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, timeWords.class)) {
			switch (currentWord.toUpperCase()) {
			case "LAST":
				timeWordsIndex = -1;
				break;
			case "NEXT":
				timeWordsIndex = 1;
				break;
			case "AGO":
				timeWordsIndex = -1;
				break;
			case "BEFORE":
				timeWordsIndex = -1;
				break;
			case "AFTER":
				timeWordsIndex = 1;
				break;
			case "SAME":
				timeWordsIndex = 0;
				break;
			case "BEGIN":
				timeWordsIndex = 0;
				break;
			case "END":
				timeWordsIndex = 0;
				break;
			case "AROUND":
				timeWordsIndex = 0;
				break;
			}
			sinceAt = true;
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, nameTime.class)) {
			switch (currentWord.toUpperCase()) {
			case "DAY":
				partOfDate = 1;
				break;
			case "WEEK":
				partOfDate = 2;
				break;
			case "MONTH":
				partOfDate = 3;
				break;
			case "YEAR":
				partOfDate = 4;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, daysOfWeek.class)) {
			switch (currentWord.toUpperCase()) {
			case "MONDAY":
				daysOfWeek = 1;
				break;
			case "TUESDAY":
				daysOfWeek = 2;
				break;
			case "WEDNESDAY":
				daysOfWeek = 3;
				break;
			case "THURSDAY":
				daysOfWeek = 4;
				break;
			case "FRIDAY":
				daysOfWeek = 5;
				break;
			case "SATURDAY":
				daysOfWeek = 6;
				break;
			case "SUNDAY":
				daysOfWeek = 7;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, monthOfYear.class)) {
			switch (currentWord.toUpperCase()) {
			case "JANUARY":
				monthOfYear = 1;
				break;
			case "FEBRUARY":
				monthOfYear = 2;
				break;
			case "MARCH":
				monthOfYear = 3;
				break;
			case "APRIL":
				monthOfYear = 4;
				break;
			case "MAI":
				monthOfYear = 5;
				break;
			case "JUNE":
				monthOfYear = 6;
				break;
			case "JULY":
				monthOfYear = 7;
				break;
			case "AUGUST":
				monthOfYear = 8;
				break;
			case "SEPTEMBER":
				monthOfYear = 9;
				break;
			case "OKTOBER":
				monthOfYear = 10;
				break;
			case "NOVEMBER":
				monthOfYear = 11;
				break;
			case "DECEMBER":
				monthOfYear = 12;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, counterWorts.class)) {
			switch (currentWord.toUpperCase()) {
			case "ONE":
				counterOrd += 1;
				break;
			case "TWO":
				counterOrd += 2;
				break;
			case "THREE":
				counterOrd += 3;
				break;
			case "FOUR":
				counterOrd += 4;
				break;
			case "FIVE":
				counterOrd += 5;
				break;
			case "SIX":
				counterOrd += 6;
				break;
			case "SEVEN":
				counterOrd += 7;
				break;
			case "EIGHT":
				counterOrd += 8;
				break;
			case "NINE":
				counterOrd += 9;
				break;
			case "TEN":
				counterOrd += 10;
				break;
			case "ELEVEN":
				counterOrd += 11;
				break;
			case "TWELFE":
				counterOrd += 12;
				break;
			case "THIRTEEN":
				counterOrd += 13;
				break;
			case "FOURTEEN":
				counterOrd += 14;
				break;
			case "FIFTEEN":
				counterOrd += 15;
				break;
			case "SIXTEEN":
				counterOrd += 16;
				break;
			case "SEVENTEEN":
				counterOrd += 17;
				break;
			case "EIGHTENN":
				counterOrd += 18;
				break;
			case "NINETEEN":
				counterOrd += 19;
				break;
			case "TWENTY":
				counterOrd += 20;
				break;
			case "THIRTY":
				counterOrd += 30;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, countAsWort.class)) {
			switch (currentWord.toUpperCase()) {
			case "FIRST":
				counterOrd += 1;
				break;
			case "SECOND":
				counterOrd += 2;
				break;
			case "THIRTH":
				counterOrd += 3;
				break;
			case "FOURTH":
				counterOrd += 4;
				break;
			case "FIFTH":
				counterOrd += 5;
				break;
			case "SIXTH":
				counterOrd += 6;
				break;
			case "SEVENTH":
				counterOrd += 7;
				break;
			case "EIGHTH":
				counterOrd += 8;
				break;
			case "NINETH":
				counterOrd += 9;
				break;
			case "TENTH":
				counterOrd += 10;
				break;
			case "ELEVENTH":
				counterOrd += 11;
				break;
			case "TWELFETH":
				counterOrd += 12;
				break;
			case "THIRTEENTH":
				counterOrd += 13;
				break;
			case "FOURTEENTH":
				counterOrd += 14;
				break;
			case "FIFTEENTH":
				counterOrd += 15;
				break;
			case "SIXTEENTH":
				counterOrd += 16;
				break;
			case "SEVENTEENTH":
				counterOrd += 17;
				break;
			case "EIGHTENNTH":
				counterOrd += 18;
				break;
			case "NINETEENTH":
				counterOrd += 19;
				break;
			case "TWENTIETH":
				counterOrd += 20;
				break;
			case "THIRTIETH":
				counterOrd += 30;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else if (isInEnum(currentWord, nameTimes.class)) {
			switch (currentWord.toUpperCase()) {
			case "DAYS":
				partOfDates = 1;
				break;
			case "WEEKS":
				partOfDates = 2;
				break;
			case "MONTHS":
				partOfDates = 3;
				break;
			case "YEARS":
				partOfDates = 4;
				break;
			}
			currentExpression.add(currentWord);
			currentWordIndex++;
			filter();
		} else {
			if (!currentExpression.isEmpty()) {
				if (currentExpression.size() == 1) {
					Pattern p = Pattern.compile("[0-9]+");
					Matcher m = p.matcher(currentExpression.get(0));
					if (partOfDates != 0 || monthOfYear != 0 || daysOfWeek != 0 || m.find()) {
						timeExtraction();
					} else {
						if (hasNextWord()) {
							currentExpression.clear();
							filter();
						}

					}
				} else {
					timeExtraction();
				}
			} else {
				if (hasNextWord()) {
					currentWordIndex++;
					filter();
				} else {
					if (eventPresentTime) {
						if (filDate == null) {
							filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
									docCreationDateTimeFormat.getLocDate().getMonthValue(),
									docCreationDateTimeFormat.getLocDate().getYear());
						}

					} else {
						if (filDate == null) {
							filDate = new FilteredDate(0, 0, 0);
						}

					}

				}
			}

		}
	}

	/**
	 * Diese Methode versucht von der filtrierte LinkedList ein Datum zu
	 * extrahieren
	 */
	private void timeExtraction() {

		if (!currentExpression.isEmpty()) {
			// Proverka za data
			String time = Strings.join(currentExpression, " ");
			dateToReturn = new Time(time, language);
			// Izchistva currentExpression
			currentExpression.clear();
			// Prodalji napred
			timeExtraction();
		} else if (dateToReturn != null) {
			if (dateToReturn.getLocDate() != null) {
				LocalDate ld = dateToReturn.getLocDate();
				filDate = new FilteredDate(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear());
			} else if (dateToReturn.getYearMonth() != null) {
				YearMonth ym = dateToReturn.getYearMonth();
				filDate = new FilteredDate(0, ym.getMonthValue(), ym.getYear());
			} else if (dateToReturn.getYear() != null) {
				Year year = dateToReturn.getYear();
				filDate = new FilteredDate(0, 0, year.getValue());
			}

		} else {

			if (partOfDate != 0) {
				switch (partOfDate) {
				case 1:
					if (timeWordsIndex == 0) {
						int tempDayIndex = 0;
						if (counterOrd != 0) {
							tempDayIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusDays(tempDayIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
								docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {

						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusDays(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
								docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusDays(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
								docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;
				case 2:
					if (timeWordsIndex == 0) {
						dateToReturn = docCreationDateTimeFormat;
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusWeeks(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusWeeks(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;
				case 3:
					if (timeWordsIndex == 0) {
						dateToReturn = docCreationDateTimeFormat;
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusMonths(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusMonths(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;
				case 4:
					if (timeWordsIndex == 0) {
						dateToReturn = docCreationDateTimeFormat;
						filDate = new FilteredDate(0, 0, docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusYears(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, 0, docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusYears(1);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, 0, docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;

				}

			} else if (partOfDates != 0) {
				switch (partOfDates) {
				case 1:
					if (timeWordsIndex == 0) {
						int tempDayIndex = 0;
						if (counterOrd != 0) {
							tempDayIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusDays(tempDayIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
								docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						int tempDayIndex = 0;
						if (counterOrd != 0) {
							tempDayIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusDays(tempDayIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
								docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						int tempDayIndex = 0;
						if (counterOrd != 0) {
							tempDayIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusDays(tempDayIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
								docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;
				case 2:
					if (timeWordsIndex == 0) {
						int tempWeekIndex = 0;
						if (counterOrd != 0) {
							tempWeekIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusWeeks(tempWeekIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						int tempWeekIndex = 0;
						if (counterOrd != 0) {
							tempWeekIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusWeeks(tempWeekIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						int tempWeekIndex = 0;
						if (counterOrd != 0) {
							tempWeekIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusWeeks(tempWeekIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;
				case 3:
					if (timeWordsIndex == 0) {
						int tempMonthIndex = 0;
						if (counterOrd != 0) {
							tempMonthIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusMonths(tempMonthIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						int tempMonthIndex = 0;
						if (counterOrd != 0) {
							tempMonthIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusMonths(tempMonthIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						int tempMonthIndex = 0;
						if (counterOrd != 0) {
							tempMonthIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusMonths(tempMonthIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
								docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;
				case 4:
					if (timeWordsIndex == 0) {
						int tempYearIndex = 0;
						if (counterOrd != 0) {
							tempYearIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusYears(tempYearIndex);
						filDate = new FilteredDate(0, 0, docCreationDateTimeFormat.getLocDate().getYear());
					} else if (timeWordsIndex == 1) {
						int tempYearIndex = 0;
						if (counterOrd != 0) {
							tempYearIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.plusYears(tempYearIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, 0, docCreationDateTimeFormat.getLocDate().getYear());
					} else {
						int tempYearIndex = 0;
						if (counterOrd != 0) {
							tempYearIndex = counterOrd;
						}
						LocalDate ld = docCreationDateTimeFormat.getLocDate();
						ld.minusYears(tempYearIndex);
						dateToReturn.setLocDate(ld);
						filDate = new FilteredDate(0, 0, docCreationDateTimeFormat.getLocDate().getYear());
					}
					break;

				}

			} else if (daysOfWeek != 0) {
				if (timeWordsIndex == 0) {
					LocalDate ld = docCreationDateTimeFormat.getLocDate();
					int dayOfWeek = ld.getDayOfWeek().getValue();
					ld.minusDays(dayOfWeek - daysOfWeek);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
							docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
				} else if (timeWordsIndex == 1) {
					LocalDate ld = docCreationDateTimeFormat.getLocDate();
					int dayOfWeek = ld.getDayOfWeek().getValue();
					ld.plusDays(7 - dayOfWeek + daysOfWeek);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
							docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
				} else {
					LocalDate ld = docCreationDateTimeFormat.getLocDate();
					int dayOfWeek = ld.getDayOfWeek().getValue();
					ld.minusDays(7 - dayOfWeek + daysOfWeek);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
							docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
				}

			} else if (monthOfYear != 0) {
				if (timeWordsIndex == 0) {
					LocalDate ld = docCreationDateTimeFormat.getLocDate();
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
				} else if (timeWordsIndex == 1) {
					LocalDate ld = docCreationDateTimeFormat.getLocDate();
					int tempMonthOfYear = ld.getMonthValue();
					ld.plusMonths(12 - tempMonthOfYear + monthOfYear);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
				} else {
					LocalDate ld = docCreationDateTimeFormat.getLocDate();
					int tempMonthOfYear = ld.getMonthValue();
					ld.minusMonths(12 - tempMonthOfYear + monthOfYear);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(0, docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
				}
			} else if (yestom != 0) {
				LocalDate ld = docCreationDateTimeFormat.getLocDate();
				switch (yestom) {
				case 1:
					ld.plusDays(1);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
							docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
					break;
				case -1:
					ld.minusDays(1);
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
							docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
					break;
				case 0:
					dateToReturn.setLocDate(ld);
					filDate = new FilteredDate(docCreationDateTimeFormat.getLocDate().getDayOfMonth(),
							docCreationDateTimeFormat.getLocDate().getMonthValue(),
							docCreationDateTimeFormat.getLocDate().getYear());
					break;
				}
			}

		}
		// Pruefe ob es noch Wörter im String gibt die noch zu untersuchen sind
		if (hasNextWord()) {
			currentExpression.clear();
			filter();
		}

	}

	/**
	 * Holt die Position des untersuchtes Wort im Satz
	 *
	 * @param currentWordIndex
	 * @return gibt der Index des Tokens zurück
	 */
	private Token getCurrentWord(int currentWordIndex) {
		return linkedWordsOfSentence.get(currentWordIndex);
	}

	/**
	 * Prüft ob es noch Worte in einem Satz gibt, die untersucht werden müssen
	 *
	 * @return gibt true zurück wenn es noch Tokens in deisem Satz gibt, sonst
	 *         false
	 */
	private boolean hasNextWord() {
		int cWord = linkedWordsOfSentence.indexOf(currentToken);
		return (cWord + 1) < linkedWordsOfSentence.size();

	}

	/**
	 * Prüft ob bestimmtes Wort in einen von den Mustern (enum) vorkommt
	 *
	 * @param <E>
	 * @param value
	 * @param enumClass
	 * @return wenn es zutrifft gibt true zurück, sonst false
	 */
	private <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).anyMatch(e -> e.name().equals(value.toUpperCase()));
	}

	/**
	 * Prüft die Zeit eines Verbes und setzt eventPressentTime auf true,wenn das
	 * Verb in Präsens Zeit ist oder gleich said,commented oder replied ist.
	 * Sonst auf false
	 *
	 * @param st
	 * @param tk
	 */
	private void verbCheck(LinkedList<SimpleToken> st, Token tk) {
		if (st.getFirst().getTokenText().equals(tk.getCoveredText())) {
			if (tk.getPos().getPosValue().equals("VBZ") || tk.getPos().getPosValue().equals("VBP")
					|| tk.getPos().getPosValue().equals("VBG") || tk.getPos().getPosValue().equals("NNS")
					|| tk.getPos().getPosValue().equals("NN") || tk.getPos().getPosValue().equals("NNP")
					|| tk.getPos().getPosValue().equals("NNPS")) {
				eventPresentTime = true;
			}
			switch (st.getFirst().getTokenText().toLowerCase()) {
			case "said":
				eventPresentTime = true;
				break;
			case "commented":
				eventPresentTime = true;
				break;
			case "replied":
				eventPresentTime = true;
				break;

			}
		}
	}

	// Prüfe ob ein String Integer ist und gibt true zurück wenn das wahr ist,
	// sonst false
	private boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	// Prüfe ob ein String Integer ist und gibt true zurück wenn das wahr ist,
	// sonst false
	private boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

}
