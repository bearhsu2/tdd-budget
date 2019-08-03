package accountant;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

class Period {
    private final LocalDate start;
    private final LocalDate end;

    Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    private static long dayCount(LocalDate start, LocalDate end) {
        return DAYS.between(start, end) + 1;
    }

    long getOverlappingDays(Period another) {
        if (hasNoOverlapping(another)) {
            return 0;
        }

        LocalDate overLappingStart = start.isAfter(another.start) ? start : another.start;
        LocalDate overLappingEnd = end.isBefore(another.end) ? end : another.end;
        return dayCount(overLappingStart, overLappingEnd);
    }

    private boolean hasNoOverlapping(Period another) {
        return end.isBefore(another.start) || start.isAfter(another.end);
    }
}
