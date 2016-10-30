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
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;


public class CitySelectionActivity extends Activity {

    private static final String TAG = new Object() {
    }.getClass().getName();
    private static final int ACTIVITY_LIST_SELECT = 1;

    private EditText edtUF;
    private EditText edtCidade;

    private ClientCache clientCache;
    private LocalDataBase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_selection);

        clientCache = ClientCache.getInstance();
        db = LocalDataBase.getInstance();

        // UF
        LinearLayout btnUF = (LinearLayout) findViewById(R.id.city_selection_btn_uf);
        edtUF = (EditText) findViewById(R.id.city_selection_edt_uf);
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

        // City
        LinearLayout btnCidade = (LinearLayout) findViewById(R.id.city_selection_btn_cidade);
        edtCidade = (EditText) findViewById(R.id.city_selection_edt_cidade);
        btnCidade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_CIDADE);
            }
        });

        edtCidade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_CIDADE);
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
            if(ClientCache.getInstance().getCidade() != null){
                final String cidadeName = ClientCache.getInstance().getCidade().getNome();
                db.open(getApplicationContext());
                Cidade cidade = new CidadeDAO(db).getCidadeByNome(cidadeName);
                UF uf = new UfDAO(db).getUfById(cidade.getIdUF());
                db.close();
                clientCache.setCidade(cidade);
                clientCache.setUf(uf);
                setResult(DashboardsActivity.CITY);
                finish();
            }

        }

    }

    public void onShowListaSelect(int tipoLista) {
        if(tipoLista == LocalitySelectionListActivity.LISTA_CIDADE){
            if(ClientCache.getInstance().getUf() != null){
                Intent it = new Intent(this, LocalitySelectionListActivity.class);
                it.putExtra("tipoLista", tipoLista);
                startActivityForResult(it, ACTIVITY_LIST_SELECT);
            }
        }
        else {
            Intent it = new Intent(this, LocalitySelectionListActivity.class);
            it.putExtra("tipoLista", tipoLista);
            startActivityForResult(it, ACTIVITY_LIST_SELECT);
        }
    }

    private void atualizarCampos(){
        UF uf = ClientCache.getInstance().getUf();
        if(uf != null){
            edtUF.setText(uf.getNome());
            edtCidade.setText(getString(R.string.empty));
            edtCidade.setHint(R.string.cidade);
        }

        Cidade cidade = ClientCache.getInstance().getCidade();
        if(cidade != null){
            edtCidade.setText(cidade.getNome());
            clientCache.setListESByCidade(null);
        }
    }

}