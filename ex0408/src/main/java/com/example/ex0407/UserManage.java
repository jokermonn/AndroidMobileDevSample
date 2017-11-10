package com.example.ex0407;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserManage extends AppCompatActivity {
    private static final String DB_NAME="dbUser.db";
    private SQLiteDatabase db=null;

    private ListView lvShowItem=null;
    private Button btnDelete=null;
    private Button btnExit=null;
    private TextView tvShowInfo=null;

    private int UNLen=-1;
    private String strUserName[];


    private int iSelectedUser=0;

    private static ArrayList<String> listStr = null;
    private static ArrayList<Integer> listInt =null;
    private static List<HashMap<String, Object>> list = null;
    private MyAdapter adapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);

        lvShowItem=(ListView)findViewById(R.id.lvUMShowItem);
        btnDelete=(Button)findViewById(R.id.btnUMDelete);
        btnExit=(Button)findViewById(R.id.btnUMExit);
        tvShowInfo=(TextView)findViewById(R.id.tvUMShowInfo);

        if(getAllNUser()==true)
        {

            showCheckBoxListView();
        }
        else
        {

            tvShowInfo.setText("未找到任何用户");
        }


        btnDelete.setOnClickListener(new OnClickListener(){

            public void onClick(View arg0)
            {

                for(int i=0;i<listStr.size();i++)
                {

                    String strTUN=listStr.get(i).toString();

                    deleteSUN(strTUN);

                    int position = listInt.get(i).intValue();

                    list.remove(position);

                    adapter.syncListInt(i,listStr.size());

                    adapter.notifyDataSetChanged();

                    adapter.setCheckBoxValue(false);
                }

                tvShowInfo.setText("");

                Toast.makeText(UserManage.this, "共删除"+iSelectedUser+"个用户", Toast.LENGTH_SHORT).show();

                iSelectedUser=0;

                listStr.removeAll(listStr);

                listInt.removeAll(listInt);

            }
        });


        btnExit.setOnClickListener(new OnClickListener(){

            public void onClick(View v)
            {

                Intent intent=new Intent();

                intent.setClass(UserManage.this, UserLogin.class);

                startActivity(intent);
            }
        });

    }

    private void deleteSUN(String strUserName)
    {

        db.execSQL("delete from tuserinfo where username='"+strUserName+"'");

    }


    public void showCheckBoxListView() {
        list = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < strUserName.length; i++)
        {

            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("item_tv", strUserName[i]);

            map.put("item_cb", false);

            list.add(map);


            adapter = new MyAdapter(this, list, R.layout.aum_listviewitem,
                    new String[] { "item_tv", "item_cb" }, new int[] {
                    R.id.item_tv, R.id.item_cb });

            lvShowItem.setAdapter(adapter);
            listStr = new ArrayList<String>();
            listInt = new ArrayList<Integer>();


            lvShowItem.setOnItemClickListener(new OnItemClickListener() {


                public void onItemClick(AdapterView<?> arg0, View view,
                                        int position, long arg3)
                {

                    ViewHolder holder = (ViewHolder) view.getTag();

                    holder.cb.toggle();

                    MyAdapter.isSelected.put(position, holder.cb.isChecked());

                    if (holder.cb.isChecked() == true)
                    {

                        listStr.add(strUserName[position]);

                        listInt.add(Integer.valueOf(position));
                    }

                    else
                    {

                        listStr.remove(strUserName[position]);

                        listInt.remove(Integer.valueOf(position));
                    }

                    iSelectedUser=listStr.size();

                    tvShowInfo.setText("共选中"+iSelectedUser+"个用户");
                }

            });
        }
    }

    private boolean getAllNUser()
    {

        db = DBUtils.OpenCreateDB(this);

        Cursor cursor=db.rawQuery("select * from tuserinfo where usertype=0",null);

        UNLen=cursor.getCount();
        if(UNLen>=0)
        {

            strUserName = new String[UNLen];

            int iLen=0;

            if(cursor.moveToFirst())
            {

                strUserName[iLen]=cursor.getString(cursor.getColumnIndex("username"));

                iLen=iLen+1;

                while (cursor.moveToNext() && iLen<=UNLen)
                {

                    strUserName[iLen]=cursor.getString(cursor.getColumnIndex("username"));

                    iLen=iLen+1;
                }

                cursor.close();

                return true;
            }
            else
            {

                return false;
            }
        }
        else
        {

            return false;
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(db!=null)
        {
            db.close();
        }
    }

    public static class MyAdapter extends BaseAdapter
    {

        public static HashMap<Integer, Boolean> isSelected;

        private Context context = null;

        private LayoutInflater inflater = null;

        private List<HashMap<String, Object>> listAdapter = null;

        private String keyString[] = null;

        private String itemString = null;

        private int idValue[] = null;


        public MyAdapter(Context context, List<HashMap<String, Object>> list,
                         int resource, String[] from, int[] to)
        {
            this.context = context;
            this.listAdapter = list;
            keyString = new String[from.length];
            idValue = new int[to.length];

            System.arraycopy(from, 0, keyString, 0, from.length);

            System.arraycopy(to, 0, idValue, 0, to.length);

            inflater = LayoutInflater.from(context);

            setCheckBoxValue(false);
        }



        public void syncListInt(int start,int len)
        {
            for(int i=start+1;i<len;i++)
            {
                listInt.set(i,listInt.get(i)-1);
            }
        }

        public void setCheckBoxValue(boolean bCheck)
        {
            isSelected = new HashMap<Integer, Boolean>();
            for (int i = 0; i < listAdapter.size(); i++)
            {
                isSelected.put(i, bCheck);
            }
        }

        public int getCount()
        {
            return listAdapter.size();
        }


        public Object getItem(int arg0)
        {
            return listAdapter.get(arg0);
        }


        public long getItemId(int arg0)
        {
            return arg0;
        }


        public View getView(int position, View view, ViewGroup arg2)
        {
            ViewHolder holder = new ViewHolder();

            if (view == null)
            {

                view = inflater.inflate(R.layout.aum_listviewitem, null);
            }

            holder.tv = (TextView) view.findViewById(R.id.item_tv);

            holder.cb = (CheckBox) view.findViewById(R.id.item_cb);

            view.setTag(holder);

            HashMap<String, Object> map = listAdapter.get(position);
            if (map != null)
            {

                itemString = (String) map.get(keyString[0]);

                holder.tv.setText(itemString);
            }

            holder.cb.setChecked(isSelected.get(position));
            return view;
        }
    }


}



