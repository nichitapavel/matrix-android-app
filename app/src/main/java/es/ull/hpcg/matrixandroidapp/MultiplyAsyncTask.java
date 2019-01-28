package es.ull.hpcg.matrixandroidapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import matrix.lib.Matrix;


public class MultiplyAsyncTask extends AsyncTask<Integer, Void, String> {
    WeakReference<Activity> mWeakActivity;

    public MultiplyAsyncTask(Activity activity) {
        mWeakActivity = new WeakReference<>(activity);
    }

    @Override
    protected String doInBackground(Integer... integers) {
        Matrix matrix_a = new Matrix(integers[0]);
        matrix_a.fill(integers[1]);
        Matrix matrix_b = new Matrix(integers[0]);
        matrix_b.fill(integers[1]);
        Matrix matrix_computed = matrix_a.multiply(matrix_b);

        String message = "";
        if (integers[2] == 1) {
            message = "Matrix A:\n".concat(
                    matrix_a.toString()
            );
            message = message.concat(
                    "\nMatrix B:\n".concat(
                            matrix_b.toString()
                    )
            );
            message = message.concat(
                    "\nMatrix computed:\n".concat(
                            matrix_computed.toString()
                    )
            );
        }

        return message;
    }

    @Override
    protected void onPreExecute() {
        Activity activity = mWeakActivity.get();
        if (activity != null){
            TextView status = activity.findViewById(R.id.status);
            status.setText(activity.getString(R.string.working));
            Button compute = activity.findViewById(R.id.compute);
            compute.setEnabled(false);
            Switch print = activity.findViewById(R.id.matrix_print);
            print.setEnabled(false);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Activity activity = mWeakActivity.get();
        if (activity != null){
            TextView status = activity.findViewById(R.id.status);
            status.setText(activity.getString(R.string.done));
            TextView result = activity.findViewById(R.id.result);
            result.setText(s);
            Button compute = activity.findViewById(R.id.compute);
            compute.setEnabled(true);
            Switch print = activity.findViewById(R.id.matrix_print);
            print.setEnabled(true);
        }
    }
}
