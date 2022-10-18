/* 
 * This class contains a template for a general matrix object using
 * the abstract Matrix.java class.
 */

import java.util.Arrays;
import java.util.Random;

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] data;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param m  The first (row) dimension of the array.
     * @param n  The second (column) dimension of the array.
     */
    public GeneralMatrix(int m, int n) throws MatrixException {
        super(m,n);
        if ( m < 1 || n < 1 ) {
            throw new MatrixException("Matrix dimensions must be positive");
        }
        data = new double [m][n];
    }

    /**
     * This is a copy constructor; it creates a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix A) {
        super(A.m, A.n);
        data = new double[A.m][A.n];
        for(int i = 0; i < A.m; i++){
            for(int j = 0; j < A.n; j++){
                data[i][j] = A.getIJ(i,j);
            }
        }
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
        Needed this bit to throw an exception if the attempted value indexes were
        not in valid positions ie the indexes exceeded the size of the matrix.
        */
        if(i >= 0 && i < m || j >= 0 && j < n){
            return data[i][j];
        }
        else{
            throw new MatrixException("Index Out of Bounds");
        }
    }

    /**
     * Setter function: set the (i,j)th entry of the data array.
     *
     * @param i    The location in the first coordinate.
     * @param j    The location in the second coordinate.
     * @param val  The value to set the (i,j)th entry to.
     */
    public void setIJ(int i, int j, double val) {
        if(i >= 0 && i < m || j >= 0 && j < n){
            data[i][j] = val;
        }
        else{
            throw new MatrixException("Index Out of Bounds");
        }
    }

    /**
     * Return the determinant of this matrix. 
     * 
     * Note: det(A) = det(LU) = det(L)det(U) where L is lower triangular
     * with leading diagonal consisting of 1s and U is upper triangular.
     * Hence det(A) = det(U) = product of diagonal elements of U.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        double[] x = new double[1];
        GeneralMatrix decomposed = this.decomp(x);
        double det = 1.0;
        for(int i = 0; i < m; i++){
            det *= decomposed.getIJ(i,i);
        }
        return det*x[0];
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {
        if(A.m != this.m || A.n != this.n){
            throw new MatrixException("The matricies are of different dimensions so cannot be summed.");
        }
        Matrix result = new GeneralMatrix(this.m, this.n);
        for(int i = 0; i < this.m; i++){
            for(int j = 0; j < this.n; j++){
                result.setIJ(i,j,(this.getIJ(i,j) + A.getIJ(i,j)));
            }
        }
        return result;
    }

    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {  
        if(this.n != A.m){
            throw new MatrixException("The matrices are of incompatible dimensions for multiplication.");
        }
        double [][] values = new double[m][A.n];
        for(int i = 0; i < this.m; i++){
            for(int j = 0; j < A.n; j++){
                for(int k = 0; k < n; k++){
                    values[i][j] += this.getIJ(i,k) * A.getIJ(k,j);
                }
            }
        }
        Matrix result = new GeneralMatrix(this.m, A.n);
        for(int x = 0; x < this.m; x++){
            for(int y = 0; y < A.n; y++){
                result.setIJ(x,y,(values[x][y]));
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
        Matrix result = new GeneralMatrix(this.m, this.n);
        for(int i = 0; i < this.m; i++){
            for(int j = 0; j < this.n; j++){
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
        for(int i = 0; i <this.m; i++){
            for(int j = 0; j < this.n; j++){
                this.setIJ(i, j, rand.nextDouble());
            }
        }
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     *
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 l_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant will need to multiply by the value of
     * d[0] calculated by the function.
     *
     * If the matrix is singular then the routine throws a MatrixException.
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C".
     *
     * @param d  An array of length 1. On exit, the value contained in here
     *           will either be 1 or -1, which will be used to calculate the
     *           correct sign on the determinant.
     * @return   The LU decomposition of the matrix.
     */
    public GeneralMatrix decomp(double[] d) {
        if (n != m)
            throw new MatrixException("Matrix is not square");
        if (d.length != 1)
            throw new MatrixException("d should be of length 1");

        int           i, imax = -10, j, k;
        double        big, dum, sum, temp;
        double[]      vv   = new double[n];
        GeneralMatrix a    = new GeneralMatrix(this);

        d[0] = 1.0;

        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++)
                if ((temp = Math.abs(a.data[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }

        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a.data[imax-1][k-1];
                    a.data[imax-1][k-1] = a.data[j-1][k-1];
                    a.data[j-1][k-1] = dum;
                }
                d[0] = -d[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.data[j-1][j-1] == 0.0)
                a.data[j-1][j-1] = 1.0e-20;
            if (j != n) {
                dum = 1.0/a.data[j-1][j-1];
                for (i = j+1; i <= n; i++)
                    a.data[i-1][j-1] *= dum;
            }
        }
        return a;
    }

    /*
     * Main function that contains some manual tests.
     */
    public static void main(String[] args) {
        GeneralMatrix x = new GeneralMatrix(4, 4);
        x.random();
        x.setIJ(3, 3, 5);
        GeneralMatrix y = new GeneralMatrix(x);
        Matrix j = x.add(y);
        Matrix k = x.multiply(2);
        Matrix l = y.multiply(-1);
        System.out.println(x);
        System.out.println(y);
        System.out.println(j);
        System.out.println(k);
        System.out.println(l);



        Matrix a = new GeneralMatrix(2,2);
		a.setIJ(0,0,1);
		a.setIJ(0,1,2);
		a.setIJ(1,0,3);
		a.setIJ(1,1,1);
		Matrix b = new GeneralMatrix(2,2);
		b.setIJ(0,0,3);
		b.setIJ(0,1,0);
		b.setIJ(1,0,-2);
		b.setIJ(1,1,9);
		Matrix aCopy = new GeneralMatrix((GeneralMatrix)a);
		//Tests the getIJ and setIJ functions
		System.out.println("Get 0,0 value of matrix A: "+a.getIJ(0,0)+"\n");
		//Tests the toString() function
		System.out.println("Matrix A: \n"+a.toString());
		System.out.println("Matrix B: \n"+b.toString());
		System.out.println("Matrix ACopy: \n"+aCopy.toString());
		//Tests the determinant() function
		System.out.println("Determinant of A is: "+a.determinant()+"\n");
		System.out.println("Determinant of B is: "+b.determinant()+"\n");
		//Tests the multiply() function, for both scalar and matrix versions
		Matrix e = a.multiply(b);
		System.out.println("Matrix A*B: \n"+e.toString());
		Matrix d = a.multiply(2);
		System.out.println("Matrix A*2: \n"+d.toString());
		//Tests the add() function
		Matrix c = a.add(b);
		System.out.println("Matrix A+B: \n"+c.toString());
		//Test the random() function
		Matrix f = new GeneralMatrix(2,2);
		f.random();
		System.out.println("Random Matrix: \n"+f.toString());
		//Tests the exceptions
		System.out.println("Trying to premultiply 3x3 matrix with 4x10 matrix: ");
		try{
			Matrix a2 = new GeneralMatrix(4,10);
			a2.random();
			Matrix b2 = new GeneralMatrix(3,3);
			b2.random();
			Matrix c2 = b2.multiply(a2);
		}catch (MatrixException except){
			System.out.println(except.getMessage());
		}
		
		System.out.println("\nTrying to add 10x10 matrix to 3x3 matrix: ");
		try{
			Matrix a2 = new GeneralMatrix(10,10);
			a2.random();
			Matrix b2 = new GeneralMatrix(3,3);
			b2.random();
			Matrix c2 = b2.add(a2);
		}catch (MatrixException except){
			System.out.println(except.getMessage());
		}		
		
		System.out.println("\nTrying to find determinant of 5x3 matrix: ");
		try{
			Matrix a2 = new GeneralMatrix(5,3);
			a2.determinant();
		}catch (MatrixException except){
			System.out.println(except.getMessage());
		}	
		
		System.out.println("\nTrying to find determinant of singular 2x2 matrix: ");
		try{
			Matrix a2 = new GeneralMatrix(2,2);
			a.setIJ(0,0,1);
			a.setIJ(0,1,1);
			a.setIJ(1,0,0);
			a.setIJ(1,1,0);		
			a2.determinant();
		}catch (MatrixException except){
			System.out.println(except.getMessage());
		}	
		
		System.out.println("\nTrying to Create Matrix with one dimension zero ");
		try{
			Matrix a3 = new GeneralMatrix(0,9);	
		}catch (MatrixException except){
			System.out.println(except.getMessage());
		}



    }
}
