package net.hongqianfly.abloutpicanddialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BottomSheetDialog(MainActivity.this).builder()
                .addSheetItem("相册", BottomSheetDialog.SheetItemColor.BULE,
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int width) {

                                Toast.makeText(MainActivity.this, "1111", Toast.LENGTH_SHORT).show();
//                                openPic();
                            }
                        })
                .addSheetItem("拍照", BottomSheetDialog.SheetItemColor.BULE,
                        new BottomSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int width) {
Toast.makeText(MainActivity.this, "222", Toast.LENGTH_SHORT).show();
//                                openPic();
                            }
                        }).show();


    }
}
