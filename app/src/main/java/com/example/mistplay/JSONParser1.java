package com.example.mistplay;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface JSONParser1 {

    @GET("posts")
    Call<List<Post>> getPosts();


}
