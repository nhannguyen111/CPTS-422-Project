package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumOfExpressionsCheckTest {

    private NumOfExpressionsCheck check;

    @BeforeEach
    void setUp() {
        check = new NumOfExpressionsCheck();
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = new int[]{
            TokenTypes.EXPR,
            TokenTypes.ASSIGN,
            TokenTypes.PLUS,
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.METHOD_CALL,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.BAND,
            TokenTypes.BOR,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.QUESTION,
            TokenTypes.LAND,
            TokenTypes.LOR
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
    void testVisitToken_withExpression() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getType()).thenReturn(TokenTypes.EXPR);

        check.visitToken(mockAST);

        assertEquals(1, check.getTotalExpressions(), "The total expression count should be 1 after one expression token.");
    }

    @Test
    void testVisitToken_withMultipleExpressions() {
        DetailAST assignAST = mock(DetailAST.class);
        when(assignAST.getType()).thenReturn(TokenTypes.ASSIGN);
        check.visitToken(assignAST);

        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getType()).thenReturn(TokenTypes.PLUS);
        check.visitToken(plusAST);

        DetailAST methodCallAST = mock(DetailAST.class);
        when(methodCallAST.getType()).thenReturn(TokenTypes.METHOD_CALL);
        check.visitToken(methodCallAST);

        assertEquals(3, check.getTotalExpressions(), "The total expression count should be 3 after visiting three expression tokens.");
    }
    
    @Test
    void testFinishTree_logsAndResetsTotalExpressions() {
        // Simulate visiting tokens
        DetailAST plusAST = mock(DetailAST.class);
        when(plusAST.getType()).thenReturn(TokenTypes.PLUS);
        check.visitToken(plusAST);

        DetailAST assignAST = mock(DetailAST.class);
        when(assignAST.getType()).thenReturn(TokenTypes.ASSIGN);
        check.visitToken(assignAST);

        // Spy on the check instance to verify log calls
        NumOfExpressionsCheck spyCheck = spy(check);

        // Use doNothing to avoid actual logging
        doNothing().when(spyCheck).log(anyInt(), anyString());

        spyCheck.finishTree(null);

        // Verify that `log` was called with the expected message
        verify(spyCheck).log(eq(0), contains("Total number of expressions: 2"));

        // Ensure that totalExpressions was reset
        assertEquals(0, spyCheck.getTotalExpressions(), "The total expression count should be reset to 0 after finishTree.");
    }
}
