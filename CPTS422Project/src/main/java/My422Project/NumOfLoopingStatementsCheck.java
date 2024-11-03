package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumOfLoopingStatementsCheck extends AbstractCheck {

    // Counter for looping statements
    private int loopCount = 0;

    @Override
    public int[] getDefaultTokens() {
        // Define tokens for looping statements
        return new int[] {
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        // We accept only for, while, and do tokens as looping statements
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];  // No specific tokens required
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Each time a loop token is encountered, increment the loop count
        loopCount++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of looping statements
        log(0, "Number of looping statements: " + loopCount);

        // Reset the loop counter for the next file
        loopCount = 0;
    }

    // Getter method for testing purposes
    public int getLoopCount() {
        return loopCount;
    }
}
