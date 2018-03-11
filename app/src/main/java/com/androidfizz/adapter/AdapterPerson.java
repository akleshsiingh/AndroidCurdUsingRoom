package com.androidfizz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidfizz.androidcurdroompersistence.R;
import com.androidfizz.model.ModelPerson;

import java.util.List;

/**
 * Created by Aklesh on 1/2/2018.
 */

public class AdapterPerson extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_DELETE = 1;
    public static final int TYPE_UPDATE = 2;
    private List<ModelPerson> personList;


    public AdapterPerson(List<ModelPerson> personList) {
        this.personList = personList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_person_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModelPerson single = personList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;

        mHolder.tvName.setText(single.getName());
        mHolder.tvEmail.setText(single.getEmail());
        mHolder.tvAge.setText(single.getAge());
        mHolder.tvDelete.setOnClickListener(new OnCustomCLick(single, TYPE_DELETE));
        mHolder.tvUpdate.setOnClickListener(new OnCustomCLick(single, TYPE_UPDATE));
    }

    //RETURN 0 if List size is null. This prvent our app from crashing
    @Override
    public int getItemCount() {
        return personList != null ? personList.size() : 0;
    }

    public void setListItems(List<ModelPerson> mTempPersonList) {
        this.personList = mTempPersonList;
        notifyDataSetChanged();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvEmail, tvAge, tvUpdate,tvDelete;

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvUpdate = itemView.findViewById(R.id.tvUpdate);
            tvDelete = itemView.findViewById(R.id.tvDelete);

        }
    }

    private class OnCustomCLick implements View.OnClickListener {
        private ModelPerson single;
        private int  type;

        OnCustomCLick(ModelPerson single, int type) {
            this.single = single;
            this.type = type;
        }

        @Override
        public void onClick(View view) {
            if (mListner != null)
                mListner.onClick(single, type);
        }
    }


    //LISTNERS FOR CLICK EVENT (DELETE)
    private OnClickListner mListner;

    public interface OnClickListner {
        void onClick(ModelPerson single, int type);
    }

    public void setOnCustomClickListner(OnClickListner mListner) {
        this.mListner = mListner;
    }


}
