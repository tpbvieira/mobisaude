package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;

public class TipoESSelectionActivity extends Activity {

    private static final String TAG = new Object() {
    }.getClass().getName();
    private static final int ACTIVITY_LIST_SELECT = 1;

    private EditText mTipoESText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_selection);

        // Tipo ES
        LinearLayout mTipoESBtn = (LinearLayout) findViewById(R.id.single_selection_btn_uf);
        mTipoESBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowSingleSelectList();
            }
        });
        mTipoESText = (EditText) findViewById(R.id.single_selection_edt_uf);
        mTipoESText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowSingleSelectList();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_LIST_SELECT ){
            setResult(resultCode);
            finish();
        }

    }

    public void onShowSingleSelectList() {

        Settings settings = new Settings(getApplicationContext());
        String tipoESString = settings.getPreferenceValues(Settings.TIPOS_ESTABELECIMENTO_SAUDE);
        Intent it = new Intent(this, SingleSelectionListActivity.class);
        it.putExtra("values", tipoESString);
        it.putExtra("title", getString(R.string.tipo_es));
        startActivityForResult(it, ACTIVITY_LIST_SELECT);

    }

}