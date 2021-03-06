package ru.anna.dev.fitnesskit.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.anna.dev.fitnesskit.R;
import ru.anna.dev.fitnesskit.mvp.model.Fitness;

public class FitnessAdapter extends RecyclerView.Adapter<FitnessAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Fitness> mFitnessList = new ArrayList<>();
    private String days[] = { "", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота", "воскресенье" };

    public FitnessAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mFitnessList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFitnessList.size();
    }

    public void addFitnesses(List<Fitness> fitnesses) {
        mFitnessList.addAll(fitnesses);
        notifyDataSetChanged();
    }

    public void clearFitnesses() {
        mFitnessList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tv_name) protected TextView mFitnessName;
        @Bind(R.id.tv_start_time) protected TextView mFitnessStart;
        @Bind(R.id.tv_end_time) protected TextView mFitnessEnd;
        @Bind(R.id.tv_teacher) protected TextView mTeacher;
        @Bind(R.id.tv_place) protected TextView mPlace;
        @Bind(R.id.tv_week_day) protected TextView mWeekDay;

        private Context mContext;
        private Fitness mFitness;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Fitness fitness) {
            mFitness = fitness;
            mFitnessName.setText(fitness.getName());
            mFitnessStart.setText(fitness.getStartTime() + " -");
            mFitnessEnd.setText(fitness.getEndTime());
            mTeacher.setText(fitness.getTeacher());
            mPlace.setText(fitness.getPlace());
            mWeekDay.setText(" (" + days[fitness.getWeekDay()] + ")");
        }

        @Override
        public void onClick(View v) {
            if (mFitnessClickListener != null) {
                mFitnessClickListener.onClick(mFitness.getName(), mFitness.getDescription(), getAdapterPosition());
            }
        }
    }

    public void setFitnessClickListener(OnFitnessClickListener listener) {
        mFitnessClickListener = listener;
    }

    private OnFitnessClickListener mFitnessClickListener;

    public interface OnFitnessClickListener {

        void onClick(String name, String description, int position);
    }
}
