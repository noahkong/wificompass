/*
 * Created on Jan 20, 2012
 * Author: Paul Woelfel
 * Email: frig@frig.at
 */
package at.fhstp.wificompass.model;

import java.sql.SQLException;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName=WifiScanResult.TABLE_NAME)
public class WifiScanResult extends BaseDaoEnabled<WifiScanResult, Integer>{
	public static final String TABLE_NAME="wifiscanresult";
	
	@DatabaseField(generatedId=true)
	protected int id;
	
	@DatabaseField
	protected long timestamp;
	
	@ForeignCollectionField
	protected ForeignCollection<BssidResult> bssids;
	
	@DatabaseField(foreign = true,foreignAutoRefresh = true, foreignAutoCreate = true)
	protected Location location;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	protected ProjectSite projectLocation;
	
	
	public WifiScanResult(){
		
	}
	
	public WifiScanResult(long timestamp,Location location,ProjectSite projectLocation){
		this.timestamp=timestamp;
		this.location=location;
		this.projectLocation=projectLocation;
	}
	
	/**
	 * copy constructor
	 * @param copy
	 */
	public WifiScanResult(WifiScanResult copy){
		timestamp=copy.timestamp;
		if(copy.location!=null)
			location=new Location(copy.location);
		else
			location=null;
		projectLocation=copy.projectLocation;
	}


	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}


	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}


	/**
	 * @return the projectLocation
	 */
	public ProjectSite getProjectLocation() {
		return projectLocation;
	}


	/**
	 * @param projectLocation the projectLocation to set
	 */
	public void setProjectLocation(ProjectSite projectLocation) {
		this.projectLocation = projectLocation;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the bssids
	 */
	public ForeignCollection<BssidResult> getBssids() {
		return bssids;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"("+id+") "+timestamp+(location!=null?" "+location.toString():"");
	}

	/* (non-Javadoc)
	 * @see com.j256.ormlite.misc.BaseDaoEnabled#delete()
	 */
	@Override
	public int delete() throws SQLException {
		for(BssidResult br: bssids){
			br.delete();
		}
		return super.delete();
	}

}
