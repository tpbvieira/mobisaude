package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;


public class SuggestionActivity extends AppCompatActivity  {

    private static final String TAG = SuggestionActivity.class.getSimpleName();

    // UI
    private View mProgressView;
    private View mContentView;
    private AutoCompleteTextView mSuggestionText;

    private SuggestionTask mSuggTask = null;
    private ClientCache clientCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // contextual information
        clientCache = ClientCache.getInstance();

        // ui
        setContentView(R.layout.activity_suggestion);
        mContentView = findViewById(R.id.suggestion_form_view);
        mProgressView = findViewById(R.id.suggestion_progress_bar);
        mSuggestionText = (AutoCompleteTextView) findViewById(R.id.suggestion_text);

        Button mSuggestionButton = (Button) findViewById(R.id.suggestion_button);
        mSuggestionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSuggestion();
            }
        });

    }

    private void attemptSuggestion() {

        if (mSuggTask != null) {
            Toast.makeText(getApplicationContext(), getString(R.string.warn_working), Toast.LENGTH_SHORT).show();
            return;
        }

        Settings settings = new Settings(getApplicationContext());
        if(!DeviceInfo.isLoggedin || settings.getPreferenceValue(Settings.USER_EMAIL) == null){
            Toast.makeText(getApplicationContext(), getString(R.string.warn_login_required), Toast.LENGTH_SHORT).show();
            return;
        }

        String suggestionText = mSuggestionText.getText().toString();

        if (TextUtils.isEmpty(suggestionText)) {
            mSuggestionText.setError(getString(R.string.error_field_required));
            mSuggestionText.requestFocus();
        }else{
            showProgress(true);
            mSuggTask = new SuggestionTask(suggestionText);
            mSuggTask.execute((Void) null);
        }

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

    public class SuggestionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mSuggestion;
        private String errMsg = null;

        SuggestionTask(String suggestion) {
            mSuggestion = suggestion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean success = false;
            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                JSONObject parameters = new JSONObject();
                parameters.put("token", token);
                parameters.put("idES", clientCache.getEstabelecimentoSaude().getIdCnes());
                parameters.put("email", settings.getPreferenceValue(Settings.USER_EMAIL));
                parameters.put("sugestao", mSuggestion);

                JSONObject request = new JSONObject();
                request.put("sugestaoRequest", parameters);

                String responseStr = ServiceBroker.getInstance(getApplicationContext()).sugerir(request.toString());
                if (responseStr != null) {
                    JSONObject response = new JSONObject(responseStr);
                    if(response.has("sugestaoResponse")){
                        Object objResponse = response.get("sugestaoResponse");
                        try{
                            JSONObject sugestaoResponse = (JSONObject) objResponse;
                            if(!JsonUtils.hasError(sugestaoResponse)){
                                success = true;
                            }else {
                                throw new MobiSaudeAppException(JsonUtils.getError(response));
                            }
                        }catch (ClassCastException e){
                            success = true;
                            Log.d(TAG,e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                errMsg = e.getMessage();
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSuggTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(getApplicationContext(), getString(R.string.suggested), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                if(errMsg == null){
                    mSuggestionText.setError(getString(R.string.error_invalid_values));
                    mSuggestionText.requestFocus();
                }else{
                    Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            mSuggTask = null;
            showProgress(false);
        }

    }

}