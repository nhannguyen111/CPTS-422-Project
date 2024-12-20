package My422Project;

import com.puppycrawl.tools.checkstyle.api.*;

public class NumOfCommentsCheck extends AbstractCheck {

    private int commentCount = 0;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
                TokenTypes.SINGLE_LINE_COMMENT,  // Single-line comment (e.g., // comment)
                TokenTypes.BLOCK_COMMENT_BEGIN   // Block comment start (e.g., /* comment)
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public boolean isCommentNodesRequired() {
        // This method is overridden to show that we are working with comment tokens.
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Increment the comment count whenever we find a comment token.
        if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            setCommentCount(getCommentCount() + 1);
        } else if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            setCommentCount(getCommentCount() + 1); // Count the entire block comment once.
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of comments.
        log(rootAST.getLineNo(), "Number of comments: " + getCommentCount() + " NN");
        
        // Reset the counter for the next file.
        setCommentCount(0);
    }

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
}
