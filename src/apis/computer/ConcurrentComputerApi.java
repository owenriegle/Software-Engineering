package apis.computer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentComputerApi implements ComputerService {

    private final ComputerApi computerAPI;
    private static final int MAX_THREADS = 4;

    public ConcurrentComputerApi(ComputerApi computerAPI) {
        this.computerAPI = Objects.requireNonNull(computerAPI);
    }

    @Override
    public ComputerResponse compute(ComputerRequest computerRequest) {

        if (computerRequest == null || computerRequest.data == null) {

            return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
        }

        try {
            ArrayList<Integer> data = computerRequest.data;
            ArrayList<Integer> results = new ArrayList<>();

            if (!data.isEmpty()) {

                // iterate through input values for null validation
                for (Integer inputValue : data) {
                    if (inputValue == null) {
                        return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
                    }
                }

                ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
                List<Future<ComputerResponse>> futures = new ArrayList<>();

                try {
                    // iterate through input values for creating futures
                    for (Integer inputValue : data) {
                        ComputerRequest taskRequest = new ComputerRequest();
                        taskRequest.data = new ArrayList<>();
                        taskRequest.data.add(inputValue);

                        Future<ComputerResponse> future = executor.submit(
                                () -> computerAPI.compute(taskRequest));

                        futures.add(future);
                    }

                    for (Future<ComputerResponse> future : futures) {

                        ComputerResponse response = future.get();

                        if (response == null
                                || response.getResponseCode() != ComputerResponseCode.SUCCESS
                                || response.getResults() == null
                                || response.getResults().size() != 1) {

                            return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
                        }

                        results.add(response.getResults().get(0));
                    }
                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                    return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
                } catch (ExecutionException e) {

                    return new ComputerResponseImpl(new ArrayList<>(), ComputerResponseCode.FAILED);
                } finally {

                    executor.shutdown();
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

}
