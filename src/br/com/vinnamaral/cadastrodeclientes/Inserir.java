package br.com.vinnamaral.cadastrodeclientes;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Inserir extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inserir);
	}

	public void CadastrarClick(View v){
    	EditText txtNome = (EditText)findViewById(R.id.txtNome);
    	EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
    	EditText txtCidade = (EditText)findViewById(R.id.txtCidade);
    	
    	if(txtNome.getText().toString().length() <= 0){
			txtNome.setError(getResources().getString(R.string.Lbfillnome));
			txtNome.requestFocus();
		} else if(txtEmail.getText().toString().length() <= 0){
			txtEmail.setError(getResources().getString(R.string.Lbfillemail));
			txtEmail.requestFocus();
		} else if(txtCidade.getText().toString().length() <= 0){
			txtCidade.setError(getResources().getString(R.string.Lbfillcidade));
			txtCidade.requestFocus();
		} else{
    		try{
	    		SQLiteDatabase db = openOrCreateDatabase("Clientes.db", Context.MODE_PRIVATE, null);
	    		
	    		ContentValues ctv = new ContentValues();
	    		ctv.put("nome", txtNome.getText().toString());
	    		ctv.put("email", txtEmail.getText().toString());
	    		ctv.put("cidade", txtCidade.getText().toString());
	    		
	    		if(db.insert("clientes", "_id", ctv) > 0){
	    			Toast.makeText(getBaseContext(), getResources().getString(R.string.Lbcadastrado_ok), Toast.LENGTH_SHORT).show();
	    			db.close();
	    			finish();
	    		} else{
	    			Toast.makeText(getBaseContext(), getResources().getString(R.string.Lbcadastrado_nok), Toast.LENGTH_SHORT).show();
	    			db.close();
	    		}
    		}catch(Exception ex){
    			Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
    		}
    	}
    }
}
