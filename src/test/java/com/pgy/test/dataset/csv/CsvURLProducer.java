package com.pgy.test.dataset.csv;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.csv.CsvDataSetWriter;
import org.dbunit.dataset.csv.CsvParser;
import org.dbunit.dataset.csv.CsvParserException;
import org.dbunit.dataset.csv.CsvProducer;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.stream.DefaultConsumer;
import org.dbunit.dataset.stream.IDataSetConsumer;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link IDataSetProducer Data Set Producer} that produces datasets from
 * CVS files found at a base URL.
 * <p/>
 * Based HEAVILY on {@link CsvProducer}.
 * <p/>
 * This class is copied from dbunit, and modified to support setting encoding.
 *
 * @author Felix
 */
@SuppressWarnings("rawtypes")
public class CsvURLProducer implements IDataSetProducer {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CsvURLProducer.class);

    /**
     * the default consumer - does nothing
     */
    private static final IDataSetConsumer EMPTY_CONSUMER = new DefaultConsumer();

    /**
     * the consumer of the produced datasets, by default a
     * {@link DefaultConsumer}
     */
    private IDataSetConsumer consumer = EMPTY_CONSUMER;

    /**
     * the base url to retrieve data from
     */
    private URL base;

    /**
     * the offset from the base url where the list of tables can be found
     */
    private String tableList;

    /**
     * the encoding of the csv files
     */
    private String encoding;

    private List tables;

    /**
     * Create a CSV Data Set Producer which uses the base URL to retrieve
     * a list of tables and the data.
     *
     * @param base      the URL where the tableList and data can be found.
     * @param tableList the relative location of the list of tables.
     */
    public CsvURLProducer(URL base, String tableList, String encoding) {
        this.base = base;
        this.tableList = tableList;
        this.encoding = encoding;
    }

    public CsvURLProducer(URL base, List tables, String encoding) {
        this.base = base;
        this.tables = tables;
        this.encoding = encoding;
    }

    /*
     * @see IDataSetProducer#setConsumer(org.dbunit.dataset.stream.IDataSetConsumer)
     */
    @Override
    public void setConsumer(IDataSetConsumer consumer) throws DataSetException {
        logger.debug("setConsumer(consumer) - start");

        this.consumer = consumer;
    }

    /*
     * @see IDataSetProducer#produce()
     */
    @Override
    public void produce() throws DataSetException {
        logger.debug("produce() - start");

        consumer.startDataSet();
        try {
            List tableSpecs = getTableSpecs();
            for (Iterator tableIter = tableSpecs.iterator(); tableIter.hasNext();) {
                String table = (String) tableIter.next();
                try {
                    produceFromURL(new URL(base, table + ".csv"));
                } catch (CsvParserException e) {
                    throw new DataSetException("error producing dataset for table '" + table + "'", e);
                }

            }
            consumer.endDataSet();
        } catch (IOException e) {
            throw new DataSetException("error getting list of tables", e);
        }
    }

    private List getTableSpecs() throws IOException {
        if (tableList == null || tableList.length() == 0) {
            return tables;
        } else {
            return CsvProducer.getTables(base, tableList);
        }
    }

    /**
     * Produce a dataset from a URL.
     * The URL is assumed to contain data in CSV format.
     *
     * @param url a url containing CSV data.
     */
    private void produceFromURL(URL url) throws DataSetException {
        logger.debug("produceFromURL(url=" + url + ") - start");

        try {
            CsvParser parser = new CsvParserImpl(encoding);
            List readData = parser.parse(url);
            List readColumns = (List) readData.get(0);
            Column[] columns = new Column[readColumns.size()];

            for (int i = 0; i < readColumns.size(); i++) {
                columns[i] = new Column((String) readColumns.get(i), DataType.UNKNOWN);
            }

            String tableName = url.getFile();
            tableName = tableName.substring(tableName.lastIndexOf("/") + 1, tableName.indexOf(".csv"));
            ITableMetaData metaData = new DefaultTableMetaData(tableName, columns);
            consumer.startTable(metaData);
            for (int i = 1; i < readData.size(); i++) {
                List rowList = (List) readData.get(i);
                Object[] row = rowList.toArray();
                for (int col = 0; col < row.length; col++) {
                    if (CsvDataSetWriter.NULL.equals(row[col])) {
                        row[col] = null;
                    }
                }
                consumer.row(row);
            }
            consumer.endTable();
        } catch (CsvParserException e) {
            throw new DataSetException("error parsing CSV for URL: '" + url + "'");
        } catch (IOException e) {
            throw new DataSetException("I/O error parsing CSV for URL: '" + url + "'");
        }
    }
}