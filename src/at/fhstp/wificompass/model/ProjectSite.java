/*
 * Created on Dec 8, 2011
 * Author: Paul Woelfel
 * Email: frig@frig.at
 */
package at.fhstp.wificompass.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import at.fhstp.wificompass.Logger;
import at.fhstp.wificompass.interfaces.XMLSerializable;
import at.fhstp.wificompass.model.helper.DatabaseHelper;
import at.fhstp.wificompass.model.xml.XMLSettings;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ProjectSite.TABLE_NAME)
public class ProjectSite extends BaseDaoEnabled<ProjectSite, Integer> implements XMLSerializable {

	public static final String TABLE_NAME = "sites";

	@DatabaseField(generatedId = true)
	protected int id;

	@DatabaseField
	protected String title;

	@DatabaseField
	protected String description;

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	protected byte[] background;

	protected Bitmap backgroundBitmap;

	@DatabaseField
	protected int width;

	@DatabaseField
	protected int height;

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	protected byte[] image;

	protected Bitmap imageBitmap;

	protected static final int quality = 100;

	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	protected Project project;
	
	@DatabaseField
	protected float gridSpacingX=30;
	
	@DatabaseField
	protected float gridSpacingY=30;
	
	@DatabaseField
	protected float north=0;

	@ForeignCollectionField
	protected ForeignCollection<AccessPoint> accessPoints;

	@ForeignCollectionField
	protected ForeignCollection<WifiScanResult> scanResults;
	
	@ForeignCollectionField
	protected ForeignCollection<BssidSelection> selectedBssids;


	@DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
	protected Location lastLocation;

	protected static final String XMLTAG = "location", XMLTITLE = "title";

	public static final String UNTITLED = "untitled";

	// maybe we should use TableUtils
//	public static String FIELD_PROJECT_FK = Project.TABLE_NAME + "_" + Project.FIELD_ID;

	public ProjectSite() {
		this(null, null);
	}

	public ProjectSite(String title) {
		this(null, title);
	}

	public ProjectSite(Project project) {
		this(project, null);
	}

	public ProjectSite(Project project, String title) {
		super();
		this.title = title;
		this.project = project;
		if (this.title == null) {
			this.title = UNTITLED;
		}
		width = 0;
		height = 0;
	}

	/**
	 * copy constructor
	 * 
	 * @param copy
	 */
	public ProjectSite(ProjectSite copy) {
		title = copy.title;
		description = copy.description;
		if (copy.image != null)
			image = copy.image.clone();
		else
			image = null;
		
		if(copy.background!=null){
			background=copy.background.clone();
		}else
			background=null;
		
		project = copy.project;
		if (copy.lastLocation != null)
			lastLocation = new Location(copy.lastLocation);
		else
			lastLocation = null;
		width=copy.width;
		height=copy.height;
				
	}

	@Override
	public void serialize(XmlSerializer serializer) throws RuntimeException, IOException {
		serializer.startTag(XMLSettings.XMLNS, XMLTAG);

		serializer.startTag(XMLSettings.XMLNS, XMLTITLE).text(title).endTag(XMLSettings.XMLNS, XMLTITLE);

		serializer.endTag(XMLSettings.XMLNS, XMLTITLE);
	}

	@Override
	public void deserialize(Element e) {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getBackgroundBitmap() {
		if (backgroundBitmap == null && background != null) {
			backgroundBitmap = BitmapFactory.decodeByteArray(background, 0, background.length);
		}
		return backgroundBitmap;
	}

	public boolean setBackgroundBitmap(Bitmap backgroundBitmap) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (backgroundBitmap.compress(Bitmap.CompressFormat.PNG, quality, baos)) {
			background = baos.toByteArray();
			this.backgroundBitmap = backgroundBitmap;
			return true;
		}
		return false;
	}

	public Bitmap getImageBitmap() {
		if (imageBitmap == null && image != null) {
			imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
		}
		return imageBitmap;
	}

	public boolean setImageBitmap(Bitmap imageBitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (imageBitmap.compress(Bitmap.CompressFormat.PNG, quality, baos)) {
			image = baos.toByteArray();
			this.imageBitmap = imageBitmap;
			return true;
		}
		return false;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the accessPoints
	 */
	public ForeignCollection<AccessPoint> getAccessPoints() {
		return accessPoints;
	}

	/**
	 * @return the scanResults
	 */
	public ForeignCollection<WifiScanResult> getScanResults() {
		return scanResults;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * set the size of the project site
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the last known Location
	 */
	public Location getLastLocation() {
		return lastLocation;
	}

	/**
	 * @param lastLocation
	 *            the lastLocation to set
	 */
	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProjectSite(" + id + ") " + title + " " + width + "x" + height;
	}

	/**
	 * @return the gridSpacingX
	 */
	public float getGridSpacingX() {
		return gridSpacingX;
	}

	/**
	 * @param gridSpacingX the gridSpacingX to set
	 */
	public void setGridSpacingX(float gridSpacingX) {
		this.gridSpacingX = gridSpacingX;
	}

	/**
	 * @return the gridSpacingY
	 */
	public float getGridSpacingY() {
		return gridSpacingY;
	}

	/**
	 * @param gridSpacingY the gridSpacingY to set
	 */
	public void setGridSpacingY(float gridSpacingY) {
		this.gridSpacingY = gridSpacingY;
	}

	/**
	 * @return the north
	 */
	public float getNorth() {
		return north;
	}

	/**
	 * <p>Define the the angle to the north of the map and the magnetic north</p>
	 * <p>The angle must be between 0 and 2*π</p>
	 * @param north the north to set
	 */
	public void setNorth(float north) {
		this.north = (float) (north%(2*Math.PI));
		if(this.north<0)
			this.north+=2*Math.PI;
	}

	
	/* (non-Javadoc)
	 * @see com.j256.ormlite.misc.BaseDaoEnabled#delete()
	 */
	@Override
	public int delete() throws SQLException {
		if(lastLocation!=null&&lastLocation.getId()!=0) lastLocation.delete();
		for(AccessPoint ap: accessPoints){
			ap.delete();
		}
		for(WifiScanResult sr: scanResults){
			sr.delete();
		}
		
		for(BssidSelection bs:selectedBssids){
			bs.delete();
		}
		return super.delete();
	}

	
	public boolean isBssidSelected(String bssid){
		if(selectedBssids!=null){
			for (BssidSelection bs:selectedBssids){
				if(bs.getBssid().equals(bssid)){
					return bs.isActive();
				}
			}
			
			// bssid is not found
			
		}

		//by default all are enabled
		return true;
	}
	
	public BssidSelection getBssidSelection(String bssid){
		if(selectedBssids!=null){
			for (BssidSelection bs:selectedBssids){
				if(bs.getBssid().equals(bssid)){
					return bs;
				}
			}
			// bssid is not found
		}
		return null;
	}
	
	public BssidSelection getBssidSelection(Context ctx,String bssid){
		DatabaseHelper dbHelper=OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		try {
			Dao<BssidSelection,Integer> selectionDao=dbHelper.getDao(BssidSelection.class);
			List<BssidSelection> found=selectionDao.queryForMatchingArgs(new BssidSelection(this,bssid,false));
			
			if(!found.isEmpty()){
				return found.get(0);
			}
			
		} catch (SQLException e) {
			Logger.e("sql exception while searching for bssidselections", e);
		} finally {
			OpenHelperManager.releaseHelper();
		}
		
		return null;
	}
	
	public boolean isBssidSelected(Context ctx, String bssid){
		
		DatabaseHelper dbHelper=OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		try {
			Dao<BssidSelection,Integer> selectionDao=dbHelper.getDao(BssidSelection.class);
			List<BssidSelection> found=selectionDao.queryForMatching(new BssidSelection(this,bssid,false));
			
			if(!found.isEmpty()){
				return true;
			}
			
		} catch (SQLException e) {
			Logger.e("sql exception while searching for bssidselections", e);
		} finally {
			OpenHelperManager.releaseHelper();
		}
		
		//by default all are enabled
		return true;
	}
	
	public void setBssidSelected(Context ctx,String bssid,boolean active){
		DatabaseHelper dbHelper=OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		try {
			Dao<BssidSelection,Integer> selectionDao=dbHelper.getDao(BssidSelection.class);
			
			boolean found=false;
			for(BssidSelection bs: selectedBssids){
			
			if(bs.getBssid().equals(bssid)){
				if(active){
					bs.delete();
				}else {
					// should already be inactive
//					bs.setActive(false);
//					bs.update();
				}
				found=true;
				break;
			}
			}
			if(!found&&!active){
				selectionDao.create(new BssidSelection(this,bssid,active));
				this.refresh();
			}
			
			
		} catch (SQLException e) {
			Logger.e("sql exception during searching for bssidselections", e);
		} finally {
			OpenHelperManager.releaseHelper();
		}
		
		
	}
	
	public void setUnselectedBssids(Context ctx,List<String> bssids){
		DatabaseHelper dbHelper=OpenHelperManager.getHelper(ctx, DatabaseHelper.class);
		try {
			Dao<BssidSelection,Integer> selectionDao=dbHelper.getDao(BssidSelection.class);
			
			ArrayList<String> current=new ArrayList<String>();
			
			for(BssidSelection bs: selectedBssids){
				if(!bssids.contains(bs.getBssid())){
					// was unselected, but is now selected
					bs.delete();
				}else {
					current.add(bs.getBssid());
				}
			}
			
			for(String toUnselect:bssids){
				if(!current.contains(toUnselect)){
					selectionDao.create(new BssidSelection(this,toUnselect,false));
				}
			}
			

		} catch (SQLException e) {
			Logger.e("sql exception during unselecting bssids", e);
		} finally {
			OpenHelperManager.releaseHelper();
		}
		
	}


	public ForeignCollection<BssidSelection> getSelectedBssids() {
		return selectedBssids;
	}

}
