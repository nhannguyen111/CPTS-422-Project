package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumOfVariableDeclarationsCheckTest {

    private NumOfVariableDeclarationsCheck check;

    @BeforeEach
    void setUp() {
        check = new NumOfVariableDeclarationsCheck();
    }

    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = new int[]{
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF
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
    void testVisitToken_withVariableDeclaration() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getType()).thenReturn(TokenTypes.VARIABLE_DEF);

        check.visitToken(mockAST);

        assertEquals(1, check.getTotalVariableDeclarations(), "The total variable declaration count should be 1 after one VARIABLE_DEF token.");
    }

    @Test
    void testVisitToken_withParameterDeclaration() {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getType()).thenReturn(TokenTypes.PARAMETER_DEF);

        check.visitToken(mockAST);

        assertEquals(1, check.getTotalVariableDeclarations(), "The total variable declaration count should be 1 after one PARAMETER_DEF token.");
    }

    @Test
    void testVisitToken_withMultipleDeclarations() {
        DetailAST variableDefAST = mock(DetailAST.class);
        when(variableDefAST.getType()).thenReturn(TokenTypes.VARIABLE_DEF);
        check.visitToken(variableDefAST);

        DetailAST parameterDefAST = mock(DetailAST.class);
        when(parameterDefAST.getType()).thenReturn(TokenTypes.PARAMETER_DEF);
        check.visitToken(parameterDefAST);

        assertEquals(2, check.getTotalVariableDeclarations(), "The total variable declaration count should be 2 after visiting VARIABLE_DEF and PARAMETER_DEF tokens.");
    }
    
    @Test
    void testFinishTree_logsAndResetsTotalVariableDeclarations() {
        // Simulate visiting tokens
        DetailAST variableDefAST = mock(DetailAST.class);
        when(variableDefAST.getType()).thenReturn(TokenTypes.VARIABLE_DEF);
        check.visitToken(variableDefAST);

        DetailAST parameterDefAST = mock(DetailAST.class);
        when(parameterDefAST.getType()).thenReturn(TokenTypes.PARAMETER_DEF);
        check.visitToken(parameterDefAST);

        // Spy on the check instance
        NumOfVariableDeclarationsCheck spyCheck = spy(check);

        // Stub the log method to do nothing
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Call finishTree and verify behavior
        spyCheck.finishTree(null);

        // Verify that `log` was called with the expected message
        verify(spyCheck).log(eq(0), contains("Total number of variable declarations: 2"));

        // Ensure that totalVariableDeclarations was reset
        assertEquals(0, spyCheck.getTotalVariableDeclarations(), "The total variable declaration count should be reset to 0 after finishTree.");
    }
}
