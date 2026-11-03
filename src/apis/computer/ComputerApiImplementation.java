package apis.computer;

import java.util.ArrayList;

public class ComputerApiImplementation implements ComputerAPI {

    @Override
    public ComputerResponse compute(ComputerRequest computerRequest) {

        if (computerRequest == null || computerRequest.data == null) {

            return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
        }

        try {
            ArrayList<Integer> data = computerRequest.data;
            ArrayList<Integer> results = new ArrayList<>();

            if (!data.isEmpty()) {
                // iterate through input values
                for (Integer inputValue : data) {

                    if (inputValue == null) {
                        return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
                    }

                    int sum = 0;
                    // count by 1 to sum all prime numbers less than or equal to input value
                    for (int counter = 2; counter <= inputValue; counter++) {
                        if (isPrime(counter)) {
                            sum += counter;
                        }
                    }
                    results.add(sum);
                }
                return new ComputerResponseImpl(results, ComputerResponseCode.SUCCESS);
            } else {
                return new ComputerResponseImpl(results, ComputerResponseCode.FAILED);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
        }
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
        for (int i = 3; i <= n / i; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
