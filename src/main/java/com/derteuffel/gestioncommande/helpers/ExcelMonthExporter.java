package com.derteuffel.gestioncommande.helpers;

import com.derteuffel.gestioncommande.entities.Caisse;
import com.derteuffel.gestioncommande.entities.Mouvement;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelMonthExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private Caisse caisse;

   private List<Mouvement> mouvements;

    private Double sortieDollar;
    private Double entrerDollar;
    private Double entrerFranc;
    private Double sortieFranc;

    public ExcelMonthExporter(Caisse caisse, List<Mouvement> mouvements, Double sortieDollar,
                              Double entrerDollar, Double entrerFranc, Double sortieFranc) {
        this.caisse = caisse;
        this.mouvements = mouvements;
        this.sortieDollar = sortieDollar;
        this.entrerDollar = entrerDollar;
        this.entrerFranc = entrerFranc;
        this.sortieFranc = sortieFranc;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet(caisse.getMois()+" - "+caisse.getAnnee());


        int firstRow = 0;
        int lastRow = 2;
        int firstCol = 0;
        int lastCol = 5;
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
        Row rowTitle = sheet.createRow(0);
        Row rowHeader = sheet.createRow(4);

        CellStyle styleTitle = workbook.createCellStyle();
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeight(22);
        styleTitle.setFont(fontTitle);

        CellStyle styleHeader = workbook.createCellStyle();
        XSSFFont fontHeader = workbook.createFont();
        fontHeader.setBold(true);
        fontHeader.setFontHeight(18);
        styleHeader.setFont(fontHeader);

        createCell(rowTitle, 0, "SOLUTION AFRICA SARL, Faite vous connaitre", styleTitle);

        createCell(rowHeader, 0, "Numero", styleHeader);
        createCell(rowHeader, 1, "Date", styleHeader);
        createCell(rowHeader, 2, "Motif", styleHeader);
        createCell(rowHeader, 3, "Type", styleHeader);
        createCell(rowHeader, 4, "Montant", styleHeader);
        createCell(rowHeader, 5, "Solde/caisse", styleHeader);



    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    private void writeDataLines() {
        int rowCount = 5;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Mouvement mouvement : mouvements) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, mouvement.getNumMouvement(), style);
            createCell(row, columnCount++, mouvement.getCreatedDate(), style);
            createCell(row, columnCount++, mouvement.getLibelle(), style);
            createCell(row, columnCount++, mouvement.getType(), style);
            createCell(row, columnCount++, mouvement.getMontantDollard()+" USD - "+mouvement.getMontantFranc()+" CDF", style);
            createCell(row, columnCount++, mouvement.getSoldeFinDollard()+" USD - "+mouvement.getSoldeFinFranc()+" CDF", style);

        }
    }


    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
