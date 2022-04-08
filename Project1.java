/**
 *  Name:   Grecia Alvarado
 *  Course: CS3010-01
 *  Due:    October 8th, 2021
 */

 import java.util.Scanner;
 import java.util.Arrays;
 import java.lang.Math;

public class Project1 {
    
    public static void main(String[] args){

        double n;
        double ratios[] = new double[10],
               greatest[] = new double[10];
        double matrix[][] = new double[10][11];

        Scanner read = new Scanner(System.in);

        System.out.println("\n~~Guassian Elimination with Scaled Partial Pivoting~~");

        //read number of equations
        System.out.println("\nHow many equations will you be working with?");
        n = read.nextDouble();

        if(n>10){
        System.out.println("The maximum possible equations is 10. Please enter a number up to 10");
        n = read.nextDouble();
        }

        System.out.println("Enter coefficients for each row including b value for " + n
        + " equations");

        //read original matrix from user
        for(int row=0; row<n; row++){
            for(int value=0; value <n+1; value++){
                matrix[row][value] = read.nextDouble();
                //assign temporary values for greatest (holds dividends)
                greatest[row] = matrix[row][0];
            }
        }

        //find largest absolute value for each row to use in scale ratios
        for(int row = 0; row< n; row++){
            for(int val = 0; val<n-1; val++){
                if(Math.abs(greatest[row]) < Math.abs(matrix[row][val+1]))
                    greatest[row] = Math.abs(matrix[row][val+1]);
            }
        }
        
        //Guassian elimination:
        for(int i = 0; i<n; i++){
            ratios = scaleRatios(i, n, matrix, greatest);
            matrix = GuassianElimination(i, n, matrix, ratios);
        }

        //backend substitution:
        double[] xValues = new double[11];
        int size = (int)n;
        for(int secondLast = size-1; secondLast >= 0; secondLast--){
                xValues[secondLast] = matrix[secondLast][size];
            for(int last = secondLast+1; last<size; last++){
                xValues[secondLast] -= matrix[secondLast][last] * xValues[last];
            }
                xValues[secondLast] = xValues[secondLast]/matrix[secondLast][secondLast];
        }
        
        //print out values:
        for(int place=0; place<n; place++)
            System.out.println("x" + (place+1) + "=" + Math.round(xValues[place]*100)/100);

        read.close();

    }
    
    public static double[][] pivotAndSwap(int iteration, double numRows, double ratios[], double matrix[][]){
        double compare = ratios[iteration];
        int place = iteration;
        for(int i=iteration; i<numRows; i++){
            if(compare < ratios[i]){
            compare = ratios[i];
            place = i;
            }
        }
        System.out.println("Pivot row: " + (place + 1) + " swapped to row " + (iteration + 1));

        //swap matrix
        double tmp[] = matrix[iteration];
        matrix[iteration] = matrix[place];
        matrix[place] = tmp;

        return matrix;
    }

    //find and display ratios
    public static double[] scaleRatios(int iteration, double numRows, double[][] matrix, double[] greatest){
        double ratios[] = new double[10];
        for(int row=iteration; row<numRows-iteration; row++){
            ratios[row] = Math.round((Math.abs((matrix[row][iteration])/Math.abs(greatest[row])))*100.0)/100.0;
        }

        //print ratios:
        System.out.print("\nScale ratios for iteration " + iteration + ": [");
        for(int row=iteration; row<numRows; row++){
            System.out.print(ratios[row] + " ");
        }
        System.out.println("]");
        return ratios;
    }

    //guassian elimination and display intermediate matrices
    public static double[][] GuassianElimination(int iteration, double numRows, double[][] matrix, double[] ratios){
        double matrix2[][] = new double[10][11];
        matrix2 = pivotAndSwap(iteration, numRows, ratios, matrix);
        double original[][] = new double[10][11];
        original = matrix2;
        System.out.print("Multipliers: [");
        for(int row=iteration+1; row<numRows; row++){
            double value = (matrix2[row][iteration]/matrix2[iteration][iteration]);

            System.out.print(" " + Math.round(value*100.0)/100.0);
            for(int row2=iteration; row2<numRows+1; row2++){
                matrix2[row][row2] -= value * original[iteration][row2];
            }
        }
        System.out.println("]");

        for(int row=0; row<numRows; row++){
            System.out.println();
            System.out.print("[");
            for(int column=0; column<numRows+1; column++){
                if(column == numRows)
                System.out.print("[ ");
                System.out.print(Math.round((matrix2[row][column])*100.0)/100.0 + " ");
                if(column == numRows-1 || column == numRows)
                System.out.print("]");
            }
        }
        System.out.println();

        return matrix2;
    }
}
