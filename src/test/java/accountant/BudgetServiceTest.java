package accountant;

import accountant.vo.Budget;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class BudgetServiceTest {
    private BudgetRepo budgetRepo;

    @Before
    public void setUp() throws Exception {
        budgetRepo = mock(BudgetRepo.class);
    }

    @Test
    public void test_single_whole_month() {

        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2019, 1, 31);

        Budget budget = new Budget("201901",3100);

        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();

        BudgetService service = new BudgetService(budgetRepo);
        double result = service.query(start, end);

        assertEquals(3100, result, 0.001);
    }

    @Test
    public void test_single_date_query() {
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2019, 1, 1);

        Budget budget = new Budget("201901",310);

        doReturn(Collections.singletonList(budget)).when(budgetRepo).getAll();
        BudgetService service = new BudgetService(budgetRepo);
        double result = service.query(start, end);

        assertEquals(10, result, 0.001);
    }
}