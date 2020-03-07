import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Function {
    private float[] coefficients;
    private int degree;

    public Function(File inputFile){
        try {
            Scanner fileScanner = new Scanner(inputFile);
            degree = Integer.parseInt(fileScanner.next());
            coefficients = getCoeffArray(degree+1, fileScanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public float getValue(float inputValue){
        return calculateValue(inputValue, coefficients, degree);
    }

    public float getDerivativeValue(float inputValue){
        return calculateDerivativeValue(inputValue, coefficients, degree);
    }

    private float calculateValue(float inputValue, float[] coefficients, int degree){
        float result = 0.0f;
        int index = coefficients.length - 1;

        for(int i = degree; i >= 0; i--, index--){
            float currentCoeff = 0.0f;

            if(index > -1) {
                currentCoeff = coefficients[index];
            }

//            System.out.printf("Current Calculation: %f * %f^%d = %f\n", currentCoeff, a, i, currentCoeff * Math.pow(a, i));
            result += (currentCoeff * Math.pow(inputValue, i));
//            System.out.printf("Current result: %f\n", result);
        }

//        System.out.println(result);

        return result;
    }

    private float calculateDerivativeValue(float inputValue, float[] coefficients, int degree){
        float[] dCoefficients = new float[degree];
        int d = degree;

        // Populate dCoefficients
        for(int i = coefficients.length-1; i > 0; i--){
            dCoefficients[i-1] = coefficients[i]*d;
            d--;
        }

        return calculateValue(inputValue, dCoefficients, degree-1);
    }

    private float[] getCoeffArray(int size, Scanner fileScanner){
        float[] coefficients = new float[size];

        for(int i = coefficients.length-1; i >= 0; i--){
            float current = Float.parseFloat(fileScanner.next());
//            System.out.printf("Adding %f to coefficients array.\n", current);
            coefficients[i] = current;
        }

        return coefficients;
    }

    @Override
    public String toString() {
        String polynomial = "";
        int index = coefficients.length - 1;

        for(int i = degree; i >= 0; i--, index--){
            float currentCoeff = 0.0f;

            if(index > -1) {
                currentCoeff = coefficients[index];
            }

            polynomial += String.format("(%f)x^%d + ", currentCoeff, i);
        }

        polynomial = polynomial.substring(0, polynomial.length()-3) + "\n";

        return polynomial;
    }
}
