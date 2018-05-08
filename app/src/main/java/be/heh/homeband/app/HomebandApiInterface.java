package be.heh.homeband.app;

/**
 * Created by christopher on 25/01/2018.
 */
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import be.heh.homeband.entities.Version;
import retrofit2.Call;
import retrofit2.http.*;

public interface HomebandApiInterface {

    @POST("api/sessions")
    @FormUrlEncoded
    Call<HomebandApiReponse> connexion(@Field("login") String login, @Field("mot_de_passe") String mot_de_passe, @Field("type") int type );

    @GET("api/styles")
    Call<HomebandApiReponse> getStyles() ;

    @GET("api/groupes")
    Call<HomebandApiReponse> getGroupes(@Query("styles")int styles, @Query("adresse")String adresse, @Query("rayon")int rayon );

    @GET("api/groupes/{id}")
    Call<HomebandApiReponse> getGroupe(@Path(value="id") int id ,@Query("membres")int get_membres);

    @GET("api/groupes/{id}/evenements/{idEvent}")
    Call<HomebandApiReponse> getGroupEvent(@Path(value="id") int id,@Path(value="idEvent") int idEvent );

    @GET("api/evenements/{id}")
    Call<HomebandApiReponse> getEvent(@Path(value="id") int id );

    @GET("api/evenements")
    Call<HomebandApiReponse> getEvenements(@Query("styles")int styles, @Query("adresse")String adresse, @Query("rayon")int rayon, @Query("date_debut")String du, @Query("date_fin")String au );

    @GET("api/versions")
    Call<HomebandApiReponse> getVersions(@Query("nomtable")String nomtable );

    @POST("api/versions/updates")
    Call<HomebandApiReponse> getAllVersions(@Body HashMap<String, Object> params);

    @GET("api/villes")
    Call<HomebandApiReponse> getVilles() ;

    @GET("api/localisations")
    Call<HomebandApiReponse> getLocalisations(@Query("type")int type, @Query("address")String address, @Query("lat")double lat, @Query("lon")double lon) ;

}

