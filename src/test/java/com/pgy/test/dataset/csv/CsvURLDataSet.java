package com.pgy.test.dataset.csv;

import java.net.URL;
import java.util.List;

import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.csv.CsvDataSet;

/**
 * This class constructs an IDataSet given a base URL containing CSV
 * files. It handles translations of "null" (the string), into null.
 * Based HEAVILY on {@link CsvDataSet}
 * <p/>
 * This class is copied from dbunit, and modified to support setting encoding.
 *
 * @author unkown
 */
public class CsvURLDataSet extends CachedDataSet {

    /** base url that data can be found at */
//  private URL base;

    /**
     * Create a Data Set from CSV files, using the base URL provided to find data.
     */
    public CsvURLDataSet(URL base, String encoding) throws DataSetException {
        super(new CsvURLProducer(base, CsvDataSet.TABLE_ORDERING_FILE, encoding));
//      this.base = base;
    }

    public CsvURLDataSet(URL base, List<String> tables, String encoding) throws DataSetException {
        super(new CsvURLProducer(base, tables, encoding));
//      this.base = base;
    }

}