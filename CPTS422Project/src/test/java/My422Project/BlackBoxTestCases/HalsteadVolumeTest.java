package My422Project.BlackBoxTestCases;

public class HalsteadVolumeTest {
 public static void main(String[] args) {
     int a = 5;
     int b = 10;
     int c = a + b;
     System.out.println(c);
 }
}

//Operators: =, +, (), println
//Operands: a, b, c, 5, 10, args
//Total Operators (N1): 6  ( =, =, +, (), (), println)
//Total Operands (N2): 7   (a, b, c, 5, 10, args, c)
//Unique Operators (n1): 4 (=, +, (), println)
//Unique Operands (n2): 6 (a, b, c, 5, 10, args)
//N = N1 + N2 = 6 + 7 = 13
//n = n1 + n2 = 4 + 6 = 10
//Volume = N * log2(n) = 13 * log2(10) ≈ 13 * 3.32 ≈ 43.16

//Expected Volume: 43.16
