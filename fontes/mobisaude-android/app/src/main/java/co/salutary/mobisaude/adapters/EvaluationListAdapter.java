package co.salutary.mobisaude.adapters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.model.Avaliacao;

public class EvaluationListAdapter extends ArrayAdapter<Avaliacao> {

    private AppCompatActivity activity;
    private List<Avaliacao> avaliacaoList;

    public EvaluationListAdapter(AppCompatActivity context, int resource, List<Avaliacao> avaliacoes) {
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
            convertView = inflater.inflate(R.layout.evaluation_list_item_listview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ratingBar.setEnabled(false);
        holder.ratingBar.setRating(getItem(position).getRating());
        holder.title.setText(getItem(position).getTitulo());
//        holder.text.setText(getItem(position).getAvaliacao());
//        holder.date.setText(JsonUtils.sdfDMY.format(getItem(position).getDate()));

        return convertView;
    }

    private static class ViewHolder {

        private TextView title;
        private RatingBar ratingBar;
        private TextView date;
        private TextView text;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.text);
            ratingBar = (RatingBar) view.findViewById(R.id.rate_img);
//            text = (TextView) view.findViewById(R.id.evaluation_listview_text);
//            date = (TextView) view.findViewById(R.id.evaluation_listview_date);
        }

    }

}