package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumOfOperatorsCheckTest {

    private NumOfOperatorsCheck check;

    @BeforeEach
    void setUp() {
        check = new NumOfOperatorsCheck();
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = new int[] {
            TokenTypes.PLUS,
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.ASSIGN,
            TokenTypes.GT,
            TokenTypes.LT,
            TokenTypes.BAND,
            TokenTypes.BOR,
            TokenTypes.EQUAL,
            TokenTypes.NOT_EQUAL,
        };
        
        assertArrayEquals(expectedTokens, check.getDefaultTokens(), "The default tokens should match the expected values.");
    }

    @Test
    void testGetAcceptableTokens() {
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens(), "The acceptable tokens should match the default tokens.");
    }

    @Test
    void testGetRequiredTokens() {
        assertArrayEquals(new int[0], check.getRequiredTokens(), "The required tokens array should be empty.");
    }

    @Test
    void testVisitToken_withOperator() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("+");

        check.visitToken(mockAST);

        assertEquals(1, check.getTotalOperators(), "The total operator count should be 1 after one operator token.");
    }

    @Test
    void testVisitToken_withNonOperator() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("non-operator");

        check.visitToken(mockAST);

        assertEquals(0, check.getTotalOperators(), "The total operator count should remain 0 when a non-operator token is visited.");
    }

    @Test
    void testVisitToken_withMultipleOperators() {
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getText()).thenReturn("+");
        check.visitToken(plusAST);

        DetailAST minusAST = mock(DetailAST.class);
        when(minusAST.getText()).thenReturn("-");
        check.visitToken(minusAST);

        assertEquals(2, check.getTotalOperators(), "The total operator count should be 2 after visiting two operator tokens.");
    }
    
    @Test
    void testFinishTree_logsAndResetsTotalOperators() {
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getText()).thenReturn("+");
        check.visitToken(plusAST);

        // Spy on the check instance to verify log calls
        NumOfOperatorsCheck spyCheck = spy(check);

        // Mock the log method to do nothing
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Call finishTree on the spy instance
        spyCheck.finishTree(null);

        // Verify that `log` was called with the expected message
        verify(spyCheck).log(eq(0), eq("Total number of operators: 1"));

        // Ensure that totalOperators was reset
        assertEquals(0, spyCheck.getTotalOperators(), "The total operator count should be reset to 0 after finishTree.");
    }
}
