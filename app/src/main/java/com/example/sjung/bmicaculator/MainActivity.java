package com.example.sjung.bmicaculator;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnBMI, btnReset, btnThoat;
    EditText txtHeight;
    EditText txtWeight;
    TextView lblBMI, lblDanhGia, lblNguyCo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBMI=(Button)findViewById(R.id.btnBMI);
        btnReset=(Button)findViewById(R.id.btnReset);
        btnThoat=(Button)findViewById(R.id.btnThoat);
        txtHeight=(EditText)findViewById(R.id.txtChieuCao);
        txtWeight=(EditText)findViewById(R.id.txtCanNang);
        lblBMI=(TextView)findViewById(R.id.lblChiSo);
        lblDanhGia=(TextView)findViewById(R.id.lblDanhGia);
        lblNguyCo=(TextView)findViewById(R.id.lblNguyCoPhatBenh);
        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBMI_Click(v);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReset_Click(v);
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    void btnBMI_Click(View v)
    {
        float h, w;
        try
        {
            h=Float.parseFloat(txtHeight.getText().toString());
        }
        catch (Exception e)
        {
            ShowMessage("Chiều cao nhập vào không đúng định dạng");
            return;
        }
        try
        {
            w=Float.parseFloat(txtWeight.getText().toString());
        }
        catch (Exception e)
        {
            ShowMessage("Cân nặng nhập vào không đúng định dạng");
            return;
        }
        float BMI=w/((h/100)*(h/100));
        lblBMI.setText(BMI+"");
        if(BMI<18.5)
        {
            lblDanhGia.setText("Gầy");
            lblNguyCo.setText("Thấp");
        }
        else if(BMI>=18.5&&BMI<24.9) {
            lblDanhGia.setText("Bình thường");
            lblNguyCo.setText("Trung bình");
        }
        else if(BMI>=25&&BMI<29.9) {
            lblDanhGia.setText("Hơi béo");
            lblNguyCo.setText("Cao");
        }else if(BMI>=30&&BMI<34.9) {
            lblDanhGia.setText("Béo phì cấp độ 1");
            lblNguyCo.setText("Cao");
        }else if(BMI>=35&&BMI<39.9) {
            lblDanhGia.setText("Béo phì cấp độ 2");
            lblNguyCo.setText("Rất cao");
        }else if(BMI>=40) {
            lblDanhGia.setText("Béo phì cấp độ 3");
            lblNguyCo.setText("Nguy hiểm");
        }}
    void btnReset_Click(View v)
    {
        lblDanhGia.setText("");
        lblBMI.setText("");
        txtWeight.setText("");
        txtHeight.setText("");
    }
    void ShowMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String shareString=String.format("Chiều cao: %s cm, \nCân nặng: %s kg, \nChỉ số BMI: %s, \nĐánh giá: %s, \nNguy cơ phát bệnh: %s",
                txtHeight.getText().toString(),
                txtWeight.getText().toString(),
                lblBMI.getText().toString(),
                lblDanhGia.getText().toString(),
                lblNguyCo.getText().toString());

        if(lblBMI.getText().toString().isEmpty()) {
            ShowMessage("Bạn chưa tính chỉ số BMI");
            return false;
        }
        switch (item.getItemId()) {
            case R.id.action_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", shareString);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this,"Đã sao chép", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_send:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent
                        .putExtra(Intent.EXTRA_TEXT,shareString);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this,"Bạn chưa cài Facebook Messenger rồi", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
