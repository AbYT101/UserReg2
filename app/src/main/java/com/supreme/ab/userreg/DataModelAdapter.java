package com.supreme.ab.userreg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.util.List;


public class DataModelAdapter extends RecyclerView.Adapter<DataModelAdapter.DataViewHolder> {
    Context context;
    List list;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    public DataModelAdapter(Context context, List list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample,null);

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        dataModel datamodel= (dataModel) list.get(position);
        holder.Name.setText(datamodel.getFullName());
        holder.gender.setText(datamodel.getGender());
        holder.username.setText(datamodel.getUserName());
        holder.itemView.setTag(datamodel.getFullName());


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public View.OnLongClickListener getOnLongClickListener() {
        return onLongClickListener;
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView username, gender, phoneNo, email;
        public String name;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            Name =itemView.findViewById(R.id.fullN);
            username =itemView.findViewById(R.id.userNa);
            gender= itemView.findViewById(R.id.gender3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a=getAdapterPosition();
                    dataModel dm=(dataModel) list.get(a);
                    Intent intent= new Intent(context,show_data.class);
                    String name=dm.getFullName();
                    intent.putExtra("name22", name );
                    intent.putExtra("phoneN", dm.getPhoneNo() );
                    intent.putExtra("email", dm.getEmail() );
                    intent.putExtra("username", dm.getUserName() );
                    intent.putExtra("gender", dm.getGender() );
                    intent.setData(Uri.parse("custom:"+Name+phoneNo+email+gender+username));
                    v.setTag(dm);
                    context.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });


        }
    }


}
