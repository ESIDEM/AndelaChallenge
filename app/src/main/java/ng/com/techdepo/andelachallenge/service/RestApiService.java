package ng.com.techdepo.andelachallenge.service;

import ng.com.techdepo.andelachallenge.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ESIDEM jnr on 3/5/2017.
 */

public interface RestApiService {

    @GET("/search/users")
    Call<UserList> getUserList(@Query("q") String filter);

}
