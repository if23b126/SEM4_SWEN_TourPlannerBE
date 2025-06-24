package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.ReportService;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    @Sql(scripts = "/exportTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void summaryReportTest() throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ResponseEntity<byte[]> methodOut = reportService.createSummarizeReport();
        InputStream path = classloader.getResourceAsStream("tmp.pdf");
        File newFile = classloader.getResource("tmp.pdf") == null ?
                new File(this.getClass().getResource("/").getPath() + "tmp.pdf") :
                new File(this.getClass().getResource("/tmp.pdf").getPath());
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(methodOut.getBody());
        byte[] pdfMethod = getChecksum(classloader.getResourceAsStream("tmp.pdf"));

        path = classloader.getResourceAsStream("summary.pdf");
        byte[] pdfChecksum = getChecksum(path);

        assertEquals(pdfChecksum.length, pdfMethod.length);
        for(int i = 0; i < pdfMethod.length; i++) {
            assertEquals(pdfChecksum[i], pdfMethod[i]);
        }
    }

    @Test
    @Sql(scripts = "/exportTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void tourReportTest() throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ResponseEntity<byte[]> methodOut = reportService.createTourReport(1L);
        File newFile = classloader.getResource("tmp.pdf") == null ?
                new File(this.getClass().getResource("/").getPath() + "tmp.pdf") :
                new File(this.getClass().getResource("/tmp.pdf").getPath());
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(methodOut.getBody());
        byte[] pdfMethod = getChecksum(classloader.getResourceAsStream("tmp.pdf"));

        InputStream path = classloader.getResourceAsStream("tmp.pdf");
        path = classloader.getResourceAsStream("tour_report.pdf");
        byte[] pdfChecksum = getChecksum(path);

        assertEquals(pdfChecksum.length, pdfMethod.length);
        for(int i = 0; i < pdfMethod.length; i++) {
            assertEquals(pdfChecksum[i], pdfMethod[i]);
        }
    }

    private byte[] getChecksum(InputStream stream) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("/ID [<")) {
                    md.update(line.getBytes());
                }
            }

            return md.digest();

        } finally {
            // close the stream
            try { reader.close(); } catch (Exception e) {}
        }
    }
}
