package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.adapters.GenericListAdapter;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;

public class BookmarksActivity extends AppCompatActivity {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private ClientCache clientCache;
    private Settings settings;

    //UI
    private View mProgressView;
    private ListView mEvaluationsList;

    private UpdateUITask uiTask;

    List<EstabelecimentoSaude> bookmark = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_list);

        settings = new Settings(getApplicationContext());

        // UI
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
                settings.setPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE, Integer.toString(bookmark.get(position).getIdCnes()));
                Intent intent = new Intent(getApplicationContext(), HealthPlaceActivity.class);
                startActivity(intent);
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
                List<String> bookmarkIds = ClientCache.getInstance().getBookmark(getApplicationContext());
                for(String idEs: bookmarkIds){
                    EstabelecimentoSaude es = getES(getApplicationContext(), token, idEs);
                    if(es != null) {
                        bookmark.add(es);
                    }
                }
            } catch (Exception e) {
                mErrorMsg = e.getMessage();
                Log.e(TAG, e.getMessage(), e);
                ok = false;
            }

            return ok;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                GenericListAdapter mListAdapter = null;
                if (bookmark != null && bookmark.size() > 0) {
                    mListAdapter = new GenericListAdapter();
                    for (EstabelecimentoSaude es : bookmark) {
                        mListAdapter.addItem(new GenericListAdapter.Item(es.getNomeFantasia(), es.getIdCnes()));
                    }
                }

                mEvaluationsList.setAdapter(mListAdapter);
                mEvaluationsList.setOnItemClickListener(onItemClickListener());
            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

            if (mWarningMsg != null) {
                Toast.makeText(getApplicationContext(), mWarningMsg, Toast.LENGTH_SHORT).show();
            }

            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }

    }

    public static EstabelecimentoSaude getES(Context context, String token, String idES) throws MobiSaudeAppException {
        EstabelecimentoSaude es = null;
        try {
            JSONObject params = new JSONObject();
            params.put("token", token);
            params.put("idES", idES);
            JSONObject request = new JSONObject();
            request.put("esRequest", params);
            String responseStr = ServiceBroker.getInstance(context).getESByIdES(request.toString());
            if (responseStr != null) {
                JSONObject json = new JSONObject(responseStr);
                JSONObject response = (JSONObject) json.get("esResponse");
                String error = JsonUtils.getError(response);
                if (error == null) {
                    JSONObject obj = (JSONObject) response.get("estabelecimentosSaude");
                    es = JsonUtils.jsonObjectToES(obj);
                } else {
                    throw new MobiSaudeAppException(JsonUtils.getError(response));
                }
            } else {
                throw new MobiSaudeAppException(context.getString(R.string.error_getting_es));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return es;

    }

}