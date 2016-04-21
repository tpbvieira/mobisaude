package co.salutary.mobisaude.adapters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.model.Avaliacao;

public class ListViewAdapter extends ArrayAdapter<Avaliacao> {

    private AppCompatActivity activity;
    private List<Avaliacao> avaliacaoList;

    public ListViewAdapter(AppCompatActivity context, int resource, List<Avaliacao> avaliacoes) {
        super(context, resource, avaliacoes);
        this.activity = context;
        this.avaliacaoList = avaliacoes;
    }

    @Override
    public Avaliacao getItem(int position) {
        return avaliacaoList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));
        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getRating());
        holder.title.setText(getItem(position).getTitulo());

        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Avaliacao item = getItem(position);
                item.setRating(v);
                Log.i("Adapter", "star: " + v);
            }
        };
    }

    private static class ViewHolder {
        private RatingBar ratingBar;
        private TextView title;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.item_listview_rate_img);
            title = (TextView) view.findViewById(R.id.item_listview_text);
        }

    }

}