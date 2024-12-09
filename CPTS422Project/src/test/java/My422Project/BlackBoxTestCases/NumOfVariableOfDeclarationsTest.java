package My422Project.BlackBoxTestCases;

public class NumOfVariableOfDeclarationsTest {

    // Test Case 1: Explicit variable declaration
    public void explicitDeclaration() {
        int a = 10;  // 1 variable declaration
        String b = "Hello";  // 1 variable declaration
        double c = 3.14;  // 1 variable declaration
    }

    // Test Case 2: Implicit variable declaration (var in Java 10+)
    public void implicitDeclaration() {
        var x = 42;  // 1 variable declaration
        var y = "World";  // 1 variable declaration
        var z = 2.718;  // 1 variable declaration
    }

    // Test Case 3: Shadowed variables (declared again in an inner scope)
    public void shadowedVariables() {
        int a = 5;  // 1 variable declaration
        {
            int aa = 10;  // 1 variable declaration (shadowing outer 'a')
            System.out.println(a);
        }
        System.out.println(a);
    }

    // Test Case 4: Loop variable declarations
    public void loopVariableDeclarations() {
        for (int i = 0; i < 5; i++) {  // 1 variable declaration
            int j = i * 2;  // 1 variable declaration (per iteration)
        }
    }

    // Test Case 5: Lambda implicit parameters (dynamic types in lambda)
    public void lambdaImplicitParameters() {
        Runnable r = () -> {
            int a = 10;  // 1 variable declaration
            var b = 20;  // 1 variable declaration
        };
        r.run();
    }

    /*
    public static void main(String[] args) {
        VariableDeclarationTest test = new VariableDeclarationTest();
        test.explicitDeclaration();
        test.implicitDeclaration();
        test.shadowedVariables();
        test.loopVariableDeclarations();
        test.lambdaImplicitParameters();
    }*/
}

/*
Expected Results:
-----------------
Test Case 1: 3 variable declarations (a, b, c)
Test Case 2: 3 variable declarations (x, y, z)
Test Case 3: 2 variable declarations (outer 'a', inner 'a')
Test Case 4: 2 variable declarations (i, j)
Test Case 5: 2 variable declarations (a, b)

Total: 12 variable declarations
*/
