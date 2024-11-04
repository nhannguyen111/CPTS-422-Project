package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumOfVariableDeclarationsCheck extends AbstractCheck {

    private int totalVariableDeclarations = 0; // Tracks the total number of variable declarations

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
            TokenTypes.VARIABLE_DEF,       // For variable declarations (local, instance)
            TokenTypes.PARAMETER_DEF       // For formal parameters in method declarations
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0]; // No specific tokens required
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Increment the count for each recognized variable declaration token
        totalVariableDeclarations++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of variable declarations
        log(0, "Total number of variable declarations: " + totalVariableDeclarations);

        // Reset the count for the next file
        totalVariableDeclarations = 0;
    }

    // Getter method for testing purposes
    public int getTotalVariableDeclarations() {
        return totalVariableDeclarations;
    }
}
