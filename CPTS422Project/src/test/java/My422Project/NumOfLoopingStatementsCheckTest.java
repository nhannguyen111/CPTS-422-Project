package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class NumOfLoopingStatementsCheckTest {

    private NumOfLoopingStatementsCheck check;

    @BeforeEach
    public void setUp() {
        check = new NumOfLoopingStatementsCheck();
    }

    @Test
    public void testGetDefaultTokens() {
        int[] expectedTokens = {
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO
        };
        assertEquals(expectedTokens, check.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        assertEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }
    
    @Test
    public void testGetRequiredTokens() {
        assertArrayEquals(new int[0], check.getRequiredTokens(), "getRequiredTokens() should return an empty array.");
    }

    @Test
    public void testVisitTokenIncrementsLoopCount() {
        // Create a mock DetailAST node for a "for" loop
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_FOR);

        check.visitToken(mockAST);
        check.visitToken(mockAST);  // Simulate encountering two loops

        assertEquals(2, check.getLoopCount(), "Loop count should be 2 after two visits to loop tokens");
    }

    @Test
    public void testFinishTreeResetsLoopCount() {
        // Simulate visiting some loop tokens
        check.visitToken(createMockAST(TokenTypes.LITERAL_FOR));
        check.visitToken(createMockAST(TokenTypes.LITERAL_WHILE));

        // Confirm loop count before finishing
        assertEquals(2, check.getLoopCount(), "Loop count should be 2 before calling finishTree");

        // Call finishTree and verify reset
        check.finishTree(null);
        assertEquals(0, check.getLoopCount(), "Loop count should be reset to 0 after finishTree");
    }

    // Helper method to create a mock DetailAST with a specific token type
    private DetailAST createMockAST(int tokenType) {
        DetailAST mockAST = mock(DetailAST.class);
        when(mockAST.getType()).thenReturn(tokenType);
        return mockAST;
    }
}
