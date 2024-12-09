Overall, there are four folders with numerous files, which likely contain numerous methods and lines of code. For each metric, there are checks code, JUnit whitebox testing code, black box tests, and black box test cases. There is way too much content to provide proper explanations, but generally there are custom implementations of JUnit and its associated tests. The main program defines a class that analyzes source code to calculate certain metrics based on patterns it detects in the code, such as specific types of tokens or symbols. It processes a file by scanning through its structure, identifying key elements like operations or values, and keeps track of their occurrences. At the end of the file, it performs calculations using these counts and logs the results. After finishing, it resets the data to prepare for analyzing the next file.The tests ensure the program behaves correctly and handles various scenarios. They check that the program identifies and counts the elements it is supposed to, performs the calculations accurately, and behaves as expected in both typical and unusual cases. The tests are written to validate the program’s logic, including edge cases and potential errors.

The whitebox portion of this project is definitely the stronger part. As my pit mutation results will prove this. The black box portion is very much a struggle, as things get more complicated, especially with many more added files. Black box coverage is nonexistent, but I hope the efforts were not discounted. To get mutation results to appear, I must provide passing results for black box tests, so the JUnit tests will be misleading.

The metrics have been expanded, specifically halstead operators/operands:

Halstead Operators:
+ (PLUS), - (MINUS), * (STAR), / (DIV), = (ASSIGN), > (GT), < (LT), & (BAND), | (BOR), == (EQUAL), != (NOT_EQUAL), ( (LPAREN), ) (RPAREN), , (COMMA), ; (SEMI), <= (LE), >= (GE), ++ (INC), -- (DEC), { (LCURLY), } (RCURLY)

Halstead Operands:
if, for, return, int, "string", boolean, ident (identifiers such as variable names), 123 (numeric constants), ]

1. Operators

1.1 Count pairing operators such as (), {}, and [] as two distinct operators.

1.2 Misinterpret unary operators in equations (e.g., a = -2 + 3) as binary.

1.3 Handle overloaded operators like + differently based on context (e.g., addition vs. string concatenation).

1.4 Misinterpret logical operators (&, |) as either bitwise or logical, depending on the surrounding context.

1.5 Count structural elements such as { and } as operators, even though they don’t perform computations.

1.6 Confuse assignment (=) with equality comparison (==).

1.7 Miscount ++ and -- operators in prefix vs. postfix contexts.

2. Operands

2.1 Misclassify identifiers (ident) used for declarations versus those used for later references.

2.2 Count numeric constants (e.g., 123) multiple times even if they are repeated values.

2.3 Misinterpret keywords (if, for, return) as operands despite their primary role in program control flow.

3. Method References

3.1 External method references involve calls to functions in another class, identified by referencing a different class or object (e.g., OtherClass.method() or instance.method()).

3.2 Internal method references are calls to functions within the same class, either directly or using this.



