import java.io.FileWriter;
import java.io.IOException;

public class Methods {
    private Function function;
    private StringBuilder output;
    private float delta, root;
    private int iterations;
    private boolean isSuccessful;

    public Methods(Function f){
        function = f;
        delta = 0.00001f;
        isSuccessful = false;
        output = new StringBuilder();
    }

    public boolean bisection(float lowerBound, float upperBound, int maxIterations, float epsilon){
        float lowerValue = function.getValue(lowerBound);
        float upperValue = function.getValue(upperBound);
        float zero = 0.0f;

        if(lowerValue * upperValue >= 0){
            System.out.println("Inadequate values for a and b.");
            root = -1;
            iterations = 0;
            isSuccessful = false;
            bufferResults();

            return isSuccessful;
        }

        float error = upperBound - lowerBound;

        for(int i = 1; i <= maxIterations; i++){
            error /= 2;
            zero = lowerBound + error;
            float midValue = function.getValue(zero);

            if(Math.abs(error) < epsilon || midValue == 0){
                System.out.printf("Algorithm has converged after %d iterations!\n", i);
                root = zero;
                iterations = i;
                isSuccessful = true;
                bufferResults();

                return isSuccessful;
            }

            if(lowerValue * midValue < 0){
                upperBound = zero;
                upperValue = midValue;
            } else {
                lowerBound = zero;
                lowerValue = midValue;
            }
        }

        System.out.println("Max iterations reached without convergence...");
        root = zero;
        iterations = -1;
        isSuccessful = false;
        bufferResults();

        return isSuccessful;
    }

    public boolean newtons(float inputValue, int maxIterations, float epsilon){
        float fx = function.getValue(inputValue);

        for(int i = 0; i < maxIterations; i++){
            float fd = function.getDerivativeValue(inputValue);

            if(Math.abs(fd) < delta){
                System.out.println("Small slope!");
                root = inputValue;
                iterations = i;
                isSuccessful = false;
                bufferResults();

                return isSuccessful;
            }

            float d = fx/fd;
            inputValue -= d;
            fx = function.getValue(inputValue);

            if(Math.abs(d) < epsilon){
                System.out.printf("Algorithm has converged after %d iterations!\n", i);
                root = inputValue;
                iterations = i;
                isSuccessful = true;
                bufferResults();

                return isSuccessful;
            }
        }

        System.out.println("Max iterations reached without convergence...");
        root = inputValue;
        iterations = -1;
        isSuccessful = false;
        bufferResults();

        return isSuccessful;
    }

    public boolean secant( float lowerBound, float upperBound, int maxIterations, float epsilon){
        float lowerValue = function.getValue(lowerBound);
        float upperValue = function.getValue(upperBound);

        if (Math.abs(lowerValue) > Math.abs(upperValue)) {
            // swap lower and upper bounds
            float temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;

            // swap f(lowerBound) and f(upperBound)
            temp = lowerValue;
            lowerValue = upperValue;
            upperValue = temp;
        }

        for(int i = 1; i <= maxIterations; i++){
            if (Math.abs(lowerValue) > Math.abs(upperValue)) {
                // swap lower and upper bounds
                float temp = lowerBound;
                lowerBound = upperBound;
                upperBound = temp;

                // swap f(lowerBound) and f(upperBound)
                temp = lowerValue;
                lowerValue = upperValue;
                upperValue = temp;
            }

            float d = (upperBound - lowerBound)/(upperValue - lowerValue);
            upperBound = lowerBound;
            upperValue = lowerValue;
            d *= lowerValue;

            if(Math.abs(d) < epsilon){
                System.out.printf("Algorithm has converged after %d iterations!\n", i);
                root = lowerBound;
                iterations = i;
                isSuccessful = true;
                bufferResults();

                return isSuccessful;
            }

            lowerBound -= d;
            lowerValue = function.getValue(lowerBound);
        }

        System.out.println("Maximum number of iterations reached!");
        root = lowerBound;
        iterations = -1;
        isSuccessful = false;
        bufferResults();

        return isSuccessful;
    }

    // starts with bisection until within 0.00001 precision or half of max iterations, then switches to newton's
    public void hybrid(float lowerBound, float upperBound, int maxIterations, float epsilon){
        bisection(lowerBound, upperBound, maxIterations/2, 0.00001f);
        newtons(root, maxIterations/2, epsilon);
    }

    private void bufferResults(){
        output.append(root + " ");
        output.append(iterations + " ");

        if(isSuccessful){
            output.append("success");
        }  else {
            output.append("fail");
        }

        output.append("\n");
    }

    public void writeResults(String path) throws IOException {
        System.out.println("Writing results to " + path);
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(output.toString());
        fileWriter.close();
    }
}
