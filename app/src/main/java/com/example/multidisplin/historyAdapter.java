package com.example.multidisplin;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.historyViewHolder> {

    private Context mContext;
    private List<ModelAnswer> mdatalist;
    private List<ModelAnswer> mdatalistfull;
    private onitemClickListener mListener;



    public int id;

    public historyAdapter(Context context, List<ModelAnswer> datalist){
        mContext = context;
        mdatalist = datalist;
//        mdatalistfull = mdatalist;
    }

    public interface onitemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onitemClickListener listener){
        mListener = listener;
    }

    public class historyViewHolder extends RecyclerView.ViewHolder{
        public TextView nimtext;
        public TextView nametext;
        public TextView titletext;
        public historyViewHolder(@NonNull View itemView,final onitemClickListener listener) {
            super(itemView);
            nimtext = itemView.findViewById(R.id.history_nim_item);
            nametext = itemView.findViewById(R.id.history_name_item);
            titletext = itemView.findViewById(R.id.history_title_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public historyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.history_item, parent, false);
        return new historyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull historyViewHolder holder, int position) {

        final ModelAnswer modelAnswer = mdatalist.get(position);
        String nim = modelAnswer.getNim();
        String name = modelAnswer.getName();
        String title = modelAnswer.getTitle();

        holder.nimtext.setText(nim);
        holder.nametext.setText(name);
        holder.titletext.setText(title);
    }

    @Override
    public int getItemCount() {
        return mdatalist.size();
    }

    public int getId() {
        return id;
    }

//    @Override
//    public Filter getFilter() {
//        return historyFilter;
//    }

//    public Filter historyFilter =  new Filter() {
////        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
////            List<ModelAnswer> modelAnswers = new ArrayList<>();
//////
////            if (constraint == null || constraint.length() == 0){
////                modelAnswers.addAll(mdatalistfull);
////            }else {
////                String filterPattern = constraint.toString().toLowerCase().trim();
////                for (ModelAnswer answer : mdatalistfull){
////                    if (answer.getName().toLowerCase().contains(filterPattern)){
////                        modelAnswers.add(answer);
////                    }
////                }
////            }
////            FilterResults results = new FilterResults();
////            results.values = modelAnswers;
////            return results;
//            return null;
//        }
////
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
////            mdatalist.clear();
////            mdatalist.addAll((List) results.values);
////            notifyDataSetChanged();
//        }
//    };

}

