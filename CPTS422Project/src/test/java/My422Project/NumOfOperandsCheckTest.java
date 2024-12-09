package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumOfOperandsCheckTest {

    private NumOfOperandsCheck check;

    @BeforeEach
    void setUp() {
        check = new NumOfOperandsCheck();
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = new int[]{
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
    void testVisitToken_withOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("int");

        check.visitToken(mockAST);

        assertEquals(1, check.getTotalOperands(), "The total operand count should be 1 after visiting one operand token.");
    }

    @Test
    void testVisitToken_withNonOperand() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getText()).thenReturn("non-operand");

        check.visitToken(mockAST);

        assertEquals(0, check.getTotalOperands(), "The total operand count should remain 0 when a non-operand token is visited.");
    }

    @Test
    void testVisitToken_withMultipleOperands() {
        DetailAST intAST = mock(DetailAST.class);
        when(intAST.getText()).thenReturn("int");
        check.visitToken(intAST);

        DetailAST floatAST = mock(DetailAST.class);
        when(floatAST.getText()).thenReturn("float");
        check.visitToken(floatAST);

        DetailAST stringAST = mock(DetailAST.class);
        when(stringAST.getText()).thenReturn("String");
        check.visitToken(stringAST);

        assertEquals(3, check.getTotalOperands(), "The total operand count should be 3 after visiting three operand tokens.");
    }
    
    @Test
    void testFinishTree() {
        // Simulate visiting tokens
        DetailAST intAST = mock(DetailAST.class);
        when(intAST.getText()).thenReturn("int");
        check.visitToken(intAST);

        DetailAST stringAST = mock(DetailAST.class);
        when(stringAST.getText()).thenReturn("String");
        check.visitToken(stringAST);

        // Spy on the check instance to verify log calls
        NumOfOperandsCheck spyCheck = spy(check);
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Call finishTree
        spyCheck.finishTree(null);

        // Verify that `log` was called with the expected message
        verify(spyCheck).log(eq(0), contains("Total number of operands: 2"));

        // Ensure that totalOperands was reset
        assertEquals(0, spyCheck.getTotalOperands(), "The total operand count should be reset to 0 after finishTree.");
    }
}
