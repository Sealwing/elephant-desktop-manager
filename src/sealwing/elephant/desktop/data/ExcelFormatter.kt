package sealwing.elephant.desktop.data

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import sealwing.elephant.desktop.converter.getRussianTitle
import sealwing.elephant.desktop.core.*

/*
    Trying to follow clear and functional style
 */

// CONVERTION TO XLSX

fun toXLSX(deal: Deal): XSSFWorkbook {
    val book = XSSFWorkbook()
    for (construction in deal.constructionList) {
        createSheet(construction, book)
    }
    //addSupplements(sheet, construction.supplementList, rowAcc)
    return book
}

// Row acc stands for row number accumulation

private fun createSheet(construction: Construction,  book: XSSFWorkbook): Sheet {
    val sheet = book.createSheet(getRussianTitle(construction.type))
    var rowAcc = 1
    rowAcc = addHeader(sheet, construction.type, construction.width, construction.height, rowAcc)
    rowAcc = addInnerPart(sheet, construction.fitWidth, construction.fitHeight, construction.type, rowAcc)
    addProfiles(sheet, construction.profilesList, rowAcc)
    return sheet
}

// Return of Int is returning of the next unused row, so to follow it in next calculations

private fun addHeader(sheet: XSSFSheet, type: ConstructionType, width: Int, height: Int, startRow: Int): Int {
    var rowAcc: Int = startRow
    val nameRow = sheet.createRow(rowAcc)
    rowAcc++
    nameRow.createCell(1).setCellValue("Конструкция")
    nameRow.createCell(2).setCellValue(getRussianTitle(type))
    sheet.createRow(rowAcc).createCell(1).setCellValue("Размеры")
    rowAcc++
    val sizeRow = sheet.createRow(rowAcc)
    rowAcc++
    sizeRow.createCell(1).setCellValue("Ширина: $width")
    sizeRow.createCell(2).setCellValue("Высота: $height")
    return rowAcc
}

private fun addInnerPart(sheet: XSSFSheet, fitWidth: Float, fitHeight: Float, type: ConstructionType, startRow: Int): Int {
    var rowAcc: Int = startRow
    sheet.createRow(rowAcc).createCell(1).setCellValue("Заполнение")
    rowAcc++
    if (type != ConstructionType.TRIPLE) {
        val row = sheet.createRow(rowAcc)
        rowAcc++
        row.createCell(1).setCellValue("Ширина: $fitWidth")
        row.createCell(2).setCellValue("Высота: $fitHeight")
    } else {
        val firstRow = sheet.createRow(rowAcc)
        rowAcc++
        firstRow.createCell(1).setCellValue("Ширина: $fitWidth")
        firstRow.createCell(2).setCellValue("Высота: $fitHeight")
        firstRow.createCell(3).setCellValue("2 шт.")
        val secondRow = sheet.createRow(rowAcc)
        rowAcc++
        // change value to bigger fit
        secondRow.createCell(1).setCellValue("Ширина: $fitWidth")
        secondRow.createCell(2).setCellValue("Высота: $fitHeight")
        secondRow.createCell(3).setCellValue("1 шт.")
    }
    return rowAcc
}

private fun addProfiles(sheet: XSSFSheet, profilesList: ArrayList<Profile>, startRow: Int): Int {
    var rowAcc: Int = startRow
    sheet.createRow(startRow).createCell(1).setCellValue("Профили")
    rowAcc++
    // add profiles
    for ((type, length, amount) in profilesList){
        val row = sheet.createRow(startRow)
        rowAcc++
        row.createCell(1).setCellValue(getRussianTitle(type))
        row.createCell(2).setCellValue("${length}мм.")
        row.createCell(3).setCellValue("${amount}шт.")
    }
    return rowAcc
}

private fun addSupplements(sheet: XSSFSheet, supplementList: HashMap<SupplementType, Int>, startRow: Int): Int {
    var rowAcc = startRow
    return rowAcc
}

// SAVING

fun saveToExcel(book: XSSFWorkbook) {

}