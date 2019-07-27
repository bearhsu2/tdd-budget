package accountant;

import java.time.LocalDate;

public class BudgetService {
    private BudgetRepo budgetRepo;

    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if(start.isEqual(end))
        {

            return this.budgetRepo.getAll().get(0).getAmount() / start.lengthOfMonth();
        }
        if (diffMonth(start, end) == 0){
            return this.budgetRepo.getAll().get(0).getAmount();
        }



        return 0;
    }

    private int diffMonth(LocalDate start, LocalDate end) {
        return start.getMonth().getValue() - end.getMonth().getValue();
    }
}
