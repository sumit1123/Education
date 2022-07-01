package com.example.education.repo;

import com.example.education.certificate.BuyExamResponse;
import com.example.education.certificate.ExamForm;
import com.example.education.repo.request.LoginRequest;
import com.example.education.repo.request.PaymentRequest;
import com.example.education.repo.request.RegisterRequest;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.ChapterResponse;
import com.example.education.response.CommonResponse;
import com.example.education.response.CoupanResponse;
import com.example.education.response.CourseResponse;
import com.example.education.response.InvoiceResonse;
import com.example.education.response.LoginResponse;
import com.example.education.response.MCQResponse;
import com.example.education.response.NotesResponse;
import com.example.education.response.RegisterResponse;
import com.example.education.response.SetResponse;
import com.example.education.response.SubjectResponse;
import com.example.education.response.VideoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {

    @Headers({"Accept: application/json"})
    @POST("loginapi.php")
    Call<List<LoginResponse>> doLoginApi(@Body LoginRequest loginRequest);

    @Headers({"Accept: application/json"})
    @POST("reg_api.php")
    Call<RegisterResponse> doRegisterApi(@Body RegisterRequest registerRequest);

    @Headers({"Accept: application/json"})
    @POST("courseapi.php")
    Call<List<CourseResponse>> doCourseApi();

    @Headers({"Accept: application/json"})
    @POST("examform.php")
    Call<CourseResponse> doExamFormApi(@Body ExamForm examForm);

    @Headers({"Accept: application/json"})
    @POST("mycourse.php")
    Call<List<CourseResponse>> doMyCourseApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("videoapi.php")
    Call<List<VideoResponse>> doVideoApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("subjectapi.php")
    Call<List<SubjectResponse>> doSubjectApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("pdfapi.php")
    Call<List<NotesResponse>> doNotesApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("sliderapi.php")
    Call<List<SubjectResponse>> doBannerApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("topicapi.php")
    Call<List<ChapterResponse>> doChapterApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("copan_api.php")
    Call<List<CoupanResponse>> doCoupanApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("purchaseinsert_api.php")
    Call<CommonResponse> doPurchaseApi(@Body PaymentRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("buyexamname.php")
    Call<List<BuyExamResponse>> doBuyExamNameeApi(@Body PaymentRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("exampurchasein.php")
    Call<CommonResponse> doPurchaseExamNameeApi(@Body PaymentRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("examnameapi.php")
    Call<List<SetResponse>> doSetApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("exammcqapi.php")
    Call<List<MCQResponse>> doMCqApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("profileapi.php")
    Call<List<LoginResponse>> doProfileApi(@Body LoginRequest loginRequest);

    @Headers({"Accept: application/json"})
    @POST("profileupdate.php")
    Call<LoginResponse> doUpdateProfileApi(@Body LoginRequest loginRequest);

    @Headers({"Accept: application/json"})
    @POST("invoiceapi.php")
    Call<List<InvoiceResonse>> doInvoiceApi(@Body LoginRequest loginRequest);

    @Headers({"Accept: application/json"})
    @POST("examresultapi.php")
    Call<List<MCQResponse>> doExamResultApi(@Body SubjectRequest subjectRequest);

    @Headers({"Accept: application/json"})
    @POST("buyexamstart.php")
    Call<List<BuyExamResponse>> doBuyExamStartApi(@Body PaymentRequest subjectRequest);

}


