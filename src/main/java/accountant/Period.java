package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

class Period {
    private final LocalDate start;
    private final LocalDate end;

    Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    static long dayCount(LocalDate start, LocalDate end) {
        return DAYS.between(start, end) + 1;
    }

    private LocalDate getStart() {
        return start;
    }

    private LocalDate getEnd() {
        return end;
    }

    long getOverlappingDays(Budget budget) {
        Period another = new Period(budget.firstDay(), budget.lastDay());
        LocalDate overLappingStart = start.isAfter(another.getStart()) ? start : another.getStart();
        LocalDate overLappingEnd = end.isBefore(another.getEnd()) ? end : another.getEnd();
        return dayCount(overLappingStart, overLappingEnd);
    }
}
