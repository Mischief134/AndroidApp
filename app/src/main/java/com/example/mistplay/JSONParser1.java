package com.example.mistplay;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface JSONParser1 {

    @GET("search")
    Call<List<Post>> getPosts();


}
