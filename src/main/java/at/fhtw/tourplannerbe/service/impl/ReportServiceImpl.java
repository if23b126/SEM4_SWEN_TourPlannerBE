package at.fhtw.tourplannerbe.service.impl;

import at.fhtw.tourplannerbe.service.ExportService;
import at.fhtw.tourplannerbe.service.ReportService;
import at.fhtw.tourplannerbe.service.dtos.Log;
import at.fhtw.tourplannerbe.service.dtos.TourImportExport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSDictionary;
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
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ExportService exportService;

    @Override
    public ResponseEntity<byte[]> createReport(Long id) throws IOException {
        TourImportExport tour = exportService.exportTour(id);
        Date diff = new Date(tour.getTour().getTimeEnd().getTime() - tour.getTour().getTimeStart().getTime());

        PDFont font = new PDType1Font(Standard14Fonts.FontName.COURIER);
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
        contentStream.showText("Transportmode: " + tour.getTour().getTransportMode());
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

        for (int i = 0; i < tour.getLogs().size(); i++) {
            Log log = tour.getLogs().get(i);
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

                if( (totalHeight + margin /*+ (6 * fontHeight)*/) >= a4Height && (i + 1) < tour.getLogs().size()) {
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
}
