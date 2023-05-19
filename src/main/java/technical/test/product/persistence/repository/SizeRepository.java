package technical.test.product.persistence.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import technical.test.product.persistence.entity.Size;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class SizeRepository {

    private final String csvFilePath;

    public SizeRepository(@Value("${datasource.size.path}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<Size> getAll() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            return csvParser.stream().map(record -> Size.builder()
                    .id(Integer.valueOf(record.get(0).trim()))
                    .productId(Integer.valueOf(record.get(1).trim()))
                    .backSoon(Boolean.valueOf(record.get(2).trim()))
                    .special(Boolean.valueOf(record.get(3).trim()))
                    .build()).toList();

        }
    }
}
