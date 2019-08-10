package accountant.vo;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

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

    public boolean matchesDate(LocalDate targetDate) {

        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("uuuuMM"))
                .equals(YearMonth.from(targetDate));
    }

    public int lengthOfMonth() {
        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("uuuuMM")).lengthOfMonth();
    }

    public int getDailyAmount() {

        return amount / lengthOfMonth();
    }
}
