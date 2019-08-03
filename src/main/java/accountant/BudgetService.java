package accountant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ofPattern;

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
        Period period = new Period(start, end);
        LocalDate currentDate = start;
//        while (currentDate.isBefore(YearMonth.from(end).plusMonths(1).atDay(1))) {
//            Optional<Budget> currentBudget = getBudget(currentDate);
//
//            if (currentBudget.isPresent()) {
//                Budget budget = currentBudget.get();
//
        for (Budget budget : budgetRepo.getAll()) {
            totalAmount += budget.getOverlappingAmount(period);
        }
//            }
//            currentDate = currentDate.plusMonths(1);
//        }
        return totalAmount;
    }

    private Optional<Budget> getBudget(LocalDate start) {
        DateTimeFormatter formatter = ofPattern("yyyyMM");
        return budgetRepo.getAll().stream()
                         .filter(b -> b.getYearMonth().equals(start.format(formatter))).findFirst();
    }

}
