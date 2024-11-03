package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumOfLinesOfCommentsCheck extends AbstractCheck {

    private int commentLinesCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN
        };
    }
    
    @Override
    public int[] getAcceptableTokens() {
        return new int[0]; // Return empty array instead of null
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0]; // Return empty array instead of null
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset the counter at the beginning of each file
        commentLinesCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            // Count single line comment
            commentLinesCount++;
        } else if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            // Count each line in a block comment
            int startLine = ast.getLineNo();
            int endLine = ast.findFirstToken(TokenTypes.BLOCK_COMMENT_END).getLineNo();
            commentLinesCount += (endLine - startLine + 1);
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the final count of comment lines
        log(0, "Number of lines of comments: " + commentLinesCount);
    }
    
    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    // Getter for testing purposes
    public int getCommentLinesCount() {
        return commentLinesCount;
    }
}