package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class HalsteadDifficultyCheck extends AbstractCheck {

    protected Set<String> uniqueOperators = new HashSet<>();
    protected Set<String> uniqueOperands = new HashSet<>();
    protected int totalOperands = 0;

    // Define operators and operands as specified
    private static final Set<String> OPERATORS = new HashSet<>();
    private static final Set<String> OPERANDS = new HashSet<>();

    static {
        // Common operators
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

        // Common operands (keywords, literals)
        OPERANDS.add("int");
        OPERANDS.add("float");
        OPERANDS.add("double");
        OPERANDS.add("String");
        OPERANDS.add("boolean");
        OPERANDS.add("null");
    }

    @Override
    public int[] getDefaultTokens() {
        // Define tokens to represent operators and operands
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

        // Check if the token is an operator or operand
        if (OPERATORS.contains(tokenText)) {
            uniqueOperators.add(tokenText);
        } else if (OPERANDS.contains(tokenText)) {
            uniqueOperands.add(tokenText);
            totalOperands++;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Calculate Halstead Difficulty
        int numUniqueOperators = uniqueOperators.size();
        int numUniqueOperands = uniqueOperands.size();
        double halsteadDifficulty = (numUniqueOperands > 0)
                ? (numUniqueOperators * totalOperands) / (2.0 * numUniqueOperands)
                : 0;

        // Log the Halstead Difficulty
        log(0, "Halstead Difficulty: " + halsteadDifficulty);

        // Reset for the next file
        uniqueOperators.clear();
        uniqueOperands.clear();
        totalOperands = 0;
    }
    
 // Getter methods for testing purposes
    public int getUniqueOperatorsCount() {
        return uniqueOperators.size();
    }

    public int getUniqueOperandsCount() {
        return uniqueOperands.size();
    }

    public int getTotalOperands() {
        return totalOperands;
    }
    
}
