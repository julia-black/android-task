package com.example.juli.myapplication;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

class NewsItemAdapter extends BaseAdapter {
    private final Context context;
    private final List<Article> data;
    private final LayoutInflater inflater;

    NewsItemAdapter(Context context, List<Article> data){

        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE); //для view
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //xml конвертруем в view (news_list_item)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        Article art = (Article) getItem(position);
        //если эта View никогда не использовалась
        if(convertView == null) {

            v = inflater.inflate(R.layout.news_list_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.titleView = (TextView) v.findViewById(R.id.title);
            holder.descriprionView =  (TextView) v.findViewById(R.id.description);
            holder.puDateView = (TextView)  v.findViewById(R.id.puDate);

            holder.titleView.setText(art.title);
            holder.descriprionView.setText(art.description);
            holder.puDateView.setText(art.pubDate);

            v.setTag(holder);//вместе с holder хранится с views
        }
        //если эта View уже использовалась
        else {
            v = convertView;
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.titleView.setText(art.title);
            holder.descriprionView.setText(art.description);
            holder.puDateView.setText(art.pubDate);

        }
        return v;
    }

    //для кэшрованя данных
    private static final class ViewHolder{
        private TextView titleView;
        private TextView descriprionView;
        private TextView puDateView;

    }
}
