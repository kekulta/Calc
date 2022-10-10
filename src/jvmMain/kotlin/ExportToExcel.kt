import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.io.FileOutputStream

fun exportToExcel(model: Model) {
    val filepath = "./test.xlsx"
    writeToExcelFile(filepath)
    model.parameters.forEach {
        println(it.name)
        println("${it.row}:${it.column}  ${it.row[0].code}:${it.column[0].code}")
        writeToExcelFile(filepath = filepath, row = it.row, column = it.column, value = it.value.value)
    }
}


fun writeToExcelFile(
    filepath: String,
    row: String = "1",
    column: String = "A",
    value: String = "ROW($row):COLUMN($column)"
) {
    val inputStream = FileInputStream(filepath)
    //Instantiate Excel workbook using existing file:
    val xlWb = WorkbookFactory.create(inputStream)
    //Get reference to first sheet:
    val xlWs = xlWb.getSheetAt(0)

    //Get number values from strings
    val rowNumber = row.toInt() - 1
    val columnNumber = column[0].code - 65

    //Write text value to cell located at ROW_NUMBER / COLUMN_NUMBER:
    try {
        xlWs.getRow(rowNumber).createCell(columnNumber).setCellValue(value)
    } catch (E: Exception) {
        println("There is no row with number $rowNumber")
        xlWs.createRow(rowNumber).createCell(columnNumber).setCellValue(value)
    }

    //Write file:
    val outputStream = FileOutputStream(filepath)
    xlWb.write(outputStream)
    xlWb.close()
}

