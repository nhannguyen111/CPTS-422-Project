package My422Project;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NumOfExternalMethodReferencesCheck extends AbstractCheck {

    private int externalMethodReferences = 0; // Tracks the number of external method references
    private String currentClassName = "";     // Holds the current class name for reference

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
            // Check if this method call is on an external class
            if (isExternalMethodReference(ast)) {
                externalMethodReferences++;
            }
        }
    }

    private boolean isExternalMethodReference(DetailAST methodCallAst) {
        DetailAST dotAST = methodCallAst.findFirstToken(TokenTypes.DOT);

        // If there's a DOT token, it implies this is a qualified method call (e.g., SomeClass.someMethod())
        if (dotAST != null) {
            DetailAST classNameAST = dotAST.findFirstToken(TokenTypes.IDENT);
            if (classNameAST != null) {
                String calledClassName = classNameAST.getText();
                return !calledClassName.equals(getCurrentClassName());
            }
        }
        // If no DOT token, assume it's an internal method call
        return false;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Log the total number of external method references
        log(0, "Total number of external method references: " + externalMethodReferences);

        // Reset for the next file
        externalMethodReferences = 0;
        setCurrentClassName("");
    }

    // Getter method for testing purposes
    public int getExternalMethodReferences() {
        return externalMethodReferences;
    }

	public String getCurrentClassName() {
		return currentClassName;
	}

	public void setCurrentClassName(String currentClassName) {
		this.currentClassName = currentClassName;
	}
}
