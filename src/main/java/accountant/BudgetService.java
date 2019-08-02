package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoUnit.DAYS;

public class BudgetService {
    private BudgetRepo budgetRepo;


    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0;
        }

        double totalAmount = 0;
        if (diffMonth(start, end) == 0) {
            Optional<Budget> budget = getBudget(start);
            if (budget.isPresent()) {
                return budget.get().dailyAmount() * dayCount(start, end);
            }
        } else {

            LocalDate currentDate = start;
            while (currentDate.isBefore(YearMonth.from(end).plusMonths(1).atDay(1))) {
                Optional<Budget> currentBudget = getBudget(currentDate);

                if (currentBudget.isPresent()) {
                    Budget budget = currentBudget.get();
                    
                    if (YearMonth.from(currentDate).equals(YearMonth.from(start))) {
                        totalAmount += budget.dailyAmount() * dayCount(start, budget.lastDay());
                    } else if (YearMonth.from(currentDate).equals(YearMonth.from(end))) {
                        totalAmount += budget.dailyAmount() * dayCount(budget.firstDay(), end);
                    } else {
                        totalAmount += budget.dailyAmount() * dayCount(budget.firstDay(), budget.lastDay());
                    }
                }
                currentDate = currentDate.plusMonths(1);
            }
        }
        return totalAmount;
    }

    private Optional<Budget> getBudget(LocalDate start) {
        DateTimeFormatter formatter = ofPattern("yyyyMM");
        return budgetRepo.getAll().stream()
                         .filter(b -> b.getYearMonth().equals(start.format(formatter))).findFirst();
    }

    private long dayCount(LocalDate start, LocalDate end) {
        return DAYS.between(start, end) + 1;
    }

    private int diffMonth(LocalDate start, LocalDate end) {
        if (start.getYear() == end.getYear()) {
            return Math.abs(start.getMonth().getValue() - end.getMonth().getValue());
        }
        YearMonth from = YearMonth.from(start);
        return YearMonth.from(end).minusMonths(from.getMonthValue()).getMonthValue();
    }
}
