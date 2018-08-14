package com.sai.udstore.Main;

public class MessageActivity{//} extends AppCompatActivity {

//    private TextView title;
//    private TouchImageView pic_message;
//    private JustifiedTextView content;
//    private Typeface vazir;
//    private int mImageThumbSize;
//    private String message_title = "", message_content = "", message_pic = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            if (extras.containsKey("am_title_message")) {
//                message_title = extras.getString("am_title_message");
//            }
//            if (extras.containsKey("am_content_message")) {
//                message_content = extras.getString("am_content_message");
//            }
//            if (extras.containsKey("am_pic_message")) {
//                message_pic = extras.getString("am_pic_message");
//            }
//        }
//
//        vazir = Typeface.createFromAsset(getAssets(), "font/vazir.ttf");
//        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail);
//
//        title = (TextView) findViewById(R.id.am_tv_message);
//        pic_message = (TouchImageView) findViewById(R.id.am_img_message);
//        content = (JustifiedTextView) findViewById(R.id.am_tv_content_message);
//
//        content.setTypeFace(vazir);
//
//        content.setLineSpacing(14);
//        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//
//        title.setText(message_title);
//        content.setText(message_content);
//
//        String pathUrl1 = UF.getMessagePath(message_pic, mImageThumbSize, mImageThumbSize);
//        UF.loadandsave(this, Settings.Path.Message, message_pic, pic_message, pathUrl1, mImageThumbSize, mImageThumbSize, R.drawable.logo, R.drawable.logo);
//    }
}
