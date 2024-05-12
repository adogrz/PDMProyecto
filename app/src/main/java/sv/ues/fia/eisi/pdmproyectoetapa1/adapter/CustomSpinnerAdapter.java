package sv.ues.fia.eisi.pdmproyectoetapa1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class CustomSpinnerAdapter<T> extends ArrayAdapter<T> {
    private List<T> objects;
    private Context context;

    public CustomSpinnerAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);

        TextView label = root.findViewById(android.R.id.text1);
        label.setText(Objects.requireNonNull(getItem(position)).toString());

        return convertView;
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Devuelve el ID del elemento seleccionado
        return position;
    }
}
