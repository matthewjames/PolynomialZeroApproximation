import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        int maxIterations = 10000;
        float point1, point2, epsilon = 0.0000000001f;

        String inFilename = args[args.length-1];
        String outFilename = inFilename.substring(0, inFilename.length()-3) + "sol";
        String root = System.getProperty("user.dir") + "\\src\\";
        String inPath = root + inFilename;
        File file = new File(inPath);
        Function function = new Function(file); // Create a function with the input file
        Methods methods = new Methods(function);

        System.out.println("Input file: " + inFilename);
        System.out.println("Equation: " + function.toString());

        switch(args[0]){
            case "-newt":
                System.out.println("Using Newton's method...");

                // Newton's method
                // check for -maxIter flag @ index 1
                if(args[1].equals("-maxIter")){
                    maxIterations = Integer.parseInt(args[2]);

                    // store inital point
                    point1 = Float.parseFloat(args[3]);
                } else {
                    point1 = Float.parseFloat(args[1]);
                }
                System.out.println("x_0 = " + point1);

                // run method
                methods.newtons(point1, maxIterations, epsilon);

                break;

            case "-sec":
                System.out.println("Using Secant method...");

                // Secant method
                // check for -maxIter flag @ index 1
                if(args[1].equals("-maxIter")){
                    maxIterations = Integer.parseInt(args[2]);

                    // store initial points
                    point1 = Float.parseFloat(args[3]);
                    point2 = Float.parseFloat(args[4]);
                } else {
                    point1 = Float.parseFloat(args[1]);
                    point2 = Float.parseFloat(args[2]);
                }

                System.out.printf("Interval: [%f, %f]\n", point1, point2);

                // run method
                methods.secant(point1, point2, maxIterations, epsilon);

                break;

            case "-hybr":
                System.out.println("Using Hybrid method...");

                // Hybrid method
                // check for -maxIter flag @ index 0
                if(args[1].equals("-maxIter")){
                    maxIterations = Integer.parseInt(args[2]);

                    // store initial points
                    point1 = Float.parseFloat(args[3]);
                    point2 = Float.parseFloat(args[4]);
                } else {
                    point1 = Float.parseFloat(args[1]);
                    point2 = Float.parseFloat(args[2]);
                }

                System.out.printf("Interval: [%f, %f]\n", point1, point2);

                // run method
                methods.hybrid(point1, point2, maxIterations, epsilon);

                break;

            default:
                System.out.println("Using Bisection method...");

                // Bisection method
                // check for -maxIter flag @ index 0
                if(args[0].equals("-maxIter")){
                    maxIterations = Integer.parseInt(args[1]);

                    // store initial points
                    point1 = Float.parseFloat(args[2]);
                    point2 = Float.parseFloat(args[3]);
                } else {
                    point1 = Float.parseFloat(args[0]);
                    point2 = Float.parseFloat(args[1]);
                }

                System.out.printf("Interval: [%f, %f]\n", point1, point2);

                // run method
                methods.bisection(point1, point2, maxIterations, epsilon);
        }

        methods.writeResults(root + outFilename);
//        System.out.printf("Result: %f", Bisection.execute(degree, coefficients, 0,1,50, 0.001f));
//        System.out.printf("Result: %f", methods.newtons(2, 10000,0.0000001f, 0.000001f));
    }
}
