package My422Project;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class NumOfLinesOfCommentsCheckTest {

    private NumOfLinesOfCommentsCheck check;
    private DetailAST singleLineCommentAst;
    private DetailAST blockCommentBeginAst;
    private DetailAST blockCommentEndAst;

    @BeforeEach
    void setUp() {
    	check = Mockito.spy(new NumOfLinesOfCommentsCheck());

        // Mock DetailAST for single line comment
        singleLineCommentAst = mock(DetailAST.class);
        when(singleLineCommentAst.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);

        // Mock DetailAST for block comment
        blockCommentBeginAst = mock(DetailAST.class);
        when(blockCommentBeginAst.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
        when(blockCommentBeginAst.getLineNo()).thenReturn(1);

        // Mock DetailAST for end of block comment
        blockCommentEndAst = mock(DetailAST.class);
        when(blockCommentEndAst.getLineNo()).thenReturn(3);
        when(blockCommentBeginAst.findFirstToken(TokenTypes.BLOCK_COMMENT_END)).thenReturn(blockCommentEndAst);
    }
    
    @Test
    void testGetDefaultTokens() {
        int[] expectedTokens = {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals(expectedTokens, check.getDefaultTokens(), "Default tokens should include SINGLE_LINE_COMMENT and BLOCK_COMMENT_BEGIN");
    }
    
    @Test
    void testGetAcceptableTokens() {
        int[] expectedTokens = new int[0];
        assertArrayEquals(expectedTokens, check.getAcceptableTokens(), "Acceptable tokens should return an empty array");
    }
    
    @Test
    void testGetRequiredTokens() {
        int[] expectedTokens = new int[0];
        assertArrayEquals(expectedTokens, check.getRequiredTokens(), "Required tokens should return an empty array");
    }

    @Test
    void testSingleLineCommentCounting() {
        check.visitToken(singleLineCommentAst);
        assertEquals(1, check.getCommentLinesCount(), "Single line comment count should be 1");

        check.visitToken(singleLineCommentAst);
        assertEquals(2, check.getCommentLinesCount(), "Single line comment count should be 2");
    }

    @Test
    void testBlockCommentCounting() {
        check.visitToken(blockCommentBeginAst);
        assertEquals(3, check.getCommentLinesCount(), "Block comment count should be 3 (lines 1-3)");
    }

    @Test
    void testBeginTreeResetsCount() {
        check.visitToken(singleLineCommentAst);
        check.beginTree(null); // Simulate beginning a new file
        assertEquals(0, check.getCommentLinesCount(), "Comment line count should reset to 0 at the beginning of a new tree");
    }
    
    @Test
    void testFinishTree() {
        // Mock the log method to do nothing
        doNothing().when(check).log(anyInt(), anyString());

        // Simulate counting comments
        check.visitToken(createSingleLineCommentMock());
        check.visitToken(createBlockCommentMock(1, 3)); // 3 lines in block comment

        // Call finishTree
        check.finishTree(null);

        // Verify the log method was called with the expected message
        verify(check).log(0, "Number of lines of comments: 4");

        // Ensure the count is as expected
        assertEquals(4, check.getCommentLinesCount(), "Comment line count should be 4");
    }

    // Helper method to create a mock for a single line comment AST
    private DetailAST createSingleLineCommentMock() {
        DetailAST singleLineCommentAst = mock(DetailAST.class);
        when(singleLineCommentAst.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        return singleLineCommentAst;
    }

    // Helper method to create a mock for a block comment AST
    private DetailAST createBlockCommentMock(int startLine, int endLine) {
        DetailAST blockCommentBeginAst = mock(DetailAST.class);
        DetailAST blockCommentEndAst = mock(DetailAST.class);

        when(blockCommentBeginAst.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
        when(blockCommentBeginAst.getLineNo()).thenReturn(startLine);
        when(blockCommentEndAst.getLineNo()).thenReturn(endLine);

        when(blockCommentBeginAst.findFirstToken(TokenTypes.BLOCK_COMMENT_END)).thenReturn(blockCommentEndAst);

        return blockCommentBeginAst;
    }

    @Test
    void testIsCommentNodesRequired() {
        assertTrue(check.isCommentNodesRequired(), "isCommentNodesRequired should return true");
    }
}
