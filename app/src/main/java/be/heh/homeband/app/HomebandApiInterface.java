package be.heh.homeband.app;

/**
 * Created on 25/01/2018.
 */

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomebandApiInterface {

    @POST("api/sessions")
    @FormUrlEncoded
    Call<HomebandApiReponse> connexion(@HeaderMap Map<String, String> headers, @Field("login") String login, @Field("mot_de_passe") String mot_de_passe, @Field("type") int type );

    @GET("api/styles")
    Call<HomebandApiReponse> getStyles(@HeaderMap Map<String, String> headers) ;

    @GET("api/groupes")
    Call<HomebandApiReponse> getGroupes(@HeaderMap Map<String, String> headers, @Query("styles")int styles, @Query("adresse")String adresse, @Query("rayon")int rayon );

    @GET("api/groupes/{id}")
    Call<HomebandApiReponse> getGroupe(@HeaderMap Map<String, String> headers, @Path(value="id") int id ,@Query("membres")int get_membres);

    @GET("api/groupes/{id}/evenements")
    Call<HomebandApiReponse> getGroupEvents(@HeaderMap Map<String, String> headers, @Path(value="id") int id );

    @GET("api/evenements/{id}")
    Call<HomebandApiReponse> getEvent(@HeaderMap Map<String, String> headers, @Path(value="id") int id );

    @GET("api/evenements")
    Call<HomebandApiReponse> getEvenements(@HeaderMap Map<String, String> headers, @Query("styles")int styles, @Query("adresse")String adresse, @Query("rayon")int rayon, @Query("date_debut")String du, @Query("date_fin")String au, @Query("get_ville")int get_villes );

    @POST("api/versions/updates")
    Call<HomebandApiReponse> getAllVersions(@HeaderMap Map<String, String> headers, @Body HashMap<String, Object> params);

    @GET("api/villes")
    Call<HomebandApiReponse> getVilles(@HeaderMap Map<String, String> headers) ;

    @GET("api/localisations")
    Call<HomebandApiReponse> getLocalisations(@HeaderMap Map<String, String> headers, @Query("type")int type, @Query("address")String address, @Query("lat")double lat, @Query("lon")double lon) ;

    @GET("api/groupes/{id}/albums/{idAlbum}")
    Call<HomebandApiReponse> getAlbumDetails(@HeaderMap Map<String, String> headers, @Path(value="id") int id,@Path(value="idAlbum") int idAlbum) ;

    @GET("api/groupes/{id}/albums")
    Call<HomebandApiReponse> getAlbums(@HeaderMap Map<String, String> headers, @Path(value="id") int id ) ;

    @DELETE("api/utilisateurs/{id_utilisateur}/groupes/{id_groupe}")
    Call <HomebandApiReponse> removeUtilisateurGroupe(@HeaderMap Map<String, String> headers, @Path(value="id_utilisateur") int id_utilisateur,@Path(value="id_groupe") int id_groupe);

    @GET("api/groupes/{id}/avis")
    Call<HomebandApiReponse> getAvis(@HeaderMap Map<String, String> headers, @Path(value="id") int id_groupe,@Query(value="type")int type ) ;

    @POST("api/groupes/{id}/avis")
    Call<HomebandApiReponse> postAvis(@HeaderMap Map<String, String> headers, @Path(value="id") int id_groupe, @Body HashMap<String,Object> params) ;

    @POST("api/utilisateurs")
    Call<HomebandApiReponse> postUser(@HeaderMap Map<String, String> headers, @Body HashMap<String,Object> params) ;

    @POST("api/utilisateurs/forget")
    @FormUrlEncoded
    Call<HomebandApiReponse> postForget(@HeaderMap Map<String, String> headers, @Field("email") String email) ;

    @PUT("api/utilisateurs/{id}")
    Call<HomebandApiReponse> putSettings(@HeaderMap Map<String, String> headers, @Path(value="id") int id_utilisateur, @Body HashMap<String,Object> params ) ;

    @POST("api/utilisateurs/{id_utilisateur}/groupes/{id_groupe}")
    @FormUrlEncoded
    Call <HomebandApiReponse> addUtilisateurGroupe(@HeaderMap Map<String, String> headers, @Path(value="id_utilisateur") int id_utilisateur,@Path(value="id_groupe") int id_groupe,@Field("get_groupe") int get_groupe,@Field("get_membres") int get_membres,@Field("get_albums") int get_albums,@Field("get_titres") int get_titres );
}

