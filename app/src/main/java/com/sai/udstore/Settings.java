package com.sai.udstore;

public class Settings {

    //    public static List<Message> message = new ArrayList<>();
//    public static List<Message> message_notify = new ArrayList<>();
//    public static List<News> news = new ArrayList<>();
//    public static List<Product> product = new ArrayList<>();
//    public static List<Category> category = new ArrayList<>();
    public static final int HOUR = 13;
    public static final int MINUTE = 20;
    public static final int Second = 12;
    public static final int interval = 1000 * 60 * 20;
    public static final String DataBase = "udstore.db";
    public static String root_url = "http://ws.unidoor.co/UnidoorOperation.svc";
    public static int soft_id = 31;

    public class From {
        public static final int ProductList = 10;
        public static final int OfferList = 20;
    }

    public class Fragments {
        public static final int Home = 0;
        public static final int Product = 1;
        //        public static final int Message = 2;
        public static final int News = 3;
        public static final int Order = 4;
        public static final int AboutUs = 5;
        public static final int ContactUs = 6;
        public static final int Share = 7;
        public static final int Exit = 8;

        public static final int Invoice_State = 10;
        public static final int Invoice_Details = 11;
        public static final int Invoice_Goods = 12;

//        public static final int Category = 9;

        public static final int Team = 10;
        public static final int Profile = 11;
        public static final int TeamDetail = 12;
        public static final int Favorites = 13;
        public static final int Invoices = 14;
        public static final int Register = 15;
        public static final int Download = 16;


    }

    public static class Language {
        public static final int Persian = 1;
        public static final int English = 2;
        public static final int Arabic = 3;
        public static final int Russion = 4;
//        public static int Persian = 5;
    }

    public class Bool {
        public static final int TRUE = 1;
        public static final int FALSE = 0;
    }

    public class Result {
        public static final int Success = 0;
        public static final int Error = 1;
        public static final int NoAuth = 2;
    }

    public class Activity {
        public static final int Register = 20;
        public static final int Verify = 21;
        public static final int Register_alert = 22;
        public static final int Splash = 23;
        public static final int Products = 24;
        public static final int SignUp = 25;
        public static final int ProductList = 26;
        public static final int ProductDetails = 27;
        public static final int SendOrder = 28;
        public static final int EditOrder = 29;
        public static final int BuyProductActivity = 30;
    }

    public static boolean if_wifi_sw_checked = false;
    public static final int one_day_seconds = 5;// * 60 * 60;// * 1000;
    public static final int one_hour_seconds = 60 * 60;// * 1000;

    public class State_Available {
        public final static int Delete = 0;
        public final static int Exist = 1;
    }

    public class Register_State {
        public final static int Nothing = 0;
        public final static int Code = 1;
        public final static int Phone = 2;
        public final static int OK = 3;
    }

    public class Factor_State {
        public final static String allStr = " همه";
        public final static String registeredStr = "ثبت شده";
        public final static String seenStr = "مشاهده شده";
        public final static String acceptStr = "تایید شده";
        public final static String sentStr = "ارسال شده";
        public final static String recievedStr = "دریافت شده";
        public final static String rejectStr = "رد شده";
        public final static String expiredStr = "منقضی شده";

        public final static int notseen = 0;
        public final static int seen = 1;
        public final static int ok = 2;
        public final static int send = 3;

        public final static int recieved = 4;
        public final static int expired = -1;
        public final static int reject = -2;
    }

    public static String API_URL_T = "http://www.smartsteb.com/phone/";

    public static class Urls {

        public final static String API_URL = root_url + "/api/";
        public final static String Charge = "http://unidoor.co/epayment/epaymentmabnacharge?username=%s&amount=%s&ip=1";
        public final static String ENamad  = "https://trustseal.enamad.ir/Verify.aspx?id=67328&p=9gRT3ZbL31xYeqeB";
        public final static String Login = API_URL + "user/Login";
        //        public final static String News = API_URL + "news.php";
        public final static String Offers = API_URL + "offers.php";
        public final static String Category = API_URL + "cats.php";
        public final static String ProductOfCat = API_URL + "product/productList";
        public final static String GetAllProducts = API_URL + "product/productList";
        public final static String GetAllDownload = API_URL + "files/uploadlist";
        public final static String GetNews = API_URL + "slide/slideListAll";
        //        public final static String Favorite = API_URL + "favorite.php";
//        public final static String FavoriteList = API_URL + "favorite_list.php";
        public final static String RegOrder = API_URL + "sale/factor";
        public final static String GetOrders = API_URL + "sale/FactorList";
        public final static String get_orders_details = API_URL + "get_orders_details.php";
//        public final static String favorite_list = API_URL + "favorite_list.php";


        public final static String SignUp = root_url + "phone/reg_buyer.php?softid=" + soft_id + "&firstName=%s&lastName=%s&address=%s" +
                "&phone=%s&mobile=%s&deviceId=%s&user=%s&pass=%s";
        public final static String Verify = root_url + "phone/verifyCode.php";
    }

    public class Jsons {
        public final static String lastTime = "newdate";
        public static final String error = "ErrorCode";

        public class Verify {
            public static final String error = "error";
            public static final String device_id = "device_id";
            public static final String user_id = "user_id";
            public static final String private_key = "private";
            public static final String phone = "phone";
            public static final String code = "code";
            public static final String uniq = "uniq";
        }

        public class Login {
            public static final String error = "error";
            public static final String device_id = "device_id";
            public static final String user_id = "user_id";
            public static final String private_key = "private";
            public static final String username = "username";
            public static final String password = "password";
            public static final String uniq = "uniq";


            public static final String UserInfo = "UserInfo";
            public static final String ActiveCode = "ActiveCode";
            public static final String DisplayName = "DisplayName";
            public static final String Email = "Email";
            public static final String IP = "IP";
            public static final String Mobile = "Mobile";
            public static final String PicContentType = "PicContentType";
            public static final String UserType = "UserType";
            public static final String Username = "Username";
            public static final String discount = "discount";
            public static final String issuer = "issuer";
            public static final String lasteditip = "lasteditip";
            public static final String remark = "remark";
            public static final String lasteditor = "lasteditor";
            public static final String settlement = "settlement";

        }

        public class Products_of_Cat {
            public static final String array = "products";
            public static final String offers = "offers";
            public static final String favorite = "favorite";
            public static final String id = "id";
            public static final String name = "title";
            public static final String comment = "desc";
            public static final String image = "img";
            public static final String price = "price";
            public static final String dprice = "dprice";
            public static final String fav = "fav";
            public static final String tax1 = "tax1";
            public static final String tax2 = "tax2";
            public static final String unit = "unit";
            public static final String brand = "brand";
            public static final String state = "state";
            public static final String parent = "parent";
            public static final String code = "num";
        }

        public class Categories {
            public static final String array = "cats";
            public static final String id = "id";
            public static final String name = "title";
            public static final String pid = "parent";
            public static final String image = "img";
            public static final String state = "state";
        }

        public class News {
            public static final String array = "news";
            public static final String id = "news_id";
            public static final String subject = "news_subject";
            public static final String content = "news_content";
            public static final String priority = "news_priority";
            public static final String image = "news_image";
            public static final String state = "news_state";
        }

        public class Message {
            public static final String array = "msg";
            public static final String id = "msg_id";
            public static final String subject = "msg_subject";
            public static final String content = "msg_content";
            public static final String image = "msg_image";
            public static final String state = "msg_state";
        }

        public class Register {
            public static final String code = "code";
            public static final String id = "id";
        }

        public class SignUp {
            public static final String id = "id";
        }

        public class Certificate {
            public static final String array = "certify";
            public static final String id = "certify_id";
            public static final String desc = "certify_desc";
            public static final String prio = "certify_priority";
            public static final String state = "certify_state";
            public static final String stylist_id = "stylist_id";
        }

        public class Team {
            public static final String array = "stylist";
            public static final String id = "stylist_id";
            public static final String name = "stylist_name";
            public static final String desc = "stylist_desc";
            public static final String prio = "stylist_priority";
            public static final String image = "stylist_image";
            public static final String state = "stylist_state";
        }
    }

    public static class Path {
        //        public final static String News = "/mobile_catalog/news/";
//        public final static String Message = "/mobile_catalog/message/";
//        public final static String Product = "/mobile_catalog/product/";
//        public final static String Category = "/mobile_catalog/category/";
//
        public final static String News = "news";
        public final static String Message = "message";
        public final static String Product = "product";
        public final static String Category = "category";
        public final static String Team = "team";
        public final static String Certificate = "certificate";
    }
}
