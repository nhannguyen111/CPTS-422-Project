package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HalsteadEffortCheckTest {

    private HalsteadEffortCheck check;

    @BeforeEach
    void setUp() { 	
        check = new HalsteadEffortCheck();
    }

    @Test
    void testGetDefaultTokens() {
    	int[] expectedTokens = {
                TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV,
                TokenTypes.ASSIGN, TokenTypes.GT, TokenTypes.LT, TokenTypes.BAND,
                TokenTypes.BOR, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
                TokenTypes.LPAREN, TokenTypes.RPAREN, TokenTypes.COMMA,
                TokenTypes.RBRACK, TokenTypes.LITERAL_IF, TokenTypes.SEMI,
                TokenTypes.LITERAL_FOR, TokenTypes.LE, TokenTypes.GE,
                TokenTypes.INC, TokenTypes.DEC, TokenTypes.LITERAL_RETURN,
                TokenTypes.LCURLY, TokenTypes.RCURLY, TokenTypes.LITERAL_INT,
                TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_BOOLEAN,
                TokenTypes.IDENT, TokenTypes.NUM_INT
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
    void testFinishTree_handlesZeroVocabularyAndOperands() {
        // Create a spy of the check instance
        HalsteadEffortCheck spyCheck = spy(new HalsteadEffortCheck());

        // Stub the log method to do nothing
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Call finishTree with empty sets (default state)
        spyCheck.finishTree(null);

        // Verify that volume and difficulty are both 0 due to vocabulary and operands being 0
        verify(spyCheck).log(eq(0), contains("Halstead Effort: 0.0"));
    }

    @Test
    void testFinishTree_calculatesVolumeAndDifficultyWithNonZeroValues() {
        // Create a spy of the check instance
        HalsteadEffortCheck spyCheck = spy(new HalsteadEffortCheck());

        // Simulate visiting tokens for operators and operands
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getText()).thenReturn("+");
        spyCheck.visitToken(plusAST);

        DetailAST intAST = mock(DetailAST.class);
        when(intAST.getText()).thenReturn("int");
        spyCheck.visitToken(intAST);

        // Stub the log method to do nothing
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Call finishTree and verify log
        spyCheck.finishTree(null);
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
    
    @Test
    void testVisitToken_withNeitherOperatorNorOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("unknownToken");

        check.visitToken(mockAST);

        // Ensure the operator and operand sets are not modified
        assertEquals(0, check.getTotalOperators(), "The total operator count should remain 0.");
        assertEquals(0, check.getTotalOperands(), "The total operand count should remain 0.");
    }

    @Test
    void testGetHalsteadEffort_withZeroVocabularyAndOperands() {
        // Ensure there are no operators or operands
        assertEquals(0, check.getUniqueOperatorsCount(), "Operators set should be empty.");
        assertEquals(0, check.getUniqueOperandsCount(), "Operands set should be empty.");

        // Call the method and assert effort is 0
        assertEquals(0.0, check.getHalsteadEffort(), 0.0001, "Halstead Effort should be 0 when vocabulary and operands are 0.");
    }

    
    @Test
    void testGetHalsteadEffort_withNonZeroVocabularyAndOperands() {
        // Add operators and operands
        DetailAST operatorAST = mock(DetailAST.class);
        when(operatorAST.getText()).thenReturn("+");
        check.visitToken(operatorAST);

        DetailAST operandAST = mock(DetailAST.class);
        when(operandAST.getText()).thenReturn("int");
        check.visitToken(operandAST);

        // Verify vocabulary and operands are non-zero
        assertEquals(1, check.getUniqueOperatorsCount(), "Operators set should contain 1 unique operator.");
        assertEquals(1, check.getUniqueOperandsCount(), "Operands set should contain 1 unique operand.");

        // Call the method and assert the effort is greater than 0
        assertTrue(check.getHalsteadEffort() > 0, "Halstead Effort should be greater than 0 for non-zero vocabulary and operands.");
    }

    @Test
    void testGetHalsteadEffort_withZeroOperands() {
        // Add only operators
        DetailAST operatorAST = mock(DetailAST.class);
        when(operatorAST.getText()).thenReturn("+");
        check.visitToken(operatorAST);

        // Verify operands are zero
        assertEquals(0, check.getUniqueOperandsCount(), "Operands set should be empty.");
        assertEquals(1, check.getUniqueOperatorsCount(), "Operators set should contain 1 unique operator.");

        // Call the method and assert effort is 0 due to zero operands
        assertEquals(0.0, check.getHalsteadEffort(), 0.0001, "Halstead Effort should be 0 when operands are 0.");
    }

    @Test
    void testGetHalsteadEffort_withZeroVocabulary() {
        // Add only operands
        DetailAST operandAST = mock(DetailAST.class);
        when(operandAST.getText()).thenReturn("int");
        check.visitToken(operandAST);

        // Verify vocabulary is 0 (no operators) but operands exist
        assertEquals(0, check.getUniqueOperatorsCount(), "Operators set should be empty.");
        assertEquals(1, check.getUniqueOperandsCount(), "Operands set should contain 1 unique operand.");

        // Call the method and assert the effort is 0 due to zero vocabulary
        assertEquals(0.0, check.getHalsteadEffort(), 0.0001, "Halstead Effort should be 0 when vocabulary is 0.");
    }
}
