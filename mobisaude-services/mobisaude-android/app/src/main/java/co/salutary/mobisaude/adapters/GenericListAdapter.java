package co.salutary.mobisaude.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.R;


public class GenericListAdapter extends BaseAdapter {

    public static abstract class Row {}
    
    public static final class Section extends Row {
        public final String text;

        public Section(String text) {
            this.text = text;
        }
    }
    
    public static final class Item extends Row {
        public final String text;
        public final int id;

        public Item(String text, int id) {
            this.text = text;
            this.id = id;
        }
    }

    private List<Row> rows = new ArrayList<Row>();
    
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
    
    public List<Row> getRows() {
		return rows;
	}

	public void addItem(Item item) {
    	rows.add(item);
		notifyDataSetChanged();
	}

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Row getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    
    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        
        if (getItemViewType(position) == 0) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_item, parent, false);  
            }
            
            Item item = (Item) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(item.text);
        } else {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, parent, false);  
            }
            
            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(section.text);
        }
        return view;
    }

}