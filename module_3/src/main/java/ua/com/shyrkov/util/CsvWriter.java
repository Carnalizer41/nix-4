package ua.com.shyrkov.util;

import com.opencsv.CSVWriter;
import ua.com.shyrkov.dto.TransactionInfoDto;
import ua.com.shyrkov.dto.TransactionsReportDto;
import ua.com.shyrkov.exception.DataLayerException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {

    public static void writeReportToCsv(String fileName, TransactionsReportDto transactionsReport)
            throws DataLayerException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true))) {
            writer.writeNext(new String[]{"id", "Date", "Amount", "Description", "Transaction type", "Categories"});
            writer.writeAll(transactionsInfoToStrings(transactionsReport.getTransactions()));
            writer.writeNext(new String[]{"","","","","",""});
            writer.writeNext(new String[]{"Total income:", String.valueOf(transactionsReport.getTotalIncome()),"",""});
            writer.writeNext(new String[]{"Balance:", String.valueOf(transactionsReport.getBalance()),"",""});
        } catch (Exception e) {
            throw new DataLayerException("Can`t write to csv: " + e.getMessage());
        }
    }

    private static List<String[]> transactionsInfoToStrings(List<TransactionInfoDto> transactions){
        List<String[]> result = new ArrayList<>();
        for (TransactionInfoDto transaction : transactions) {
            StringBuilder categories = new StringBuilder();
            for (String category : transaction.getCategories()) {
                categories.append(category).append(";");
            }
            result.add(new String[]{
                    String.valueOf(transaction.getId()),
                    String.valueOf(transaction.getTimestamp()),
                    String.valueOf(transaction.getAmount()),
                    transaction.getDescription(),
                    transaction.getTransactionType(),
                    categories.toString()
            });
        }
        return result;
    }
}
