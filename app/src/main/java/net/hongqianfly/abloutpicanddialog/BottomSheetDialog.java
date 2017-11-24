package net.hongqianfly.abloutpicanddialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongQian.Wang on 2017/11/24.
 */

public class BottomSheetDialog {
    private Context mContext;
    private Dialog dialog;

    private boolean isShowTitle;
    private Display display;
    private TextView txtcancel;
    private LinearLayout lLayoutcontent;
    private TextView txttitle;

    private List<SheetItem> list;

    public  BottomSheetDialog(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
        this.mContext = context;
    }

    public BottomSheetDialog  builder() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_sheet_bottom, null);
        txtcancel = (TextView) inflate.findViewById(R.id.txt_cancel);
        lLayoutcontent = (LinearLayout) inflate.findViewById(R.id.lLayout_content);
        txttitle = (TextView) inflate.findViewById(R.id.txt_title);
        inflate.setMinimumWidth(display.getWidth());
        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog = new Dialog(mContext, R.style.BottomSheetDialog);
        dialog.setContentView(inflate);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

        return this;
    }
    public BottomSheetDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            isShowTitle = true;
            txttitle.setText(title);
        }else {
            isShowTitle = false;
        }
        return this;
    }

    public BottomSheetDialog setCanceledOnTouchOutSide(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public BottomSheetDialog  addSheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
        if (null == list) {
            list = new ArrayList<>();
        }

        list.add(new SheetItem(name, listener, color));
        return this;
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }
    private void setSheetItems() {
        if (null == list || list.size() < 1) {
            return;
        }

        int size = list.size();
        for (int i = 0; i <size; i++) {
            final int index=i;
            final SheetItem sheetItem = list.get(i);
            TextView textView = new TextView(mContext);
            textView.setText(sheetItem.name);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            if (size == 1) {
                if (isShowTitle) {
                    txttitle.setVisibility(View.VISIBLE);
                    textView.setBackgroundResource(R.drawable.bottom_sheet_bottom_selector);
                } else {
                    txttitle.setVisibility(View.GONE);
                    textView.setBackgroundResource(R.drawable.bottom_sheet_single_selector);//如果显示标题 且条目只有一个 圆角
                }
            } else {
                if (isShowTitle) {
                    txttitle.setVisibility(View.VISIBLE);
                    if ( i < size-1) {
                        textView.setBackgroundResource(R.drawable.bottom_sheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.bottom_sheet_bottom_selector);
                    }
                } else {
                    txttitle.setVisibility(View.GONE);
                    if (i == 0) {
                        textView.setBackgroundResource(R.drawable.bottom_sheet_top_selector);
                    } else if (i < size-1) {
                        textView.setBackgroundResource(R.drawable.bottom_sheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.bottom_sheet_bottom_selector);
                    }

                }
            }

            if(sheetItem.color!=null) {
                textView.setTextColor(Color.parseColor(sheetItem.color.getName()));
            }else {
                textView.setTextColor(Color.parseColor(SheetItemColor.BULE.getName()));
            }

            float scale=mContext.getResources().getDisplayMetrics().density;
            int height= (int) (45*scale+0.5);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetItem.listener.onClick(index);
                }
            });

            lLayoutcontent.addView(textView);
        }
    }

    private class SheetItem {
        String name;
        OnSheetItemClickListener listener;
        SheetItemColor color;

        public SheetItem(String name, OnSheetItemClickListener listener, SheetItemColor color) {
            this.name = name;
            this.listener = listener;
            this.color = color;
        }
    }

    public interface OnSheetItemClickListener {
        void onClick(int width);

    }

    public enum SheetItemColor {
        BULE("#037BFF"), RED("#FD4A2E");

        String name;

        public String getName() {
            return name;
        }

        private SheetItemColor(String name) {
            this.name = name;
        }
    }

}
