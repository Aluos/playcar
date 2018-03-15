package com.gavin.yang.indexprogav;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements com.gavin.yang.indexprogav.segmentView.onSegmentViewClickListener {

    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.segmentView)
    com.gavin.yang.indexprogav.segmentView segmentView;
    @BindView(R.id.main_index_title_img)
    ImageView mainIndexTitleImg;
    @BindView(R.id.main_title_img_layout)
    RelativeLayout mainTitleImgLayout;
    @BindView(R.id.index_banner)
    Banner banner;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    @BindView(R.id.gridview1)
    GridView gridview1;
    @BindView(R.id.gridview2)
    GridView gridview2;
    @BindView(R.id.gridview3)
    GridView gridview3;

    private PictureAdapter pictureAdapter, pictureAdapter2, pictureAdapter3;

    private List<String> imgs = new ArrayList<>();
    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "甜品饮品",
            "水上乐园", "汽车服务", "美发", "丽人", "景点",
            "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "外卖", "自助餐", "KTV", "机票/火车票"};

    private int[] icons = {R.drawable.hun_icon_15, R.drawable.hun_icon_17, R.drawable.hun_icon_19, R.drawable.hun_icon_21, R.drawable.hun_icon_27, R.drawable.hun_icon_28, R.drawable.hun_icon_29, R.drawable.hun_icon_30, R.drawable.new_icon_btn_24, R.drawable.new_icon_btn_34, R.drawable.hun_icon_15, R.drawable.hun_icon_17, R.drawable.hun_icon_19, R.drawable.hun_icon_21, R.drawable.hun_icon_27, R.drawable.hun_icon_28, R.drawable.hun_icon_29, R.drawable.hun_icon_30, R.drawable.new_icon_btn_24, R.drawable.new_icon_btn_34};


    private List<Subject> subjectList;
    private LinearLayout ll_dot;
    //每页展示的主题个数
    private final int pageSize = 10;
    //当前页索引
    private int currentIndex;


    private String[] titles1 = new String[]{
            "司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "司仪一号", "司仪一号"
    };

    private Integer[] images = {
            R.drawable.hun_icon_35, R.drawable.hun_icon_35,
            R.drawable.hun_icon_35, R.drawable.hun_icon_35,
            R.drawable.hun_icon_35, R.drawable.hun_icon_35,
            R.drawable.hun_icon_35, R.drawable.hun_icon_35
    };


    private String[] titles2 = new String[]{
            "司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春"

    };

    private Integer[] images2 = {
            R.drawable.hun_icon_39, R.drawable.hun_icon_42,
            R.drawable.hun_icon_45, R.drawable.hun_icon_51,
            R.drawable.hun_icon_54, R.drawable.hun_icon_57
    };


    private String[] titles3 = new String[]{
            "司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "司仪一号", "司仪一号"
    };


    private Integer[] images3 = {
            R.drawable.hun_icon_35, R.drawable.hun_icon_35,
            R.drawable.hun_icon_35, R.drawable.hun_icon_35,
            R.drawable.hun_icon_35, R.drawable.hun_icon_35,
            R.drawable.hun_icon_35, R.drawable.hun_icon_35
    };


    private boolean isfirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        segmentView.setSegmentText("订单", 0);
        segmentView.setSegmentText("抢单", 1);
        segmentView.setOnSegmentViewClickListener(this);

        imgs.add("http://chw.zhudantech.com/carplay/Public/attached/Banner/phpZl574873.jpeg");
        imgs.add("http://chw.zhudantech.com/carplay/Public/attached/Banner/phpZp882536.jpeg");

        //说了的警告了今晚


        /**
         * 轮播图点击事件
         */
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.setImages(imgs).setImageLoader(new GlideImageLoader()).start();

        ll_dot = (LinearLayout) findViewById(R.id.ll_dot);
        subjectList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            subjectList.add(new Subject(titles[i], icons[i]));
        }
        //需要的页面数
        int pageCount = (int) Math.ceil(subjectList.size() * 1.0 / pageSize);
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            GridView gridView = (GridView) getLayoutInflater().inflate(R.layout.layout_grid_view, viewPager, false);
            gridView.setAdapter(new GridViewAdapter(this, subjectList, i, pageSize));
            viewList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + currentIndex * pageSize;
                    Toast.makeText(MainActivity.this, subjectList.get(pos).getName(), Toast.LENGTH_SHORT).show();
                    //TODO  跟换请求参数重新获取数据加入数组
                    if (isfirst) {
                        isfirst = false;
                        titles1 = new String[]{"司仪一号", "司仪二号", "司仪三号", "司仪一号", "谢霆锋", "李晓春", "谢霆锋", "司仪一号", "司仪一号"};
                        titles2 = new String[]{"司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春"};
                        titles3 = new String[]{"司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "司仪一号", "司仪一号"};
                        images = new Integer[]{
                                R.drawable.hun_icon_42, R.drawable.hun_icon_42,
                                R.drawable.hun_icon_42, R.drawable.hun_icon_42,
                                R.drawable.hun_icon_39, R.drawable.hun_icon_39,
                                R.drawable.hun_icon_42, R.drawable.hun_icon_42};

                        images2 = new Integer[]{
                                R.drawable.hun_icon_39, R.drawable.hun_icon_42,
                                R.drawable.hun_icon_45, R.drawable.hun_icon_51,
                                R.drawable.hun_icon_54, R.drawable.hun_icon_57
                        };

                        images3 = new Integer[]{
                                R.drawable.hun_icon_57, R.drawable.hun_icon_51,
                                R.drawable.hun_icon_57, R.drawable.hun_icon_51,
                                R.drawable.hun_icon_57, R.drawable.hun_icon_51,
                                R.drawable.hun_icon_57, R.drawable.hun_icon_51
                        };


                    } else {
                        isfirst = true;
                        titles1 = new String[]{"司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "司仪一号", "司仪一号"};
                        titles2 = new String[]{"司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春"};
                        titles3 = new String[]{"司仪一号", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "李晓春", "司仪一号", "司仪一号"};
                        images = new Integer[]{
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35};

                        images2 = new Integer[]{
                                R.drawable.hun_icon_39, R.drawable.hun_icon_39,
                                R.drawable.hun_icon_39, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_39, R.drawable.hun_icon_39
                        };

                        images3 = new Integer[]{
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35,
                                R.drawable.hun_icon_35, R.drawable.hun_icon_35
                        };
                    }


                    pictureAdapter = new PictureAdapter(titles1, images, MainActivity.this);
                    gridview1.setAdapter(pictureAdapter);

                    pictureAdapter2 = new PictureAdapter(titles2, images2, MainActivity.this);
                    gridview2.setAdapter(pictureAdapter2);

                    pictureAdapter3 = new PictureAdapter(titles3, images3, MainActivity.this);
                    gridview3.setAdapter(pictureAdapter3);


                }
            });
        }
        viewPager.setAdapter(new ViewPagerAdapter(viewList));
        for (int i = 0; i < pageCount; i++) {
            ll_dot.addView(getLayoutInflater().inflate(R.layout.view_dot, null));
        }
        //使第一个小圆点呈选中状态
        ll_dot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                ll_dot.getChildAt(currentIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                ll_dot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
                currentIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });


        pictureAdapter = new PictureAdapter(titles1, images, this);
        gridview1.setAdapter(pictureAdapter);

        pictureAdapter2 = new PictureAdapter(titles2, images2, this);
        gridview2.setAdapter(pictureAdapter2);

        pictureAdapter3 = new PictureAdapter(titles3, images3, this);
        gridview3.setAdapter(pictureAdapter3);

    }


    @Override
    public void onSegmentViewClick(View view, int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, "订单", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "抢单", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            Log.i("indeddd", "图片地址是：" + String.valueOf(path));
            Glide.with(context).load(String.valueOf(path)).into(imageView);
        }
    }


}
