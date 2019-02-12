package br.com.vinnamaral.cadastrodeclientes;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void ListarClientesClick(View v){
		Intent it = new Intent(getBaseContext(), ListarActivity.class);
		startActivity(it);
	}
	
	public void CadastrarClientesClick(View v){
		Intent it = new Intent(getBaseContext(), Inserir.class);
		startActivity(it);
	}
}
