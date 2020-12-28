package com.application.radiofreq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> implements Filterable {

    private ArrayList<ParseItem> parseItems;
    private ArrayList<ParseItem> parseItemsAll;
    private Context context;

    public ParseAdapter(ArrayList<ParseItem> parseItems, Context context) {
        this.parseItems = parseItems;
        this.parseItemsAll = new ArrayList<>(parseItems);
        this.context = context;
    }

    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdapter.ViewHolder holder, int position) {
        ParseItem parseItem = parseItems.get(position);
        holder.textViewTitle.setText(parseItem.getTitle());
        holder.textViewFrequency.setText(parseItem.getFrequency());
        if (parseItem.getImgUrl().isEmpty()) {
            holder.imageView.setImageResource(R.drawable.ic_arrow_back_24);
        } else {
            Picasso.get().load(parseItem.getImgUrl()).into(holder.imageView);
        }

        //go to Playing fragment while button play from Home fragment is pressed
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                PlayingFragment playingFragment = new PlayingFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playingFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            parseItemsAll = parseItems;

            List<ParseItem> filteredArrayList = new ArrayList<>();

            if(constraint.toString().isEmpty()) {
                filteredArrayList.addAll(parseItems);
            } else {
                for(ParseItem p : parseItemsAll) {
                    if(p.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredArrayList.add(p);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredArrayList;
            filterResults.count = filteredArrayList.size();

            return filterResults;
        }

        //runs on a UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            parseItems.clear();
            parseItems.addAll((Collection<? extends ParseItem>) results.values);
            notifyDataSetChanged();
//            parseItemsAll = (ArrayList<ParseItem>) results.values;
//            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewFrequency;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.radioTitle);
            textViewFrequency = itemView.findViewById(R.id.frequency);
            button = itemView.findViewById(R.id.play_button);
        }
    }
}
