package technical.test.product.persistence.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import technical.test.product.persistence.entity.Product;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/datasource/product.csv";

    public List<Product> getAll() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            return csvParser.stream().map(record -> Product.builder()
                    .id(Integer.valueOf(record.get(0).trim()))
                    .sequence(Integer.valueOf(record.get(1).trim()))
                    .build()).toList();
        }
    }
}
