package sv.ues.fia.eisi.pdmproyectoetapa1.data;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

public class GetDataTask extends AsyncTask<String, Void, JsonObject> {
    private static final String TAG = GetDataTask.class.getSimpleName();
    private final OnTaskCompleted listener;

    public GetDataTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected JsonObject doInBackground(String... urls) {
        JsonObject response = new HttpHandler().makeServiceCall(urls[0]);
        if (response != null) {
            Log.e(TAG, "Response from URL: " + response);
        } else {
            Log.e(TAG, "Couldn't get JSON from server.");
        }
        return response;
    }

    @Override
    protected void onPostExecute(JsonObject result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(JsonObject result);
    }
}
