package threadharness;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import apis.computer.ComputerApiImpl;
import apis.datastorage.DataStorageApiImpl;
import apis.userinput.ConcurrentUserInputApi;
import apis.userinput.UserInputApi;

public class TestMultiUser {

	private UserInputApi coordinator;

	@BeforeEach
	public void initializeComputeEngine() {
		DataStorageApiImpl dataStorageAPI = new DataStorageApiImpl();
		ComputerApiImpl computerAPI = new ComputerApiImpl();
		coordinator = new ConcurrentUserInputApi(dataStorageAPI, computerAPI);
	}

	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int nthreads = 4;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < nthreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}

		// Run single threaded
		String singleThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.singleThreadOut.tmp";
		for (int i = 0; i < nthreads; i++) {
			File singleThreadedOut = new File(singleThreadFilePrefix + i);
			singleThreadedOut.deleteOnExit();
			testUsers.get(i).run(singleThreadedOut.getCanonicalPath());
		}

		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.multiThreadOut.tmp";
		for (int i = 0; i < nthreads; i++) {
			File multiThreadedOut = new File(multiThreadFilePrefix + i);
			multiThreadedOut.deleteOnExit();
			String multiThreadOutputPath = multiThreadedOut.getCanonicalPath();
			TestUser testUser = testUsers.get(i);
			results.add(threadPool.submit(() -> testUser.run(multiThreadOutputPath)));
		}

		results.forEach(future -> {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		threadPool.shutdown();

		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, nthreads);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, nthreads);
		Assertions.assertEquals(singleThreaded, multiThreaded);
	}

	private List<String> loadAllOutput(String prefix, int nthreads) throws IOException {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < nthreads; i++) {
			File multiThreadedOut = new File(prefix + i);
			result.addAll(Files.readAllLines(multiThreadedOut.toPath()));
		}
		return result;
	}

}
