package io.sample.vertx.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HBaseDAO {

	final Logger logger = LoggerFactory.getLogger(HBaseDAO.class);

	@Autowired
	private HTablePool htablePool;
	@Autowired
	private Configuration defaultHBaseConfig;

	/*
	 * Return the latest row data, Olny one 
	 */
	public Result resultFirstKeyOnlyFilter(String tableName, String rowKey) throws Exception {

		Result r = null;
		try {

			Filter filter = new FirstKeyOnlyFilter();

			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);
			// For getting a object to get rows in the table
			Get get = new Get(Bytes.toBytes(rowKey));
			get.setFilter(filter);

			// Return a Result
			r = hTable.get(get);

		} catch (TableNotFoundException e) {
			logger.error("TableNotFoundException >>> ", e);
			return null;
		}

		return r;
	}

	public Result resultColumnCountGetFilter(String tableName, String rowKey) throws Exception {

		Result r = null;
		try {

			
			Filter filter = new ColumnCountGetFilter(1);

			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);
			// For getting a object to get rows in the table
			Get get = new Get(Bytes.toBytes(rowKey));
			get.setFilter(filter);

			// Return a Result
			r = hTable.get(get);

		} catch (TableNotFoundException e) {
			logger.error("TableNotFoundException >>> ", e);
			return null;
		}

		return r;
	}

	/*
	 * Return a result with a qualifier
	 */
	public Result resultRowAndQualifier(String tableName, String rowKey, String qualifier) throws Exception {

		Result r = null;
		try {

			BinaryComparator comparator = new BinaryComparator(Bytes.toBytes(qualifier));
			Filter filter = new QualifierFilter(CompareOp.EQUAL, comparator);

			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);
			// For getting a object to get rows in the table
			Get get = new Get(Bytes.toBytes(rowKey));
			get.setFilter(filter);

			// Return a Result
			r = hTable.get(get);

		} catch (TableNotFoundException e) {
			logger.error("TableNotFoundException", e);
			return null;
		}

		return r;
	}

	public Result resultRowAndFamilyAndTimestemp(String tableName, String rowKey, String family, long lngStart, long lngEnd) throws Exception {

		Result r = null;
		try {

			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);
			// For getting a object to get rows in the table
			Get get = new Get(rowKey.getBytes());
			get.addFamily(family.getBytes());

			if(lngStart > 0 && lngEnd > 0) {
				get.setTimeRange(lngStart, lngEnd);
			}

			// Return a Result
			r = hTable.get(get);

		} catch (TableNotFoundException e) {
			logger.error("TableNotFoundException", e);
			return null;
		}

		return r;
	}

	public Result resultRowAndFamily(String tableName, String rowKey, String family) throws Exception {
		return this.resultRowAndFamilyAndTimestemp(tableName, rowKey, family, 0, 0);
	}

	public Result resultRowAndFamilyAndQualifier(String tableName, String rowKey, String family, String qualifier) throws Exception {

		Result r = null;
		try {

			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);
			// For getting a object to get rows in the table
			Get get = new Get(rowKey.getBytes());
			get.addColumn(family.getBytes(), qualifier.getBytes());

			// Return a Result
			r = hTable.get(get);

		} catch (TableNotFoundException e) {
			logger.error("TableNotFoundException", e);
			return null;
		}

		return r;
	}

//	public Result resultColumnFamily(String tableName, String rowKey, String family, String qualifier) throws Exception {
//
//		Result r = null;
//		try {
//
//			// Get a object from Pool
//			HTableInterface hTable =  htablePool.getTable(tableName);
//			Filter filter = new DependentColumnFilter(Bytes.toBytes(family), Bytes.toBytes(qualifier));
//
//			Get get = new Get(Bytes.toBytes(rowKey));
//			get.setFilter(filter);
//
//			// Return a Result
//			r = hTable.get(get);
//
//		} catch (TableNotFoundException e) {
//			logger.error("TableNotFoundException", e);
//			return null;
//		}
//
//		return r;
//	}

	/*
	 * Return a result with a row
	 */	
	public Result result(String tableName, String rowKey) throws Exception {

		Result r = null;
		try {

			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);
			// For getting a object to get rows in the table
			Get get = new Get(Bytes.toBytes(rowKey));

			// Return a Result
			r = hTable.get(get);

		} catch (TableNotFoundException e) {
			logger.error("TableNotFoundException", e);
			return null;
		}

		return r;
	}

	/*
	 * Return a result with cf
	 */
	public ResultScanner resultScannerFamily(String tableName, String family, int intPages) throws Exception {

		// Get a object from Pool
		HTableInterface hTable =  htablePool.getTable(tableName);

		Scan s =new Scan();
		s.addFamily(family.getBytes());
		if(intPages < 1) {
			Filter filter = new PageFilter(intPages);
			s.setFilter(filter);			
		}

        ResultScanner rs = hTable.getScanner(s);

		return rs;
	}

	public ResultScanner resultScannerRowAndFamily(String tableName, String rowKey, String family) throws Exception {
		return this.resultScannerRowFamilyAndTimestemp(tableName, rowKey, family, 0, 0);
	}

	public ResultScanner resultScannerRowFamilyAndTimestemp(String tableName, String rowKey, String family, 
			long lngStart, long lngEnd) throws Exception {

		// Get a object from Pool
		HTableInterface hTable =  htablePool.getTable(tableName);

		BinaryComparator comparator = new BinaryComparator(Bytes.toBytes(rowKey));
		Filter filter = new RowFilter(CompareOp.EQUAL, comparator);
		Scan s =new Scan();
		s.addFamily(family.getBytes());
		s.setFilter(filter);

		if(lngStart > 0 && lngEnd > 0) {
			s.setTimeRange(lngStart, lngEnd);
		}

        ResultScanner rs = hTable.getScanner(s);

		return rs;
	}

	/*
	 * Return a result with pages
	 */
	public ResultScanner resultScanner(String tableName, int intPages) throws Exception {

		// Get a object from Pool
		HTableInterface hTable =  htablePool.getTable(tableName);

		Filter filter = new PageFilter(intPages);
		Scan s =new Scan();
		s.setFilter(filter);

        ResultScanner rs = hTable.getScanner(s);

		return rs;
	}

	public List<String> listTables(String tableName) throws Exception {

		List<String> listTableName = new ArrayList<String>();

		// Get a object from Pool
		HBaseAdmin admin = new HBaseAdmin(defaultHBaseConfig);
		try {
			HTableDescriptor [] htd = admin.listTables();
			
			for(int i=0; htd.length < i; i++) {
				listTableName.add(htd[i].getNameAsString());
			}

	    } finally {
	    	if(admin !=null) admin.close();
	    }

		return listTableName;
	}

	/*
	 * Create a table on Hbase
	 */
	public void createTableColumn(String tableName, String[] cfs) throws Exception {

		HBaseAdmin admin = new HBaseAdmin(defaultHBaseConfig);
		HTableDescriptor htDescriptor = new HTableDescriptor(tableName);

        try {
	        for(int i=0; i<cfs.length; i++) {
	            HColumnDescriptor meta = new HColumnDescriptor(cfs[i].getBytes());
	            htDescriptor.addFamily(meta);
	        }
	        admin.createTable(htDescriptor);

        } finally {
        	if(admin !=null) admin.close();
        }

	}

	/*
	 * Add a row with a key, column family
	 */
	public boolean addKeyValue(String tableName, String rowKey, String cfs, String qfs, String vals) {

		try {
			// Get a object from Pool
			HTableInterface hTable =  htablePool.getTable(tableName);

	        // Put
            Put put =new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(cfs), Bytes.toBytes(qfs), Bytes.toBytes(vals));
            hTable.put(put);
		} catch (Exception e) {
			logger.error("Exception >>> ", e);
			return false;
		}

		return true;
	}

	/*
	 * Add a row with keys, column families, qualifiers
	 */
	public boolean addKeyValue(String tableName, String[] rowKey, String cfs, String qfs, String[] vals) {

		try {
			// Get a object from Pool
			HTableInterface hTable =  htablePool.getTable(tableName);

	        // Put
	        for(int i=0; i<rowKey.length; i++) {
	            Put put =new Put(Bytes.toBytes(rowKey[i]));
	            put.add(Bytes.toBytes(cfs), Bytes.toBytes(qfs), Bytes.toBytes(vals[i]));
	            hTable.put(put);
	        }
		} catch (Exception e) {
			logger.error("Exception >>> ", e);
			return false;
		}

		return true;
	}

	/*
	 * Add a row with keys, column families, qualifiers
	 */
	public boolean addKeyValue(String tableName, String[] rowKey, String cfs, String[] qfs, String[] vals) {

		try {
			// Get a object from Pool
			HTableInterface hTable =  htablePool.getTable(tableName);

	        // Put
	        for(int i=0; i<rowKey.length; i++) {
	            Put put =new Put(Bytes.toBytes(rowKey[i]));
	            put.add(Bytes.toBytes(cfs), Bytes.toBytes(qfs[i]), Bytes.toBytes(vals[i]));
	            hTable.put(put);
	        }
		} catch (Exception e) {
			logger.error("Exception >>> ", e);
			return false;
		}

		return true;
	}

	/*
	 * Add a row with keys, column families, qualifiers
	 */
	public boolean addKeyValue(String tableName, String rowKey, String[] cfs, String[] qfs, String[] vals) {

		try {
			// Get a object from Pool
			HTableInterface hTable =  htablePool.getTable(tableName);

	        // Put
	        for(int i=0; i<cfs.length; i++) {
	            Put put =new Put(Bytes.toBytes(rowKey));
	            put.add(Bytes.toBytes(cfs[i]), Bytes.toBytes(qfs[i]), Bytes.toBytes(vals[i]));
	            hTable.put(put);
	        }
		} catch (Exception e) {
			logger.error("Exception >>> ", e);
			return false;
		}

		return true;
	}

	/*
	 * Delete a row
	 */
	public boolean deleteRow(String tableName, String rowKey) {

		try {
			// Get a object from Pool
			HTableInterface hTable = htablePool.getTable(tableName);

			// Delete row
			List<Delete> list =new ArrayList<Delete>();
			Delete d1 =new Delete(rowKey.getBytes());
			list.add(d1);
			hTable.delete(list);

		} catch (Exception e) {
			logger.error("Exception error>>>", e);
			return false;
		}

		return true;
	}

	/*
	 * Delete a table
	 * I will delete it in Product
	 */
	public boolean deleteTable(String tableName) throws Exception {

		// Delete row
		HBaseAdmin admin = new HBaseAdmin(defaultHBaseConfig);
		try {
			admin.disableTable(tableName);
			admin.deleteTable(tableName);		
		} catch (Exception e) {
			logger.error("Exception error>>>", e);
		} finally {
			admin.close();
		}

		return true;
	}
	
}