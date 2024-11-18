package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
            TokenTypes.IDENT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_DOUBLE,
            TokenTypes.STRING_LITERAL,
            TokenTypes.PLUS,
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.ASSIGN,
            TokenTypes.GT,
            TokenTypes.LT,
            TokenTypes.LAND,
            TokenTypes.LOR,
            TokenTypes.EQUAL,
            TokenTypes.NOT_EQUAL,
            TokenTypes.LITERAL_INT,
            TokenTypes.LITERAL_FLOAT,
            TokenTypes.LITERAL_DOUBLE,
            TokenTypes.LITERAL_BOOLEAN,
            TokenTypes.LITERAL_NULL
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

        assert halsteadVolumeCheck.getVocabularySize() == 1;
        assert halsteadVolumeCheck.getProgramLength() == 1;
    }

    @Test
    public void testVisitTokenWithOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("int");

        halsteadVolumeCheck.visitToken(mockAST);

        assert halsteadVolumeCheck.getVocabularySize() == 1;
        assert halsteadVolumeCheck.getProgramLength() == 1;
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

        // Assert the internal state was reset
        assert halsteadVolumeCheck.getProgramLength() == 0;
        assert halsteadVolumeCheck.getVocabularySize() == 0;
    }

    @Test
    public void testNoHalsteadVolumeWhenNoTokens() {
        DetailAST mockAST = mock(DetailAST.class);
        halsteadVolumeCheck = Mockito.spy(new HalsteadVolumeCheck());

        halsteadVolumeCheck.finishTree(mockAST);

        verify(halsteadVolumeCheck).log(eq(0), eq("Halstead Volume: 0.0"));
    }
}
