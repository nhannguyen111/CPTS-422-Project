package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumOfExpressionsCheck extends AbstractCheck {

    private int totalExpressions = 0; // Tracks the total number of expressions

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
            TokenTypes.EXPR,           // Represents general expressions
            TokenTypes.ASSIGN,         // Assignment expressions
            TokenTypes.PLUS,           // Arithmetic expressions
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.METHOD_CALL,    // Method calls
            TokenTypes.PLUS_ASSIGN,    // Compound assignments
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.BAND,           // Bitwise and logical operators
            TokenTypes.BOR,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.POST_INC,       // Post-increment and post-decrement
            TokenTypes.POST_DEC,
            TokenTypes.INC,            // Pre-increment and pre-decrement
            TokenTypes.DEC,
            TokenTypes.QUESTION,       // Ternary operator
            TokenTypes.LAND,           // Logical AND
            TokenTypes.LOR             // Logical OR
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
        // Increment the count for each recognized expression token
        totalExpressions++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of expressions
        log(0, "Total number of expressions: " + totalExpressions);

        // Reset the count for the next file
        totalExpressions = 0;
    }

    // Getter method for testing purposes
    public int getTotalExpressions() {
        return totalExpressions;
    }
}
