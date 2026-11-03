import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.JobHandler;
import apis.computer.ComputerAPI;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponseCode;
import apis.computer.ComputerResponseImpl;
import apis.datastorage.DataStorageAPI;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponseCode;
import apis.datastorage.ReadResponseImplementation;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponseCode;
import apis.datastorage.WriteResponseImplementation;
import apis.userinput.InputRequest;

public class JobHandlerUnitTest {

    private ComputerAPI computerAPI;
    private DataStorageAPI dataStorageAPI;
    private JobHandler jobHandler;

    @BeforeEach
    void setUp() {
        computerAPI = Mockito.mock(ComputerAPI.class);
        dataStorageAPI = Mockito.mock(DataStorageAPI.class);
        jobHandler = new JobHandler(computerAPI, dataStorageAPI);
    }

    @Test
    void testNullInputRequest() {

        String result = jobHandler.handleJob(null);

        // verify expected failure and that dependencies werent called
        Assertions.assertEquals(JobHandler.INVALID_REQUEST_MESSAGE, result);
        Mockito.verifyNoInteractions(computerAPI, dataStorageAPI);
    }

    @Test
    void testNullReadResponse() {

        // simulate a failed storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class))).thenReturn(null);

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        // verify expected failure and that later operations are skipped
        Assertions.assertEquals(JobHandler.READ_FAILURE_MESSAGE, result);
        Mockito.verify(computerAPI, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }
    
    @Test 
    void testFailedReadResponse() {

        // simulate a failed storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(new ArrayList<>(), ReadResponseCode.FAILED));

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        // verify expected failure and that later operations are skipped
        Assertions.assertEquals(JobHandler.READ_FAILURE_MESSAGE, result);
        Mockito.verify(computerAPI, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }

    @Test 
    void testNullReadData() {

        // simulate a successful storage read with null data
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(null, ReadResponseCode.SUCCESS));

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        // verify expected failure and that later operations are skipped
        Assertions.assertEquals(JobHandler.READ_FAILURE_MESSAGE, result);
        Mockito.verify(computerAPI, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }

    @Test
    void testNullComputerResponse() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a null computer response
        Mockito.when(computerAPI.compute(Mockito.any(ComputerRequest.class)))
                .thenReturn(null);

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failure and that writing is skipped
        Assertions.assertEquals(JobHandler.COMPUTE_FAILURE_MESSAGE, result);
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
        Mockito.verify(computerAPI, Mockito.times(1))
        .compute(Mockito.any(ComputerRequest.class));
    }

    @Test 
    void testNullComputerResults() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a null computer response
        Mockito.when(computerAPI.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(null, ComputerResponseCode.FAILED));

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failure and that writing is skipped
        Assertions.assertEquals(JobHandler.COMPUTE_FAILURE_MESSAGE, result);
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
        Mockito.verify(computerAPI, Mockito.times(1))
        .compute(Mockito.any(ComputerRequest.class));
    }

    @Test
    void testNullWriteResponse() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a computer response
        Mockito.when(computerAPI.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(new ArrayList<>(List.of(1, 2, 3)), ComputerResponseCode.SUCCESS));

        Mockito.when(dataStorageAPI.write(Mockito.any(WriteRequest.class))).thenReturn(null);

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failures
        Assertions.assertEquals(JobHandler.WRITE_FAILURE_MESSAGE, result);
        Mockito.verify(computerAPI, Mockito.times(1))
            .compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .write(Mockito.any(WriteRequest.class));
    }

    @Test 
    void testFailedWriteResponse() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a computer response
        Mockito.when(computerAPI.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(new ArrayList<>(List.of(1, 2, 3)), ComputerResponseCode.SUCCESS));

        // simulate a failed storage write
        Mockito.when(dataStorageAPI.write(Mockito.any(WriteRequest.class)))
        .thenReturn(new WriteResponseImplementation(WriteResponseCode.FAILED));

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failures
        Assertions.assertEquals(JobHandler.WRITE_FAILURE_MESSAGE, result);
        Mockito.verify(computerAPI, Mockito.times(1))
            .compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .write(Mockito.any(WriteRequest.class));
    }

    @Test 
    void testSuccessfulJob() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImplementation(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a computer response
        Mockito.when(computerAPI.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(new ArrayList<>(List.of(1, 2, 3)), ComputerResponseCode.SUCCESS));

        // simulate a successful storage write
        Mockito.when(dataStorageAPI.write(Mockito.any(WriteRequest.class)))
        .thenReturn(new WriteResponseImplementation(WriteResponseCode.SUCCESS));

        String result = jobHandler.handleJob(new InputRequest());

        // verify successful execution
        Assertions.assertEquals(JobHandler.SUCCESS_MESSAGE, result);
        Mockito.verify(computerAPI, Mockito.times(1))
            .compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .read(Mockito.any(ReadRequest.class));        
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .write(Mockito.any(WriteRequest.class));
    }
}
