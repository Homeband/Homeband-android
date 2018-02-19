package be.heh.homeband.app;

/**
 * Created by christopher on 25/01/2018.
 */
import retrofit2.Call;
import retrofit2.http.*;

public interface HomebandApiInterface {

    @POST("api/sessions")
    @FormUrlEncoded
    Call<HomebandApiReponse> connexion(@Field("login") String login, @Field("mot_de_passe") String mot_de_passe, @Field("type") int type );

    @GET("api/styles")
    Call<HomebandApiReponse> getStyles() ;

    @GET("api/evenements")
    Call<HomebandApiReponse> getEvenements() ;
}

