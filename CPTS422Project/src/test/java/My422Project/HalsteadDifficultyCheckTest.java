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
        assertArrayEquals(expectedTokens, check.getDefaultTokens(), "The default tokens do not match the expected values.");
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
    void testFinishTree_withAndWithoutUniqueOperands() {
        // Case 1: Non-zero unique operands
        DetailAST operatorAST1 = mock(DetailAST.class);
        when(operatorAST1.getText()).thenReturn("+");
        check.visitToken(operatorAST1);

        DetailAST operandAST = mock(DetailAST.class);
        when(operandAST.getText()).thenReturn("int");
        check.visitToken(operandAST);

        // Spy on the HalsteadDifficultyCheck instance
        HalsteadDifficultyCheck spyCheck1 = spy(check);

        // Mock the log method to avoid actual logging
        doNothing().when(spyCheck1).log(anyInt(), anyString());

        DetailAST rootAST1 = mock(DetailAST.class);
        spyCheck1.finishTree(rootAST1);

        // Verify the log message for non-zero unique operands
        verify(spyCheck1).log(eq(0), contains("Halstead Difficulty: "));
        assertEquals(0, spyCheck1.getUniqueOperatorsCount());
        assertEquals(0, spyCheck1.getUniqueOperandsCount());
        assertEquals(0, spyCheck1.getTotalOperands());

        // Reset the check instance for the second case
        check = new HalsteadDifficultyCheck();

        // Case 2: Zero unique operands
        DetailAST operatorAST2 = mock(DetailAST.class);
        when(operatorAST2.getText()).thenReturn("+");
        check.visitToken(operatorAST2);

        HalsteadDifficultyCheck spyCheck2 = spy(check);
        doNothing().when(spyCheck2).log(anyInt(), anyString());

        DetailAST rootAST2 = mock(DetailAST.class);
        spyCheck2.finishTree(rootAST2);

        // Verify the log message for zero unique operands
        verify(spyCheck2).log(eq(0), contains("Halstead Difficulty: 0"));
        assertEquals(0, spyCheck2.getUniqueOperatorsCount());
        assertEquals(0, spyCheck2.getUniqueOperandsCount());
        assertEquals(0, spyCheck2.getTotalOperands());
    }

    
}
