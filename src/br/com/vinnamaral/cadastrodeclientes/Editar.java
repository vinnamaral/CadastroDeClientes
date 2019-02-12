package br.com.vinnamaral.cadastrodeclientes;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editar extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar);

		Intent it = getIntent();
		int id = it.getIntExtra("id", 0);

		SQLiteDatabase db = openOrCreateDatabase("Clientes.db", Context.MODE_PRIVATE, null);

		Cursor cursor = db.rawQuery("SELECT * FROM CLIENTES WHERE _id = ?", 
				new String[]{String.valueOf(id)});

		if(cursor.moveToFirst()){
			EditText txtNome = (EditText)findViewById(R.id.txtNome);
			EditText txtEmail = (EditText)findViewById(R.id.txtEmail);
			EditText txtCidade = (EditText)findViewById(R.id.txtCidade);

			txtNome.setText(cursor.getString(1));
			txtEmail.setText(cursor.getString(2));
			txtCidade.setText(cursor.getString(3));
		}
		db.close();
		cursor.close();
	}
	
	private String getStringResourceByName(String aString) {
	    String packageName = getPackageName();
	    int resId = getResources().getIdentifier(aString, "string", packageName);
	    if (resId == 0) {
	        return aString;
	    } else {
	        return getString(resId);
	    }
	}

	public void AtualizarClick(View v){
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

				Intent it = getIntent();
				int id = it.getIntExtra("id", 0);

				if(db.update("clientes", ctv, "_id = ?", new String[]{String.valueOf(id)}) > 0){
					Toast.makeText(getBaseContext(), getResources().getString(R.string.Lbatualizado_ok), Toast.LENGTH_SHORT).show();
					db.close();
					finish();
				} else{
					Toast.makeText(getBaseContext(), getResources().getString(R.string.Lbatualizado_nok), Toast.LENGTH_SHORT).show();
					db.close();
				}
			}catch(Exception ex){
				Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void RemoverClick(View v){
		try{
			final SQLiteDatabase db = openOrCreateDatabase("Clientes.db", Context.MODE_PRIVATE, null);

			Intent it = getIntent();
			final int id = it.getIntExtra("id", 0);
			
			Builder msg = new Builder(Editar.this);
			msg.setMessage(getResources().getString(R.string.Lbdeletar));
			msg.setNegativeButton(getResources().getString(R.string.Lbnao), null);
			msg.setPositiveButton(getResources().getString(R.string.Lbsim), new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which){
					if(db.delete("clientes", "_id = ?", new String[]{ String.valueOf(id) }) > 0){
						Toast.makeText(getBaseContext(), getResources().getString(R.string.Lbremovido_ok), Toast.LENGTH_SHORT).show();
						db.close();
						finish();
					} else{
						Toast.makeText(getBaseContext(), getResources().getString(R.string.Lbremovido_nok), Toast.LENGTH_SHORT).show();
						db.close();
					}
				}
			});
			
			msg.show();
			
		}catch(Exception ex){
			Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}
