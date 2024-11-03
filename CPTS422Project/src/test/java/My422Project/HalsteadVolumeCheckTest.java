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
        DetailAST mockAST = mock(DetailAST.class);
        halsteadVolumeCheck = Mockito.spy(new HalsteadVolumeCheck());

        // Simulate tokens
        when(mockAST.getText()).thenReturn("+", "-", "int", "int");

        // Simulate visits to tokens
        halsteadVolumeCheck.visitToken(mockAST);
        halsteadVolumeCheck.visitToken(mockAST);
        halsteadVolumeCheck.visitToken(mockAST);
        halsteadVolumeCheck.visitToken(mockAST);

        halsteadVolumeCheck.finishTree(mockAST);

        verify(halsteadVolumeCheck).log(eq(0), contains("Halstead Volume: "));

        // Check for reset after finishTree
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
