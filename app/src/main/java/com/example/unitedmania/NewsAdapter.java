package com.example.unitedmania;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import static androidx.core.content.ContextCompat.startActivity;

public class NewsAdapter extends ArrayAdapter<News>{

    private String mTitle;
    private String mDetails;

    public NewsAdapter(Context context, int resource, List<News> news) {
        super(context, 0, news);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }
        final News currentNews = getItem(position);

        //Setting the source's text.
        TextView newsSource = listItemView.findViewById(R.id.news_source);
        newsSource.setText(currentNews.getSource());

        //Setting the title's text.
        TextView newsTitle = listItemView.findViewById(R.id.news_title);
        mTitle = currentNews.getTitle();
        newsTitle.setText(mTitle);

        mDetails = currentNews.getDetails();

        //Go to the corresponding URL to know more about a piece of news when clicked on.
        LinearLayout newsListItem = (LinearLayout) listItemView.findViewById(R.id.news_list_item);
        newsListItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("title", mTitle);
                extras.putString("details", mDetails);
                i.putExtras(extras);
                startActivity(getContext(),i,null);

            }
        });

        return listItemView;
    }
}
