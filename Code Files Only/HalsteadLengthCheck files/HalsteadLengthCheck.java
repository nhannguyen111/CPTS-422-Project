package My422Project;

import com.puppycrawl.tools.checkstyle.api.*;

import java.util.HashSet;
import java.util.Set;

public class HalsteadLengthCheck extends AbstractCheck {

    private int numOperators = 0;
    private int numOperands = 0;

    private static final Set<String> OPERATORS = new HashSet<>();
    private static final Set<String> OPERANDS = new HashSet<>();

    static {
        // Here are some common operators
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

        // Here are some common operands (keywords, literals)
        OPERANDS.add("int");
        OPERANDS.add("float");
        OPERANDS.add("double");
        OPERANDS.add("String");
        OPERANDS.add("boolean");
        OPERANDS.add("null");
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
        // No required tokens
        return new int[0];
    }

    @Override
    public void visitToken(DetailAST ast) {

        if (isOperator(ast)) {
            numOperators++;
        } else if (isOperand(ast)) {
            numOperands++;
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Calculate the Halstead Length
        int halsteadLength = numOperators + numOperands;

        // Log/display the result
        log(rootAST.getLineNo(), "Halstead Length: " + halsteadLength + " NN");

        // Reset counters for the next file
        numOperators = 0;
        numOperands = 0;
    }

    private boolean isOperator(DetailAST ast) {
        return OPERATORS.contains(ast.getText());
    }

    private boolean isOperand(DetailAST ast) {
        return OPERANDS.contains(ast.getText()) ||
                ast.getType() == TokenTypes.LITERAL_INT ||
                ast.getType() == TokenTypes.STRING_LITERAL ||
                ast.getType() == TokenTypes.LITERAL_BOOLEAN ||
                ast.getType() == TokenTypes.IDENT ||
                ast.getType() == TokenTypes.NUM_INT;
    }
    
 // Getter for testing purposes
    public int getNumOperators() {
        return numOperators;
    }

    public int getNumOperands() {
        return numOperands;
    }

    // Setter for testing purposes
    public void setNumOperators(int numOperators) {
        this.numOperators = numOperators;
    }

    public void setNumOperands(int numOperands) {
        this.numOperands = numOperands;
    }
    
}
