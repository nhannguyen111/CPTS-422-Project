package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HalsteadVocabularyCheckTest {

    private HalsteadVocabularyCheck check;
    private DetailAST mockAST;

    @BeforeEach
    void setUp() {
        check = spy(new HalsteadVocabularyCheck());
        mockAST = mock(DetailAST.class);
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
        assertArrayEquals(expectedTokens, check.getDefaultTokens());
    }
    
    @Test
    void testGetAcceptableTokens() {
        assertNull(check.getAcceptableTokens(), "Expected getAcceptableTokens to return null");
    }
    
    @Test
    void testGetRequiredTokens() {
        assertNull(check.getRequiredTokens(), "Expected getRequiredTokens to return null");
    }
    
    @Test
    void testOperatorRecognition() {
        // Case 1: Operator is recognized
        when(mockAST.getText()).thenReturn("+");
        check.visitToken(mockAST);

        assertTrue(check.getUniqueOperators().contains("+"), "Operator '+' should be recognized and added to the operators set.");
        assertEquals(1, check.getUniqueOperators().size(), "Operators set should contain exactly one operator.");

        // Case 2: Operator is not recognized
        when(mockAST.getText()).thenReturn("unknownOperator");
        check.visitToken(mockAST);

        assertFalse(check.getUniqueOperators().contains("unknownOperator"), "Unknown operator should not be added to the operators set.");
    }
    
    @Test
    void testOperandRecognition() {
        // Case 1: Operand is recognized
        when(mockAST.getText()).thenReturn("int");
        check.visitToken(mockAST);

        assertTrue(check.getUniqueOperands().contains("int"), "Operand 'int' should be recognized and added to the operands set.");
        assertEquals(1, check.getUniqueOperands().size(), "Operands set should contain exactly one operand.");

        // Case 2: Operand is not recognized
        when(mockAST.getText()).thenReturn("unknownOperand");
        check.visitToken(mockAST);

        assertFalse(check.getUniqueOperands().contains("unknownOperand"), "Unknown operand should not be added to the operands set.");
    }

    @Test
    void testFinishTreeHalsteadVocabularyCalculation() {
        // Simulate adding unique operators and operands
        check.getUniqueOperators().add("+");
        check.getUniqueOperators().add("-");
        check.getUniqueOperands().add("int");
        check.getUniqueOperands().add("boolean");

        // Mock DetailAST to pass the line number to log method
        DetailAST rootAST = mock(DetailAST.class);
        when(rootAST.getLineNo()).thenReturn(1);
    }
    
    @Test
    void testFinishTreeClearsSets() {
        // Pre-condition: Add elements to sets
        check.getUniqueOperators().add("+");
        check.getUniqueOperands().add("int");

        // Mock logging behavior
        doNothing().when(check).log(anyInt(), anyString());

        // Call finishTree
        check.finishTree(mockAST);

        // Post-condition: Verify sets are cleared
        assertTrue(check.getUniqueOperators().isEmpty(), "Operators set should be cleared after finishTree.");
        assertTrue(check.getUniqueOperands().isEmpty(), "Operands set should be cleared after finishTree.");

        // Verify log was called
        verify(check).log(anyInt(), anyString());

        // Additional checks to confirm sets were not modified unexpectedly
        check.getUniqueOperators().add("*");
        assertFalse(check.getUniqueOperators().isEmpty(), "Operators set should not be cleared unless finishTree is called.");
    }
    
    @Test
    void testTokenNotRecognized() {
        when(mockAST.getText()).thenReturn("unknownToken");

        check.visitToken(mockAST);

        // Ensure that neither operators nor operands sets contain the token
        assertFalse(check.getUniqueOperators().contains("unknownToken"), "Unknown token should not be in operators set.");
        assertFalse(check.getUniqueOperands().contains("unknownToken"), "Unknown token should not be in operands set.");
    }
}
