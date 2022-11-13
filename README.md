# LU Matrix Decomposition and Analysis

#### This repository contains code I wrote for a Java Programming module I took in 2019. Assuming you have all the necessary dependencies installed, you should be able to download the repo and run it.

[DeterminantDistribution.java](https://github.com/r-reji/matrixLUDecomp/blob/main/src/DeterminantDistribution.java) is the main file in this repository, information on all other files can be found at the bottom.

This class performs analysis on the variance distribution of the determinants of matrices. The matrices in question are random with values uniformly distributed between (0,1). Determinants are computed using LU decomposition - a detailed explanation of the mathematics is available commented within the respective files. The method is an adaptation of one found in the book [Numerical Recipes in C](https://www.amazon.co.uk/Numerical-Recipes-3rd-Scientific-Computing-dp-0521880688/dp/0521880688/ref=dp_ob_title_bk).

This analysis is performed on a per-size basis on square matrices of n = 2 up to n = 50. The resulting data can be found in [variance.data](https://github.com/r-reji/matrixLUDecomp/blob/main/variance.data). This data was then used to generate the following graphs in MATLAB:

![graphs](https://user-images.githubusercontent.com/112977394/196675926-e34aef00-5fa8-43ee-bca6-c39569b14494.png)

- [MatrixException.java](https://github.com/r-reji/matrixLUDecomp/blob/main/src/MatrixException.java)
   - Defines an exception type that will be used throughout to handle errors.
- [Matrix.java](https://github.com/r-reji/matrixLUDecomp/blob/main/src/Matrix.java)
   - Provides a template for a Matrix object as an abstract class
- [GeneralMatrix.java](https://github.com/r-reji/matrixLUDecomp/blob/main/src/GeneralMatrix.java)
   - Implements all the necessary functions for general matrix operations
   - Implements the LU decomposition algorithm
- [TriMatrix.java](https://github.com/r-reji/matrixLUDecomp/blob/main/src/TriMatrix.java)
   - Implements a method to generate a tri-diagonal matrix using the decomposition algorithm defined in the previous class
   - Defines the methods for all operations on a tri-diagonal matrix including computing the determinant
