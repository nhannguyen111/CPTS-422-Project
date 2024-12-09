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

    private static final String[] OPERATORS = {
            "+", "-", "*", "/", "=", ">", "<", "&&", "||", "==", "!=", 
            "(", ")", ",", "[", "]", "if", ";", "for", "<=", ">=", "++", "--", "return", "{", "}"
    };

    private static final String[] OPERANDS = {
            "int", "float", "double", "String", "boolean", "null"
    };
    
    // HashSet for quick lookup
    private static final Set<String> OPERATORS_SET = new HashSet<>();
    private static final Set<String> OPERANDS_SET = new HashSet<>();
    
    static {
        for (String operator : OPERATORS) {
            OPERATORS_SET.add(operator);
        }
        for (String operand : OPERANDS) {
            OPERANDS_SET.add(operand);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
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
                TokenTypes.LPAREN,
                TokenTypes.RPAREN,
                TokenTypes.COMMA,
                // TokenTypes.LBRACK,		// Left Bracket doesn't exist apparently..
                TokenTypes.RBRACK,
                TokenTypes.LITERAL_IF,
                TokenTypes.SEMI,
                TokenTypes.LITERAL_FOR,
                TokenTypes.LE,
                TokenTypes.GE,
                TokenTypes.INC,
                TokenTypes.DEC,
                TokenTypes.LITERAL_RETURN,
                TokenTypes.LCURLY,
                TokenTypes.RCURLY,
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
        if (OPERATORS_SET.contains(tokenText)) {
            uniqueOperators.add(tokenText);
        } else if (OPERANDS_SET.contains(tokenText)) {
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
