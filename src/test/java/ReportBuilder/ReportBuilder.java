package ReportBuilder;

import Assistant.ReportJSONEntity;
import Assistant.UrlSystemAssistant;
import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportBuilder {

    private JSONObject jsonObject;
    private JSONParser parser = new JSONParser();
    private ReportJSONEntity entity = new ReportJSONEntity();
    private PdfPTable table = new PdfPTable(3); //4
    private Document document = new Document();

    public void pdfBuilderBatch() throws FileNotFoundException, DocumentException {
        setupPDFConfiguration();
        ArrayList<ReportJSONEntity> listReportJSONEntity = listGsonFromJson();

        for (int i =0;i<listReportJSONEntity.size();i++){
            fillPDFReport(listReportJSONEntity.get(i));
        }
        document.add(table);
        document.close();

    }

    public JSONObject jsonReader(String sourceJson){
        try {
            //String pathDir = "C:/Project/AllureReportTest/target/allure-results/";
            jsonObject = (JSONObject) parser.parse(new FileReader(UrlSystemAssistant.PATH_DIR_JSON_TESTS + sourceJson));
            //readGsonFromJson(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public ArrayList<String> listJsonReader(){
        File dir = new File(UrlSystemAssistant.PATH_DIR_JSON_TESTS);
        ArrayList<String> listJsonNames = new ArrayList<>();
        for (File fileEntry: dir.listFiles()) {
            listJsonNames.add(fileEntry.getName());
        }
        return listJsonNames;
    }

    public static void clearDirectory(){
        File dir = new File(UrlSystemAssistant.PATH_DIR_JSON_TESTS);
        for (File file:dir.listFiles()) {
            if(!file.isDirectory()){
                file.delete();
            }
        }
    }

    public String getDateTime(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public String configureNameFile(){
        String name = "AutomationReport_";
        Date _date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String date = formatter.format(_date);
        String source = UrlSystemAssistant.PATH_DIR_REPORTS;
        String extension = ".pdf";
        String concatName = source+name+date+extension;
        return concatName;

    }

    public ReportJSONEntity readGsonFromJson(String obj){
        Gson gson = new Gson();
        entity = gson.fromJson(obj, ReportJSONEntity.class);
        return entity;
    }

    public ArrayList<ReportJSONEntity> listGsonFromJson(){
        File dir = new File(UrlSystemAssistant.PATH_DIR_JSON_TESTS);
        ArrayList<ReportJSONEntity>listEntity = new ArrayList<>();
        for (String fileEntry:listJsonReader()) {
            jsonObject = jsonReader(fileEntry);
            ReportJSONEntity ReportJSONEntity = readGsonFromJson(jsonObject.toJSONString());
            listEntity.add(ReportJSONEntity);
        }
        return listEntity;
    }

    private void setupPDFConfiguration() throws FileNotFoundException, DocumentException {

        Font font = new Font();
        font.setSize(18);
        font.isBold();

        PdfWriter.getInstance(document, new FileOutputStream(configureNameFile()));
        document.open();
        document.add(new Paragraph("Automation Tests Report - " + getDateTime(),font));
        document.addCreationDate();

        table.setWidthPercentage(100);//Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f);//Space after table

    }

    private void fillPDFReport(ReportJSONEntity _entity) throws DocumentException {

        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f /*, 1f*/};
        table.setWidths(columnWidths);
        table.addCell("Name");
        table.addCell("Description");
        table.addCell("Status");

        PdfPCell nameTest = new PdfPCell(new Paragraph(_entity.name));
        //cell1.setBorderColor(BaseColor.BLUE);
        nameTest.setPaddingLeft(10);
        nameTest.setHorizontalAlignment(Element.ALIGN_LEFT);
        nameTest.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell statusTest = new PdfPCell(new Paragraph(_entity.status));
        if (_entity.status.equalsIgnoreCase("passed")) {
            statusTest = new PdfPCell(new Paragraph("Passed"));
            statusTest.setBackgroundColor(BaseColor.GREEN);
        } else {
            statusTest.setBackgroundColor(BaseColor.RED);
        }
        statusTest.setPaddingLeft(10);
        statusTest.setHorizontalAlignment(Element.ALIGN_CENTER);
        statusTest.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell descriptionTest = new PdfPCell(new Paragraph(_entity.description));
        //cell3.setBorderColor(BaseColor.RED);
        descriptionTest.setPaddingLeft(10);
        descriptionTest.setHorizontalAlignment(Element.ALIGN_LEFT);
        descriptionTest.setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.addCell(nameTest);
        table.addCell(descriptionTest);
        table.addCell(statusTest);

        if (_entity.status.equalsIgnoreCase("failed")||_entity.status.equalsIgnoreCase("broken")){
            PdfPCell detailTest = new PdfPCell(new Paragraph("Details: "+_entity.statusDetails.message));
            detailTest.setBorderColor(BaseColor.RED);
            detailTest.setColspan(3);
            table.addCell(detailTest);
        } else {
            PdfPCell detailTest = new PdfPCell(new Paragraph("Details: "+"Test executed successfully"));
            detailTest.setColspan(3);
            table.addCell(detailTest);
        }

        PdfPCell suiteTest = new PdfPCell(new Paragraph("Suite: "+_entity.labels.get(0).value));
        suiteTest.setColspan(3);
        table.addCell(suiteTest);

        Chunk dotLine = new Chunk(new DottedLineSeparator());
        PdfPCell separator = new PdfPCell(new Paragraph(dotLine));
        separator.setBackgroundColor(BaseColor.LIGHT_GRAY);
        separator.setColspan(3);
        table.addCell(separator);
    }

}
