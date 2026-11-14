import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import apis.datastorage.DataStorageApiImplementation;
import apis.datastorage.ReadRequest;
import apis.datastorage.ReadResponse;
import apis.datastorage.ReadResponseCode;
import apis.datastorage.WriteRequest;
import apis.datastorage.WriteResponse;
import apis.datastorage.WriteResponseCode;

public class DataStorageApiImplUnitTest {

    private DataStorageApiImplementation dataStorageApi;
    private ReadRequest readRequest;

    @BeforeEach
    void setUp() {
        dataStorageApi = new DataStorageApiImplementation();
        readRequest = new ReadRequest();
    }

    @Test
    void testNullReadRequest() {

        ReadResponse response = dataStorageApi.read(null);

        Assertions.assertEquals(ReadResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getData().isEmpty());
    }

    @Test
    void testBlankInputSource() {

        readRequest.inputSource = "";

        ReadResponse response = dataStorageApi.read(readRequest);

        Assertions.assertEquals(ReadResponseCode.FAILED, response.getResponseCode());
        Assertions.assertTrue(response.getData().isEmpty());
    }

    @Test
    void testEmptyInputFile() throws IOException {

        File file = File.createTempFile("file", ".txt");

        try {
            readRequest.inputSource = file.getPath();

            ReadResponse response = dataStorageApi.read(readRequest);

            Assertions.assertEquals(ReadResponseCode.FAILED, response.getResponseCode());
            Assertions.assertTrue(response.getData().isEmpty());
        } finally {
            file.delete();
        }
    }

    @Test
    void testNotEmptyInputFile() throws IOException {

        File file = File.createTempFile("file", ".txt");
        String filePath = file.getPath();

        try (BufferedWriter br = new BufferedWriter(new FileWriter(filePath))) {
            br.write("10");
        }
        try {
            readRequest.inputSource = filePath;
            ReadResponse response = dataStorageApi.read(readRequest);

            Assertions.assertEquals(ReadResponseCode.SUCCESS, response.getResponseCode());
            Assertions.assertFalse(response.getData().isEmpty());

        } finally {
            file.delete();
        }
    }

    @Test
    void testInvalidInputFileContents() throws IOException {

        File file = File.createTempFile("file", ".txt");

        try {
            Files.writeString(file.toPath(), "hello");
            readRequest.inputSource = file.getPath();

            ReadResponse response = dataStorageApi.read(readRequest);

            Assertions.assertEquals(ReadResponseCode.FAILED, response.getResponseCode());
            Assertions.assertTrue(response.getData().isEmpty());
        } finally {
            file.delete();
        }
    }

    @Test
    void testNullWriteRequest() {

        WriteResponse writeResponse = dataStorageApi.write(null);

        Assertions.assertEquals(WriteResponseCode.FAILED, writeResponse.getResponseCode());
    }

    @Test
    void testNullOutputDestination() {
        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = null;

        WriteResponse response = dataStorageApi.write(writeRequest);

        Assertions.assertEquals(
                WriteResponseCode.FAILED,
                response.getResponseCode());
    }

    @Test
    void testBlankOutputDestination() {
        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = "   ";

        WriteResponse response = dataStorageApi.write(writeRequest);

        Assertions.assertEquals(
                WriteResponseCode.FAILED,
                response.getResponseCode());
    }

    @Test
    void testNullWriteResults() {
        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = "test.txt";
        writeRequest.results = null;

        WriteResponse response = dataStorageApi.write(writeRequest);

        Assertions.assertEquals(
                WriteResponseCode.FAILED,
                response.getResponseCode());
    }

    @Test
    void testNullDelimiter() {
        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = "test.txt";
        writeRequest.results = new ArrayList<>();
        writeRequest.delimiter = null;

        WriteResponse response = dataStorageApi.write(writeRequest);

        Assertions.assertEquals(WriteResponseCode.FAILED, response.getResponseCode());
    }

    @Test
    void testNullInputValue() throws IOException {

        File file = File.createTempFile("file", ".txt");

        try {
            WriteRequest writeRequest = new WriteRequest();

            writeRequest.outputDestination = file.getPath();
            writeRequest.results = new ArrayList<>();
            writeRequest.delimiter = "";

            writeRequest.results.add(null);

            WriteResponse response = dataStorageApi.write(writeRequest);

            Assertions.assertEquals(WriteResponseCode.FAILED, response.getResponseCode());
        } finally {
            file.delete();
        }
    }

    @Test
    void testWriteIOException() {

        WriteRequest writeRequest = new WriteRequest();
        writeRequest.outputDestination = "nonexistent_directory/test.txt";
        writeRequest.results = new ArrayList<>();
        writeRequest.delimiter = ",";

        writeRequest.results.add(1);
        writeRequest.results.add(2);
        writeRequest.results.add(3);

        WriteResponse response = dataStorageApi.write(writeRequest);
        
        Assertions.assertEquals(WriteResponseCode.FAILED, response.getResponseCode());
    }

    @Test
    void testWriteSuccessfulDefaultDelim() throws IOException {

        File file = File.createTempFile("file", ".txt");

        try {
            WriteRequest writeRequest = new WriteRequest();

            writeRequest.outputDestination = file.getPath();
            writeRequest.results = new ArrayList<>();
            writeRequest.delimiter = "";

            writeRequest.results.add(10);
            writeRequest.results.add(20);
            writeRequest.results.add(30);

            WriteResponse response = dataStorageApi.write(writeRequest);

            Assertions.assertEquals(WriteResponseCode.SUCCESS, response.getResponseCode());
            Assertions.assertEquals("10,20,30", Files.readString(file.toPath()));
        } finally {
            file.delete();
        }
    }

    @Test
    void testWriteSuccessfulNonDefaultDelim() throws IOException {

        File file = File.createTempFile("file", ".txt");

        try {
            WriteRequest writeRequest = new WriteRequest();

            writeRequest.outputDestination = file.getPath();
            writeRequest.results = new ArrayList<>();
            writeRequest.delimiter = ";";

            writeRequest.results.add(10);
            writeRequest.results.add(20);
            writeRequest.results.add(30);

            WriteResponse response = dataStorageApi.write(writeRequest);

            Assertions.assertEquals(WriteResponseCode.SUCCESS, response.getResponseCode());
            Assertions.assertEquals("10;20;30", Files.readString(file.toPath()));
        } finally {
            file.delete();
        }
    }
}