package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HalsteadEffortCheckTest {

    private HalsteadEffortCheck check;

    @BeforeEach
    void setUp() { 	
        check = new HalsteadEffortCheck();
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = new int[]{
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
            TokenTypes.LITERAL_INT,
            TokenTypes.STRING_LITERAL,
            TokenTypes.LITERAL_BOOLEAN,
            TokenTypes.IDENT,
            TokenTypes.NUM_INT
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
        assertEquals(0, check.getTotalOperands(), "The total operand count should be 0.");
    }

    @Test
    void testVisitToken_withOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("int");

        check.visitToken(mockAST);

        assertEquals(0, check.getTotalOperators(), "The total operator count should be 0.");
        assertEquals(1, check.getTotalOperands(), "The total operand count should be 1 after one operand token.");
    }

    @Test
    void testVisitToken_withMultipleOperatorsAndOperands() {
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getText()).thenReturn("+");
        check.visitToken(plusAST);

        DetailAST minusAST = mock(DetailAST.class);
        when(minusAST.getText()).thenReturn("-");
        check.visitToken(minusAST);

        DetailAST intAST = mock(DetailAST.class);
        when(intAST.getText()).thenReturn("int");
        check.visitToken(intAST);

        DetailAST floatAST = mock(DetailAST.class);
        when(floatAST.getText()).thenReturn("float");
        check.visitToken(floatAST);

        assertEquals(2, check.getTotalOperators(), "The total operator count should be 2.");
        assertEquals(2, check.getTotalOperands(), "The total operand count should be 2.");
    }

    @Test
    void testFinishTree_calculatesEffortAndResets() {
        // Simulate visiting tokens for operators and operands
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getText()).thenReturn("+");
        check.visitToken(plusAST);

        DetailAST intAST = mock(DetailAST.class);
        when(intAST.getText()).thenReturn("int");
        check.visitToken(intAST);

        // Spy on the check instance to verify the log call
        HalsteadEffortCheck spyCheck = spy(check);
        spyCheck.finishTree(null);

        // Verify that `log` was called with the expected Halstead Effort message
        double expectedEffort = spyCheck.getHalsteadEffort();
        verify(spyCheck).log(eq(0), contains("Halstead Effort: " + expectedEffort));

        // Ensure that operator and operand counts are reset after finishTree
        assertEquals(0, spyCheck.getTotalOperators(), "The total operator count should be reset to 0 after finishTree.");
        assertEquals(0, spyCheck.getTotalOperands(), "The total operand count should be reset to 0 after finishTree.");
    }

    @Test
    void testGetHalsteadEffort_calculation() {
        // Simulate visiting tokens
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getText()).thenReturn("+");
        check.visitToken(plusAST);

        DetailAST minusAST = mock(DetailAST.class);
        when(minusAST.getText()).thenReturn("-");
        check.visitToken(minusAST);

        DetailAST intAST = mock(DetailAST.class);
        when(intAST.getText()).thenReturn("int");
        check.visitToken(intAST);

        DetailAST floatAST = mock(DetailAST.class);
        when(floatAST.getText()).thenReturn("float");
        check.visitToken(floatAST);

        // Calculate expected Halstead Effort manually
        int programLength = check.getTotalOperators() + check.getTotalOperands();
        int vocabulary = check.getUniqueOperatorsCount() + check.getUniqueOperandsCount();
        double volume = vocabulary > 0 ? programLength * (Math.log(vocabulary) / Math.log(2)) : 0;
        double difficulty = check.getUniqueOperandsCount() > 0 ? (check.getUniqueOperatorsCount() * check.getTotalOperands()) / (2.0 * check.getUniqueOperands().size()) : 0;
        double expectedEffort = difficulty * volume;

        assertEquals(expectedEffort, check.getHalsteadEffort(), 0.0001, "The calculated Halstead Effort should match the expected value.");
    }
}
