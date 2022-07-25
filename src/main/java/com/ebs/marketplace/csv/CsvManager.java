package com.ebs.marketplace.csv;

import com.ebs.marketplace.mapper.CheckedFilesMapper;
import com.ebs.marketplace.mapper.ProductMapper;
import com.ebs.marketplace.model.CheckedFiles;
import com.ebs.marketplace.model.Product;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.postgresql.copy.CopyIn;
import org.postgresql.copy.CopyManager;
import org.postgresql.copy.PGCopyOutputStream;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

@Component
public class CsvManager {

    private final ProductMapper productMapper;
    private final CheckedFilesMapper checkedFilesMapper;
    @Value("${file.path}")
    private String filePath;
    @Value("${table.name}")
    private String tableName;

    @Autowired
    public CsvManager(ProductMapper productMapper, CheckedFilesMapper checkedFilesMapper) {
        this.productMapper = productMapper;
        this.checkedFilesMapper = checkedFilesMapper;
    }

    @Scheduled(cron = "${schedule.timeout}")
    public void csvToSql() throws IOException {
            try (Stream<Path> paths = Files.walk(Paths.get(filePath))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(pathName -> {
                            try {
                                Connection connection = createConnection();
                                Writer writer = defineWriter(connection);
                                if (!pathName.getFileName().toString().startsWith("start")
                                        || checkedFilesMapper.existsByName(pathName.getFileName().toString()) > 0) return;
                                CsvToBean<Product> bean = new CsvToBeanBuilder<Product>
                                        (new FileReader(pathName.toString())).withType(Product.class).build();
                                writeDataToSql(writer, bean, connection);
                                checkedFilesMapper.insert(new CheckedFiles(pathName.getFileName().toString()));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/marketplace2", "postgres", "olanesti905");
        connection.setAutoCommit(false);
        return connection;
    }

    private Writer defineWriter(Connection connection) throws SQLException {
        CopyManager copyManager = new CopyManager((BaseConnection) connection);
        CopyIn copyIn = copyManager.copyIn("COPY " + tableName + " FROM STDIN WITH CSV");
        return new OutputStreamWriter(new PGCopyOutputStream(copyIn), StandardCharsets.UTF_8);
    }

    private void writeDataToSql(Writer writer, CsvToBean<Product> bean, Connection connection) throws IOException, SQLException {
        for (Product product : bean) {
            if (productMapper.existsById(product.getId()) == 0) writer.write(product.toCsv());
        }

        writer.close();
        connection.commit();
        connection.close();
    }
}
