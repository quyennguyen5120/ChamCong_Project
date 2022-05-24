package com.example.todoapi.services.ServiceImpl;

import com.example.todoapi.dtos.SalaryDto;
import com.example.todoapi.dtos.StaffDTO;
import com.example.todoapi.dtos.StaffSalaryDTO;
import com.example.todoapi.dtos.TimekeepingDTO;
import com.example.todoapi.entities.SalaryEntity;
import com.example.todoapi.entities.StaffEntity;
import com.example.todoapi.entities.Timekeeping;
import com.example.todoapi.repositories.SalaryRepository;
import com.example.todoapi.repositories.StaffRepository;
import com.example.todoapi.repositories.TimeKeepingRepository;
import com.example.todoapi.services.SalaryService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    StaffRepository staffRepository;

    public List<SalaryDto> getAll(){
        return  salaryRepository.getAll();
    }

    public SalaryDto findById(Long id){
        return new SalaryDto(salaryRepository.findById(id).get());
    }

    public boolean deleteById(Long id){
        try {
            salaryRepository.deleteById(id);
            return true;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public SalaryDto saveOrUpdate(SalaryDto salaryDto){
        SalaryEntity salaryEntity = null;
        if (salaryDto.getId() != null){
            salaryEntity = salaryRepository.findById(salaryDto.getId()).get();
        }else {
            salaryEntity = new SalaryEntity();
        }
        salaryEntity.setSalary(salaryDto.getSalary());
        salaryRepository.save(salaryEntity);
        return new SalaryDto(salaryEntity);
    }

    public StaffSalaryDTO calculateSalary(Long staffId, int month, int year){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate1 = yearMonth.atDay(1);
        LocalDate localDate2 = yearMonth.atEndOfMonth();
        Date firstOfMonth = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date lastOfMonth = Date.from(localDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Timekeeping> timekeepingList = timeKeepingRepository.findAllByTimeStartBetween(firstOfMonth, lastOfMonth);
        List<TimekeepingDTO> timekeepingDTOList = new ArrayList<>();
        timekeepingList.forEach(t -> {
            TimekeepingDTO timekeepingDTO = new TimekeepingDTO(t);
            timekeepingDTOList.add(timekeepingDTO);
        });

        double heSoLuong = 0;
        double luong = 0D;

        StaffEntity staffEntity = staffRepository.findById(staffId).get();
        StaffDTO staffDTO = new StaffDTO(staffEntity);

        for (TimekeepingDTO t: timekeepingDTOList){
            if(t.getEndStart() != null) {
                LocalDateTime startTime = LocalDateTime.ofInstant(t.getTimeStart().toInstant(), ZoneId.systemDefault());
                LocalDateTime endTime = LocalDateTime.ofInstant(t.getEndStart().toInstant(), ZoneId.systemDefault());

                double timesBetween = Duration.between(startTime, endTime).toMinutes();
                timesBetween = timesBetween / 60;

                if (timesBetween > 8)
                    timesBetween = 8;
                if (t.getDimuon() != null)
                    timesBetween -= t.getDimuon().doubleValue() / 60;
                if (t.getXinLamThem() != null) {
                    if (t.getXinLamThem())
                        timesBetween += t.getLamthem().doubleValue() / 60;
                }
                if (t.getXinVeSom() != null) {
                    if (!t.getXinVeSom())
                        timesBetween -= t.getVesom().doubleValue() / 60;
                } else {
                    if (t.getVesom() != null)
                        timesBetween -= t.getVesom().doubleValue() / 60;
                }

                heSoLuong += timesBetween / 8;
            }
        }

        if(staffEntity.getSalaryEntity() != null)//####
            luong = heSoLuong * staffEntity.getSalaryEntity().getSalary();

        return new StaffSalaryDTO(staffDTO, heSoLuong, luong);
    }

    @Override
    public Workbook exportBySearchDto(HttpServletResponse response) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Report");
            sheet.addMergedRegion(new CellRangeAddress(0, 0 , 0, 3));

            List<StaffEntity> listStaffEntity = staffRepository.findAll();
            List<StaffSalaryDTO> listStaffSalaryDTO = new ArrayList<>();

            Date date = new Date();
            int month = date.getMonth();

            for (StaffEntity s : listStaffEntity){
                listStaffSalaryDTO.add(calculateSalary(s.getId(),month + 1));
            }

            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(HSSFFont.COLOR_NORMAL);
            font.setBold(true);

            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(font);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setWrapText(false);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);

            HSSFFont fontcontent = workbook.createFont();
            fontcontent.setFontHeightInPoints((short) 11);
            fontcontent.setColor(HSSFFont.COLOR_NORMAL);

            HSSFCellStyle contentCellStyle = workbook.createCellStyle();
            contentCellStyle.setFont(fontcontent);
            contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
            contentCellStyle.setWrapText(false);
            contentCellStyle.setBorderBottom(BorderStyle.THIN);
            contentCellStyle.setBorderLeft(BorderStyle.THIN);
            contentCellStyle.setBorderRight(BorderStyle.THIN);
            contentCellStyle.setBorderTop(BorderStyle.THIN);

            HSSFCellStyle contentCellStyleDate = workbook.createCellStyle();
            contentCellStyleDate.setFont(fontcontent);
            contentCellStyleDate.setAlignment(HorizontalAlignment.CENTER);
            contentCellStyleDate.setWrapText(false);
            contentCellStyleDate.setBorderBottom(BorderStyle.THIN);
            contentCellStyleDate.setBorderLeft(BorderStyle.THIN);
            contentCellStyleDate.setBorderRight(BorderStyle.THIN);
            contentCellStyleDate.setBorderTop(BorderStyle.THIN);
            contentCellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

            Integer rowIndex = 0;
            Integer cellIndex = 0;
            Row rowHeader = sheet.createRow(rowIndex);
            Cell cellHeader = null;

            rowHeader.setHeightInPoints(33);
            cellHeader = rowHeader.createCell(cellIndex);
            cellHeader.setCellValue("THỐNG KÊ LƯƠNG");
            cellHeader.setCellStyle(headerCellStyle);

            rowHeader = sheet.createRow(rowIndex +=1);
            cellHeader = rowHeader.createCell(cellIndex);
            cellHeader.setCellValue("STT");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex +=1);
            cellHeader.setCellValue("Tên nhân viên ");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Hệ số lương ");
            cellHeader.setCellStyle(headerCellStyle);

            cellHeader = rowHeader.createCell(cellIndex += 1);
            cellHeader.setCellValue("Lương ");
            cellHeader.setCellStyle(headerCellStyle);

            Integer numberOfItem = 0;
            for (StaffSalaryDTO item : listStaffSalaryDTO) {
                numberOfItem +=1;
                rowIndex += 1;
                cellIndex = 0;
                rowHeader = sheet.createRow(rowIndex);
                cellHeader = rowHeader.createCell(cellIndex);
                cellHeader.setCellValue(numberOfItem);
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex +=1);
                cellHeader.setCellValue(item.getStaffDTO().getFullname());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getHeSoLuong());
                cellHeader.setCellStyle(contentCellStyle);

                cellHeader = rowHeader.createCell(cellIndex += 1);
                cellHeader.setCellValue(item.getLuong());
                cellHeader.setCellStyle(contentCellStyle);
            }
            sheet.setColumnWidth(0, 7500);
            sheet.setColumnWidth(1, 7500);
            sheet.setColumnWidth(2, 7500);
            sheet.setColumnWidth(3, 7500);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "attachment; filename=ReportsData.xls");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return workbook;
    }
}
