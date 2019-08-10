package accountant;

import accountant.vo.Budget;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class BudgetService {
    private BudgetRepo budgetRepo;


    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0;
        }

        Period period = new Period(start, end);

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

                total += budget.getOverlappingAmount(period);
            }

        } else {

            // first month
            Optional<Budget> firstBudgetOpt = findBudget(start);
            if (firstBudgetOpt.isPresent()) {
                Budget budget = firstBudgetOpt.get();

                total += budget.getOverlappingAmount(period);
            }


            // last month
            Optional<Budget> lastBudgetOpt = findBudget(end);
            if (lastBudgetOpt.isPresent()) {
                Budget budget = lastBudgetOpt.get();

                total += budget.getOverlappingAmount(period);
            }


            for (int i = 1; i < diffMonth; i++) {

                LocalDate middle = start.plusMonths(i);


                Optional<Budget> middleBudgetOpt = findBudget(middle);
                if (middleBudgetOpt.isPresent()) {
                    Budget budget = middleBudgetOpt.get();

                    total += budget.getOverlappingAmount(period);
                }


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

    private int diffMonth(LocalDate start, LocalDate end) {


        if (start.getYear() == end.getYear()) {
            return Math.abs(start.getMonth().getValue() - end.getMonth().getValue());
        }
        YearMonth from = YearMonth.from(start);
        return YearMonth.from(end).minusMonths(from.getMonthValue()).getMonthValue();
    }
}
