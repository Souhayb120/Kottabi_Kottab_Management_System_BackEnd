package com.example.kottabi.services.ServiceImp;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfRapportService {

    private static final float MARGIN_LEFT   = 55f;
    private static final float MARGIN_RIGHT  = 50f;
    private static final float MARGIN_TOP    = 60f;
    private static final float MARGIN_BOTTOM = 60f;

    private static final float TITLE_FONT_SIZE = 20f;
    private static final float BODY_FONT_SIZE  = 13f;
    private static final float LINE_HEIGHT     = 24f;
    private static final float PARAGRAPH_GAP   = 12f;
    private static final float TITLE_GAP       = 30f;

    private static final String FONT_RESOURCE = "/fonts/Amiri-Regular.ttf";

    public void generatePdf(String rapport, long studentId) {
        generatePdf(rapport, studentId, "تقرير الطالب");
    }

    public void generatePdf(String rapport, long studentId, String title) {

        String folderPath = "src/main/resources/reports";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "/rapport-" + studentId + ".pdf";

        try (PDDocument document = new PDDocument()) {

            PDType0Font font = loadArabicFont(document);

            float pageWidth   = PDRectangle.A4.getWidth();
            float usableWidth = pageWidth - MARGIN_LEFT - MARGIN_RIGHT;

            List<RenderLine> lines = buildRenderLines(rapport, font, BODY_FONT_SIZE, usableWidth);

            List<List<RenderLine>> pages = paginate(lines, title != null && !title.isBlank());
            int totalPages = pages.size();

            for (int i = 0; i < pages.size(); i++) {
                drawPage(document, font, pageWidth, title, i == 0, pages.get(i), i + 1, totalPages);
            }

            document.save(filePath);
            System.out.println("PDF created: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("Error creating PDF", e);
        }
    }

    private PDType0Font loadArabicFont(PDDocument document) throws IOException {
        try (InputStream fontStream = getClass().getResourceAsStream(FONT_RESOURCE)) {
            if (fontStream == null) {
                throw new RuntimeException("Amiri font not found at " + FONT_RESOURCE);
            }
            return PDType0Font.load(document, fontStream, true);
        }
    }

    private static class RenderLine {
        final List<Word> words;
        final boolean justify;
        final boolean spacer;

        RenderLine(List<Word> words, boolean justify, boolean spacer) {
            this.words = words;
            this.justify = justify;
            this.spacer = spacer;
        }
    }

    private static class Word {
        final String visualText;
        final float width;

        Word(String visualText, float width) {
            this.visualText = visualText;
            this.width = width;
        }
    }

    private List<RenderLine> buildRenderLines(String rapport, PDFont font, float fontSize, float maxWidth)
            throws IOException {

        List<RenderLine> result = new ArrayList<>();
        float spaceWidth = width(font, fontSize, " ");

        String[] paragraphs = rapport.split("\\R", -1);

        for (String paragraph : paragraphs) {

            if (paragraph.isBlank()) {
                result.add(new RenderLine(new ArrayList<>(), false, true));
                continue;
            }

            String[] rawWords = paragraph.trim().split("\\s+");

            List<Word> shapedWords = new ArrayList<>();
            for (String raw : rawWords) {
                String visual = shapeAndReorder(raw);
                shapedWords.add(new Word(visual, width(font, fontSize, visual)));
            }

            List<List<Word>> subLines = new ArrayList<>();
            List<Word> current = new ArrayList<>();
            float currentWidth = 0f;

            for (Word word : shapedWords) {
                float extra = current.isEmpty() ? word.width : spaceWidth + word.width;
                if (!current.isEmpty() && currentWidth + extra > maxWidth) {
                    subLines.add(current);
                    current = new ArrayList<>();
                    currentWidth = 0f;
                    extra = word.width;
                }
                current.add(word);
                currentWidth += extra;
            }
            if (!current.isEmpty()) {
                subLines.add(current);
            }

            for (int i = 0; i < subLines.size(); i++) {
                boolean isLast = (i == subLines.size() - 1);
                result.add(new RenderLine(subLines.get(i), !isLast, false));
            }
        }

        return result;
    }

    private String shapeAndReorder(String text) {
        try {
            ArabicShaping shaping = new ArabicShaping(ArabicShaping.LETTERS_SHAPE);
            String shaped = shaping.shape(text);
            Bidi bidi = new Bidi(shaped, Bidi.DIRECTION_RIGHT_TO_LEFT);
            return bidi.writeReordered(Bidi.DO_MIRRORING);
        } catch (Exception e) {
            throw new RuntimeException("Error processing Arabic text: " + text, e);
        }
    }

    private float width(PDFont font, float fontSize, String text) throws IOException {
        return font.getStringWidth(text) / 1000f * fontSize;
    }

    private List<List<RenderLine>> paginate(List<RenderLine> lines, boolean hasTitle) {

        float pageHeight = PDRectangle.A4.getHeight();
        float top = pageHeight - MARGIN_TOP - (hasTitle ? TITLE_FONT_SIZE + TITLE_GAP : 0);
        float bottom = MARGIN_BOTTOM;

        List<List<RenderLine>> pages = new ArrayList<>();
        List<RenderLine> currentPage = new ArrayList<>();
        float y = top;

        for (RenderLine line : lines) {
            float advance = line.spacer ? PARAGRAPH_GAP : LINE_HEIGHT;
            if (y - advance < bottom) {
                pages.add(currentPage);
                currentPage = new ArrayList<>();
                y = pageHeight - MARGIN_TOP;
            }
            currentPage.add(line);
            y -= advance;
        }
        if (!currentPage.isEmpty() || pages.isEmpty()) {
            pages.add(currentPage);
        }
        return pages;
    }

    private void drawPage(PDDocument document, PDType0Font font, float pageWidth, String title,
                          boolean isFirstPage, List<RenderLine> lines, int pageNumber, int totalPages)
            throws IOException {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        float pageHeight   = page.getMediaBox().getHeight();
        float rightEdge    = pageWidth - MARGIN_RIGHT;
        float usableWidth  = pageWidth - MARGIN_LEFT - MARGIN_RIGHT;

        try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

            float y = pageHeight - MARGIN_TOP;

            if (isFirstPage && title != null && !title.isBlank()) {
                String visualTitle = shapeAndReorder(title);
                float titleWidth = width(font, TITLE_FONT_SIZE, visualTitle);
                float titleX = MARGIN_LEFT + (usableWidth - titleWidth) / 2f;

                cs.beginText();
                cs.setFont(font, TITLE_FONT_SIZE);
                cs.setTextMatrix(Matrix.getTranslateInstance(titleX, y));
                cs.showText(visualTitle);
                cs.endText();

                cs.setLineWidth(0.7f);
                cs.moveTo(MARGIN_LEFT, y - 8);
                cs.lineTo(pageWidth - MARGIN_RIGHT, y - 8);
                cs.stroke();

                y -= TITLE_FONT_SIZE + TITLE_GAP;
            }

            cs.setFont(font, BODY_FONT_SIZE);

            for (RenderLine line : lines) {
                if (line.spacer) {
                    y -= PARAGRAPH_GAP;
                    continue;
                }
                drawJustifiedLine(cs, line, rightEdge, usableWidth, y);
                y -= LINE_HEIGHT;
            }

            drawFooter(cs, font, pageWidth, pageNumber, totalPages);
        }
    }

    private void drawJustifiedLine(PDPageContentStream cs, RenderLine line, float rightEdge,
                                   float usableWidth, float y) throws IOException {

        List<Word> words = line.words;
        if (words.isEmpty()) return;

        float totalWordWidth = 0f;
        for (Word w : words) totalWordWidth += w.width;

        int gaps = words.size() - 1;
        float minSpace = 4f;
        float gapWidth;

        if (line.justify && gaps > 0) {
            gapWidth = (usableWidth - totalWordWidth) / gaps;
            if (gapWidth < minSpace) gapWidth = minSpace;
        } else {
            gapWidth = Math.max(minSpace, usableWidth * 0.02f);
        }

        float x = rightEdge;
        cs.beginText();
        for (Word word : words) {
            x -= word.width;
            cs.setTextMatrix(Matrix.getTranslateInstance(x, y));
            cs.showText(word.visualText);
            x -= gapWidth;
        }
        cs.endText();
    }

    private void drawFooter(PDPageContentStream cs, PDType0Font font, float pageWidth,
                            int pageNumber, int totalPages) throws IOException {
        String footer = pageNumber + " / " + totalPages;
        String visual = shapeAndReorder(footer);
        float footerSize = 9f;
        float w = width(font, footerSize, visual);
        float x = (pageWidth - w) / 2f;

        cs.beginText();
        cs.setFont(font, footerSize);
        cs.setTextMatrix(Matrix.getTranslateInstance(x, MARGIN_BOTTOM / 2f));
        cs.showText(visual);
        cs.endText();
    }
}