package app.bowling.bowlingapp.bowling.core.viewadapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import rabaapp.raba.app.raba.R;
import rabaapp.raba.app.raba.core.models.GridAnswer;

public class AnswersAdapter extends BaseAdapter{

    private Context mContext;

    public GridAnswer[] getGridAnswer() {
        return gridAnswer;
    }

    private GridAnswer[] gridAnswer = new GridAnswer[4];

    // 1
    public AnswersAdapter(Context context) {
        this.mContext = context;
        //this.gridAnswer = gridAnswer;
    }

    public void setAnswers(GridAnswer[] gridAnswer){
            this.gridAnswer = gridAnswer;
            Log.e("Rain", gridAnswer.toString());
            this.notifyDataSetChanged();
    }

    // 2
    @Override
    public int getCount() {
        return gridAnswer.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return gridAnswer[position].getAnswer_id();
    }

    // 4
    @Override
    public GridAnswer getItem(int position) {
        return gridAnswer[position];
    }




    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridAnswer gridAnswer = this.gridAnswer[position];

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.layout_answer, null);
        }

        TextView answerValue = convertView.findViewById(R.id.answer_content);
        answerValue.setText(gridAnswer.getValue());
        TextView answerOption = convertView.findViewById(R.id.option_label);
        answerOption.setText(gridAnswer.getOption());

        //Log.e("Rain", gridAnswer.getOption());

        return convertView;
    }
}
