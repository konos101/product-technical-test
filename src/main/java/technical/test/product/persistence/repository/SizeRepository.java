package technical.test.product.persistence.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import technical.test.product.persistence.entity.Size;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SizeRepository {

    private static final String CSV_FILE_PATH = "src/main/resources/datasource/size-1.csv";

    public List<Size> getAll() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            return csvParser.stream().map(record -> Size.builder()
                    .id(Integer.valueOf(record.get(0)))
                    .productId(Integer.valueOf(record.get(1)))
                    .backSoon(Boolean.valueOf(record.get(2)))
                    .special(Boolean.valueOf(record.get(3)))
                    .build()).toList();

        }
    }
}
