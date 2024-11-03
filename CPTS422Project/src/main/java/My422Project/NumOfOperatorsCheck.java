package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class NumOfOperatorsCheck extends AbstractCheck {

    private int totalOperators = 0; // Tracks the total number of operators

    // Define the set of operator tokens we want to count
    private static final Set<String> OPERATORS = new HashSet<>();

    static {
        // Define common operators as strings
        OPERATORS.add("+");
        OPERATORS.add("-");
        OPERATORS.add("*");
        OPERATORS.add("/");
        OPERATORS.add("=");
        OPERATORS.add(">");
        OPERATORS.add("<");
        OPERATORS.add("&&");
        OPERATORS.add("||");
        OPERATORS.add("==");
        OPERATORS.add("!=");
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.PLUS,
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.ASSIGN,
            TokenTypes.GT,
            TokenTypes.LT,
            TokenTypes.BAND,
            TokenTypes.BOR,
            TokenTypes.EQUAL,
            TokenTypes.NOT_EQUAL,
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
        String tokenText = ast.getText();

        // Check if the token is an operator
        if (OPERATORS.contains(tokenText)) {
            totalOperators++;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of operators
        log(0, "Total number of operators: " + totalOperators);

        // Reset the count for the next file
        totalOperators = 0;
    }

    // Getter method for testing purposes
    public int getTotalOperators() {
        return totalOperators;
    }
}
