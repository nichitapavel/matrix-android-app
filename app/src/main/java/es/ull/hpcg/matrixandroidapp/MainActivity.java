package es.ull.hpcg.matrixandroidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText matrixSizeEditText = findViewById(R.id.matrix_size);
        matrixSizeEditText.setTransformationMethod(null);
        EditText matrixModuleEditText = findViewById(R.id.matrix_module);
        matrixModuleEditText.setTransformationMethod(null);
    }

    public void compute(View view) {
        EditText matrixSizeEditText = findViewById(R.id.matrix_size);
        int matrixSize = Integer.parseInt(
                matrixSizeEditText.getText().toString()
        );

        EditText matrixModuleEditText = findViewById(R.id.matrix_module);
        int matrixModule = Integer.parseInt(
                matrixModuleEditText.getText().toString()
        );
        Switch printSwitch = findViewById(R.id.matrix_print);
        boolean matrixPrint = printSwitch.isChecked();
        int asyncPrint = matrixPrint ? 1 : 0;

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        MultiplyAsyncTask task = new MultiplyAsyncTask(this);
        task.execute(matrixSize, matrixModule, asyncPrint);
    }
}
