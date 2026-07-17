package api.tripstack.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public final class ExtentManager {
    private static final String REPORT_PATH = "target/extent-reports/extent-report.html";
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            File reportDir = new File("target/extent-reports");
            if (!reportDir.exists() && !reportDir.mkdirs()) {
                throw new IllegalStateException("Unable to create extent report directory: " + reportDir);
            }

            ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH);
            reporter.config().setReportName("TripStack API Test Report");
            reporter.config().setDocumentTitle("TripStack API Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }

    public static ExtentTest createTest(String name) {
        ExtentTest test = getInstance().createTest(name);
        CURRENT_TEST.set(test);
        return test;
    }

    public static ExtentTest getCurrentTest() {
        return CURRENT_TEST.get();
    }

    public static void info(String message) {
        ExtentTest test = getCurrentTest();
        if (test != null) {
            test.info(message);
        }
    }

    public static void pass(String message) {
        ExtentTest test = getCurrentTest();
        if (test != null) {
            test.pass(message);
        }
    }

    public static void fail(String message) {
        ExtentTest test = getCurrentTest();
        if (test != null) {
            test.fail(message);
        }
    }

    public static void clearCurrentTest() {
        CURRENT_TEST.remove();
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
