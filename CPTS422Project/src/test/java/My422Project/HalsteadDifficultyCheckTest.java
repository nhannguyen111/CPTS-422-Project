package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HalsteadDifficultyCheckTest {

    private HalsteadDifficultyCheck check;

    @BeforeEach
    void setUp() {
        check = new HalsteadDifficultyCheck();
    }
    
    @Test
    void testGetDefaultTokens() {
        HalsteadDifficultyCheck check = new HalsteadDifficultyCheck();
        
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
            TokenTypes.LITERAL_INT,
            TokenTypes.STRING_LITERAL,
            TokenTypes.LITERAL_BOOLEAN,
            TokenTypes.IDENT,
            TokenTypes.NUM_INT
        };
        
        assertArrayEquals(expectedTokens, check.getDefaultTokens(), "The default tokens do not match the expected values.");
    }
    
    @Test
    void testGetAcceptableTokens() {
        // `getAcceptableTokens()` should return the same array as `getDefaultTokens()`
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens(), "The acceptable tokens should match the default tokens.");
    }

    @Test
    void testGetRequiredTokens() {
        // `getRequiredTokens()` should return an empty array
        assertArrayEquals(new int[0], check.getRequiredTokens(), "The required tokens array should be empty.");
    }

    @Test
    void testVisitToken_withOperator() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("+");

        check.visitToken(mockAST);

        assertEquals(1, check.getUniqueOperatorsCount());
        assertEquals(0, check.getUniqueOperandsCount());
    }

    @Test
    void testVisitToken_withOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("int");

        check.visitToken(mockAST);

        assertEquals(0, check.getUniqueOperatorsCount());
        assertEquals(1, check.getUniqueOperandsCount());
        assertEquals(1, check.getTotalOperands());
    }

    @Test
    void testVisitToken_withMultipleOperatorsAndOperands() {
        DetailAST operatorAST = mock(DetailAST.class);
        when(operatorAST.getText()).thenReturn("+");
        check.visitToken(operatorAST);

        DetailAST operandAST = mock(DetailAST.class);
        when(operandAST.getText()).thenReturn("int");
        check.visitToken(operandAST);

        DetailAST anotherOperandAST = mock(DetailAST.class);
        when(anotherOperandAST.getText()).thenReturn("int");
        check.visitToken(anotherOperandAST);

        assertEquals(1, check.getUniqueOperatorsCount());
        assertEquals(1, check.getUniqueOperandsCount());
        assertEquals(2, check.getTotalOperands());
    }

    @Test
    void testFinishTree_withCalculations() {
        // Simulate visit tokens
        DetailAST operatorAST = mock(DetailAST.class);
        when(operatorAST.getText()).thenReturn("+");
        check.visitToken(operatorAST);

        DetailAST operandAST1 = mock(DetailAST.class);
        when(operandAST1.getText()).thenReturn("int");
        check.visitToken(operandAST1);

        DetailAST operandAST2 = mock(DetailAST.class);
        when(operandAST2.getText()).thenReturn("float");
        check.visitToken(operandAST2);

        DetailAST rootAST = mock(DetailAST.class);
        
        // Spy on the check instance to verify log calls
        HalsteadDifficultyCheck spyCheck = spy(check);
        spyCheck.finishTree(rootAST);

        // Verify Halstead Difficulty calculation and logging
        verify(spyCheck).log(eq(0), contains("Halstead Difficulty: "));

        assertEquals(1, spyCheck.getUniqueOperatorsCount());
        assertEquals(2, spyCheck.getUniqueOperandsCount());
        assertEquals(2, spyCheck.getTotalOperands());
    }

    @Test
    void testFinishTree_resetsState() {
        // Simulate visit tokens
        DetailAST operatorAST = mock(DetailAST.class);
        when(operatorAST.getText()).thenReturn("+");
        check.visitToken(operatorAST);

        DetailAST operandAST = mock(DetailAST.class);
        when(operandAST.getText()).thenReturn("int");
        check.visitToken(operandAST);

        DetailAST rootAST = mock(DetailAST.class);
        check.finishTree(rootAST);

        // Ensure state is reset after finishTree
        assertEquals(0, check.getUniqueOperatorsCount());
        assertEquals(0, check.getUniqueOperandsCount());
        assertEquals(0, check.getTotalOperands());
    }
}
