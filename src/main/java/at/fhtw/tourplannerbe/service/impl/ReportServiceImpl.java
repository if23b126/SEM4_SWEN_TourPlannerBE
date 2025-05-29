package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.ExportService;
import at.fhtw.tourplannerbe.service.ReportService;
import at.fhtw.tourplannerbe.service.TourService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.Tour;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ExportService exportService;
    private final TourService tourService;
    private final PDFont font = new PDType1Font(Standard14Fonts.FontName.COURIER);

    @Override
    public ResponseEntity<byte[]> createTourReport(Long id) throws IOException {
        TourImportExport tour = exportService.exportTour(id);
        Date diff = new Date(tour.getTour().getTimeEnd().getTime() - tour.getTour().getTimeStart().getTime());

        float fontSize = 14;
        float headingSize = 38;
        float margin = 50;
        float leading = fontSize * 1.5f;
        float totalHeight = margin;
        float fontHeight = font.getFontDescriptor().getFontBoundingBox()
                .getHeight()
                / 1000 * fontSize;
        float headingHeight = font.getFontDescriptor().getFontBoundingBox()
                .getHeight()
                / 1000 * headingSize;
        float a4Height= PDRectangle.A4.getHeight();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        PDRectangle pageSize = page.getMediaBox();
        float startX = pageSize.getLowerLeftX() + margin;
        float startY = pageSize.getUpperRightY() - margin;

        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(startX, startY);
        contentStream.setFont(font, headingSize);
        contentStream.showText("Tour");
        totalHeight += headingHeight;

        contentStream.setFont(font, fontSize);
        contentStream.newLine();
        contentStream.showText("Tourname: " + tour.getTour().getName());
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.showText("Tour Description: " + tour.getTour().getDescription());
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.showText("Startpoint: " + tour.getTour().getStart());
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.showText("Endpoint: " + tour.getTour().getEnd());
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.showText("Transport Mode: " + tour.getTour().getTransportMode());
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.showText("Distance: " + tour.getTour().getDistance());
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.showText("Duration: " + diff.getTime()/3600000);
        totalHeight += fontHeight;
        totalHeight += fontHeight;

        contentStream.newLine();
        contentStream.newLine();
        totalHeight += fontHeight;
        totalHeight += fontHeight;
        contentStream.setFont(font, headingSize);
        contentStream.showText("Logs");
        totalHeight += headingHeight;
        contentStream.setFont(font, fontSize);

        int counter = 0;
        for (Log log : tour.getLogs()) {
            try {
                Date logDiff = new Date(log.getTimeEnd().getTime() - log.getTimeStart().getTime());

                contentStream.newLine();
                contentStream.showText("Logtime: " + log.getTime());
                totalHeight += fontHeight;
                totalHeight += fontHeight;

                contentStream.newLine();
                contentStream.showText("Comment: " + log.getComment());
                totalHeight += fontHeight;
                totalHeight += fontHeight;

                contentStream.newLine();
                contentStream.showText("Difficulty: " + log.getDifficulty());
                totalHeight += fontHeight;
                totalHeight += fontHeight;

                contentStream.newLine();
                contentStream.showText("Distance: " + log.getDistance());
                totalHeight += fontHeight;
                totalHeight += fontHeight;

                contentStream.newLine();
                contentStream.showText("Rating: " + log.getRating());
                totalHeight += fontHeight;
                totalHeight += fontHeight;

                contentStream.newLine();
                contentStream.showText("Duration: " + logDiff.getTime()/3600000);
                totalHeight += fontHeight;
                totalHeight += fontHeight;

                contentStream.newLine();
                totalHeight += fontHeight;

                if( (totalHeight + margin) >= a4Height && (counter + 1) < tour.getLogs().size()) {
                    contentStream.endText();
                    page = new PDPage();
                    document.addPage(page);
                    totalHeight = margin;
                    contentStream.close();
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.beginText();
                    contentStream.setLeading(leading);
                    contentStream.newLineAtOffset(startX, startY);
                    contentStream.setFont(font, fontSize);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        contentStream.endText();
        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(baos.size());
        headers.set("Content-Disposition", "attachment; filename=" + "pdf-" + id + ".pdf");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> createSummarizeReport() throws IOException {
        float fontSize = 14;
        float headingSize = 38;
        float margin = 50;
        float leading = fontSize * 1.5f;
        float totalHeight = margin;
        float fontHeight = font.getFontDescriptor().getFontBoundingBox()
                .getHeight()
                / 1000 * fontSize;
        float headingHeight = font.getFontDescriptor().getFontBoundingBox()
                .getHeight()
                / 1000 * headingSize;
        float a4Height= PDRectangle.A4.getHeight();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        PDRectangle pageSize = page.getMediaBox();
        float startX = pageSize.getLowerLeftX() + margin;
        float startY = pageSize.getUpperRightY() - margin;

        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(startX, startY);

        List<Tour> tours = tourService.getTours();
        int counter = 0;
        for (Tour tmp : tours) {
            TourImportExport tour = exportService.exportTour(tmp.getId());
            List<Log> logs = tour.getLogs();
            int logSize = logs.size();
            int logDivisor = logSize == 0 ? 1 : logSize;
            long timeSum = 0L;
            double distanceSum = 0;
            double ratingSum = 0;
            for (Log log : logs) {
                Date logDiff = new Date(log.getTimeEnd().getTime() - log.getTimeStart().getTime());
                timeSum += logDiff.getTime();
                distanceSum += log.getDistance();
                ratingSum += log.getRating();
            }

            contentStream.setFont(font, headingSize);
            contentStream.showText("Tour: " + tmp.getName());
            totalHeight += headingHeight;

            contentStream.setFont(font, fontSize);
            contentStream.newLine();
            contentStream.showText("Log count: " + logSize);
            totalHeight += fontHeight;
            totalHeight += fontHeight;


            contentStream.newLine();
            contentStream.showText("Average Time: " + timeSum/logDivisor/3600000);
            totalHeight += fontHeight;
            totalHeight += fontHeight;

            contentStream.newLine();
            contentStream.showText("Average Distance: " + distanceSum/logDivisor);
            totalHeight += fontHeight;
            totalHeight += fontHeight;

            contentStream.newLine();
            contentStream.showText("Average Rating: " + ratingSum/logDivisor);
            totalHeight += fontHeight;
            totalHeight += fontHeight;

            contentStream.newLine();
            contentStream.newLine();

            if( (totalHeight + margin) >= a4Height && (counter + 1) < tours.size()) {
                contentStream.endText();
                page = new PDPage();
                document.addPage(page);
                totalHeight = margin;
                contentStream.close();
                contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setLeading(leading);
                contentStream.newLineAtOffset(startX, startY);
                contentStream.setFont(font, fontSize);
            }
            counter++;
        }
        contentStream.endText();
        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(baos.size());
        headers.set("Content-Disposition", "attachment; filename=" + "pdf-summary.pdf");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }
}
