package accountant;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public long overlappingDays(Period another) {

        LocalDate head = getStart().isAfter(another.getStart())
                ? getStart()
                : another.getStart();

        LocalDate tail = getEnd().isBefore(another.getEnd())
                ? getEnd()
                : another.getEnd();

        return DAYS.between(head, tail) + 1;
    }
}
