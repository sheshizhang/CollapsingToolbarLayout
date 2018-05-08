package com.InterPolator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.InterPolator.View.AppBarStateChangeLister;
import com.InterPolator.View.SimpleDividerItemDecoration;
import com.viewpager.demo.R;

/**
 * Created by feiran.zhang on 2018/4/3.
 */

public class WeixinBankActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private WeixinBankAdapter mWeixinBankAdapter;
    private Toolbar mToolBar;
    private CollapsingToolbarLayout mcollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private TextView mTitle;
    private ImageView mImageView;

    private int[] icons = new int[]{R.mipmap.f1,
            R.mipmap.f2, R.mipmap.f3, R.mipmap.f4,
            R.mipmap.f5, R.mipmap.f6, R.mipmap.f7,
            R.mipmap.f8,R.mipmap.f1,R.mipmap.f2, R.mipmap.f3, R.mipmap.f4,
            R.mipmap.f5,R.mipmap.f6,R.mipmap.f7,R.mipmap.f8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weixin_bank);
        initView();
    }

    public void initView(){
//        mcollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(
//                R.id.collapsing_toolbar);
//        mcollapsingToolbarLayout.setTitle(getString(R.string.app_name));

        mTitle=(TextView)findViewById(R.id.title_textView);
        mImageView=(ImageView)findViewById(R.id.backdrop);
        mAppBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        mRecyclerView=(RecyclerView)findViewById(R.id.RecyclerViewid);
        mToolBar=(Toolbar)findViewById(R.id.toolbar);
        mcollapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        mcollapsingToolbarLayout.setTitle(getString(R.string.app_name));

//        mToolBar.setTitle("苍井空");
//        setSupportActionBar(mToolBar);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this,
                2, DividerItemDecoration.VERTICAL,null));

        mWeixinBankAdapter=new WeixinBankAdapter();
        mRecyclerView.setAdapter(mWeixinBankAdapter);

        mcollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        mcollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mcollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置
        mcollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
        mcollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeLister() {
            @Override
            public void onstateChanged(AppBarLayout appBarLayout, State state) {
                if(state==State.EXPANDED){
                    //展开状态

                    mTitle.setVisibility(View.GONE);
                }else if (state==State.COLLAPSED){
                    //折叠状态

                    mTitle.setVisibility(View.VISIBLE);
                }else{
                    //中间状态
                    mTitle.setVisibility(View.GONE);
                }
            }
        });




    }


    /**
     * adapter
     */
    public class WeixinBankAdapter extends RecyclerView.Adapter<WeixinBankAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder=new MyViewHolder(LayoutInflater.from(WeixinBankActivity.this)
                    .inflate(R.layout.activity_weixin_item,parent,false));
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.icon.setImageResource(icons[position]);

            Palette.from(BitmapFactory.decodeResource(getResources(),icons[position])).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Palette.Swatch lightswatch=palette.getDarkVibrantSwatch();
                    if(lightswatch==null){
                        for(Palette.Swatch swatch:palette.getSwatches()){
                            if (null != swatch) {
                                lightswatch = swatch;
                                break;
                            }
                        }
                    }
                    //设置背景色
                    GradientDrawable gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                            new int[]{colorLight(lightswatch.getRgb()),colorBurn(lightswatch.getRgb())});

                    holder.mBgRelativeLayout.setBackground(gradientDrawable);
                }
            });

        }



        @Override
        public int getItemCount() {
//            Log.w("zhangfeiran100","icons.length=="+icons.length);
            return icons.length;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder{
            private RelativeLayout mBgRelativeLayout;
            public ImageView icon;
            public MyViewHolder(View itemView) {
                super(itemView);

                mBgRelativeLayout=itemView.findViewById(R.id.bg);
                icon=itemView.findViewById(R.id.img);
            }
        }
    }


    private int colorBurn(int rgb) {
        //颜色加深
        int red=rgb>>16&0xFF;
        int green=rgb>>8&0xFF;
        int blue=rgb&0xFF;

        red= (int) Math.floor(red*(1-0.2));
        green=(int) Math.floor(green*(1-0.2));
        blue=(int)Math.floor(blue*(1-0.2));

        return Color.rgb(red,green,blue);
    }

    private int colorLight(int rgb) {
        //颜色变浅
        int red=rgb>>16&0xFF;
        int green=rgb>>8&0xFF;
        int blue=rgb&0xFF;

        red= (int) Math.floor(red*(1+0.2));
        green=(int) Math.floor(green*(1+0.2));
        blue=(int)Math.floor(blue*(1+0.2));
        return Color.rgb(red,green,blue);
    }

}
