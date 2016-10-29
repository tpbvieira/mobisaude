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
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.config.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;


public class EvaluationActivity extends AppCompatActivity  {

    private static final String TAG = EvaluationActivity.class.getSimpleName();

    // UI
    private View mProgressView;
    private View mContentView;
    private RatingBar mRatingBar;
    private AutoCompleteTextView mTitleText;
    private AutoCompleteTextView mEvaluationText;

    private EvaluationTask mEvalTask = null;
    private ClientCache clientCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // contextual information
        clientCache = ClientCache.getInstance();

        // ui
        setContentView(R.layout.activity_evaluation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContentView = findViewById(R.id.evaluation_form);
        mProgressView = findViewById(R.id.evaluation_progress_bar);
        mRatingBar = (RatingBar) findViewById(R.id.evaluation_ratingbar);
        mTitleText = (AutoCompleteTextView) findViewById(R.id.evaluation_title);
        mEvaluationText = (AutoCompleteTextView) findViewById(R.id.evaluation_text_evaluation);

        Button mEvaluationButton = (Button) findViewById(R.id.evaluation_button);
        mEvaluationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEvaluation();
            }
        });

    }

    private void attemptEvaluation() {

        if (mEvalTask != null) {
            Toast.makeText(getApplicationContext(), getString(R.string.warn_working), Toast.LENGTH_SHORT).show();
            return;
        }

        Settings settings = new Settings(getApplicationContext());
        if(!DeviceInfo.isLoggedin()){
            Toast.makeText(getApplicationContext(), getString(R.string.warn_login_required), Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValid = true;
        View focusView = null;

        Float rating = mRatingBar.getRating();
        String title = mTitleText.getText().toString();
        String evalText = mEvaluationText.getText().toString();

        // data validation
        if (TextUtils.isEmpty(title)) {
            mTitleText.setError(getString(R.string.error_field_required));
            focusView = mTitleText;
            isValid = isValid && false;
        }

        if (TextUtils.isEmpty(evalText)) {
            mEvaluationText.setError(getString(R.string.error_field_required));
            focusView = mEvaluationText;
            isValid = isValid && false;
        }

        if (isValid) {
            showProgress(true);
            mEvalTask = new EvaluationTask(title, evalText, rating);
            mEvalTask.execute((Void) null);
        } else {
            focusView.requestFocus();
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

    public class EvaluationTask extends AsyncTask<Void, Void, Boolean> {

        private final String mTitle;
        private final String mEvaluationText;
        private final Float mRating;
        private String errMsg = null;

        EvaluationTask(String title, String evaluationText, Float rating) {
            mTitle = title;
            mEvaluationText = evaluationText;
            mRating = rating;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean success = true;
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
                parameters.put("titulo", mTitle);
                parameters.put("avaliacao", mEvaluationText);
                parameters.put("rating", mRating);

                JSONObject request = new JSONObject();
                request.put("avaliacaoRequest", parameters);

                // verify it it is the first evaluation
                String responseStr = ServiceBroker.getInstance(getApplicationContext()).getAvaliacaoByIdESEmail(request.toString());
                if (responseStr != null) {
                    JSONObject response = new JSONObject(responseStr);
                    if(response.has("avaliacaoResponse")){
                        Object objResponse = response.get("avaliacaoResponse");
                        try{
                            JSONObject avaliacaoResponse = (JSONObject) objResponse;
                            if(!JsonUtils.hasError(avaliacaoResponse) && avaliacaoResponse.has("email") &&
                                    avaliacaoResponse.getString("email").equals(settings.getPreferenceValue(Settings.USER_EMAIL))){
                                success = false;
                                throw new MobiSaudeAppException(getString(R.string.warn_many_evaluations));
                            }
                        }catch (ClassCastException e){
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }

                //evaluates
                responseStr = ServiceBroker.getInstance(getApplicationContext()).avaliar(request.toString());
                if (responseStr != null) {
                    JSONObject response = new JSONObject(responseStr);
                    if(response.has("avaliacaoResponse")){
                        Object objResponse = response.get("avaliacaoResponse");
                        try{
                            JSONObject avaliacaoResponse = (JSONObject) objResponse;
                            if(JsonUtils.hasError(avaliacaoResponse)){
                                success = false;
                                throw new MobiSaudeAppException(JsonUtils.getError(response));
                            }
                        }catch (ClassCastException e){
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }
            } catch (Exception e) {
                success = false;
                errMsg = e.getMessage();
                Log.e(TAG, e.getMessage(), e);
            }

            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mEvalTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(getApplicationContext(), getString(R.string.evaluated), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                if(errMsg == null){
                    mTitleText.setError(getString(R.string.error_invalid_values));
                    mTitleText.requestFocus();
                }else{
                    Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            mEvalTask = null;
            showProgress(false);
        }

    }

}