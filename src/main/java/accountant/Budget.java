package accountant;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.format.DateTimeFormatter.ofPattern;

class Budget {
    private String yearMonth;
    private int amount;

    Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    private String getYearMonth() {
        return yearMonth;
    }

    private int dayCount() {
        return getMonth().lengthOfMonth();
    }

    private double dailyAmount() {
        return amount / dayCount();
    }

    private LocalDate lastDay() {
        return getMonth().atEndOfMonth();
    }

    private YearMonth getMonth() {
        return YearMonth.parse(getYearMonth(), ofPattern("uuuuMM"));
    }

    private LocalDate firstDay() {
        return getMonth().atDay(1);
    }

    private Period getPeriod() {
        return new Period(firstDay(), lastDay());
    }

    double getOverlappingAmount(Period period) {
        return dailyAmount() * period.getOverlappingDays(getPeriod());
    }
}
