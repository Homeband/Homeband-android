package be.heh.homeband.app;

/**
 * Created by christopher on 25/01/2018.
 */
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

    @GET("api/evenements")
    Call<HomebandApiReponse> getEvenements(@Query("styles")int styles, @Query("cp")String cp, @Query("rayon")int rayon, @Query("lat")int lat,@Query("lon")int lon,@Query("date_debut")int du,@Query("fate_fin")int au ) ;

    @GET("api/versions")
    Call<HomebandApiReponse> getVersions(@Query("nomtable")String nomtable );

    @POST("api/versions/updates")
    @FormUrlEncoded
    Call<HomebandApiReponse> getAllVersions(@Field("versions[]")List<Version> versions );

    @GET("api/villes")
    Call<HomebandApiReponse> getVilles() ;

    @GET("api/localisations")
    Call<HomebandApiReponse> getLocalisations(@Query("type")int type, @Query("address")String address, @Query("lat")double lat, @Query("lon")double lon) ;

}

