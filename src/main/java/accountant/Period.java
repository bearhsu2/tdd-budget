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
//        LocalDate overLappingStart = start.isAfter(budget.firstDay()) ? start : budget.firstDay();
//        LocalDate overLappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        Period another = new Period(budget.firstDay(), budget.lastDay());
        LocalDate firstDay = another.getStart();
        LocalDate lastDay = another.getEnd();
        LocalDate overLappingStart = start.isAfter(firstDay) ? start : firstDay;
        LocalDate overLappingEnd = end.isBefore(lastDay) ? end : lastDay;
        return dayCount(overLappingStart, overLappingEnd);
    }
}
