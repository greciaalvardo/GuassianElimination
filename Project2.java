import java.util.Scanner;
import java.io.*;

public class Project2
{  
        public static void main(String args[])
        {

                double matrix[][] = new double[10][11];
                double start[] = new double[10];
                int n, method;

                Scanner read = new Scanner(System.in);

                //get number of equations to be worked with
                System.out.println("How many equations will you be working with? ");
                n = read.nextInt();
                
                //make sure user does not give over 10 equations
                if(n > 10){
                        System.out.println("Cannot be more than 10 equations. Enter number of equations:");
                        n = read.nextInt();
                }
                
                //prompt user to choose method to read matrix
                System.out.println("Would you like to:");
                System.out.println("1.Enter Coefficients from command line");
                System.out.println("2.Read from file");
                method = read.nextInt();
                
                if(method==1){
                 System.out.println("Enter diagonally dominant matrix:");
                    for(int row=0; row<n ;row++) {
                     for(int column=0; column<=n; column++){
                         matrix[row][column]=read.nextDouble();
                     }
                    }
                } else if(method == 2){
                                System.out.println("Enter name of file containing diagonally dominant matrix:");
                                try{
                                    read.nextLine();
                                    String fName;
                                    fName = read.nextLine();
                                    Scanner readFile = new Scanner(new FileReader(fName));

                                    //read matrix from file
                                        for(int row = 0; row<n; row++){
                                         for(int column=0; column<n+1; column++){
                                             matrix[row][column] = readFile.nextInt();
                                         }
                                        }
                                        readFile.close();
                                }
                                catch(Exception e){
                                        System.out.println("Error opening file. Please restart and try again.");
                                      //  return;
                                }
                            }

                //obtain desired error
                System.out.println("What is your desired stopping error?");
                double eVal = read.nextDouble();
                //obtain starting solution:
                System.out.println("Enter starting solutions to be applied to both Jacobi and Gauss-Seidel methods: ");
                for(int i=0; i<n; i++)
                {
                        start[i] = read.nextDouble();
                }

                read.close();
                
                
                //need to make a second array to hold starting values in order to work with both methods
                double start2[]  = new double[n];
                for(int i=0; i<n; i++)
                 start2[i] = start[i];

                //use jacobi method
                System.out.println("\nUsing JACOBI METHOD:");
                jacobiMethod(matrix,start2,eVal, n);
                
                //use gauss-seidal method
                System.out.println("\n\nUsing GUASS-SEIDEL:");
                //set starting values to original
                for(int i=0; i<n; i++)
                 start2[i] = start[i];

                gaussSeidel(matrix,start2,eVal, n);

        }

        static void jacobiMethod(double matrix[][], double val[],double eVal, int n)
         {
                 double norm = 0;
                 double temp[] = new double[n];
                 int iteration = 1;
                 double sum = 0;
                 double temp2, eVal2;
                 
                 while(iteration<=50){
                         for(int column=0; column<n; column++){
                             for(int row=0; row<n; row++){
                                if(column!=row)
                                 sum += matrix[column][row]*val[row];
                             }
                                 temp2 = (matrix[column][n] - sum)/matrix[column][column];
                                 
                                 eVal2 = Math.abs((val[column]-temp2)/temp2);
                                 
                                 if(eVal2 > norm) 
                                    norm = eVal2;
                                 temp[column] = temp2;
                                 sum = 0.0;
                         }
                         
                         for(int i=0; i<n; i++){
                                 val[i] = temp[i];
                         }
                         //print value of coefficents at current iteration:
                         System.out.println("Iteration " +iteration+ ":");
                         System.out.print("[");
                         for(int i=0; i<n; i++){
                                 System.out.print(String.format("%.4f", val[i])+" ");
                         }
                         System.out.print("]");
                         System.out.println("\nCurrent error: " +String.format("%.4f", norm)+"\n");
                          
                         if(norm < eVal) 
                            return;

                         iteration++;
                         norm=0;
                 }
                 System.out.println("Error not acheived by 50 iterations.");
         }


         static void gaussSeidel(double matrix[][], double val[], double eVal, int n)
         {
                 double eVal2,
                        temp,
                        norm = 0,
                        sum = 0;
                 int iteration = 1;

                 while(iteration <= 50){
                         //for every row 
                         for(int i=0; i<n; i++){
                            for(int j=0; j<n; j++){
                             if(i!=j)
                                 sum = sum + matrix[i][j]*val[j];
                            }
                            temp = (matrix[i][n] - sum)/matrix[i][i];  
                            eVal2 = Math.abs((val[i]-temp)/temp);
                                 
                            if(eVal2 > norm) 
                             norm = eVal2;
                             val[i] = temp;
                             sum = 0;
                         }

                         System.out.println("Iteration " +iteration+ ":");
                         System.out.print("[");
                         for(int i=0; i<n;i++){
                                 System.out.print(String.format("%.4f", val[i])+" ");
                         }
                         System.out.println("]");
                         System.out.println("Current error: " +String.format("%.4f", norm)+"\n");

                         if(norm < eVal)
                            return;

                         iteration++;
                         norm=0;
                 }
                 System.out.println("Error not acheived by 50 iterations.");
         }
 
 
}