package com.ebs.marketplace.csv;

import com.ebs.marketplace.model.Product;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.postgresql.copy.CopyIn;
import org.postgresql.copy.CopyManager;
import org.postgresql.copy.PGCopyOutputStream;
import org.postgresql.core.BaseConnection;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CsvManager {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public void csvToSql(String tableName, String path) {
        try {
            Connection connection = createConnection();
            Writer writer = defineWriter(connection, tableName);

            CsvToBean<Product> bean = new CsvToBeanBuilder<Product>(new FileReader("C:\\" + path)).withType(Product.class).build();

            writeDataToSql(writer, bean, connection);

        } catch (Exception e) {
            logger.warning(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "olanesti905");
        connection.setAutoCommit(false);
        return connection;
    }

    private Writer defineWriter(Connection connection, String tableName) throws SQLException {
        CopyManager copyManager = new CopyManager((BaseConnection) connection);
        CopyIn copyIn = copyManager.copyIn("COPY " + tableName + " FROM STDIN WITH CSV");
        return new OutputStreamWriter(new PGCopyOutputStream(copyIn), StandardCharsets.UTF_8);
    }

    private void writeDataToSql(Writer writer, CsvToBean<Product> bean, Connection connection) throws IOException, SQLException {
        for (Product product : bean) {
            writer.write(product + "\n");
        }

        writer.close();
        connection.commit();
        connection.close();
    }
}
