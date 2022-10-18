/* 
 * This class provides the template for a Matrix object.
 */

public abstract class Matrix {
    /**
     * Two variables to describe the dimensions of the Matrix.
     */
    protected int m, n;

    /**
     * Constructor function. This is protected since abstract classes cannot
     * be instantiated anyway. 
     *
     * @param M  The 'row'' dimension of the matrix.
     * @param N  The 'column' dimension of the matrix.
     */
    protected Matrix(int M, int N) {
        m = M; n = N;
    }

    /**
     * Returns a String representation of the Matrix using the getIJ getter
     * function.
     *
     * @return A String representation of the Matrix.
     */
    public String toString() {
        String s = "";
        for(int i = 0; i < m; i++){
            for(int j = 0; j <n; j++){
                s += String.format( "%.3f", getIJ(i, j))+"/t";
            }
            s += "/n";
        }
        return s;
    }

    /**
     * Getter function: return the (i,j)th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)th entry of the matrix.
     */
    public abstract double getIJ(int i, int j);

    /**
     * Setter function: set the (i,j)th entry of the data array.
     *
     * @param i    The location in the first coordinate.
     * @param j    The location in the second coordinate.
     * @param val  The value to set the (i,j)th entry to.
     */
    public abstract void setIJ(int i, int j, double val);

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public abstract double determinant();

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public abstract Matrix add(Matrix A);

    /**
     * Multiply the matrix by another matrix A. This is a 'left' product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public abstract Matrix multiply(Matrix A);

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public abstract Matrix multiply(double a);

    /**
     * Fills the matrix with random numbers which are uniformly distributed
     * between 0 and 1.
     */
    public abstract void random();
}
