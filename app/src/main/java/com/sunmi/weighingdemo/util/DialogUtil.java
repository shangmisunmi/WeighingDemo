package com.sunmi.weighingdemo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sunmi.weighingdemo.R;

public class DialogUtil {

    private static Dialog dialog;
    private static StringBuilder sb;

    public static void showDialog(Context context, onClickListener listener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pcs, null, false);
        sb = new StringBuilder();
        TextView tv1 = view.findViewById(R.id.tv_1);
        TextView tv2 = view.findViewById(R.id.tv_2);
        TextView tv3 = view.findViewById(R.id.tv_3);
        TextView tv4 = view.findViewById(R.id.tv_4);
        TextView tv5 = view.findViewById(R.id.tv_5);
        TextView tv6 = view.findViewById(R.id.tv_6);
        TextView tv7 = view.findViewById(R.id.tv_7);
        TextView tv8 = view.findViewById(R.id.tv_8);
        TextView tv9 = view.findViewById(R.id.tv_9);
        TextView tv0 = view.findViewById(R.id.tv_0);
        TextView tvClear = view.findViewById(R.id.tv_clear);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        TextView tvPcs = view.findViewById(R.id.tv_pcs);

        tvCancel.setOnClickListener(view1 -> disMissDialog());

        tvConfirm.setOnClickListener(view12 -> {
            boolean isEmpty = sb.toString().isEmpty();
            if (isEmpty){
                return;
            }
            listener.confirm(Integer.parseInt(sb.toString()));
            disMissDialog();
        });

        tv0.setOnClickListener(view13 -> {
            if (!sb.toString().isEmpty()){
                sb.append(tv0.getText());
                tvPcs.setText(sb.toString());
            }
        });

        tv1.setOnClickListener(view14 -> {
            sb.append(tv1.getText());
            tvPcs.setText(sb.toString());
        });

        tv2.setOnClickListener(view15 -> {
            sb.append(tv2.getText());
            tvPcs.setText(sb.toString());
        });

        tv3.setOnClickListener(view16 -> {
            sb.append(tv3.getText());
            tvPcs.setText(sb.toString());
        });

        tv4.setOnClickListener(view17 -> {
            sb.append(tv4.getText());
            tvPcs.setText(sb.toString());
        });

        tv5.setOnClickListener(view18 -> {
            sb.append(tv5.getText());
            tvPcs.setText(sb.toString());
        });

        tv6.setOnClickListener(view19 -> {
            sb.append(tv6.getText());
            tvPcs.setText(sb.toString());
        });
        tv1.setOnClickListener(view110 -> {
            sb.append(tv1.getText());
            tvPcs.setText(sb.toString());
        });
        tv7.setOnClickListener(view111 -> {
            sb.append(tv7.getText());
            tvPcs.setText(sb.toString());
        });
        tv8.setOnClickListener(view112 -> {
            sb.append(tv8.getText());
            tvPcs.setText(sb.toString());
        });
        tv9.setOnClickListener(view113 -> {
            sb.append(tv9.getText());
            tvPcs.setText(sb.toString());
        });

        tvClear.setOnClickListener(view114 -> {
            sb.delete(0,sb.length());
            tvPcs.setText(sb.toString());
        });

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.show();
    }

    public static void disMissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            sb.delete(0, sb.length());
        }
    }

    public interface onClickListener {
        void confirm(int pcs);
    }
}
