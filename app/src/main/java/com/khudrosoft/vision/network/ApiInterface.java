package com.khudrosoft.vision.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import com.khudrosoft.vision.model.BirthDay;
import com.khudrosoft.vision.model.CampaignEntryPageResponse;
import com.khudrosoft.vision.model.EntryRx;
import com.khudrosoft.vision.model.GetCampaignResponse;
import com.khudrosoft.vision.model.MaxproEntryResponse;
import com.khudrosoft.vision.model.NoticeResponse;
import com.khudrosoft.vision.model.ProfileResponse;
import com.khudrosoft.vision.model.RxDetails;
import com.khudrosoft.vision.model.SignUpResponse;
import com.khudrosoft.vision.model.SimpleResponse;
import com.khudrosoft.vision.model.SubmitCampaignEntryPageResponse;
import com.khudrosoft.vision.model.SuggestedDoctorResponse;
import com.khudrosoft.vision.model.SuggestedProductResponse;
import com.khudrosoft.vision.model.TargetResponse;
import com.khudrosoft.vision.model.UpdatePassResponse;

/**
 * Created by Amir on 11/25/2016.
 */
public interface ApiInterface {


    //    http://rx-calculator.xyz/rxapi/public/api/v1/login
    @POST("/rxapi/public/api/v1/login")
    Call<LoginResponse> login(@Query("user_id") String user_name,
                              @Query("password") String password
    );

    @Multipart
    @POST("api?")
    Call<SignUpResponse> signup(@Part("user_name") RequestBody user_name,
                                @Part("name") RequestBody name,
                                @Part("password") RequestBody password,
                                @Part("action") RequestBody action,
                                @Part("email") RequestBody email,
                                @Part("phone") RequestBody phone,
                                @Part("icard") RequestBody icard
    );

    @POST("/rxapi/public/api/v1/home")
    Call<RxDetails> getUserRxDetails(@Query("user_id") String user_name,
                                     @Query("token") String token
    );


    @POST("/rxapi/public/api/v1/rxEntryForm")
    Call<EntryRx> getRxEntryDetails(@Query("user_id") String user_name,
                                    @Query("token") String token
    );


    @Multipart
    @POST("/rxapi/public/api/v1/rxEntryFormSubmit")
    Call<RxSubmitResponse> submitRxEntryDetails(@Query("user_id") String user_name,
                                                @Query("token") String token,
                                                @Query("doctor_id") String doctor_id,
                                                @Query("n_date") String n_date,
                                                @Query("product_1") String product_1,
                                                @Query("product_2") String product_2,
                                                @Query("product_3") String product_3,
                                                @Query("product_4") String product_4,
                                                @Query("product_5") String product_5,
                                                @Part MultipartBody.Part file
    );


    @POST("/rxapi/public/api/v1/doctBirtNoti")
    Call<BirthDay> getDoctorsBirthdayList(@Query("user_id") String user_name,
                                          @Query("token") String token
    );


    @POST("/rxapi/public/api/v1/profile")
    Call<ProfileResponse> getProfileDetails(@Query("user_id") String user_name,
                                            @Query("token") String token
    );

    @Multipart
    @POST("/rxapi/public/api/v1/updateProfile")
    Call<ProfileResponse> updateProfile(@Query("user_id") String user_name,
                                        @Query("token") String token,
                                        @Query("name") String name,										
                                        @Query("ffc") String ffc,
                                        @Query("pin_code") String pin_code,										
                                        @Query("email") String email,
                                        @Query("mobile_no") String mobile_no,
										@Query("depot") String depot,
                                        @Part MultipartBody.Part file
    );


    @POST("/rxapi/public/api/v1/notice")
    Call<NoticeResponse> getNoticeList(@Query("user_id") String user_name,
                                       @Query("token") String token
    );


    @POST("/rxapi/public/api/v1/campaignEntryForm")
    Call<CampaignEntryPageResponse> getCampaignPageEntryResponse(@Query("user_id") String user_name,
                                                                 @Query("token") String token,
                                                                 @Query("camp_id") String camp_id
    );

    @Multipart
    @POST("/rxapi/public/api/v1/campaignEntryFormSubmit")
    Call<SubmitCampaignEntryPageResponse> submitCampaignPageEntryResponse(
            @Query("user_id") String user_name,
            @Query("token") String token,
            @Query("doctor_id") String doctor_id,
            @Query("product_1") String product_1,
            @Query("product_2") String product_2,
            @Query("camp_id") String camp_id,
            @Part MultipartBody.Part file
    );

    @POST("/rxapi/public/api/v1/updatePassword")
    Call<UpdatePassResponse> updatePassword(
            @Query("user_id") String user_name,
            @Query("token") String token,
            @Query("new_password") String new_password,
            @Query("retype_password") String retype_password

    );

    @POST("/rxapi/public/api/v1/targetProfile")
    Call<TargetResponse> getTargetResponse(
            @Query("user_id") String user_name,
            @Query("token") String token,
            @Query("camp_id") String id



    );

    @POST("/rxapi/public/api/v1/suggestedProducts")
    Call<SuggestedProductResponse> getSuggestedProducts(
            @Query("user_id") String user_name,
            @Query("token") String token


    );

    @POST("/rxapi/public/api/v1/suggestedDoctors")
    Call<SuggestedDoctorResponse> getSuggestedDoctors(
            @Query("user_id") String user_name,
            @Query("token") String token


    );

    @POST("/rxapi/public/api/v1/maxProProducts")
    Call<MaxproEntryResponse> getMaxproData(
            @Query("user_id") String user_name,
            @Query("token") String token

    );

    @Multipart
    @POST("/rxapi/public/api/v1/maxProFormSubmit")
    Call<SimpleResponse> submitMaxProEntryResponse(
            @Query("user_id") String user_name,
            @Query("token") String token,
            @Query("doctor_id") String doctor_id,
            @Query("n_date") String n_date,
            @Query("product") String product,
            @Part MultipartBody.Part file
    );


    @POST("/rxapi/public/api/v1/getCampaignList")
    Call<GetCampaignResponse> getCampaignList(
            @Query("user_id") String user_name,
            @Query("token") String token

    );




}