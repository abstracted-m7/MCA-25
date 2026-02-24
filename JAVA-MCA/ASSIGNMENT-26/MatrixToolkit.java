import java.util.Scanner;

public class MatrixToolkit {

    //main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Define first matrix
        System.out.println("--- Matrix A ---");
        int[][] matrixA = inputMatrix(sc);

        // Define second matrix for addition
        System.out.println("\n--- Matrix B (for Addition) ---");
        int[][] matrixB = inputMatrix(sc);

        // 1. Matrix Addition
        int[][] sumResult = addMatrices(matrixA, matrixB);
        if (sumResult != null) {
            System.out.println("\nResult of Addition (A + B):");
            displayMatrix(sumResult);
        }

        // 2. Transpose of Matrix A
        int[][] transposeA = transpose(matrixA);
        System.out.println("\nTranspose of Matrix A:");
        displayMatrix(transposeA);

        // 3. Diagonal Sum of Matrix A
        Integer diagSum = calculateDiagonalSum(matrixA);
        if (diagSum != null) {
            System.out.println("\nSum of Diagonal Elements (Matrix A): " + diagSum);
        } else {
            System.out.println("\nDiagonal Sum: Not a square matrix.");
        }

        sc.close();
    }

    // input values in matrix
    public static int[][] inputMatrix(Scanner sc) {
        System.out.print("Enter rows and columns: ");
        int r = sc.nextInt();
        int c = sc.nextInt();
        int[][] matrix = new int[r][c];
        System.out.println("Enter elements:");
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        return matrix;
    }

    //addition matix A and B
    public static int[][] addMatrices(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("Error: Matrices must have same dimensions for addition.");
            return null;
        }
        int[][] res = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        return res;
    }

    // Transpose the matrix A
    public static int[][] transpose(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        int[][] temp = new int[c][r]; // Swapping dimensions
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }

    public static Integer calculateDiagonalSum(int[][] matrix) {
        if (matrix.length != matrix[0].length) return null;
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i]; // Using i,i for the main diagonal
        }
        return sum; // Returns Integer wrapper
    }

    public static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + "\t");
            }
            System.out.println();
        }
    }
}