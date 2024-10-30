package My422Project;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumOfCommentsCheckTest {

	private NumOfCommentsCheck check;
    private DetailAST singleLineComment;
    private DetailAST blockComment;
    private DetailAST rootAST;

    @BeforeEach
    public void setUp() {
        // Create a real instance of NumOfCommentsCheck for all tests except finishTree
        check = new NumOfCommentsCheck();
        
        // Mock AST nodes for single-line and block comments
        singleLineComment = mock(DetailAST.class);
        blockComment = mock(DetailAST.class);
        rootAST = mock(DetailAST.class);

        // Set up mock behavior for the comment token types
        when(singleLineComment.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        when(blockComment.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
    }

    @Test
    public void testDefaultTokens() {
        // Verify that getDefaultTokens returns expected tokens for single-line and block comments
        int[] tokens = check.getDefaultTokens();
        assertEquals(2, tokens.length);
        assertEquals(TokenTypes.SINGLE_LINE_COMMENT, tokens[0]);
        assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, tokens[1]);
    }

    @Test
    public void testAcceptableTokens() {
        // Verify that getAcceptableTokens returns the same tokens as getDefaultTokens
        assertArrayEquals(check.getDefaultTokens(), check.getAcceptableTokens());
    }

    @Test
    public void testRequiredTokens() {
        // Verify that getRequiredTokens returns an empty array as expected
        assertArrayEquals(new int[0], check.getRequiredTokens());
    }

    @Test
    public void testIsCommentNodesRequired() {
        // Verify that isCommentNodesRequired returns true, indicating comment nodes are required
        assertEquals(true, check.isCommentNodesRequired());
    }

    @Test
    public void testVisitTokenSingleLineComment() {
        // Test that visitToken increments comment count for a single-line comment
        check.visitToken(singleLineComment);
        assertEquals(1, check.getCommentCount());
    }

    @Test
    public void testVisitTokenBlockComment() {
        // Test that visitToken increments comment count for a block comment
        check.visitToken(blockComment);
        assertEquals(1, check.getCommentCount());
    }

    @Test
    public void testVisitTokenMultipleComments() {
        // Test that multiple calls to visitToken increment the comment count correctly
        check.visitToken(singleLineComment);
        check.visitToken(blockComment);
        check.visitToken(singleLineComment);
        assertEquals(3, check.getCommentCount());
    }

    @Test
    public void testFinishTreeLoggingAndReset() {
        // Spy on NumOfCommentsCheck to intercept the log method behavior only for this test
        NumOfCommentsCheck spyCheck = spy(check);

        // Stub the log method to do nothing, isolating the test from logging output
        doNothing().when(spyCheck).log(anyInt(), anyString());

        // Mock the line number for the root AST node
        doReturn(0).when(rootAST).getLineNo();

        // Simulate counting comments by visiting tokens
        spyCheck.visitToken(singleLineComment);
        spyCheck.visitToken(blockComment);

        // Call finishTree to log the final count and reset the counter
        spyCheck.finishTree(rootAST);

        // Verify that log was called with the expected message and line number
        verify(spyCheck).log(eq(0), eq("Number of comments: 2 NN"));
        
        // Check that comment count is reset to 0 after finishTree is called
        assertEquals(0, spyCheck.getCommentCount());
    }
	
}
