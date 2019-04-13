package com.example.kechennsheji;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
//import package com.example.kechennsheji.User;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
import com.example.kechennsheji.User.UserActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnalysisChartActivity extends AppCompatActivity {
    private DatabaseHelper helper_out;
    private DatabaseHelperPayin helperPayin;
    private Button jizhang,chakan,user,fenxibaogao;
    private Button bt_shouru,bt_huafei;

    private EditText editText1, editText2;
    private TextView tv_expense,tv_jiaotong,tv_zhufang,tv_yilioa,tv_yule,tv_shenghuoyongp,tv_jiaoyu,tv_qita;
    private PieChart mChart;
    private final String TAG="Piechart";
    private String[] x = new String[] { "交通", "住房", "医疗","娱乐","教育","生活用品","其他" };
    private float m_jiaotong,m_zhufang,m_yiliao,m_yule,m_jiaoyu,m_shenghuoyongping,m_qita,total;
    private float m_jiaotong2,m_zhufang2,m_yiliao2,m_yule2,m_jiaoyu2,m_shenghuoyongping2,m_qita2;

    // 凑成100 % 100
    private float[] y ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysizechart);
            mChart = (PieChart) findViewById(R.id.pie_chart);
            // 图的描述
            mChart.setCenterText("饼状图");
            // 以7个对应数据集做测试
            QTotal_expense();
            initview();
           /* Log.d(TAG,String.valueOf(m_jiaotong));
            Log.d(TAG,String.valueOf(m_jiaotong2));
            Log.d(TAG,String.valueOf(m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita));
            Log.d(TAG,String.valueOf((float)m_jiaotong/(m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita)));*/
         total=m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita;
        m_jiaotong=((float)m_jiaotong/total)*100;
        m_zhufang=((float)m_zhufang/total)*100;
        m_yiliao=((float)m_yiliao/total)*100;
        m_yule=((float)m_yule/total)*100;
        m_jiaoyu=((float)m_jiaoyu/total)*100;
        m_shenghuoyongping=((float)m_shenghuoyongping/total)*100;
        m_qita=((float)m_qita/total)*100;

            y = new float[]{m_jiaotong,m_zhufang,m_yiliao,m_yule,m_jiaoyu,m_shenghuoyongping,m_qita};
            setData(x.length);

        }
        DecimalFormat df=new DecimalFormat("0.00");
    public void QTotal_expense()
    {
        helper_out=new DatabaseHelper(AnalysisChartActivity.this,"table_payout",null,1);
        SQLiteDatabase db=helper_out.getReadableDatabase();
        Cursor cursor_jiaotong=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"交通"});
        Cursor cursor_zhufang=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"住房"});
        Cursor cursor_yiliao=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"医疗"});
        Cursor cursor_yule=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"娱乐"});
        Cursor cursor_jiaoyu=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"教育"});
        Cursor cursor_shenghuoyongp=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"生活用品"});
        Cursor cursor_qita=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"其他"});

        if (cursor_jiaotong.moveToFirst())
        { do
            { m_jiaotong=cursor_jiaotong.getInt(0); }
            while (cursor_jiaotong.moveToNext());

        } m_jiaotong2=m_jiaotong;
        cursor_jiaotong.close();

        if (cursor_zhufang.moveToFirst())
        { do
        {
            m_zhufang=cursor_zhufang.getInt(0); }
        while (cursor_zhufang.moveToNext());
        }m_zhufang2=m_zhufang;
        cursor_zhufang.close();
        if(cursor_yiliao.moveToFirst())
        {
            do{
                m_yiliao=cursor_yiliao.getInt(0);
            }while (cursor_yiliao.moveToNext());
        }m_yiliao2=m_yiliao;
        cursor_yiliao.close();
        if(cursor_yule.moveToFirst())
        {
            do {
                m_yule=cursor_yule.getInt(0);

            }while (cursor_yule.moveToNext()) ;
        }m_yule2=m_yule;
        cursor_yule.close();
        if(cursor_jiaoyu.moveToFirst())
        {
            do {
                m_jiaoyu=cursor_jiaoyu.getInt(0);

            }while (cursor_jiaoyu.moveToNext()) ;
        }m_jiaoyu2=m_jiaoyu;
        cursor_jiaoyu.close();
        if(cursor_shenghuoyongp.moveToFirst())
        {
            do{
                m_shenghuoyongping=cursor_shenghuoyongp.getInt(0);
            }while (cursor_shenghuoyongp.moveToNext());
        }m_shenghuoyongping2=m_shenghuoyongping;
        cursor_shenghuoyongp.close();
        if(cursor_qita.moveToFirst())
        {
            do {
                m_qita=cursor_qita.getInt(0);

            }while (cursor_qita.moveToNext()) ;
        }m_qita2=m_qita;
        cursor_qita.close();


    }
    public void initview()
    {
        tv_expense=findViewById(R.id.total_expense2);
        tv_jiaotong=findViewById(R.id.total_jiaotong2);
        tv_jiaoyu=findViewById(R.id.total_jiaoyu2);
        tv_yilioa=findViewById(R.id.total_yiliao2);
        tv_zhufang=findViewById(R.id.total_zhufang2);
        tv_yule=findViewById(R.id.total_yule2);
        tv_qita=findViewById(R.id.total_qita2);
        tv_shenghuoyongp=findViewById(R.id.total_shenghuo2);
        tv_expense.setText(String.valueOf(m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita)+" 元");
        tv_jiaotong.setText(String.valueOf(m_jiaotong2)+" 元");
        tv_jiaoyu.setText(String.valueOf((m_jiaoyu2))+" 元");
        tv_yilioa.setText(String.valueOf(m_yiliao2)+" 元");
        tv_zhufang.setText((String.valueOf(m_zhufang2))+" 元");
        tv_yule.setText(String.valueOf(m_yule2)+" 元");
        tv_shenghuoyongp.setText((String.valueOf(m_shenghuoyongping2))+" 元");
        tv_qita.setText(String .valueOf(m_qita2)+" 元");
    }
        private void setData(int count) {
            // 准备x"轴"数据：在i的位置，显示x[i]字符串
            ArrayList<String> xVals = new ArrayList<String>();
            // 真实的饼状图百分比分区。
            // Entry包含两个重要数据内容：position和该position的数值。
            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int xi = 0; xi < count; xi++) {
                xVals.add(xi, x[xi]);
                // y[i]代表在x轴的i位置真实的百分比占
                yVals.add(new Entry(y[xi], xi));
            }
            PieDataSet yDataSet = new PieDataSet(yVals, "百分比占");
            // 每个百分比占区块绘制的不同颜色
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);
            colors.add(Color.YELLOW);
            colors.add(Color.GRAY);
            colors.add(Color.DKGRAY);
            colors.add(Color.WHITE);
            yDataSet.setColors(colors);
            // 将x轴和y轴设置给PieData作为数据源
            PieData data = new PieData(xVals, yDataSet);
            // 设置成PercentFormatter将追加%号
            data.setValueFormatter(new PercentFormatter());
            // 文字的颜色
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(10f);
            // 最终将全部完整的数据喂给PieChart
            mChart.setData(data);
            mChart.invalidate();

        jizhang = findViewById(R.id.btn_jizhang);
        jizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisChartActivity.this, MainPayoutActivity.class);
                startActivity(intent);
            }
        });
        chakan = findViewById(R.id.btn_chakanzhangdan);
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisChartActivity.this, BillListActivity.class);
                startActivity(intent);
            }
        });
        fenxibaogao = findViewById(R.id.btn_fenxibaogao);
        fenxibaogao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisChartActivity.this, AnalysisChartActivity.class);
                startActivity(intent);
            }
        });
        user = findViewById(R.id.btn_yonghuzhongxin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisChartActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
        bt_shouru=findViewById(R.id.bt_shouru);
        bt_shouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AnalysisChartActivity.this,AnalysisIncomeActivity.class);
                startActivity(intent);
            }
        });


      /*  //调出日历
        editText1 = findViewById(R.id.Et_date1);
        editText2 = findViewById(R.id.Et_date2);
        editText1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        editText2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg1();
                    return true;
                }
                return false;
            }
        });


        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg1();
                }
            }
        });*/
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AnalysisChartActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AnalysisChartActivity.this.editText1.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    protected void showDatePickDlg1() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(AnalysisChartActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AnalysisChartActivity.this.editText2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog1.show();


    }

}