package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class NumOfOperandsCheck extends AbstractCheck {

    private int totalOperands = 0; // Tracks the total number of operands

    // Define the set of operand tokens we want to count
    private static final Set<String> OPERANDS = new HashSet<>();

    static {
        // Define common operands (keywords, literals)
        OPERANDS.add("int");
        OPERANDS.add("float");
        OPERANDS.add("double");
        OPERANDS.add("String");
        OPERANDS.add("boolean");
        OPERANDS.add("null");
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
            TokenTypes.LITERAL_INT,
            TokenTypes.STRING_LITERAL,
            TokenTypes.LITERAL_BOOLEAN,
            TokenTypes.IDENT,
            TokenTypes.NUM_INT
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

        // Check if the token is an operand
        if (OPERANDS.contains(tokenText)) {
            totalOperands++;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of operands
        log(0, "Total number of operands: " + totalOperands);

        // Reset the count for the next file
        totalOperands = 0;
    }

    // Getter method for testing purposes
    public int getTotalOperands() {
        return totalOperands;
    }
}
