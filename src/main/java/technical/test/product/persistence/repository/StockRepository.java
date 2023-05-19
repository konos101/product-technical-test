package technical.test.product.persistence.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import technical.test.product.persistence.entity.Stock;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class StockRepository {
    private final String csvFilePath;

    public StockRepository(@Value("${datasource.stock.path}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<Stock> getAll() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            return csvParser.stream().map(record -> Stock.builder()
                    .sizeId(Integer.valueOf(record.get(0).trim()))
                    .quantity(Integer.valueOf(record.get(1).trim()))
                    .build()).toList();

        }
    }
}
