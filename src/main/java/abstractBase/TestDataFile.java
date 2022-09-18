package abstractBase;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

public class TestDataFile {
	WebDriver driver;
	
	public TestDataFile(WebDriver driver) {
		this.driver=driver;	
	}
	
	public static String path = "D:\\New Project\\ResolverUITest\\ExcelSheets";
	
	public String readData(String dataFile, String sheetName, String ColumnByText, String rowByText) throws Exception {
		
		DataFormatter dataFormatter = new DataFormatter();
		
		String fileName = dataFile;
		File file = new File(path + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){
			Workbook = new XSSFWorkbook(inputStream);
		}
		else if(fileExtensionName.equals(".xls")){
			Workbook = new HSSFWorkbook(inputStream);
		}
		Sheet Sheet = Workbook.getSheet(sheetName);
		
		//=======================================================
		int LastRow = Sheet.getLastRowNum();
		int FirstRow = Sheet.getFirstRowNum();
		int LastColOfFirstRow = Sheet.getRow(0).getLastCellNum();
	
	 	System.out.println("LastRow: " + Sheet.getLastRowNum());
		System.out.println("FirstRow: "+Sheet.getFirstRowNum());
		System.out.println("LastColumn of First Row: " + Sheet.getRow(0).getLastCellNum());
	 
		//=======================================================
		
		//==================================================
				int colNumByText = 0;
				for(int col=0;col<LastColOfFirstRow;col++) {
					String valueOfFirstRow = Sheet.getRow(0).getCell(col).getStringCellValue();
					if(valueOfFirstRow.equalsIgnoreCase(ColumnByText)) {
						colNumByText = col;
					}
				}
				if(colNumByText == 0) {
					System.out.println("No Data Exist.");
				}
				System.out.println("Col Num: "+ colNumByText);
		//==================================================
		
		int rowNum=0;
		int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();
		System.out.println("Row count: " + rowCount);
		for (int i = 0; i < rowCount+1; i++) {
			Row row = Sheet.getRow(i);
			String data = "";
			data = dataFormatter.formatCellValue(row.getCell(0));
			if(data.equalsIgnoreCase(rowByText)) {
				rowNum = i;
			}
		}
		
		String dataSheetValue = dataFormatter.formatCellValue(Sheet.getRow(rowNum).getCell(colNumByText));
		System.out.println(dataSheetValue);
		
		return dataSheetValue;
	}
	

}
