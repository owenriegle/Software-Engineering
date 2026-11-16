import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import apis.JobHandler;
import apis.computer.ComputerRequest;
import apis.computer.ComputerResponseCode;
import apis.computer.ComputerResponseImpl;
import apis.computer.ComputerService;
import apis.datastorage.DataStorageApi;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponseCode;
import apis.datastorage.ReadResponseImpl;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponseCode;
import apis.datastorage.WriteResponseImpl;
import apis.userinput.InputRequest;

public class JobHandlerUnitTest {

    private ComputerService computerService;
    private DataStorageApi dataStorageAPI;
    private JobHandler jobHandler;

    @BeforeEach
    void setUp() {
        computerService = Mockito.mock(ComputerService.class);
        dataStorageAPI = Mockito.mock(DataStorageApi.class);
        jobHandler = new JobHandler(computerService, dataStorageAPI);
    }

    @Test 
    void testNullComputerAPIDependency() {

        Assertions.assertThrows(
            NullPointerException.class,
            () -> new JobHandler(null, dataStorageAPI));
        Mockito.verifyNoInteractions(computerService, dataStorageAPI);
    }
    
    @Test 
    void testNullDataStorageAPIDependency() {

        Assertions.assertThrows(
            NullPointerException.class,
            () -> new JobHandler(computerService, null));
        Mockito.verifyNoInteractions(computerService, dataStorageAPI);
    }

    @Test
    void testNullInputRequest() {

        String result = jobHandler.handleJob(null);

        // verify expected failure and that dependencies werent called
        Assertions.assertEquals(JobHandler.INVALID_REQUEST_MESSAGE, result);
        Mockito.verifyNoInteractions(computerService, dataStorageAPI);
    }

    @Test 
    void testRuntimeExceptionDuringRead() {
        
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class))).thenThrow(new IllegalStateException());

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        Assertions.assertEquals(JobHandler.GENERAL_FAILURE_MESSAGE, result);
        Mockito.verify(computerService, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }

    @Test
    void testNullReadResponse() {

        // simulate a failed storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class))).thenReturn(null);

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        // verify expected failure and that later operations are skipped
        Assertions.assertEquals(JobHandler.READ_FAILURE_MESSAGE, result);
        Mockito.verify(computerService, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }
    
    @Test 
    void testFailedReadResponse() {

        // simulate a failed storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(new ArrayList<>(), ReadResponseCode.FAILED));

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        // verify expected failure and that later operations are skipped
        Assertions.assertEquals(JobHandler.READ_FAILURE_MESSAGE, result);
        Mockito.verify(computerService, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }

    @Test 
    void testNullReadData() {

        // simulate a successful storage read with null data
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(null, ReadResponseCode.SUCCESS));

        InputRequest inputRequest = new InputRequest();
        String result = jobHandler.handleJob(inputRequest);

        // verify expected failure and that later operations are skipped
        Assertions.assertEquals(JobHandler.READ_FAILURE_MESSAGE, result);
        Mockito.verify(computerService, never()).compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
    }

    @Test
    void testNullComputerResponse() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a null computer response
        Mockito.when(computerService.compute(Mockito.any(ComputerRequest.class)))
                .thenReturn(null);

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failure and that writing is skipped
        Assertions.assertEquals(JobHandler.COMPUTE_FAILURE_MESSAGE, result);
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
        Mockito.verify(computerService, Mockito.times(1))
        .compute(Mockito.any(ComputerRequest.class));
    }

    @Test 
    void testNullComputerResults() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a null computer response
        Mockito.when(computerService.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(null, ComputerResponseCode.FAILED));

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failure and that writing is skipped
        Assertions.assertEquals(JobHandler.COMPUTE_FAILURE_MESSAGE, result);
        Mockito.verify(dataStorageAPI, never()).write(Mockito.any(WriteRequest.class));
        Mockito.verify(computerService, Mockito.times(1))
        .compute(Mockito.any(ComputerRequest.class));
    }

    @Test
    void testNullWriteResponse() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a computer response
        Mockito.when(computerService.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(new ArrayList<>(List.of(1, 2, 3)), ComputerResponseCode.SUCCESS));

        Mockito.when(dataStorageAPI.write(Mockito.any(WriteRequest.class))).thenReturn(null);

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failures
        Assertions.assertEquals(JobHandler.WRITE_FAILURE_MESSAGE, result);
        Mockito.verify(computerService, Mockito.times(1))
            .compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .write(Mockito.any(WriteRequest.class));
    }

    @Test 
    void testFailedWriteResponse() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a computer response
        Mockito.when(computerService.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(new ArrayList<>(List.of(1, 2, 3)), ComputerResponseCode.SUCCESS));

        // simulate a failed storage write
        Mockito.when(dataStorageAPI.write(Mockito.any(WriteRequest.class)))
        .thenReturn(new WriteResponseImpl(WriteResponseCode.FAILED));

        String result = jobHandler.handleJob(new InputRequest());

        // verify expected failures
        Assertions.assertEquals(JobHandler.WRITE_FAILURE_MESSAGE, result);
        Mockito.verify(computerService, Mockito.times(1))
            .compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .write(Mockito.any(WriteRequest.class));
    }

    @Test 
    void testSuccessfulJob() {

        // simulate a successful storage read
        Mockito.when(dataStorageAPI.read(Mockito.any(ReadRequest.class)))
        .thenReturn(new ReadResponseImpl(
            new ArrayList<>(List.of(1, 2, 3)), ReadResponseCode.SUCCESS));

        // simulate a computer response
        Mockito.when(computerService.compute(Mockito.any(ComputerRequest.class)))
        .thenReturn(new ComputerResponseImpl(new ArrayList<>(List.of(1, 2, 3)), ComputerResponseCode.SUCCESS));

        // simulate a successful storage write
        Mockito.when(dataStorageAPI.write(Mockito.any(WriteRequest.class)))
        .thenReturn(new WriteResponseImpl(WriteResponseCode.SUCCESS));

        String result = jobHandler.handleJob(new InputRequest());

        // verify successful execution
        Assertions.assertEquals(JobHandler.SUCCESS_MESSAGE, result);
        Mockito.verify(computerService, Mockito.times(1))
            .compute(Mockito.any(ComputerRequest.class));
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .read(Mockito.any(ReadRequest.class));        
        Mockito.verify(dataStorageAPI, Mockito.times(1))
            .write(Mockito.any(WriteRequest.class));
    }
}
