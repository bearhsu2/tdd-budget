package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.time.YearMonth;

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

    LocalDate getStart() {
        return start;
    }

    LocalDate getEnd() {
        return end;
    }

    long getOverlappingDays(Budget budget) {
        LocalDate overLappingStart = start.isAfter(budget.firstDay()) ? start : budget.firstDay();
        LocalDate overLappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        if (YearMonth.from(budget.firstDay()).equals(YearMonth.from(getStart()))) {
//            overLappingStart = getStart();
//            overLappingEnd = budget.lastDay();
        } else if (YearMonth.from(budget.lastDay()).equals(YearMonth.from(getEnd()))) {
//            overLappingStart = budget.firstDay();
//            overLappingEnd = getEnd();
        } else {
//            overLappingStart = budget.firstDay();
//            overLappingEnd = budget.lastDay();
        }
        return dayCount(overLappingStart, overLappingEnd);
    }
}
