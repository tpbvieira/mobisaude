package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.ListViewAdapter;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.Avaliacao;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;

public class HealthPlaceActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = new Object() {
    }.getClass().getName();

    // UI references.
    private View mContentView;
    private View mProgressView;
    private TextView mNomeFantasiaText;
    private TextView mTipoESText;
    private TextView mTipoGestãoText;
    private TextView mEnderecoText;
    private ListView mEvaluationsList;
    private RatingBar mRatingBar;

    private ClientCache clientCache;
    private Settings settings;

    private UpdateUITask uiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // contextual information
        clientCache = ClientCache.getInstance();

        settings = new Settings(getApplicationContext());

        setContentView(R.layout.activity_healthplace);
        mProgressView = findViewById(R.id.healthplace_progress_bar);
        mContentView = findViewById(R.id.healthplace_form_view);
        mNomeFantasiaText = (TextView) findViewById(R.id.healthplace_name);
        mTipoESText = (TextView) findViewById(R.id.healthplace_tipo_es);
        mTipoGestãoText = (TextView) findViewById(R.id.healthplace_tipo_gestao);
        mEnderecoText = (TextView) findViewById(R.id.healthplace_endereco);
        mRatingBar = (RatingBar) findViewById(R.id.healthplace_avaliacao_media_ratingbar);

        Button mEvaluateButton = (Button) findViewById(R.id.healthplace_evaluate_button);
        mEvaluateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceInfo.isLoggedin) {
                    startActivity(new Intent(HealthPlaceActivity.this, EvaluationActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.warn_login_required), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mSuggestButton = (Button) findViewById(R.id.healthplace_suggest_button);
        mSuggestButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceInfo.isLoggedin) {
                    startActivity(new Intent(HealthPlaceActivity.this, SuggestionActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.warn_login_required), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEvaluationsList = (ListView) findViewById(R.id.healthplace_evaluations_list);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            mContentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };
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
        protected Boolean doInBackground(Void... param) {
            boolean ok = true;

            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                // ES
                JSONObject params = new JSONObject();
                params.put("token", token);
                params.put("idES", settings.getPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE));
                JSONObject request = new JSONObject();
                request.put("esRequest", params);
                String responseStr = ServiceBroker.getInstance(getApplicationContext()).getESByIdES(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject response = (JSONObject) json.get("esResponse");
                    String error = JsonUtils.getError(response);
                    if (error == null) {
                        JSONObject obj = (JSONObject) response.get("estabelecimentosSaude");
                        EstabelecimentoSaude es = JsonUtils.jsonObjectToES(obj);
                        if (es != null) {
                            clientCache.setEstabelecimentoSaude(es);
                        } else {
                            throw new MobiSaudeAppException(getString(R.string.error_getting_es));
                        }
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getError(response));
                    }
                } else {
                    throw new MobiSaudeAppException(getString(R.string.error_getting_es));
                }

                // AvaliacaoMedia
                params = new JSONObject();
                params.put("token", token);
                params.put("idES", settings.getPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE));
                request = new JSONObject();
                request.put("avaliacaoMediaRequest", params);
                responseStr = ServiceBroker.getInstance(getApplicationContext()).getAvaliacaoMediaByIdES(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject response = (JSONObject) json.get("avaliacaoMediaResponse");
                    String error = JsonUtils.getError(response);
                    if (error == null) {
                        Avaliacao avMedia = JsonUtils.jsonObjectToAvaliacao(response);
                        if (avMedia != null) {
                            clientCache.setAvaliacaoMedia(avMedia);
                        } else {
                            throw new MobiSaudeAppException(getString(R.string.error_getting_evaluation));
                        }
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getError(response));
                    }
                } else {
                    throw new MobiSaudeAppException(getString(R.string.error_getting_evaluation));
                }

                // Evaluations
                params = new JSONObject();
                params.put("token", token);
                params.put("idES", settings.getPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE));
                request = new JSONObject();
                request.put("avaliacaoRequest", params);
                responseStr = ServiceBroker.getInstance(getApplicationContext()).listAvaliacaoByIdES(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    Object objResponse = json.get("avaliacaoResponse");
                    try {
                        JSONObject avaliacaoResponse = (JSONObject) objResponse;
                        if (!JsonUtils.hasError(avaliacaoResponse)) {
                            if (avaliacaoResponse.has("avaliacoes")) {
                                List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
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
                                    Avaliacao avaliacao = JsonUtils.jsonObjectToAvaliacao(avaliacaoResponse);
                                    if (avaliacao != null) {
                                        avaliacoes.add(avaliacao);
                                    }
                                }
                                clientCache.setListAvaliacoes(avaliacoes);
                            } else {
                                mWarningMsg = getString(R.string.error_no_evaluation);
                            }
                        } else {
                            throw new MobiSaudeAppException(JsonUtils.getError(avaliacaoResponse));
                        }
                    } catch (ClassCastException e) {
                        mWarningMsg = getString(R.string.error_no_evaluation);
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

                // ES
                EstabelecimentoSaude es = clientCache.getEstabelecimentoSaude();
                if (es != null) {
                    try {
                        String values = settings.getPreferenceValues(Settings.TIPOS_ESTABELECIMENTO_SAUDE);
                        HashMap map = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(values));
                        short id = es.getIdTipoEstabelecimentoSaude();
                        String name = (String) map.get(Short.toString(id));
                        mTipoESText.setText(name);
                        values = settings.getPreferenceValues(Settings.TIPO_GESTAO);
                        map = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(values));
                        id = es.getIdTipoGestao();
                        name = (String) map.get(Short.toString(id));
                        mTipoGestãoText.setText(name);
                        mNomeFantasiaText.setText(es.getNomeFantasia());
                        mEnderecoText.setText(es.getEndereco());
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }

                // AvaliacaoMedia
                Avaliacao avMedia = clientCache.getAvaliacaoMedia();
                if (avMedia != null) {
                    mRatingBar.setEnabled(true);
                    mRatingBar.setRating(avMedia.getRating());

                } else {
                    mRatingBar.setEnabled(true);
                }

                // Avaliacoes
                List<Avaliacao> avaliacoes = clientCache.getListAvaliacoes();
                if (avaliacoes != null) {
                    mEvaluationsList.setAdapter(new ListViewAdapter(HealthPlaceActivity.this, R.layout.item_listview, avaliacoes));
                    mEvaluationsList.setOnItemClickListener(onItemClickListener());
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

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog dialog = new Dialog(HealthPlaceActivity.this);
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
                mDate.setText(JsonUtils.sdfDMY.format(avaliacao.getDate()));
                mEvaluationButton.setText(getString(R.string.ok));
                mEvaluationButton.setOnClickListener(new OnClickListener() {
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

}