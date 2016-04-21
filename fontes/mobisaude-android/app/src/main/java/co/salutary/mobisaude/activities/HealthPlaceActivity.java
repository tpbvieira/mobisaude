package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
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
import android.widget.Button;
import android.widget.ListView;
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

    private static final int REQUEST_READ_CONTACTS = 0;

    private UpdateUITask mloadUITask = null;

    // UI references.
    private View mESFormView;
    private View mProgressView;
    private TextView mNomeFantasiaText;
    private TextView mTipoESText;
    private TextView mTipoGestãoText;
    private TextView mEnderecoText;
    private ListView mEvaluationsList;

    private ClientCache clientCache;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // contextual information
        clientCache = clientCache = ClientCache.getInstance();
        settings = new Settings(getApplicationContext());

        setContentView(R.layout.activity_healthplace);
        mProgressView = findViewById(R.id.healthplace_progress_bar);
        mESFormView = findViewById(R.id.healthplace_form_view);
        mNomeFantasiaText = (TextView) findViewById(R.id.healthplace_name);
        mTipoESText = (TextView) findViewById(R.id.healthplace_tipo_es);
        mTipoGestãoText = (TextView) findViewById(R.id.healthplace_tipo_gestao);
        mEnderecoText = (TextView) findViewById(R.id.healthplace_endereco);
        Button mUpdateButton = (Button) findViewById(R.id.healthplace_evaluate_button);
        if(DeviceInfo.isLoggedin){
            mUpdateButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    enableUpdate();
                }
            });
        }else{
            mUpdateButton.setEnabled(false);
        }

        mEvaluationsList = (ListView) findViewById(R.id.healthplace_evaluations_list);        ;

        new UpdateUITask().execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { }

    private void enableUpdate() {

//        if (mAuthTask != null) {
//            return;
//        }
//
//        // Reset variables
//        mEnderecoText.setError(null);
//        mNomeFantasiaText.setError(null);
//        mTipoESText.setError(null);
//        boolean isValid = true;
//        View focusView = null;
//
//        // Update login and password
//        String name = mEnderecoText.getText().toString();
//        String email = mNomeFantasiaText.getText().toString();
//        String password = mTipoESText.getText().toString();
//
//        // Check for a valid name
//        if (TextUtils.isEmpty(name)) {
//            mEnderecoText.setError(getString(R.string.error_field_required));
//            focusView = mEnderecoText;
//            isValid = isValid && false;
//        }
//        if (!Validator.isValidName(name)) {
//            mEnderecoText.setError(getString(R.string.error_invalid_name));
//            focusView = mEnderecoText;
//            isValid = isValid && false;
//        }
//
//        // Check for a valid email address
//        if (TextUtils.isEmpty(email)) {
//            mNomeFantasiaText.setError(getString(R.string.error_field_required));
//            focusView = mNomeFantasiaText;
//            isValid = isValid && false;
//        }
//        if (!Validator.isValidEmail(email)) {
//            mNomeFantasiaText.setError(getString(R.string.error_invalid_email));
//            focusView = mNomeFantasiaText;
//            isValid = isValid && false;
//        }
//
//        // Check for a valid password
//        if (TextUtils.isEmpty(password) || !Validator.isValidPassword(password)) {
//            mTipoESText.setError(getString(R.string.error_invalid_password));
//            focusView = mTipoESText;
//            isValid = isValid && false;
//        }
//
//        if (isValid) {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new EvaluateTask(email, password, name, contactable);
//            mAuthTask.execute((Void) null);
//        } else {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mESFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mESFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mESFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mESFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) { }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public class UpdateUITask extends AsyncTask<Void, Void, Boolean> {

        UpdateUITask() { }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            showProgress(true);

        }


        @Override
        protected Boolean doInBackground(Void... params) {
            boolean ok = true;
            String mErrorMsg;
            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                // ES data
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("idES", settings.getPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE));
                JSONObject request = new JSONObject();
                request.put("esRequest", data);
                String responseStr = ServiceBroker.getInstance(getApplicationContext()).getESByIdES(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject esResponse = (JSONObject) json.get("esResponse");
                    int idErro = JsonUtils.getErrorCode(esResponse);
                    if (idErro == 0) {
                        JSONObject esJson = (JSONObject) esResponse.get("estabelecimentosSaude");
                        EstabelecimentoSaude es = JsonUtils.jsonObjectToES(esJson);
                        if(es != null){
                            clientCache.setEstabelecimentoSaude(es);
                        }else{
                            throw new MobiSaudeAppException(JsonUtils.getErrorMessage(esResponse));
                        }
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getErrorMessage(esResponse));
                    }
                }else{
                    throw new MobiSaudeAppException("responseStr == null");
                }

                // Evaluations
                data = new JSONObject();
                data.put("token", token);
                data.put("idES", settings.getPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE));
                request = new JSONObject();
                request.put("avaliacaoRequest", data);
                responseStr = ServiceBroker.getInstance(getApplicationContext()).listAvaliacaoByIdES(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject avaliacaoResponse = (JSONObject) json.get("avaliacaoResponse");
                    int idErro = JsonUtils.getErrorCode(avaliacaoResponse);
                    if (idErro == 0) {
                        JSONArray avaliacoesArr = avaliacaoResponse.getJSONArray("avaliacoes");
                        List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
                        for (int i = 0; i < avaliacoesArr.length(); ++i) {
                            JSONObject obj = avaliacoesArr.getJSONObject(i);
                            Avaliacao avaliacao = JsonUtils.jsonObjectToAvaliacao(obj);
                            if(avaliacao != null){
                                avaliacoes.add(avaliacao);
                            }
                        }
                        clientCache.setListAvaliacoes(avaliacoes);
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getErrorMessage(avaliacaoResponse));
                    }
                } else {
                    throw new MobiSaudeAppException("responseStr == null");
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
            mloadUITask = null;

            if (success) {
                EstabelecimentoSaude es = clientCache.getEstabelecimentoSaude();
                if(es != null){
                    try {
                        String values = settings.getPreferenceValues(Settings.TIPOS_ESTABELECIMENTO_SAUDE);
                        HashMap map = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(values));
                        short id = es.getIdTipoEstabelecimentoSaude();
                        String name = (String)map.get(Short.toString(id));
                        mTipoESText.setText(name);
                        values = settings.getPreferenceValues(Settings.TIPO_GESTAO);
                        map = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(values));
                        id = es.getIdTipoGestao();
                        name = (String)map.get(Short.toString(id));
                        mTipoGestãoText.setText(name);
                        mNomeFantasiaText.setText(es.getNomeFantasia());
                        mEnderecoText.setText(es.getEndereco());
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }

                List<Avaliacao> avaliacoes = clientCache.getListAvaliacoes();
                if(avaliacoes != null){
                    mEvaluationsList.setAdapter(new ListViewAdapter(HealthPlaceActivity.this, R.layout.item_listview, avaliacoes));
//                    mEvaluationsList.setOnItemClickListener(onItemClickListener());
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_connecting_server), Toast.LENGTH_SHORT).show();
                finish();
            }

            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mloadUITask = null;
            showProgress(false);
        }

    }
//
//    private AdapterView.OnItemClickListener onItemClickListener() {
//        return new AdapterView.OnItemClickListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final Dialog dialog = new Dialog(MainActivity.this);
//                dialog.setContentView(R.layout.layout_dialog);
//                dialog.setTitle("Movie details");
//
//                TextView name = (TextView) dialog.findViewById(R.id.movie_name);
//                TextView country = (TextView) dialog.findViewById(R.id.country);
//                TextView starRate = (TextView) dialog.findViewById(R.id.rate);
//
//                Movie movie = (Movie) parent.getAdapter().getItem(position);
//                name.setText("Movie name: " + movie.getName());
//                country.setText("Producing country: " + movie.getCountry());
//                starRate.setText("Your rate: " + movie.getRatingStar());
//
//                dialog.show();
//            }
//        };
//    }

}