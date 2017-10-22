package com.project.dwidianayu.getpagesource;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    EditText textUrl;
    Spinner spinn;
    ProgressBar progres;
    TextView result;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUrl = (EditText)findViewById(R.id.InputURL);
        spinn = (Spinner)findViewById(R.id.spinner);
        progres = (ProgressBar)findViewById(R.id.Proses);
        result = (TextView)findViewById(R.id.Sourcecode);

        progres.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinn.setAdapter(adapter);
    }

    public void getSource(View view) {
        url = spinn.getSelectedItem()+textUrl.getText().toString();
        boolean valid = Patterns.WEB_URL.matcher(url).matches();
        if(valid){
            getSupportLoaderManager().restartLoader(0,null,this);
            progres.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
        }else{
            Loader loader = getSupportLoaderManager().getLoader(0);
            if(loader != null){
                loader.cancelLoad();
            }
            result.setText("URL Tidak Valid");
            progres.setVisibility(View.GONE);
            result.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new GetSource(this,url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        progres.setVisibility(View.GONE);
        result.setVisibility(View.VISIBLE);
        result.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}
