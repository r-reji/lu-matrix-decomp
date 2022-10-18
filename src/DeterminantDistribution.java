/* 
 * Class contains the methods to complete the determinant analysis.
 * The methods will calculate the variance of the distribution of the 
 * determinants of random uniformly distributed matrices.
 * 
 */

public class DeterminantDistribution {
    /* 
     * Calculates the variance of the distribution defined by the determinant.
     *  
     * @param m The matrix object that will be filled with random numbers.
     * 
     * @return The variance of the distribution.
     */
    public static double matVariance(Matrix m, int numSamples) {
        double[] determinants = new double[numSamples];
        double determinantSum = 0.0;
        double determinantSumSQ = 0.0;
        for(int i = 0; i < numSamples; i++){
            m.random();
            determinants[i] = m.determinant();
            determinantSum += determinants[i];
            determinantSumSQ += Math.pow(determinants[i],2);
        }
        double var = (determinantSumSQ/numSamples)-Math.pow(determinantSum/numSamples,2);
        return var;
    }
    
    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50.
     */
    public static void main(String[] args) {
        DeterminantDistribution p = new DeterminantDistribution();
        int normal = 15000;
        int tri = 150000;
        for(int n = 2; n <= 50; n++){
            GeneralMatrix x = new GeneralMatrix(n,n);
            TriMatrix y = new TriMatrix(n);
            double i = p.matVariance(x,normal);
            double j = p.matVariance(y,tri);
            System.out.println(n+"\t"+i+"\t"+j);
        }
    }
}
