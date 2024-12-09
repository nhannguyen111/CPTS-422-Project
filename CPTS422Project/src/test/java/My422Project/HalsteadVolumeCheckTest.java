package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class HalsteadVolumeCheckTest {

    private HalsteadVolumeCheck halsteadVolumeCheck;

    @BeforeEach
    public void setUp() {
        halsteadVolumeCheck = new HalsteadVolumeCheck();
    }
    
    @Test
    public void testGetDefaultTokens() {
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

        assertArrayEquals(expectedTokens, halsteadVolumeCheck.getDefaultTokens(), "getDefaultTokens() did not return the expected tokens.");
    }
    
    @Test
    public void testGetAcceptableTokens() {
        assertNull(halsteadVolumeCheck.getAcceptableTokens(), "getAcceptableTokens() should return null.");
    }

    @Test
    public void testGetRequiredTokens() {
        assertNull(halsteadVolumeCheck.getRequiredTokens(), "getRequiredTokens() should return null.");
    }

    @Test
    public void testVisitTokenWithOperator() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("+");

        halsteadVolumeCheck.visitToken(mockAST);
        
        assertEquals(1, halsteadVolumeCheck.getVocabularySize(), "Vocabulary size should be 1 after visiting a single operator.");
        assertEquals(1, halsteadVolumeCheck.getProgramLength(), "Program length should be 1 after visiting a single operator.");
    }

    @Test
    public void testVisitTokenWithOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("int");

        halsteadVolumeCheck.visitToken(mockAST);

        assertEquals(1, halsteadVolumeCheck.getVocabularySize(), "Vocabulary size should be 1 after visiting a single operand.");
        assertEquals(1, halsteadVolumeCheck.getProgramLength(), "Program length should be 1 after visiting a single operand.");
    }
    
    @Test
    public void testFinishTree() {
        // Create a spy of HalsteadVolumeCheck
        halsteadVolumeCheck = Mockito.spy(new HalsteadVolumeCheck());

        // Mock the log method
        doNothing().when(halsteadVolumeCheck).log(anyInt(), anyString());

        // Simulate tokens
        DetailAST mockAST1 = mock(DetailAST.class);
        DetailAST mockAST2 = mock(DetailAST.class);
        when(mockAST1.getText()).thenReturn("+");
        when(mockAST2.getText()).thenReturn("int");

        // Simulate visits to tokens
        halsteadVolumeCheck.visitToken(mockAST1);
        halsteadVolumeCheck.visitToken(mockAST2);
        halsteadVolumeCheck.visitToken(mockAST2);

        // Call finishTree
        halsteadVolumeCheck.finishTree(mockAST1);

        // Verify that log was called with the expected message
        verify(halsteadVolumeCheck).log(eq(0), contains("Halstead Volume: "));

        assertEquals(0, halsteadVolumeCheck.getProgramLength(), "Program length should be reset to 0 after finishTree.");
        assertEquals(0, halsteadVolumeCheck.getVocabularySize(), "Vocabulary size should be reset to 0 after finishTree.");
    }
    
    @Test
    public void testNoHalsteadVolumeWhenNoTokens() {
        DetailAST mockAST = mock(DetailAST.class);
        halsteadVolumeCheck = Mockito.spy(new HalsteadVolumeCheck());

        // Mock the log method to avoid side effects
        doNothing().when(halsteadVolumeCheck).log(anyInt(), anyString());

        // Call finishTree
        halsteadVolumeCheck.finishTree(mockAST);

        // Verify that log was called with the expected message
        verify(halsteadVolumeCheck).log(eq(0), eq("Halstead Volume: 0.0"));
    }
    
    @Test
    public void testVisitTokenWithNeitherOperatorNorOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("unknownToken");

        halsteadVolumeCheck.visitToken(mockAST);

        // Ensure the program length and vocabulary size do not change
        assertEquals(0, halsteadVolumeCheck.getProgramLength(), "Program length should remain 0 when visiting neither an operator nor an operand.");
        assertEquals(0, halsteadVolumeCheck.getVocabularySize(), "Vocabulary size should remain 0 when visiting neither an operator nor an operand.");
    }
    
}
