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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfRapportService {

    private static final float MARGIN_LEFT   = 55f;
    private static final float MARGIN_RIGHT  = 50f;
    private static final float MARGIN_TOP    = 60f;
    private static final float MARGIN_BOTTOM = 60f;

    private static final float EYEBROW_FONT_SIZE = 9.5f;
    private static final float TITLE_FONT_SIZE   = 22f;
    private static final float HEADING_FONT_SIZE = 14f;
    private static final float BODY_FONT_SIZE    = 13f;
    private static final float FOOTER_FONT_SIZE  = 8.5f;

    private static final float LINE_HEIGHT   = 26f;
    private static final float PARAGRAPH_GAP = 14f;
    private static final float TITLE_GAP     = 26f;
    private static final float TITLE_BLOCK_HEIGHT = 120f;

    private static final float[] GOLD  = {0.78f, 0.60f, 0.23f};
    private static final float[] INK   = {0.13f, 0.19f, 0.17f};
    private static final float[] GREEN = {0.06f, 0.24f, 0.18f};
    private static final float[] MUTED = {0.42f, 0.45f, 0.43f};

    private static final String FONT_RESOURCE = "/fonts/Amiri-Regular.ttf";
    private static final String FONT_BOLD_RESOURCE = "/fonts/Amiri-Bold.ttf";
    private static final String REPORT_TITLE = "تقرير الطالب";

    private static final List<String> HEADING_KEYS = List.of(
            "معلومات الطالب",
            "الحضور والمواظبة",
            "مستوى حفظ القرآن الكريم",
            "المشاركة في المسابقات",
            "المشاركات",
            "التقييم العام",
            "الأقسام",
            "المطلوب"
    );

    public void generatePdf(String rapport, long studentId) {
        generatePdf(rapport, studentId, REPORT_TITLE);
    }

    public void generatePdf(String rapport, long studentId, String title) {

        String folderPath = "src/main/resources/reports";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "/rapport-" + studentId + ".pdf";

        try (PDDocument document = new PDDocument()) {

            PDFont regular = loadFont(document, FONT_RESOURCE);
            PDFont bold = loadFont(document, FONT_BOLD_RESOURCE);
            if (bold == null) {
                bold = regular;
            }

            float pageWidth = PDRectangle.A4.getWidth();
            float usableWidth = pageWidth - MARGIN_LEFT - MARGIN_RIGHT;

            List<RenderLine> lines = buildRenderLines(rapport, regular, bold, pageWidth, usableWidth);

            List<List<RenderLine>> pages = paginate(lines, true);
            int totalPages = pages.size();

            for (int i = 0; i < pages.size(); i++) {
                drawPage(document, regular, bold, pageWidth, usableWidth,
                        title, i == 0, pages.get(i), i + 1, totalPages);
            }

            document.save(filePath);
            System.out.println("PDF created: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("Error creating PDF", e);
        }
    }

    public File getRapportFile(long studentId) {
        File file = new File("src/main/resources/reports/rapport-" + studentId + ".pdf");
        if (!file.exists()) {
            throw new RuntimeException("Rapport not found for student " + studentId);
        }
        return file;
    }

    private PDType0Font loadFont(PDDocument document, String resource) throws IOException {
        try (InputStream fontStream = getClass().getResourceAsStream(resource)) {
            if (fontStream == null) {
                if (FONT_RESOURCE.equals(resource)) {
                    throw new RuntimeException("Amiri font not found at " + resource);
                }
                return null;
            }
            return PDType0Font.load(document, fontStream, true);
        }
    }

    private static class RenderLine {
        final List<Word> words;
        final boolean justify;
        final boolean spacer;
        final boolean heading;
        final boolean endOfBlock;

        RenderLine(List<Word> words, boolean justify, boolean spacer, boolean heading, boolean endOfBlock) {
            this.words = words;
            this.justify = justify;
            this.spacer = spacer;
            this.heading = heading;
            this.endOfBlock = endOfBlock;
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

    private String normalize(String text) {
        return text.replaceAll("[\\u064B-\\u065F\\u0670\\s]", "");
    }

    private boolean isHeading(String line) {
        String n = normalize(line);
        if (n.matches("^\\d+[.)].+")) {
            return true;
        }
        for (String key : HEADING_KEYS) {
            if (n.contains(normalize(key))) {
                return true;
            }
        }
        return false;
    }

    private List<RenderLine> buildRenderLines(String rapport, PDFont regular, PDFont bold,
                                              float pageWidth, float maxWidth) throws IOException {

        List<RenderLine> result = new ArrayList<>();
        float spaceWidth = width(regular, BODY_FONT_SIZE, " ");

        String[] paragraphs = rapport.split("\\R", -1);

        for (String paragraph : paragraphs) {

            if (paragraph.isBlank()) {
                result.add(new RenderLine(new ArrayList<>(), false, true, false, false));
                continue;
            }

            boolean heading = isHeading(paragraph);
            PDFont font = heading ? bold : regular;
            float fontSize = heading ? HEADING_FONT_SIZE : BODY_FONT_SIZE;
            float pSpaceWidth = width(font, fontSize, " ");

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
                float extra = current.isEmpty() ? word.width : pSpaceWidth + word.width;
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
                int wordCount = subLines.get(i).size();
                boolean isEmptySub = false;
                if (pageWidth > 0 && subLines.get(i).isEmpty()) {
                    isEmptySub = true;
                }
                result.add(new RenderLine(
                        subLines.get(i),
                        !isLast && wordCount > 1 && !empty(wordsOnly(subLines.get(i))),
                        false,
                        heading,
                        isLast
                ));
                if (isEmptySub) {
                    result.add(new RenderLine(new ArrayList<>(), false, true, false, false));
                }
            }
        }

        return result;
    }

    private boolean empty(List<Word> words) {
        return words == null || words.isEmpty();
    }

    private List<Word> wordsOnly(List<Word> words) {
        return words == null ? List.of() : words;
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

    private String toArabicDigits(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                sb.append((char) ('\u0660' + (c - '0')));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private List<List<RenderLine>> paginate(List<RenderLine> lines, boolean hasTitle) {

        float pageHeight = PDRectangle.A4.getHeight();
        float top = pageHeight - MARGIN_TOP - (hasTitle ? TITLE_BLOCK_HEIGHT : 0);
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

    private void drawPage(PDDocument document, PDFont regular, PDFont bold, float pageWidth,
                          float usableWidth, String title, boolean isFirstPage,
                          List<RenderLine> lines, int pageNumber, int totalPages) throws IOException {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        float pageHeight = page.getMediaBox().getHeight();

        try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

            drawHeader(cs, regular, pageWidth, pageHeight);

            float y;
            if (isFirstPage) {
                y = drawTitleBlock(cs, title, regular, bold, pageWidth, usableWidth, pageHeight);
            } else {
                y = pageHeight - MARGIN_TOP;
            }

            for (RenderLine line : lines) {
                if (line.spacer) {
                    y -= PARAGRAPH_GAP;
                    continue;
                }
                PDFont font = line.heading ? bold : regular;
                float fontSize = line.heading ? HEADING_FONT_SIZE : BODY_FONT_SIZE;
                drawTextLine(cs, line, font, fontSize, pageWidth, usableWidth, y);
                y -= LINE_HEIGHT;
            }

            drawFooter(cs, regular, pageWidth, pageNumber, totalPages);
        }
    }

    private void drawHeader(PDPageContentStream cs, PDFont regular, float pageWidth, float pageHeight)
            throws IOException {
        float y = pageHeight - 32f;

        cs.setFont(regular, FOOTER_FONT_SIZE);
        setColor(cs, MUTED);

        String right = shapeAndReorder("كتّابي · التقرير التربوي");
        float rightW = width(regular, FOOTER_FONT_SIZE, right);
        drawText(cs, right, rightEdge(pageWidth) - rightW, y);

        String right2 = shapeAndReorder("السلام عليكم ورحمة الله");
        float rightW2 = width(regular, FOOTER_FONT_SIZE, right2);
        drawText(cs, right2, rightEdge(pageWidth) - rightW - rightW2 - 14f, y);

        String left = shapeAndReorder(LocalDate.now().toString());
        drawText(cs, left, MARGIN_LEFT, y);

        setColor(cs, GOLD);
        cs.setLineWidth(0.9f);
        drawRule(cs, MARGIN_LEFT, pageHeight - 38f, pageWidth - MARGIN_RIGHT);
    }

    private float drawTitleBlock(PDPageContentStream cs, String title, PDFont regular, PDFont bold,
                                 float pageWidth, float usableWidth, float pageHeight) throws IOException {

        float cx = pageWidth / 2f;
        float y = pageHeight - MARGIN_TOP;

        y -= 4f;
        drawOrnament(cs, cx, y, 170f);

        y -= 40f;
        cs.setFont(bold, TITLE_FONT_SIZE);
        setColor(cs, GREEN);
        String titleVisual = shapeAndReorder(title);
        float titleW = width(bold, TITLE_FONT_SIZE, titleVisual);
        drawText(cs, titleVisual, cx - titleW / 2f, y);

        y -= TITLE_GAP + 6f;
        drawOrnament(cs, cx, y, 110f);

        y -= 26f;
        cs.setFont(regular, EYEBROW_FONT_SIZE);
        setColor(cs, MUTED);
        String dateVisual = shapeAndReorder("تاريخ الإصدار: " + LocalDate.now());
        float dateW = width(regular, EYEBROW_FONT_SIZE, dateVisual);
        drawText(cs, dateVisual, cx - dateW / 2f, y);

        return y - 20f;
    }

    private void drawOrnament(PDPageContentStream cs, float cx, float y, float span) throws IOException {
        setColor(cs, GOLD);
        cs.setLineWidth(0.9f);
        cs.moveTo(cx - span, y);
        cs.lineTo(cx - 9f, y);
        cs.stroke();
        cs.moveTo(cx + 9f, y);
        cs.lineTo(cx + span, y);
        cs.stroke();

        float r = 2.4f;
        cs.moveTo(cx, y - r);
        cs.lineTo(cx + r, y);
        cs.lineTo(cx, y + r);
        cs.lineTo(cx - r, y);
        cs.closeAndStroke();
    }

    private void drawTextLine(PDPageContentStream cs, RenderLine line, PDFont font, float fontSize,
                              float pageWidth, float usableWidth, float y) throws IOException {

        if (line.words.isEmpty()) {
            return;
        }

        List<Word> words = line.words;
        float totalWordWidth = 0f;
        for (Word w : words) {
            totalWordWidth += w.width;
        }

        int gaps = words.size() - 1;
        float minSpace = 4f;
        float gapWidth;

        if (line.justify && gaps > 0) {
            gapWidth = (usableWidth - totalWordWidth) / gaps;
            if (gapWidth < minSpace) {
                gapWidth = minSpace;
            }
        } else {
            gapWidth = Math.max(minSpace, usableWidth * 0.02f);
        }

        float rightEdge = pageWidth - MARGIN_RIGHT;
        float x = rightEdge;

        setColor(cs, line.heading ? GREEN : INK);
        cs.setFont(font, fontSize);
        cs.beginText();
        for (Word word : words) {
            x -= word.width;
            cs.setTextMatrix(Matrix.getTranslateInstance(x, y));
            cs.showText(word.visualText);
            x -= gapWidth;
        }
        cs.endText();

        if (line.heading && line.endOfBlock) {
            float lineWidth = totalWordWidth + gapWidth * Math.max(0, gaps);
            setColor(cs, GOLD);
            cs.setLineWidth(0.8f);
            drawRule(cs, rightEdge - lineWidth, y - 2f, rightEdge);
        }
    }

    private void drawFooter(PDPageContentStream cs, PDFont regular, float pageWidth,
                            int pageNumber, int totalPages) throws IOException {
        setColor(cs, GOLD);
        cs.setLineWidth(0.6f);
        drawRule(cs, MARGIN_LEFT, 38f, pageWidth - MARGIN_RIGHT);

        String footer = shapeAndReorder(toArabicDigits(pageNumber + " / " + totalPages));
        float footerSize = FOOTER_FONT_SIZE;
        float w = width(regular, footerSize, footer);
        float x = (pageWidth - w) / 2f;

        cs.setFont(regular, footerSize);
        setColor(cs, MUTED);
        drawText(cs, footer, x, 26f);
    }

    private void drawText(PDPageContentStream cs, String visual, float x, float y) throws IOException {
        cs.beginText();
        cs.setTextMatrix(Matrix.getTranslateInstance(x, y));
        cs.showText(visual);
        cs.endText();
    }

    private void drawRule(PDPageContentStream cs, float x1, float y, float x2) throws IOException {
        cs.moveTo(x1, y);
        cs.lineTo(x2, y);
        cs.stroke();
    }

    private void setColor(PDPageContentStream cs, float[] rgb) throws IOException {
        cs.setNonStrokingColor(rgb[0], rgb[1], rgb[2]);
        cs.setStrokingColor(rgb[0], rgb[1], rgb[2]);
    }

    private float rightEdge(float pageWidth) {
        return pageWidth - MARGIN_RIGHT;
    }

    private float leftEdge() {
        return MARGIN_LEFT;
    }
}