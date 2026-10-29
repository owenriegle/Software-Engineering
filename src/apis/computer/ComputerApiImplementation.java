package apis.computer;

import java.util.ArrayList;

public class ComputerApiImplementation implements ComputerAPI {

    @Override
    public ComputerResponse compute(ComputerRequest computerRequest) {
        
        ArrayList<Integer> results = new ArrayList<>();

        // iterate through input values
        for (int inputValue : computerRequest.data) {
            int sum = 0;

            // count by 1 to sum all prime numbers less than or equal to input value
            for (int counter = 2; counter <= inputValue; counter++) {
                if (isPrime(counter)) {
                    sum += counter;
                }
            }

            results.add(sum);
        }
        return new ComputerResponseImpl(results);
    }

    private static boolean isPrime(int n) {
        // 1 or less are not prime numbers
        if (n <= 1) {
            return false;
        }
        // 2 is the only even prime number
        if (n == 2) {
            return true;
        }
        // Exclude all other even numbers
        if (n % 2 == 0) {
            return false;
        }
        // Check odd factors up to the square root of n
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
    
        return true;
    }

}
