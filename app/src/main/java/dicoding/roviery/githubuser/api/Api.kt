package dicoding.roviery.githubuser.api

import dicoding.roviery.githubuser.data.DetailUserResponse
import dicoding.roviery.githubuser.data.User
import dicoding.roviery.githubuser.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}

