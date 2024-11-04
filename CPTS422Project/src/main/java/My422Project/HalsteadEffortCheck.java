package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.HashSet;
import java.util.Set;

public class HalsteadEffortCheck extends AbstractCheck {

    private final Set<String> uniqueOperators = new HashSet<>();
    private final Set<String> uniqueOperands = new HashSet<>();
    private int totalOperators = 0;
    private int totalOperands = 0;

    // Define operator and operand tokens as specified
    private static final Set<String> OPERATORS = new HashSet<>();
    private static final Set<String> OPERANDS = new HashSet<>();

    static {
        // Define common operators
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
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST ast) {
        String tokenText = ast.getText();

        // Check if the token is an operator or operand
        if (OPERATORS.contains(tokenText)) {
            uniqueOperators.add(tokenText);
            totalOperators++;
        } else if (OPERANDS.contains(tokenText)) {
            getUniqueOperands().add(tokenText);
            totalOperands++;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Calculate Halstead metrics
        int programLength = totalOperators + totalOperands;
        int vocabulary = uniqueOperators.size() + getUniqueOperands().size();
        
        double volume = vocabulary > 0 ? programLength * (Math.log(vocabulary) / Math.log(2)) : 0;
        double difficulty = getUniqueOperands().size() > 0 ? (uniqueOperators.size() * totalOperands) / (2.0 * getUniqueOperands().size()) : 0;
        double effort = difficulty * volume;

        // Log the Halstead Effort
        log(0, "Halstead Effort: " + effort);

        // Reset for the next file
        uniqueOperators.clear();
        getUniqueOperands().clear();
        totalOperators = 0;
        totalOperands = 0;
    }

    // Getter methods for testing purposes
    public int getTotalOperators() {
        return totalOperators;
    }

    public int getTotalOperands() {
        return totalOperands;
    }

    public double getHalsteadEffort() {
        int programLength = totalOperators + totalOperands;
        int vocabulary = uniqueOperators.size() + getUniqueOperands().size();
        
        double volume = vocabulary > 0 ? programLength * (Math.log(vocabulary) / Math.log(2)) : 0;
        double difficulty = getUniqueOperands().size() > 0 ? (uniqueOperators.size() * totalOperands) / (2.0 * getUniqueOperands().size()) : 0;
        return difficulty * volume;
    }
    
 // New getter methods to expose uniqueOperators and uniqueOperands for testing
    public int getUniqueOperatorsCount() {
        return uniqueOperators.size();
    }

    public int getUniqueOperandsCount() {
        return getUniqueOperands().size();
    }

	public Set<String> getUniqueOperands() {
		return uniqueOperands;
	}
    
}
