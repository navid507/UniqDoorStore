package com.sai.udstore.Main.Adapter;

/**
 * Created by navid on 4/4/2017.
 */

public class ProductsMoreAdapter{//} extends PagerAdapter {

//    private TextView subject;
//    private ImageView pic;
//    private JustifiedTextView content;
//    private Typeface vazir;
//    private int mImageThumbSize;
//    private List<Product> product;
//    private LayoutInflater inflater;
////    private Button sefaresh;
//    private Context context;
//    private EB_Preference prefrence;
//    private User userp;
//    private Products_moreActivity mc;
//
//    public ProductsMoreAdapter(Context c, List<Product> paths, Products_moreActivity mc) {
//        this.mc = mc;
//        this.product = paths;
//        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        this.context = c;
//    }
//
//    @Override
//    public int getCount() {
//        return this.product.size();
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        // View viewLayout
//
//        ((ViewPager) container).removeView((FrameLayout) object);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//
//        View rv = inflater.inflate(R.layout.activity_category_more, container, false);
//        final Product product = this.product.get(position);
//
//        prefrence = new EB_Preference(context);
//        userp = prefrence.User();
//
//        vazir = Typeface.createFromAsset(context.getAssets(), "font/vazir.ttf");
//        mImageThumbSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail);
//
//        subject = (TextView) rv.findViewById(R.id.apm_tv_products);
//        content = (JustifiedTextView) rv.findViewById(R.id.apm_tv_content_products);
//        pic = (ImageView) rv.findViewById(R.id.apm_img_products);
////        sefaresh = (Button) rv.findViewById(R.id.apm_btn_sefaresh_product);
//
//        content.setTypeFace(vazir);
////        sefaresh.setTypeface(vazir);
//        content.setLineSpacing(14);
//        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//
//        subject.setText(product.getName());
//        content.setText(product.getComment());
//
//        String pathUrl1 = Settings.Urls.imgproduct + product.getImage();
//        UF.loadandsave(context, Settings.Path.News, product.getImage(), pic, pathUrl1, mImageThumbSize,mImageThumbSize, R.drawable.logo, R.drawable.logo);
//
//        ((ViewPager) container).addView(rv);
//
////        sefaresh.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mc.doRegister(String.valueOf(product.getId()));
////            }
////        });
//
//        return rv;
//    }
//
//    @Override
//    public boolean isViewFromObject(View arg0, Object arg1) {
//        return arg0 == ((FrameLayout) arg1);
//    }
}