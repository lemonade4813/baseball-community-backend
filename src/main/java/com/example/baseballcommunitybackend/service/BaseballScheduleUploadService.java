package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.Schedule;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaseballScheduleUploadService {

    public List<Schedule> parseBaseballExcel(InputStream is) throws IOException {
        List<Schedule> schedules = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Schedule schedule = new Schedule();

            schedule.setMonth(formatter.formatCellValue(row.getCell(0)));
            schedule.setDate(formatter.formatCellValue(row.getCell(1)));
            schedule.setTime(formatter.formatCellValue(row.getCell(2)));
            schedule.setStadium(formatter.formatCellValue(row.getCell(3)));
            schedule.setDay(formatter.formatCellValue(row.getCell(4)));
            schedule.setAwayTeam(formatter.formatCellValue(row.getCell(5)));
            schedule.setHomeTeam(formatter.formatCellValue(row.getCell(6)));

            Cell notesCell = row.getCell(7);
            schedule.setNotes(notesCell != null ? formatter.formatCellValue(notesCell) : "-");

            schedules.add(schedule);
        }
        workbook.close();
        return schedules;
    }
}