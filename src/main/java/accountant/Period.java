package accountant;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    private LocalDate getStart() {
        return start;
    }

    private LocalDate getEnd() {
        return end;
    }

    public long overlappingDays(Period another) {

        if (this.end.isBefore(another.getStart())
                || this.start.isAfter(another.getEnd())) {
            return 0L;
        }


        LocalDate head = getStart().isAfter(another.getStart())
                ? getStart()
                : another.getStart();

        LocalDate tail = getEnd().isBefore(another.getEnd())
                ? getEnd()
                : another.getEnd();

        return DAYS.between(head, tail) + 1;
    }

    long diffMonth() {

        return MONTHS.between(YearMonth.from(start), YearMonth.from(end));

    }
}
