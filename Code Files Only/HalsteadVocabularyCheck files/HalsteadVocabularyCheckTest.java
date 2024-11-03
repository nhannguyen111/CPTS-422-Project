package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class HalsteadVocabularyCheckTest {

    private HalsteadVocabularyCheck check;
    private DetailAST mockAST;

    @BeforeEach
    void setUp() {
        check = new HalsteadVocabularyCheck();
        mockAST = mock(DetailAST.class);
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = {
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
        when(mockAST.getText()).thenReturn("+");

        check.visitToken(mockAST);
        assert(check.getUniqueOperators().contains("+"));
    }

    @Test
    void testOperandRecognition() {
        when(mockAST.getText()).thenReturn("int");

        check.visitToken(mockAST);
        assert(check.getUniqueOperands().contains("int"));
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

        // Spy on the check instance to verify logging
        HalsteadVocabularyCheck spyCheck = Mockito.spy(check);
        spyCheck.finishTree(rootAST);

        // Halstead Vocabulary is sum of unique operators and operands (2 + 2)
        verify(spyCheck).log(1, "Halstead Vocabulary: " + 4);
    }

    @Test
    void testFinishTreeClearsSets() {
        check.getUniqueOperators().add("+");
        check.getUniqueOperands().add("int");

        DetailAST rootAST = mock(DetailAST.class);
        check.finishTree(rootAST);

        assert(check.getUniqueOperators().isEmpty());
        assert(check.getUniqueOperands().isEmpty());
    }
    
}
