package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumOfLocalMethodReferencesCheck extends AbstractCheck {

    private int localMethodReferences = 0; // Tracks the number of local method references
    private String currentClassName = "";  // Holds the current class name for reference

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
            TokenTypes.CLASS_DEF,        // For capturing the current class name
            TokenTypes.METHOD_CALL       // For identifying method calls
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
        if (ast.getType() == TokenTypes.CLASS_DEF) {
            // Capture the current class name from the CLASS_DEF token
            DetailAST classNameAST = ast.findFirstToken(TokenTypes.IDENT);
            if (classNameAST != null) {
                setCurrentClassName(classNameAST.getText());
            }
        } else if (ast.getType() == TokenTypes.METHOD_CALL) {
            // Check if this method call is on the same class
            if (isLocalMethodReference(ast)) {
                localMethodReferences++;
            }
        }
    }

    private boolean isLocalMethodReference(DetailAST methodCallAst) {
        DetailAST dotAST = methodCallAst.findFirstToken(TokenTypes.DOT);

        // If there's no DOT token, this is an unqualified call (e.g., someMethod()), so it's local
        if (dotAST == null) {
            return true;
        }

        // If there's a DOT token, check if the qualified class name matches the current class
        DetailAST classNameAST = dotAST.findFirstToken(TokenTypes.IDENT);
        if (classNameAST != null) {
            String calledClassName = classNameAST.getText();
            return calledClassName.equals(getCurrentClassName());
        }

        return false;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of local method references
        log(0, "Total number of local method references: " + localMethodReferences);

        // Reset for the next file
        localMethodReferences = 0;
        setCurrentClassName("");
    }

    // Getter method for testing purposes
    public int getLocalMethodReferences() {
        return localMethodReferences;
    }

	public String getCurrentClassName() {
		return currentClassName;
	}

	public void setCurrentClassName(String currentClassName) {
		this.currentClassName = currentClassName;
	}
}
