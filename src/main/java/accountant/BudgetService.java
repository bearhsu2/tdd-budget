package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

        // find budget
        // find budget daily amount
        // find overlapping days
        // get daily amount * overlapping days

        double total = 0D;
        int diffMonth = diffMonth(start, end);
        if (diffMonth == 0) {

            Optional<Budget> budgetOpt = findBudget(start);

            if (budgetOpt.isPresent()) {
                Budget budget = budgetOpt.get();

                double dailyAmount = budget.getDailyAmount();
                long overlappingDays = overlappingDays(start, end);
                total += dailyAmount * overlappingDays;
            }

        } else {

            // first month
            Optional<Budget> budgetOpt = findBudget(start);
            if (budgetOpt.isPresent()) {
                Budget budget = budgetOpt.get();

                double dailyAmount = budget.getDailyAmount();
                long overlappingDays = overlappingDays(start, budget.lastDay());
                total += dailyAmount * overlappingDays;
            }



            double lastMonthBudget = calculateBudgetAverage(end) * (end.getDayOfMonth());
            total += lastMonthBudget;

            for (int i = 1; i < diffMonth; i++) {
                LocalDate middle = start.plusMonths(i);
                total += calculateBudgetAverage(middle) * middle.lengthOfMonth();
            }

        }
        return total;

    }

    private Optional<Budget> findBudget(LocalDate targetDate) {
        return this.budgetRepo.getAll()
                .stream()
                .filter(budget -> budget.matchesDate(targetDate))
                .findFirst();
    }

    private double calculateBudgetAverage(LocalDate current) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        for (Budget budget : this.budgetRepo.getAll()) {
            if (budget.getYearMonth().equals(current.format(formatter))) {
                return budget.getAmount() / current.lengthOfMonth();
            }
        }
        return 0D;
    }

    private long overlappingDays(LocalDate start, LocalDate end) {
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
