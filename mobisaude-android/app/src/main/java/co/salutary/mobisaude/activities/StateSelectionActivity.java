package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.UF;

public class StateSelectionActivity extends Activity {

    private static final String TAG = new Object() {
    }.getClass().getName();
    private static final int ACTIVITY_LIST_SELECT = 1;

    private EditText edtUF;

    private ClientCache clientCache;
    private LocalDataBase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_state_selection);

        clientCache = ClientCache.getInstance();
        db = LocalDataBase.getInstance();

        // UF
        LinearLayout btnUF = (LinearLayout) findViewById(R.id.state_selection_btn_uf);
        edtUF = (EditText) findViewById(R.id.state_selection_edt_uf);
        btnUF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_UF);
            }
        });
        edtUF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_UF);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_LIST_SELECT && resultCode == LocalitySelectionListActivity.RESULTADO_ITEM_SELECIONADO){
            atualizarCampos();
            if(ClientCache.getInstance().getUf() != null){
                final int ufId = ClientCache.getInstance().getUf().getIdUf();
                db.open(getApplicationContext());
                UF uf = new UfDAO(db).getUfById(ufId);
                db.close();
                clientCache.setCidade(null);
                clientCache.setUf(uf);
                setResult(DashboardsActivity.CITY);
                finish();
            }

        }

    }

    public void onShowListaSelect(int tipoLista) {
        if(tipoLista == LocalitySelectionListActivity.LISTA_UF){
            Intent it = new Intent(this, LocalitySelectionListActivity.class);
            it.putExtra("tipoLista", tipoLista);
            startActivityForResult(it, ACTIVITY_LIST_SELECT);
        }
    }

    private void atualizarCampos(){
        UF uf = ClientCache.getInstance().getUf();
        if(uf != null){
            edtUF.setText(uf.getNome());
        }

    }

}