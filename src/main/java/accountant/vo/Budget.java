package accountant.vo;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.format.DateTimeFormatter.ofPattern;

public class Budget {
    public String yearMonth;
    public int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public String getYearMonth() {
        return yearMonth;
    }


    public int getAmount() {
        return amount;
    }

    public int dayCount() {
        return getMonth().lengthOfMonth();
    }

    public double dailyAmount() {
        return getAmount() / dayCount();
    }

    public LocalDate lastDay() {
        return getMonth().atEndOfMonth();
    }

    private YearMonth getMonth() {
        return YearMonth.parse(getYearMonth(), ofPattern("uuuuMM"));
    }

    public LocalDate firstDay() {
        return getMonth().atDay(1);
    }
}
