/*
 * Created on Dec 22, 2011
 * Author: Paul Woelfel
 * Email: frig@frig.at
 */
package at.fhstp.wificompass.model;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

public class ProjectListAdapter extends BaseAdapter {
	
	protected Context context;
	
	protected List<Project> projects;
	
	public ProjectListAdapter(Context context) throws SQLException{
		this.context=context;
		Dao<Project,String> projectDao=OpenHelperManager.getHelper(context, DatabaseHelper.class).getDao(Project.class);
		projects=projectDao.queryForAll();
		
		
	}

	@Override
	public int getCount() {
		return projects.size();
	}

	@Override
	public Object getItem(int position) {
		return projects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return projects.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		//layout.setGravity(Gravity.CENTER);

		TextView tv = new TextView(context);
		tv.setText(((Project)getItem(position)).getName());
		
		layout.addView(tv);
		
		return layout;
	}

}
