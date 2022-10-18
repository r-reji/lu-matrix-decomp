/* 
 * Contains a template for a TriMatrix object. This will be a matrix which 
 * is populated by zeroes except on the leading diagonal and the two off-diagonals.
 */

import java.util.Random;

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diag;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upper;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lower;

    /**
     * Constructor function initialises m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param N  The dimension of the array.
     */
    public TriMatrix(int N) {
        super(N,N);
        if (N < 1){
            throw new MatrixException("Matrix must be at least 1x1");
        }
        diag = new double[N];
        upper = new double[N-1];
        lower = new double[N-1];
    }

    /**
     * Getter function: return the (i,j)th entry of the matrix.
     *
     * @param i  The location in the first coordinate.
     * @param j  The location in the second coordinate.
     * @return   The (i,j)th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        /*
        First I added an exception for incorrect indexing and then used a series
        of 'if' statements to determine which number to get.
        */
        if (i >= diag.length || j >= diag.length) {
            throw new MatrixException("Indexing is Out of Bounds");
        }
        double value = 0.0;
        if (i == j) {
            value = diag[i];
        }else if(i-1 == j){
            value = lower[j];
        }else if(i+1 == j){
            value = upper[i];
        }else{
            value = 0.0;
        }
        return value;
    }

    /**
     * Setter function for the (i,j)th entry of the data array.
     *
     * @param i    The location in the first coordinate.
     * @param j    The location in the second coordinate.
     * @param val  The value to set the (i,j)th entry to.
     */
    public void setIJ(int i, int j, double val) {

        if (i >= diag.length || j >= diag.length) {
            throw new MatrixException("Indexing is Out of Bounds");
        }
        if(i == j) {
            diag[i] = val;
        } else if(i + 1 == j) {
            upper[i] = val;
        } else if (i - 1 == j) {
            lower[i] = val;
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        TriMatrix decomposed = this.decomp();
        double result = 1.0;
        for(int i =0; i < diag.length; i++){
            result *= decomposed.diag[i];
        }
        return result;
    }

    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     *
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix decomp() {
        // Set a new TriMatrix to maintain the first diagonal then compute the rest
        // of the decomposed values.
        TriMatrix decomposed = new TriMatrix(diag.length);
        decomposed.diag[0] = diag[0];
        for(int i = 0; i < upper.length; i++){
            decomposed.upper[i] = upper[i];
        }
        for(int j = 0; j < lower.length; j++){
            decomposed.lower[j] = lower[j]/decomposed.diag[j];
            decomposed.diag[j+1] = diag[j+1] - (decomposed.lower[j]*decomposed.upper[j]);
        }
        return decomposed;
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A){
        if(A.m != diag.length || A.n != diag.length){
            throw new MatrixException("Can't add these matricies");
        }
        else if (A instanceof GeneralMatrix){
            return A.add(this);
        }
        Matrix result = new GeneralMatrix(m,n);
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n ; j++){
                result.setIJ(i,j,(getIJ(i,j) + A.getIJ(i,j)));
            }
        }
        return result;
    }

    /**
     * Multiply the matrix by another matrix A. This is a left product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        if(diag.length != A.m){
            throw new MatrixException("Can't multiply these matricies");
        }
        double[][] values = new double[diag.length][A.n];
        for(int i = 0; i < diag.length; i++){
            for(int j = 0; j < A.n; j++){
                for(int k = 0; k < n; k++){
                    values[i][j] += getIJ(i,k)*A.getIJ(k,j);
                }
            }
        }
        Matrix result = new GeneralMatrix(diag.length,A.n);
        for(int x = 0; x < diag.length; x++){
            for(int y = 0; y < A.n; y++){
                result.setIJ(x,y,values[x][y]);
            }
        }
        return result;
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {
        Matrix result = new TriMatrix(diag.length);
        for(int i = 0; i < diag.length; i++){
            for(int j = 0; j < diag.length; j++){
                result.setIJ(i,j,(getIJ(i,j)*a));
            }
        }
        return result;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        Random rand = new Random();
        for(int i = 0; i < diag.length; i++){
            diag[i] = rand.nextDouble();
        }
        for(int j = 0; j < diag.length-1; j++){
            lower[j] = rand.nextDouble();
            upper[j] = rand.nextDouble();
        }
    }

    /*
     * Main function with some manual tests.
     */
    public static void main(String[] args) {
        TriMatrix m = new TriMatrix(3);
        m.random();
        Matrix x = new GeneralMatrix(3, 3);
        x.random();
        TriMatrix y = new TriMatrix(4);
        y.diag = new double[] {1, 2, 3, 4};
        y.upper = new double[] {1, 2, 3};
        y.lower = new double[] {3, 2, 1};
        TriMatrix f = y.decomp();
        System.out.println(m);
        System.out.println(x);
        System.out.println(x.add(m));
        System.out.println(y);
        System.out.println(f);
    }
}
