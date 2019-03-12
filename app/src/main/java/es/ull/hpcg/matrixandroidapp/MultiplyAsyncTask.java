package es.ull.hpcg.matrixandroidapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import matrix.lib.HTTPData;
import matrix.lib.Matrix;
import matrix.lib.Operation;
import matrix.lib.TimeController;


public class MultiplyAsyncTask extends AsyncTask<Integer, Void, List<String>> {
    WeakReference<Activity> mWeakActivity;

    public MultiplyAsyncTask(Activity activity) {
        mWeakActivity = new WeakReference<>(activity);
    }

    @Override
    protected List<String> doInBackground(Integer... integers) {
        TimeController timeCon = new TimeController();

        // TODo Add field in UI
        HTTPData req = new HTTPData("http://10.209.3.126:5000/message");
        StringBuilder message = new StringBuilder();

        boolean print = (integers[2] != 0);
        message.append(
                String.format(
                        Locale.ENGLISH,
                        "Input data:\nMatrix size: %d\t Matrix module: %d\t Matrix print: %b\n",
                        integers[0], integers[1], print
                )
        );

        Matrix matrix_a = new Matrix(integers[0]);
        timeCon.setName("Matrix fill A");
        timeCon.snapStart();
        req.setData(timeCon.getStart(), Operation.AS);
        req.sendData();
        matrix_a.fill(integers[1]);
        timeCon.snapFinish();
        req.setData(timeCon.getFinish(), Operation.AF);
        req.sendData();
        message.append(timeCon);

        Matrix matrix_b = new Matrix(integers[0]);
        timeCon.setName("Matrix fill B");
        timeCon.snapStart();
        req.setData(timeCon.getStart(), Operation.BS);
        req.sendData();
        matrix_b.fill(integers[1]);
        timeCon.snapFinish();
        req.setData(timeCon.getFinish(), Operation.BF);
        req.sendData();
        message.append(timeCon);

        timeCon.setName("Matrix compute");
        timeCon.snapStart();
        req.setData(timeCon.getStart(), Operation.XS);
        req.sendData();
        Matrix matrix_computed = matrix_a.multiply(matrix_b);
        timeCon.snapFinish();
        req.setData(timeCon.getFinish(), Operation.XF);
        req.sendData();
        message.append(timeCon);

        String matrixResult = "";
        if (integers[2] == 1) {
            matrixResult = "Matrix A:\n".concat(
                    matrix_a.toString()
            );
            matrixResult = matrixResult.concat(
                    "\nMatrix B:\n".concat(
                            matrix_b.toString()
                    )
            );
            matrixResult = matrixResult.concat(
                    "\nMatrix computed:\n".concat(
                            matrix_computed.toString()
                    )
            );
        }

        List<String> listOfStrings = new ArrayList<>();
        listOfStrings.add(matrixResult);
        listOfStrings.add(message.toString());

        return listOfStrings;
    }

    @Override
    protected void onPreExecute() {
        Activity activity = mWeakActivity.get();
        if (activity != null){
            TextView status = activity.findViewById(R.id.status);
            status.setText(activity.getString(R.string.working));
            EditText input = activity.findViewById(R.id.matrix_size);
            input.setEnabled(false);
            input = activity.findViewById(R.id.matrix_module);
            input.setEnabled(false);
            Button compute = activity.findViewById(R.id.compute);
            compute.setEnabled(false);
            Switch print = activity.findViewById(R.id.matrix_print);
            print.setEnabled(false);
        }
    }

    @Override
    protected void onPostExecute(List<String> s) {
        super.onPostExecute(s);
        Activity activity = mWeakActivity.get();
        if (activity != null){
            TextView status = activity.findViewById(R.id.status);
            status.setText(activity.getString(R.string.done));
            TextView result = activity.findViewById(R.id.result);
            result.setText(s.get(0));
            EditText input = activity.findViewById(R.id.matrix_size);
            input.setEnabled(true);
            input = activity.findViewById(R.id.matrix_module);
            input.setEnabled(true);
            Button compute = activity.findViewById(R.id.compute);
            compute.setEnabled(true);
            Switch print = activity.findViewById(R.id.matrix_print);
            print.setEnabled(true);
            TextView matrixTiming = activity.findViewById(R.id.matrix_timing);
            matrixTiming.setText(s.get(1));
        }
    }
}
