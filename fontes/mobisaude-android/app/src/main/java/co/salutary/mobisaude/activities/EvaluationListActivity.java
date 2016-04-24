package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.ListViewAdapter;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.Avaliacao;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;

public class EvaluationListActivity extends AppCompatActivity {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private ClientCache clientCache;

    //UI
    private View mProgressView;
    private ListView mEvaluationsList;

    private UpdateUITask uiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_evaluation_list);
        mProgressView = findViewById(R.id.evaluation_list_progress_bar);
        mEvaluationsList = (ListView)findViewById(R.id.evaluation_list_listview);

        clientCache = ClientCache.getInstance();

        uiTask = new UpdateUITask();
        uiTask.execute();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        clientCache.setListAvaliacoes(null);
        if(uiTask != null){
            uiTask.cancel(true);
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog dialog = new Dialog(EvaluationListActivity.this);
                dialog.setContentView(R.layout.activity_evaluation);

                RatingBar mRatingBar = (RatingBar) dialog.findViewById(R.id.evaluation_ratingbar);
                AutoCompleteTextView mTitleText = (AutoCompleteTextView) dialog.findViewById(R.id.evaluation_title);
                AutoCompleteTextView mEvaluationText = (AutoCompleteTextView) dialog.findViewById(R.id.evaluation_text_evaluation);
                AutoCompleteTextView mDate = (AutoCompleteTextView) dialog.findViewById(R.id.evaluation_date);
                Button mEvaluationButton = (Button) dialog.findViewById(R.id.evaluation_button);

                mRatingBar.setEnabled(false);
                mTitleText.setEnabled(false);
                mTitleText.setFocusable(false);
                mEvaluationText.setEnabled(false);
                mEvaluationText.setFocusable(false);
                mDate.setVisibility(View.VISIBLE);
                mDate.setFocusable(false);

                Avaliacao avaliacao = (Avaliacao) parent.getAdapter().getItem(position);
                mRatingBar.setRating(avaliacao.getRating());
                mTitleText.setText(avaliacao.getTitulo());
                mEvaluationText.setText(avaliacao.getAvaliacao());
                mDate.setEnabled(true);
                mDate.setHint(R.string.prompt_date);
                mDate.setText(JsonUtils.sdfDMY.format(avaliacao.getDate()));
                mDate.setEnabled(false);
                mEvaluationButton.setText(getString(R.string.ok));
                mEvaluationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.setTitle(clientCache.getEstabelecimentoSaude().getNomeFantasia());
                dialog.show();
            }

        };

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mEvaluationsList.setVisibility(show ? View.GONE : View.VISIBLE);
            mEvaluationsList.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mEvaluationsList.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mEvaluationsList.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UpdateUITask extends AsyncTask<Void, Void, Boolean> {

        String mErrorMsg = null;
        String mWarningMsg = null;

        UpdateUITask() {
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            showProgress(true);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean ok = true;

            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                // Evaluations
                JSONObject parameters = new JSONObject();
                parameters.put("token", token);
                parameters.put("idES", settings.getPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE));
                JSONObject request = new JSONObject();
                request.put("avaliacaoRequest", parameters);
                String responseStr = ServiceBroker.getInstance(getApplicationContext()).listAvaliacaoByIdES(request.toString());
                if (responseStr != null) {

                    JSONObject json = new JSONObject(responseStr);
                    Object objResponse = json.get("avaliacaoResponse");
                    try {

                        JSONObject avaliacaoResponse = (JSONObject) objResponse;
                        if (!JsonUtils.hasError(avaliacaoResponse)) {

                            if (avaliacaoResponse.has("avaliacoes")) {

                                List<Avaliacao> avaliacoes = new ArrayList<>();
                                try {

                                    JSONArray array = avaliacaoResponse.getJSONArray("avaliacoes");
                                    for (int i = 0; i < array.length(); ++i) {
                                        JSONObject obj = array.getJSONObject(i);
                                        Avaliacao avaliacao = JsonUtils.jsonObjectToAvaliacao(obj);
                                        if (avaliacao != null) {
                                            avaliacoes.add(avaliacao);
                                        }
                                    }

                                } catch (Exception e) {
                                    Avaliacao avaliacao = JsonUtils.jsonObjectToAvaliacao((JSONObject) avaliacaoResponse.get("avaliacoes"));
                                    if (avaliacao != null) {
                                        avaliacoes.add(avaliacao);
                                    }
                                }
                                clientCache.setListAvaliacoes(avaliacoes);

                            } else {
                                mWarningMsg = getString(R.string.warn_no_evaluation);
                            }

                        } else {
                            throw new MobiSaudeAppException(JsonUtils.getError(avaliacaoResponse));
                        }

                    } catch (ClassCastException e) {
                        mWarningMsg = getString(R.string.warn_no_evaluation);
                        Log.d(TAG, e.getMessage());
                    }

                } else {
                    throw new MobiSaudeAppException(getString(R.string.error_getting_evaluations));
                }

            } catch (Exception e) {
                mErrorMsg = e.getMessage();
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
                ok = false;
            }

            return ok;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                // Avaliacoes
                List<Avaliacao> avaliacoes = clientCache.getListAvaliacoes();
                if (avaliacoes != null && avaliacoes.size() > 0) {
                    mEvaluationsList.setAdapter(new ListViewAdapter(EvaluationListActivity.this, R.layout.item_listview, avaliacoes));
                    mEvaluationsList.setOnItemClickListener(onItemClickListener());
                }else{
                    finish();
                }

                if (mWarningMsg != null) {
                    Toast.makeText(getApplicationContext(), mWarningMsg, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), mErrorMsg, Toast.LENGTH_SHORT).show();
                finish();
            }

            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }

    }

}