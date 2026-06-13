package com.example.dbframework.support


import static com.example.dbframework.support.RequestSpecFactory.config

 class EnvironmentalVariables {
     static final String TOKEN_URL_CLIENT_ID = config("OAUTH_CLIENT_ID", "retail-ops-client");
     static final String TOKEN_URL_CLIENT_SECRET = config("OAUTH_CLIENT_SECRET", "2a2729b27b47fe27b6412403d886ef4781bbff36b0e2b58e");
      static final String VIEWER_CLIENT_ID = config("OAUTH_VIEWER_CLIENT_ID", "retail-viewer-client");
      static final String VIEWER_CLIENT_SECRET = config("OAUTH_VIEWER_CLIENT_SECRET", "241354ac6e2b75796df4eea67c3681ee520fed7d1d78c7fb");
      static final String EXPIRED_CLIENT_ID = config("OAUTH_EXPIRED_CLIENT_ID", "retail-expired-client");
      static final String EXPIRED_CLIENT_SECRET = config("OAUTH_EXPIRED_CLIENT_SECRET", "851d19d4df703b868ae8fc71d0143410670272630553ae21");
      static final String API_KEY = config("RETAIL_API_KEY", "4bd53b7820525db94f8f34d9316bb35ee1e2ab9fe80e4c3f");
      static final String invalid_token="token";
      static final String invalid_Id = 5644;
      static final String existing_id=5077;

     static var Order = Map.of("items", List.of(103, 112), "currency", "INR");
     static String client_secret(){
        return TOKEN_URL_CLIENT_SECRET;
    }

     static Map<String,String> getOrder() {
         return Order
     }

     static  String client() {
        return TOKEN_URL_CLIENT_ID;
    }

     static String getVIEWER_CLIENT_ID() {
         return VIEWER_CLIENT_ID
     }

     static String getVIEWER_CLIENT_SECRET() {
         return VIEWER_CLIENT_SECRET
     }

     static String getEXPIRED_CLIENT_ID() {
         return EXPIRED_CLIENT_ID
     }

     static String getEXPIRED_CLIENT_SECRET() {
         return EXPIRED_CLIENT_SECRET
     }

     static String getAPI_KEY() {
         return API_KEY
     }

     static String getInvalid_token() {
         return invalid_token
     }
 }
