package ba.unsa.etf.rma.rma20tomasrobert24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter {
    private int resource;
    Integer[] Image;
    String[] Text;

    public SpinnerAdapter(Context context, int _resource, String[] text, Integer[] image) {
        super(context, _resource, text);
        Image = image;
        Text = text;
        resource = _resource;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LinearLayout newView;
        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout)convertView;
        }

        TextView tv = (TextView)newView.findViewById(R.id.textView);
        ImageView img = (ImageView) newView.findViewById(R.id.imageView);

        tv.setText(Text[position]);
        img.setImageResource(Image[position]);

        return newView;
    }

    @Override
    public View getDropDownView(int position,View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
