package accountant;

import accountant.vo.Budget;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class BudgetServiceTest {
    private BudgetRepo budgetRepo = mock(BudgetRepo.class);
    private final BudgetService service = new BudgetService(budgetRepo);

    @Test
    public void test_single_whole_month() {
        LocalDate start = prepareLocalDate(2019, 1, 1);
        LocalDate end = prepareLocalDate(2019, 1, 31);

        Budget budget = createBudget("201901", 3100);
        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 3100);
    }

    private Budget createBudget(String yearMonth, int amount) {
        return new Budget(yearMonth, amount);
    }

    private LocalDate prepareLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    private void budgetShouldBe(LocalDate start, LocalDate end, int expected) {
        assertEquals(expected, service.query(start, end), 0.001);
    }

    @Test
    public void test_single_date_query() {
        LocalDate start = prepareLocalDate(2019, 1, 1);
        LocalDate end = prepareLocalDate(2019, 1, 1);

        Budget budget = createBudget("201901", 310);
        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 10);
    }

    @Test
    public void test_partial_month() {
        LocalDate start = prepareLocalDate(2019, 1, 1);
        LocalDate end = prepareLocalDate(2019, 1, 15);

        Budget budget = createBudget("201901", 310);
        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 150);
    }

    @Test
    public void test_invalid_range() {
        LocalDate start = prepareLocalDate(2019, 1, 15);
        LocalDate end = prepareLocalDate(2019, 1, 1);

        Budget budget = createBudget("201901", 310);
        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 0);
    }

    @Test
    public void test_cross_month() {
        LocalDate start = prepareLocalDate(2019, 1, 1);
        LocalDate end = prepareLocalDate(2019, 2, 28);

        Budget budget = createBudget("201901", 310);
        Budget budget1 = createBudget("201902", 280);
        doReturn(Arrays.asList(budget, budget1)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 590);
    }

    @Test
    public void test_cross_three_month() {
        LocalDate start = prepareLocalDate(2019, 1, 31);
        LocalDate end = prepareLocalDate(2019, 3, 3);

        Budget budget = createBudget("201901", 310);
        Budget budget1 = createBudget("201902", 280);
        Budget budget2 = createBudget("201903", 310);
        doReturn(Arrays.asList(budget, budget1, budget2)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 320);
    }

    @Test
    public void test_no_budget() {
        LocalDate start = prepareLocalDate(2019, 1, 31);
        LocalDate end = prepareLocalDate(2019, 3, 3);

        Budget budget = createBudget("201904", 300);
        doReturn(Arrays.asList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 0);
    }

    @Test
    public void test_cross_year() {
        LocalDate start = prepareLocalDate(2018, 12, 31);
        LocalDate end = prepareLocalDate(2019, 2, 1);

        Budget budget = createBudget("201812", 310);
        Budget budget1 = createBudget("201901", 310);
        doReturn(Arrays.asList(budget, budget1)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 320);
    }

    @Test
    public void period_without_overlapping_before_budget() {
        LocalDate start = prepareLocalDate(2018, 12, 31);
        LocalDate end = prepareLocalDate(2019, 2, 1);

        Budget budget = createBudget("201903", 310);
        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 0);
    }

    @Test
    public void period_without_overlapping_after_budget() {
        LocalDate start = prepareLocalDate(2019, 10, 31);
        LocalDate end = prepareLocalDate(2019, 10, 1);

        Budget budget = createBudget("201903", 310);
        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        budgetShouldBe(start, end, 0);
    }
}