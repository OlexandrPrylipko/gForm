package libraries;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import java.io.FileInputStream;

public class ReadExcelFile {

    private static Logger LOG = Logger.getLogger(ReadExcelFile.class);

    public static String readData(String fileName, String sheetName) {
        String strData = null;
        try {
            // Get the excel file and create an input stream for excel
            // Use XSSF for *.xlsx excel file and HSSF for *.xls excel file
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("./src/main/resources/" + fileName));

            // Get list name
            XSSFSheet workSheet = workbook.getSheet(sheetName);

            // Get the row
            XSSFRow getRow = workSheet.getRow(0);

            // Get the column
            XSSFCell getCell = getRow.getCell(0);

            // Select random value
            int i = (int) (Math.random() * workSheet.getLastRowNum());
            Cell cell = workSheet.getRow(i).getCell(0);

            switch (cell.getCellType()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        strData = String.valueOf(cell.getDateCellValue());
                    } else {
                        int value = (int) cell.getNumericCellValue();
                        strData = String.valueOf(value);
                    }
                    break;
                case STRING:
                    strData = cell.getRichStringCellValue().getString();
                    break;
            }

            workbook.close();
        } catch (Exception e) {
            LOG.error("File not found: " + e);
            Assert.fail("File not found: " + e);
        }
        return strData;
    }
}