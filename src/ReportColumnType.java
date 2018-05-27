import net.sf.dynamicreports.report.definition.datatype.DRIDataType;

public class ReportColumnType {

	private String columnHeader;
	private String databaseColumnName;
	private DRIDataType<? super String, String> dataType;
	
	public ReportColumnType(String columnHeader, String databaseColumnName,
			DRIDataType<? super String, String> dataType) {
		super();
		this.columnHeader = columnHeader;
		this.databaseColumnName = databaseColumnName;
		this.dataType = dataType;
	}
	
	public String getColumnHeader() {
		return columnHeader;
	}
	
	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}
	public String getDatabaseColumnName() {
		return databaseColumnName;
	}
	public void setDatabaseColumnName(String databaseColumnName) {
		this.databaseColumnName = databaseColumnName;
	}
	public DRIDataType<? super String, String> getDataType() {
		return dataType;
	}
	public void setDataType(DRIDataType<? super String, String> dataType) {
		this.dataType = dataType;
	}
	
}



