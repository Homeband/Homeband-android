package be.heh.homeband.app;

/**
 * Created by christopher on 25/01/2018.
 */
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface HomebandApi {
    @POST("sessions")
    Call<ResponseBody> connexion(@Field("login") String login, @Field("mot_de_passe") String mot_de_passe, @Field("type") int type );
}
