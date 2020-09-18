package idv.david.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.Month.*;

public class TestFluent {
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		// Not very readable - is this September 8th or August 9th?
		LocalDate myBday = LocalDate.of(1984, 9, 8);
		// A fluent approach
		myBday = Year.of(1984).atMonth(SEPTEMBER).atDay(8);

		DateTimeFormatter format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

		// Schedule a meeting fluently
		LocalDateTime meeting = LocalDate.of(2018, MARCH, 25).atTime(12, 30);
		System.out.println("meeting:      " + meeting.format(format));

		// Schedule that meeting using the London timezone
		ZonedDateTime meetingUK = meeting.atZone(ZoneId.of("Europe/London"));
		System.out.println("meetingUK:    " + meetingUK.format(format));

		// What time is in San Francisco for that meeting?
		ZonedDateTime earlyMeeting = meetingUK.withZoneSameInstant(ZoneId.of("America/Los_Angeles"));
		System.out.println("earlyMeeting: " + earlyMeeting.format(format));
	}
}
