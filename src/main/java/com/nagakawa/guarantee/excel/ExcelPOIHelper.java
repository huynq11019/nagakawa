/**
 *
 */
package com.nagakawa.guarantee.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 * @author LinhLH
 *
 */
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.nagakawa.guarantee.util.DateUtils;
import com.nagakawa.guarantee.util.FileUtil;
import com.nagakawa.guarantee.util.GetterUtil;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelPOIHelper {
	public Map<Integer, List<SimpleCell>> readExcel(String fileLocation) throws IOException {

		Map<Integer, List<SimpleCell>> data = new HashMap<>();
		
		FileInputStream fis = new FileInputStream(new File(fileLocation));

		String ext = FileUtil.getFileExtension(fileLocation);
		
		if (ext.equalsIgnoreCase(FileUtil.XLS)) {
			data = readHSSFWorkbook(fis);
		} else if (ext.equalsIgnoreCase(FileUtil.XLSX)) {
			data = readXSSFWorkbook(fis);
		}

		int maxNrCols = data.values().stream().mapToInt(List::size).max().orElse(0);

		data.values().stream().filter(ls -> ls.size() < maxNrCols).forEach(ls -> {
			IntStream.range(ls.size(), maxNrCols).forEach(i -> ls.add(new SimpleCell(StringPool.BLANK)));
		});

		return data;
	}

	public String readCellContent(Cell cell) {
		String content;

		switch (cell.getCellType()) {

		case STRING:
			content = GetterUtil.getString(cell.getStringCellValue());

			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {

				content = DateUtils.formatLongDate(cell.getDateCellValue());
			} else {
				content = new BigDecimal(cell.getNumericCellValue()).toString();
			}

			break;
		case BOOLEAN:
			content = cell.getBooleanCellValue() + "";

			break;
		case FORMULA:
			content = cell.getCellFormula() + "";

			break;
		default:
			content = "";
		}

		return content;
	}

	private Map<Integer, List<SimpleCell>> readHSSFWorkbook(FileInputStream fis) throws IOException {
		Map<Integer, List<SimpleCell>> data = new HashMap<>();
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(fis);

			HSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);

				data.put(i, new ArrayList<>());

				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						HSSFCell cell = row.getCell(j);

						if (cell != null) {
							HSSFCellStyle cellStyle = cell.getCellStyle();

							SimpleCell SimpleCell = new SimpleCell();

							HSSFColor bgColor = cellStyle.getFillForegroundColorColor();

							if (bgColor != null) {
								short[] rgbColor = bgColor.getTriplet();
								SimpleCell
										.setBgColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
							}

							HSSFFont font = cell.getCellStyle().getFont(workbook);

							SimpleCell.setTextSize(font.getFontHeightInPoints() + "");

							if (font.getBold()) {
								SimpleCell.setTextWeight("bold");
							}

							HSSFColor textColor = font.getHSSFColor(workbook);

							if (textColor != null) {
								short[] rgbColor = textColor.getTriplet();
								SimpleCell.setTextColor(
										"rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
							}

							SimpleCell.setContent(readCellContent(cell));

							data.get(i).add(SimpleCell);
						} else {
							data.get(i).add(new SimpleCell(""));
						}
					}
				}
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return data;
	}

	private Map<Integer, List<SimpleCell>> readXSSFWorkbook(FileInputStream fis) throws IOException {
		XSSFWorkbook workbook = null;
		Map<Integer, List<SimpleCell>> data = new HashMap<>();
		try {

			workbook = new XSSFWorkbook(fis);

			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);

				data.put(i, new ArrayList<>());

				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						XSSFCell cell = row.getCell(j);

						if (cell != null) {
							XSSFCellStyle cellStyle = cell.getCellStyle();

							SimpleCell SimpleCell = new SimpleCell();

							XSSFColor bgColor = cellStyle.getFillForegroundColorColor();

							if (bgColor != null) {
								byte[] rgbColor = bgColor.getRGB();

								SimpleCell.setBgColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0])
										+ "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + ","
										+ (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
							}
							XSSFFont font = cellStyle.getFont();

							SimpleCell.setTextSize(font.getFontHeightInPoints() + "");

							if (font.getBold()) {
								SimpleCell.setTextWeight("bold");
							}

							XSSFColor textColor = font.getXSSFColor();

							if (textColor != null) {
								byte[] rgbColor = textColor.getRGB();
								SimpleCell.setTextColor("rgb(" + (rgbColor[0] < 0 ? (rgbColor[0] + 0xff) : rgbColor[0])
										+ "," + (rgbColor[1] < 0 ? (rgbColor[1] + 0xff) : rgbColor[1]) + ","
										+ (rgbColor[2] < 0 ? (rgbColor[2] + 0xff) : rgbColor[2]) + ")");
							}

							SimpleCell.setContent(readCellContent(cell));

							data.get(i).add(SimpleCell);
						} else {
							data.get(i).add(new SimpleCell(""));
						}
					}
				}
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return data;
	}

	public void createCell(Row row, int index, CellStyle style, String message) {
		Cell cell = row.createCell(index, CellType.STRING);
		cell.setCellValue(message);

		cell.setCellStyle(style);

		row.setRowStyle(style);
	}

	public CellStyle createCellStyle(Workbook workbook, short fontIndex) {
		CellStyle style = workbook.createCellStyle();

		Font font = workbook.createFont();

		font.setColor(fontIndex);

		style.setFont(font);

		return style;
	}

	public CellStyle createErrorCellStyle(Workbook workbook) {
		return createCellStyle(workbook, IndexedColors.RED.getIndex());
	}

	public CellStyle createSuccessCellStyle(Workbook workbook) {
		return createCellStyle(workbook, IndexedColors.BLUE.getIndex());
	}

	public boolean isValidHeader(Sheet sheet, int[] headerIndexs, List<String[]> headerTexts) {
		if (headerIndexs.length != headerTexts.size()) {
			return false;
		}

		for (int i = 0; i < headerIndexs.length; i++) {
			if (!isValidHeader(sheet, headerIndexs[i], headerTexts.get(i))) {
				return false;
			}
		}

		return true;

	}
	
	public boolean isValidHeader(Sheet sheet, int headerIndex, String... headerTexts) {
		Row headerRow = sheet.getRow(headerIndex);

		if (headerRow == null) {
			return false;
		}

		int headerSize = headerTexts.length;

		if (headerRow.getPhysicalNumberOfCells() < headerSize || headerRow.getLastCellNum() < headerSize - 1) {
			return false;
		}

		for (int i = 0; i < headerSize; i++) {
			Cell cell = headerRow.getCell(i);

			if (cell == null) {
				return false;
			}

			String value = readCellContent(cell);

			if ((value == null && headerTexts[i] != null)
					|| !value.equalsIgnoreCase(headerTexts[i])) {
				return false;
			}
		}

		return true;
	}

	public List<String[]> getHeaderText(InputStream is, int sheetIndex, int[] headerRowIndexs) {
		List<String[]> headerTexts = new ArrayList<>();

		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheetAt(sheetIndex);

			for (int index : headerRowIndexs) {
				List<String> headerText = new ArrayList<>();
				
				Row headerRow = null;

				if (Validator.isNull(sheet) || Validator.isNull(headerRow = sheet.getRow(index))) {
					return null;
				}

				for (int i = 0; i < headerRow.getLastCellNum(); i++) {
					Cell cell = headerRow.getCell(i);

					if (cell == null) {
						continue;
					}

					String value = readCellContent(cell);

					headerText.add(value);
				}
				
				headerTexts.add(headerText.toArray(new String[0]));
			}

		} catch (Exception e) {
			_log.error("Cannot read the template header");
		}

		return headerTexts;
	}
	
	public String[] getHeaderText(InputStream is, int sheetIndex, int headerRowIndex) {
		List<String> headerTexts = new ArrayList<>();
		
		try (Workbook workbook = new XSSFWorkbook(is)){
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			
			Row headerRow = null;

			if (Validator.isNull(sheet)
					|| Validator.isNull(headerRow = sheet.getRow(headerRowIndex))) {
				return null;
			}
			
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				Cell cell = headerRow.getCell(i);

				if (cell == null) {
					continue;
				}

				String value = readCellContent(cell);
				
				headerTexts.add(value);
			}
		} catch (Exception e) {
			_log.error("Cannot read the template header");
		}
		
		return headerTexts.toArray(new String[0]);
	}
	
	public boolean checkIfRowIsEmpty(Row row) {
		if (row == null || row.getLastCellNum() <= 0) {
			return true;
		}

		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			
			Cell cell = row.getCell(cellNum);
			
			if (cell != null && !cell.getCellType().equals(CellType.BLANK)
					&& Validator.isNotNull(cell.toString())) {
				return false;
			}
		}
		
		return true;
	}
}
